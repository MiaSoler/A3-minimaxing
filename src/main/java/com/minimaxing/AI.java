package com.minimaxing;

import java.util.*;

public class AI {

    private static final int AI_PLAYER = 2;
    private static final int HUMAN = 1;

    public int getBestMove(Board board, int depth) {
        return minimax(board, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true)[0];
    }

    private int[] minimax(Board board, int depth, int alpha, int beta, boolean maximizing) {

        List<Integer> validMoves = getValidMoves(board);

        if (depth == 0 || board.checkWin(AI_PLAYER) || board.checkWin(HUMAN) || validMoves.isEmpty()) {
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
                temp.dropPiece(col, HUMAN);

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
        for (int c = 0; c < Board.COLS; c++) {
            if (board.isValidMove(c)) moves.add(c);
        }
        return moves;
    }

    private int evaluate(Board board) {
        // simple heuristic (you can improve later)
        return 0;
    }
}