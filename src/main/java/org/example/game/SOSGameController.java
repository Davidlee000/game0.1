package org.example.game;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;


public class SOSGameController {

  @FXML
  private GridPane gameBoard;
  @FXML
  private Label statusLabel;
  @FXML
  private RadioButton sRadioButton;
  @FXML
  private RadioButton oRadioButton;
  @FXML
  private ToggleGroup letterGroup;
  @FXML
  private ComboBox<Integer> boardSizeComboBox;
  @FXML
  private Button startGameButton;
  @FXML
  private Label boardSizeComboboxLabel;

  private int boardSize;
  private Button[][] buttons;
  private int currentPlayer = 1;
  private boolean gameStarted = false;

  @FXML
  public void initialize() {
    boardSizeComboBox.getItems().addAll(3, 4, 5, 6, 7, 8, 9, 10);
    boardSizeComboBox.setValue(3);
    startGameButton.setOnAction(e -> startGame());
    startGameButton.setText("Start Game");
    updateStatus("Select board size and click Start Game");

    // Hide radio buttons initially
    sRadioButton.setVisible(false);
    oRadioButton.setVisible(false);
  }

  private void startGame() {
    // Show radio buttons
    sRadioButton.setVisible(true);
    oRadioButton.setVisible(true);
    // Hide Board Size ComboBox
    boardSizeComboBox.setVisible(false);
    // Hide Board Size ComboBox label
    boardSizeComboboxLabel.setVisible(false);

    boardSize = boardSizeComboBox.getValue();
    buttons = new Button[boardSize][boardSize];
    gameBoard.getChildren().clear();

    for (int i = 0; i < boardSize; i++) {
      for (int j = 0; j < boardSize; j++) {
        Button button = new Button();
        button.setPrefSize(400 / boardSize, 400 / boardSize);
        button.setOnAction(e -> handleButtonClick((Button) e.getSource()));
        buttons[i][j] = button;
        gameBoard.add(button, j, i);
      }
    }

    gameStarted = true;
    startGameButton.setText("Reset Game");
    currentPlayer = 1;
    updateStatus();
  }

  private void toggleGameState() {
    if (gameStarted) {
      // Reset to start menu
      gameBoard.getChildren().clear();
      buttons = null;
      gameStarted = false;
      boardSizeComboBox.setDisable(false);
      boardSizeComboBox.setVisible(true);
      boardSizeComboboxLabel.setVisible(true);
      startGameButton.setText("Start Game");
      updateStatus("Select board size and click Start Game");
    } else {
      // Start new game
      boardSize = boardSizeComboBox.getValue();
      buttons = new Button[boardSize][boardSize];
      gameBoard.getChildren().clear();

      for (int i = 0; i < boardSize; i++) {
        for (int j = 0; j < boardSize; j++) {
          Button button = new Button();
          button.setPrefSize(400 / boardSize, 400 / boardSize);
          button.setOnAction(e -> handleButtonClick((Button) e.getSource()));
          buttons[i][j] = button;
          gameBoard.add(button, j, i);
        }
      }

      gameStarted = true;
      boardSizeComboBox.setDisable(true);
      boardSizeComboBox.setVisible(false);
      boardSizeComboboxLabel.setVisible(false);
      startGameButton.setText("Reset Game");
      currentPlayer = 1;
      updateStatus("Player " + currentPlayer + "'s turn");
    }
  }

  private void handleButtonClick(Button button) {
    if (!gameStarted || !button.getText().isEmpty()) {
      return;
    }
    String letter = getSelectedLetter();
    button.setText(letter);
    if (checkForSOS()) {
      updateStatus("SOS formed! Player " + currentPlayer + " wins!");
      disableAllButtons();
    } else if (isBoardFull()) {
      updateStatus("It's a draw!");
    } else {
      currentPlayer = (currentPlayer == 1) ? 2 : 1;
      updateStatus();
    }
  }

  private String getSelectedLetter() {
    return sRadioButton.isSelected() ? "S" : "O";
  }

  private boolean checkForSOS() {
    // Check rows and columns
    for (int i = 0; i < boardSize; i++) {
      for (int j = 0; j < boardSize - 2; j++) {
          if (checkLine(buttons[i][j], buttons[i][j + 1], buttons[i][j + 2])) {
              return true; // rows
          }
          if (checkLine(buttons[j][i], buttons[j + 1][i], buttons[j + 2][i])) {
              return true; // columns
          }
      }
    }
    // Check diagonals
    for (int i = 0; i < boardSize - 2; i++) {
      for (int j = 0; j < boardSize - 2; j++) {
          if (checkLine(buttons[i][j], buttons[i + 1][j + 1], buttons[i + 2][j + 2])) {
              return true; // diagonal
          }
          if (checkLine(buttons[i][j + 2], buttons[i + 1][j + 1], buttons[i + 2][j])) {
              return true; // other diagonal
          }
      }
    }
    return false;
  }

  public boolean checkLine(Button b1, Button b2, Button b3) {
    return b1.getText().equals("S") && b2.getText().equals("O") && b3.getText().equals("S");
  }

  public boolean checkLine(String s1, String s2, String s3) {
    return s1.equals("S") && s2.equals("O") && s3.equals("S");
  }

  private boolean isBoardFull() {
    for (int i = 0; i < boardSize; i++) {
      for (int j = 0; j < boardSize; j++) {
        if (buttons[i][j].getText().isEmpty()) {
          return false;
        }
      }
    }
    return true;
  }

  private void disableAllButtons() {
    for (int i = 0; i < boardSize; i++) {
      for (int j = 0; j < boardSize; j++) {
        buttons[i][j].setDisable(true);
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