package org.example.game;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class SOSGameController {

    @FXML private GridPane gameBoard;
    @FXML private Label statusLabel;

    private static final int BOARD_SIZE = 3;
    private Button[][] buttons = new Button[BOARD_SIZE][BOARD_SIZE];
    private char currentPlayer = 'S';

    @FXML
    public void initialize() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Button button = new Button();
                button.setPrefSize(100, 100);
                button.setOnAction(e -> handleButtonClick((Button) e.getSource()));
                buttons[i][j] = button;
                gameBoard.add(button, j, i);
            }
        }
        updateStatus();
    }

    private void handleButtonClick(Button button) {
        if (button.getText().isEmpty()) {
            button.setText(String.valueOf(currentPlayer));
            if (checkForSOS()) {
                updateStatus("SOS formed! Player " + currentPlayer + " wins!");
                disableAllButtons();
            } else if (isBoardFull()) {
                updateStatus("It's a draw!");
            } else {
                currentPlayer = (currentPlayer == 'S') ? 'O' : 'S';
                updateStatus();
            }
        }
    }

    private boolean checkForSOS() {
        // Check rows, columns, and diagonals
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (checkLine(buttons[i][0], buttons[i][1], buttons[i][2])) return true; // rows
            if (checkLine(buttons[0][i], buttons[1][i], buttons[2][i])) return true; // columns
        }
        if (checkLine(buttons[0][0], buttons[1][1], buttons[2][2])) return true; // diagonal
        if (checkLine(buttons[0][2], buttons[1][1], buttons[2][0])) return true; // other diagonal
        return false;
    }

    private boolean checkLine(Button b1, Button b2, Button b3) {
        return b1.getText().equals("S") && b2.getText().equals("O") && b3.getText().equals("S");
    }

    private boolean isBoardFull() {
        for (Button[] row : buttons) {
            for (Button button : row) {
                if (button.getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void disableAllButtons() {
        for (Button[] row : buttons) {
            for (Button button : row) {
                button.setDisable(true);
            }
        }
    }

    private void updateStatus() {
        updateStatus("Player " + currentPlayer + "'s turn");
    }

    private void updateStatus(String message) {
        statusLabel.setText(message);
    }
}