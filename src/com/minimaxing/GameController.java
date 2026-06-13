package com.minimaxing;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

// Handles the game UI and player interaction
public class GameController {

    // Main layout
    private BorderPane root;

    // Grid used to display the board
    private GridPane boardPane;

    // Shows messages like "Your turn" or "AI wins"
    private Label statusLabel;

    // Button used to restart the game
    private Button resetButton;

    // Game logic
    private Board board;

    // AI logic
    private AI ai;

    // End state
    private boolean gameOver = false;

    public GameController() {

        // Create board and AI
        board = new Board();
        ai = new AI();

        // Main layout
        root = new BorderPane();

        // Grid 
        boardPane = new GridPane();

        // Label shown at the top
        statusLabel = new Label("Your turn!");
        statusLabel.setStyle("-fx-font-size: 20px;");

        // Reset button
        resetButton = new Button("Play Again");

        // Restart game when button is clicked
        resetButton.setOnAction(e -> resetGame());

        // Top section with message + reset button
        HBox topBar = new HBox(20, statusLabel, resetButton);
        topBar.setAlignment(Pos.CENTER);

        // Add UI sections to the window
        root.setTop(topBar);
        root.setCenter(boardPane);

        // Draw the board
        updateUI();
    }

    // Returns the main layout
    public BorderPane getRoot() {
        return root;
    }

    // Draws an empty board
    private void drawBoard() {

        root.getChildren().clear();

        for (int col = 0; col < Board.COLS; col++) {

            for (int row = 0; row < Board.ROWS; row++) {

                Circle circle = new Circle(40);

                // Empty spaces are gray
                circle.setFill(Color.LIGHTGRAY);

                StackPane cell = new StackPane(circle);

                int circleCol = col;

                // Handle human player click
                cell.setOnMouseClicked(e -> handleMove(circleCol));

                boardPane.add(cell, col, row);
            }
        }
    }

    // Handles human player and AI moves
    private void handleMove(int col) {

        if (gameOver || !board.isValidMove(col)) {
            return;
        }

        board.dropPiece(col, 1);

        // Refresh board graphics
        updateUI();

        // Check if player wins
        if (board.checkWin(1)) {

            statusLabel.setText("You win!");

            gameOver = true;

            return;
        }

        // Check draw
        if (board.isFull()) {

            statusLabel.setText("Draw!");

            gameOver = true;

            return;
        }

        statusLabel.setText("AI thinking...");

        // Get best move from minimax
        int aiMove = ai.getBestMove(board, 4);

        // Place AI piece
        board.dropPiece(aiMove, 2);

        updateUI();

        // Check AI win
        if (board.checkWin(2)) {

            statusLabel.setText("AI wins!");

            gameOver = true;

            return;
        }

        // Check draw again
        if (board.isFull()) {

            statusLabel.setText("Draw!");

            gameOver = true;

            return;
        }

        // Continue game
        statusLabel.setText("Your turn!");
    }

    // Updates the board graphics
    private void updateUI() {

        // Clear old board
        boardPane.getChildren().clear();

        for (int col = 0; col < Board.COLS; col++) {

            for (int row = 0; row < Board.ROWS; row++) {

                // Get current cell value
                int value = board.getGrid()[row][col];

                Circle circle = new Circle(40);

                // White = player
                if (value == 1) {
                    circle.setFill(Color.WHITE);

                }

                // Black = AI
                else if (value == 2) {
                    circle.setFill(Color.BLACK);

                }

                // Empty space
                else {
                    circle.setFill(Color.LIGHTGRAY);
                }

                // Add border around circles
                circle.setStroke(Color.BLACK);

                StackPane cell = new StackPane(circle);

                int currentCol = col;

                // Handle clicks
                cell.setOnMouseClicked(e -> handleMove(currentCol));

                // Add circle to board
                boardPane.add(cell, col, row);
            }
        }
    }

    // Resets the game
    private void resetGame() {
        // Create new board
        board = new Board();

        // Enable moves again
        gameOver = false;

        // Reset message
        statusLabel.setText("Your turn!");

        // Redraw board
        updateUI();
    }
}