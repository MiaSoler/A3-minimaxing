package com.minimaxing;

import java.util.ArrayList;
import java.util.List;

// Handles the AI logic using minimax with alpha-beta pruning
public class AI {

    // AI = black circle
    private static final int AI_PLAYER = 2;

    // Human = white circle
    private static final int HUMAN_PLAYER = 1;

    // Returns the best column for the AI to play
    public int getBestMove(Board board, int depth) {
        return minimax(
            board,
            depth,
            Integer.MIN_VALUE,
            Integer.MAX_VALUE,
            true
        )[0];
    }

    // maximizing = AI turn
    // minimizing = human turn
    private int[] minimax(Board board, int depth, int alpha, int beta, boolean maximizing) {

        // Get all playable columns
        List<Integer> validMoves = getValidMoves(board);

        // Stop searching for the following cases
        if (
            depth == 0 ||
            board.checkWin(AI_PLAYER) ||
            board.checkWin(HUMAN_PLAYER) ||
            validMoves.isEmpty()
        ) {
            return new int[]{-1, evaluate(board)};
        }

        // Default move
        int bestCol = validMoves.get(0);

        if (maximizing) {

            // Start with the worst possible score
            int maxEval = Integer.MIN_VALUE;

            for (int col : validMoves) {

                // Copy the board so the real board is not modified
                Board temp = board.copy();

                // Simulate AI move
                temp.dropPiece(col, AI_PLAYER);

                // Continue searching deeper
                int eval = minimax(
                    temp,
                    depth - 1,
                    alpha,
                    beta,
                    false
                )[1];

                // Keep best score and column
                if (eval > maxEval) {
                    maxEval = eval;
                    bestCol = col;
                }

                // Update alpha value
                alpha = Math.max(alpha, eval);

                // Stop searching this branch if it cannot improve
                if (alpha >= beta) {
                    break;
                }
            }

            return new int[]{bestCol, maxEval};
        }
        // Human turn
        else {

            // Start with the highest possible score
            int minEval = Integer.MAX_VALUE;

            for (int col : validMoves) {

                // Copy board
                Board temp = board.copy();

                // Simulate human move
                temp.dropPiece(col, HUMAN_PLAYER);

                // Continue searching deeper
                int eval = minimax(
                    temp,
                    depth - 1,
                    alpha,
                    beta,
                    true
                )[1];

                // Keep lowest score
                if (eval < minEval) {
                    minEval = eval;
                    bestCol = col;
                }

                // Update beta value
                beta = Math.min(beta, eval);

                // Alpha-beta pruning
                if (alpha >= beta) {
                    break;
                }
            }
            return new int[]{bestCol, minEval};
        }
    }

    // Returns all columns that are still playable
    private List<Integer> getValidMoves(Board board) {

        List<Integer> moves = new ArrayList<>();

        for (int col = 0; col < Board.COLS; col++) {

            if (board.isValidMove(col)) {
                moves.add(col);
            }
        }

        return moves;
    }

    // Evaluates how good the current board is for the AI
    private int evaluate(Board board) {

        int score = 0;

        int[][] grid = board.getGrid();

        // Decided that center gives more oportunities
        int centerCol = Board.COLS / 2;

        int centerCount = 0;

        for (int row = 0; row < Board.ROWS; row++) {

            if (grid[row][centerCol] == AI_PLAYER) {
                centerCount++;
            }
        }

        score += centerCount * 6;

        // Lets try every possible combination
        // Horitzontal
        for (int row = 0; row < Board.ROWS; row++) {

            for (int col = 0; col < Board.COLS - 3; col++) {

                int[] window = {
                    grid[row][col],
                    grid[row][col + 1],
                    grid[row][col + 2],
                    grid[row][col + 3]
                };

                score += evaluateWindow(window);
            }
        }

        // Vertical
        for (int col = 0; col < Board.COLS; col++) {

            for (int row = 0; row < Board.ROWS - 3; row++) {

                int[] window = {
                    grid[row][col],
                    grid[row + 1][col],
                    grid[row + 2][col],
                    grid[row + 3][col]
                };

                score += evaluateWindow(window);
            }
        }

        // Diagonals
        for (int row = 0; row < Board.ROWS - 3; row++) {

            for (int col = 0; col < Board.COLS - 3; col++) {

                int[] window = {
                    grid[row][col],
                    grid[row + 1][col + 1],
                    grid[row + 2][col + 2],
                    grid[row + 3][col + 3]
                };

                score += evaluateWindow(window);
            }
        }

        for (int row = 3; row < Board.ROWS; row++) {

            for (int col = 0; col < Board.COLS - 3; col++) {

                int[] window = {
                    grid[row][col],
                    grid[row - 1][col + 1],
                    grid[row - 2][col + 2],
                    grid[row - 3][col + 3]
                };

                score += evaluateWindow(window);
            }
        }

        return score;
    }

    // Scores a group of 4 cells
    // use high scores to see the diference
    private int evaluateWindow(int[] window) {

        int score = 0;

        int aiCount = 0;
        int humanCount = 0;
        int emptyCount = 0;

        // Count AI, human and empty cells
        for (int cell : window) {

            if (cell == AI_PLAYER) {
                aiCount++;
            }
            else if (cell == HUMAN_PLAYER) {
                humanCount++;
            }
            else {
                emptyCount++;
            }
        }

        // Reward good AI positions
        if (aiCount == 4) {
            score += 100000;
        }
        else if (aiCount == 3 && emptyCount == 1) {
            score += 100;
        }
        else if (aiCount == 2 && emptyCount == 2) {
            score += 10;
        }

        // Penalize dangerous human positions
        if (humanCount == 3 && emptyCount == 1) {
            score -= 80;
        }

        // Human win
        if (humanCount == 4) {
            score -= 100000;
        }

        return score;
    }
}