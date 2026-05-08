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
public class GameController {

    private BorderPane root;
    private GridPane boardPane;
    
    private Label statusLabel;
    private Button resetButton;
    
    private Board board;
    private AI ai;
    
    private boolean gameOver = false;

    public GameController() {

        board = new Board();
        ai = new AI();
    
        root = new BorderPane();
        boardPane = new GridPane();
    
        statusLabel = new Label("Your turn!");
        statusLabel.setStyle("-fx-font-size: 20px;");
    
        resetButton = new Button("Play Again");
    
        resetButton.setOnAction(e -> resetGame());
    
        HBox topBar = new HBox(20, statusLabel, resetButton);
        topBar.setAlignment(Pos.CENTER);
    
        root.setTop(topBar);
        root.setCenter(boardPane);
    
        updateUI();
    }

    public BorderPane getRoot() {
        return root;
    }

    private void drawBoard() {
        root.getChildren().clear();

        for (int col = 0; col < Board.COLS; col++) {
            for (int row = 0; row < Board.ROWS; row++) {

                Circle circle = new Circle(40);
                circle.setFill(Color.LIGHTGRAY);

                StackPane cell = new StackPane(circle);
                int circleCol = col;

                cell.setOnMouseClicked(e -> handleMove(circleCol));

                boardPane.add(cell, col, row);
            }
        }
    }

    private void handleMove(int col) {

        if (gameOver || !board.isValidMove(col)) {
            return;
        }
    
        // PLAYER MOVE
        board.dropPiece(col, 1);
    
        updateUI();
    
        if (board.checkWin(1)) {
            statusLabel.setText("🎉 You win!");
            gameOver = true;
            return;
        }
    
        if (board.isFull()) {
            statusLabel.setText("Draw!");
            gameOver = true;
            return;
        }
    
        // AI MOVE
        statusLabel.setText("AI thinking...");
    
        int aiMove = ai.getBestMove(board, 4);
    
        board.dropPiece(aiMove, 2);
    
        updateUI();
    
        if (board.checkWin(2)) {
            statusLabel.setText("🤖 AI wins!");
            gameOver = true;
            return;
        }
    
        if (board.isFull()) {
            statusLabel.setText("Draw!");
            gameOver = true;
            return;
        }
    
        statusLabel.setText("Your turn!");
    }

    private void updateUI() {

        boardPane.getChildren().clear();
    
        for (int col = 0; col < Board.COLS; col++) {
    
            for (int row = 0; row < Board.ROWS; row++) {
    
                int value = board.getGrid()[row][col];
    
                Circle circle = new Circle(40);
    
                if (value == 1) {
                    circle.setFill(Color.WHITE);
                } else if (value == 2) {
                    circle.setFill(Color.BLACK);
                } else {
                    circle.setFill(Color.LIGHTGRAY);
                }
    
                circle.setStroke(Color.BLACK);
    
                StackPane cell = new StackPane(circle);
    
                int currentCol = col;
    
                cell.setOnMouseClicked(e -> handleMove(currentCol));
    
                boardPane.add(cell, col, row);
            }
        }
    }

    private void resetGame() {

        board = new Board();
    
        gameOver = false;
    
        statusLabel.setText("Your turn!");
    
        updateUI();
    }
}