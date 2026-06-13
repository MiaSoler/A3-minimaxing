package com.minimaxing;

// Handles the game board
public class Board {

    // Board size
    public static final int ROWS = 6;
    public static final int COLS = 7;

    // 2D array representing the board
    private int[][] grid;

    // Creates an empty board
    public Board() {

        grid = new int[ROWS][COLS];
    }

    // Returns the board grid
    public int[][] getGrid() {

        return grid;
    }

    // Checks if a column still has space
    public boolean isValidMove(int col) {

        return grid[0][col] == 0;
    }

    // Drops a piece into the selected column and returns row
    public int dropPiece(int col, int piece) {

        // Start from bottom row because of gravity
        for (int row = ROWS - 1; row >= 0; row--) {

            // Find first empty space
            if (grid[row][col] == 0) {

                // Place piece
                grid[row][col] = piece;

                return row;
            }
        }

        return -1;
    }

    // Checks if a player has 4 connected pieces
    public boolean checkWin(int piece) {
        //horitzontal
        for (int row = 0; row < ROWS; row++) {

            for (int col = 0; col < COLS - 3; col++) {

                if (
                    grid[row][col] == piece &&
                    grid[row][col + 1] == piece &&
                    grid[row][col + 2] == piece &&
                    grid[row][col + 3] == piece
                ) {
                    return true;
                }
            }
        }
        //Vertical
        for (int col = 0; col < COLS; col++) {

            for (int row = 0; row < ROWS - 3; row++) {

                if (
                    grid[row][col] == piece &&
                    grid[row + 1][col] == piece &&
                    grid[row + 2][col] == piece &&
                    grid[row + 3][col] == piece
                ) {
                    return true;
                }
            }
        }
        //Diagonals
        for (int row = 0; row < ROWS - 3; row++) {

            for (int col = 0; col < COLS - 3; col++) {

                if (
                    grid[row][col] == piece &&
                    grid[row + 1][col + 1] == piece &&
                    grid[row + 2][col + 2] == piece &&
                    grid[row + 3][col + 3] == piece
                ) {
                    return true;
                }
            }
        }
        for (int row = 3; row < ROWS; row++) {

            for (int col = 0; col < COLS - 3; col++) {

                if (
                    grid[row][col] == piece &&
                    grid[row - 1][col + 1] == piece &&
                    grid[row - 2][col + 2] == piece &&
                    grid[row - 3][col + 3] == piece
                ) {
                    return true;
                }
            }
        }

        return false;
    }

    // Checks if the board is completely full
    public boolean isFull() {

        for (int col = 0; col < COLS; col++) {

            if (grid[0][col] == 0) {
                return false;
            }
        }

        return true;
    }

    // Creates a copy of the board
    // Used by minimax to simulate moves
    public Board copy() {

        Board newBoard = new Board();

        for (int row = 0; row < ROWS; row++) {

            System.arraycopy(
                this.grid[row],
                0,
                newBoard.grid[row],
                0,
                COLS
            );
        }

        return newBoard;
    }
}