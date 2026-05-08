package com.minimaxing;

public class Board {

    public static final int ROWS = 6;
    public static final int COLS = 7;

    private int[][] grid;

    public Board() {
        grid = new int[ROWS][COLS];
    }

    public int[][] getGrid() {
        return grid;
    }

    public boolean isValidMove(int col) {
        return grid[0][col] == 0;
    }

    public int dropPiece(int col, int piece) {
        for (int row = ROWS - 1; row >= 0; row --) {
            if (grid[row][col] == 0) {
                grid[row][col] = piece;
                return row;
            }
        }
        return -1;
    }

    public boolean checkWin(int piece) {
        // 🔹 Horizontal →
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS - 3; col++) {
                if (grid[row][col] == piece &&
                    grid[row][col + 1] == piece &&
                    grid[row][col + 2] == piece &&
                    grid[row][col + 3] == piece) {
                    return true;
                }
            }
        }

        // 🔹 Vertical ↓
        for (int col = 0; col < COLS; col++) {
            for (int row = 0; row < ROWS - 3; row++) {
                if (grid[row][col] == piece &&
                    grid[row + 1][col] == piece &&
                    grid[row + 2][col] == piece &&
                    grid[row + 3][col] == piece) {
                    return true;
                }
            }
        }

        // 🔹 Diagonal ↘ (top-left → bottom-right)
        for (int row = 0; row < ROWS - 3; row++) {
            for (int col = 0; col < COLS - 3; col++) {
                if (grid[row][col] == piece &&
                    grid[row + 1][col + 1] == piece &&
                    grid[row + 2][col + 2] == piece &&
                    grid[row + 3][col + 3] == piece) {
                    return true;
                }
            }
        }

        // 🔹 Diagonal ↗ (bottom-left → top-right)
        for (int row = 3; row < ROWS; row++) {
            for (int col = 0; col < COLS - 3; col++) {
                if (grid[row][col] == piece &&
                    grid[row - 1][col + 1] == piece &&
                    grid[row - 2][col + 2] == piece &&
                    grid[row - 3][col + 3] == piece) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isFull() {
        for (int col = 0; col < COLS; col++) {
            if (grid[0][col] == 0) return false;
        }
        return true;
    }

    public Board copy() {
        Board newBoard = new Board();
        for (int row= 0; row < ROWS; row++) {
            System.arraycopy(this.grid[row], 0, newBoard.grid[row], 0, COLS);
        }
        return newBoard;
    }
}