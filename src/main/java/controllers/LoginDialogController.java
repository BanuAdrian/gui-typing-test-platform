package controllers;

import exceptions.InvalidUsernameException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginDialogController {

    @FXML
    private TextField usernameField;

    @FXML
    private Button confirmButton;

    private String username;

    @FXML
    private void initialize() {
        confirmButton.setOnAction(event -> handleConfirm());
    }

    private void handleConfirm() {
        username = usernameField.getText();
        closeDialog();
    }

    private void closeDialog() {
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

    public String getUsername() {
        if (username == null || username.trim().isEmpty() || !username.matches("[A-z]+[A-z0-9]*")) {
            throw new InvalidUsernameException("Invalid username. Please try again!");
        }
        return username;
    }
}