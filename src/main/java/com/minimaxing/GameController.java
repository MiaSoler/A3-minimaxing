import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

public class GameController {

    private GridPane root;
    private Board board;
    private AI ai;

    public GameController() {
        root = new GridPane();
        board = new Board();
        ai = new AI();

        drawBoard();
    }

    public GridPane getRoot() {
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

                root.add(cell, col, row);
            }
        }
    }

    private void handleMove(int col) {

        if (!board.isValidMove(col)) return;

        board.dropPiece(col, 1); 
        updateUI();

        if (board.checkWin(1)) {
            System.out.println("You win!");
            return;
        }

        int aiMove = ai.getBestMove(board, 4);
        board.dropPiece(aiMove, 2);

        updateUI();

        if (board.checkWin(2)) {
            System.out.println("AI wins!");
        }
    }

    private void updateUI() {
        root.getChildren().clear();

        for (int col = 0; col < Board.COLS; col++) {
            for (int row = 0; row < Board.ROWS; row++) {

                int value = board.getGrid()[row][col];

                Circle circle = new Circle(40);

                if (value == 1) circle.setFill(Color.WHITE);
                else if (value == 2) circle.setFill(Color.BLACK);
                else circle.setFill(Color.LIGHTGRAY);

                StackPane cell = new StackPane(circle);
                int cellCol = col;

                cell.setOnMouseClicked(e -> handleMove(circleCol));

                root.add(cell, col, row);
            }
        }
    }
}