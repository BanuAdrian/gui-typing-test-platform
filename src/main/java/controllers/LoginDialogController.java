package controllers;

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
        if (username != null && !username.trim().isEmpty()) {
            System.out.println("Entered username: " + username);
            closeDialog();
        } else {
            System.out.println("Invalid username.");
        }
    }

    private void closeDialog() {
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

    public String getUsername() {
        return username;
    }
}