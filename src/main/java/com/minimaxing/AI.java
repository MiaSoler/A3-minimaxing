package com.minimaxing;

import java.util.ArrayList;
import java.util.List;

public class AI {

    private static final int AI_PLAYER = 2;
    private static final int HUMAN_PLAYER = 1;

    public int getBestMove(Board board, int depth) {
        return minimax(board, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true)[0];
    }

    private int[] minimax(Board board, int depth, int alpha, int beta, boolean maximizing) {

        List<Integer> validMoves = getValidMoves(board);

        if (depth == 0 || board.checkWin(AI_PLAYER) || board.checkWin(HUMAN_PLAYER) || validMoves.isEmpty()) {
            return new int[]{-1, evaluate(board)};
        }

        int bestCol = validMoves.get(0);

        if (maximizing) {
            int maxEval = Integer.MIN_VALUE;

            for (int col : validMoves) {
                Board temp = board.copy();
                temp.dropPiece(col, AI_PLAYER);

                int eval = minimax(temp, depth - 1, alpha, beta, false)[1];

                if (eval > maxEval) {
                    maxEval = eval;
                    bestCol = col;
                }

                alpha = Math.max(alpha, eval);
                if (alpha >= beta) break;
            }

            return new int[]{bestCol, maxEval};

        } else {
            int minEval = Integer.MAX_VALUE;

            for (int col : validMoves) {
                Board temp = board.copy();
                temp.dropPiece(col, HUMAN_PLAYER);

                int eval = minimax(temp, depth - 1, alpha, beta, true)[1];

                if (eval < minEval) {
                    minEval = eval;
                    bestCol = col;
                }

                beta = Math.min(beta, eval);
                if (alpha >= beta) break;
            }

            return new int[]{bestCol, minEval};
        }
    }

    private List<Integer> getValidMoves(Board board) {
        List<Integer> moves = new ArrayList<>();
        for (int col = 0; col < Board.COLS; col++) {
            if (board.isValidMove(col)) moves.add(col);
        }
        return moves;
    }

    private int evaluate(Board board) {

        int score = 0;
    
        int[][] grid = board.getGrid();
    
        // =========================
        // CENTER COLUMN PREFERENCE
        // =========================
        int centerCol = Board.COLS / 2;
    
        int centerCount = 0;
    
        for (int row = 0; row < Board.ROWS; row++) {
            if (grid[row][centerCol] == AI_PLAYER) {
                centerCount++;
            }
        }
    
        score += centerCount * 6;
    
        // =========================
        // HORIZONTAL
        // =========================
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
    
        // =========================
        // VERTICAL
        // =========================
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
    
        // =========================
        // DIAGONAL ↘
        // =========================
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
    
        // =========================
        // DIAGONAL
        // =========================
        for (int row= 3; row < Board.ROWS; row++) {
    
            for (int col= 0; col< Board.COLS - 3; col++) {
    
                int[] window = {
                    grid[row] [col],
                    grid[row - 1] [col + 1],
                    grid[row - 2] [col + 2],
                    grid[row - 3] [col + 3]
                };
    
                score += evaluateWindow(window);
            }
        }
    
        return score;
    }

    private int evaluateWindow(int[] window) {

        int score = 0;
    
        int aiCount = 0;
        int humanCount = 0;
        int emptyCount = 0;
    
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
    
        // AI GOOD POSITIONS
        if (aiCount == 4) {
            score += 100000;
        }
        else if (aiCount == 3 && emptyCount == 1) {
            score += 100;
        }
        else if (aiCount == 2 && emptyCount == 2) {
            score += 10;
        }
    
        // BLOCK HUMAN
        if (humanCount == 3 && emptyCount == 1) {
            score -= 80;
        }
    
        if (humanCount == 4) {
            score -= 100000;
        }
    
        return score;
    }
}