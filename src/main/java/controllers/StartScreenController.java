package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import views.Main;

import java.io.IOException;

public class StartScreenController {

    @FXML
    private Button loginButton;

    @FXML
    private void initialize() {
        loginButton.setOnAction(event -> showLoginDialog());
    }

    private void showLoginDialog() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/login-dialog.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.showAndWait();

            LoginDialogController controller = fxmlLoader.getController();
            String username = controller.getUsername();

            if (username != null && !username.trim().isEmpty()) {
                System.out.println("Login realizat pentru: " + username);
                openMainMenu(username);
            } else {
                System.out.println("Username invalid. Introduceți un username valid.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openMainMenu(String username) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/main-menu.fxml"));

            MainMenuController mainMenuController = new MainMenuController(username, loginButton.getScene());
            fxmlLoader.setController(mainMenuController);

            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

            Stage stage = new Stage();
            stage.setTitle("Main Menu - " + username);
            stage.setScene(scene);
            stage.show();

            loginButton.getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}