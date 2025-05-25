package controllers;

import exceptions.InvalidUsernameException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import views.Main;

import java.io.IOException;

public class StartScreenController {

    @FXML
    public ImageView logo;

    @FXML
    private Button loginButton;

    @FXML
    private void initialize() {
        logo.setImage(new Image(getClass().getResource("/images/typing.png").toExternalForm()));
        loginButton.setOnAction(event -> showLoginDialog());
    }

    private void showLoginDialog() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/login-dialog.fxml"));

            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            try {
                scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            } catch (NullPointerException e) {
                System.out.println("Failed to load stylesheet!");
            }

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.showAndWait();

            LoginDialogController controller = fxmlLoader.getController();

            try {
                String username = controller.getUsername();
                openMainMenu(username);
            } catch (InvalidUsernameException e) {
                System.out.println(e.getMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openMainMenu(String username) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/main-view.fxml"));

            MainMenuController mainMenuController = new MainMenuController(username, loginButton.getScene());
            fxmlLoader.setController(mainMenuController);

            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            try {
                scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            } catch (NullPointerException e) {
                System.out.println("Failed to load stylesheet!");
            }

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