package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Achievement.Achievement;
import models.Challenge.Challenge;
import models.Notification;
import models.Text;
import models.enums.TextCategory;
import org.kordamp.bootstrapfx.BootstrapFX;
import services.*;

import java.io.IOException;
import java.util.*;

public class MainMenuController {
    private final MainService mainService;
    private String typedUsername;
    private Scene startScreenScene;

    @FXML
    private Button takeTestButton, wpmLeaderboardButton, achievementsButton,
            notificationsButton, randomChallengeButton, completedChallengesButton,
            scoreLeaderboardButton, logoutButton;

    public MainMenuController(String typedUsername, Scene startScreenScene) {
        mainService = new MainService(typedUsername);
        this.startScreenScene = startScreenScene;
    }

    @FXML
    private void initialize() {
        takeTestButton.setOnAction(event -> openTest(false));
        wpmLeaderboardButton.setOnAction(event -> openWPMLeaderboard());
        achievementsButton.setOnAction(event -> openAchievements());
        notificationsButton.setOnAction(event -> openNotifications());
        randomChallengeButton.setOnAction(event -> openTest(true));
        completedChallengesButton.setOnAction(event -> openCompletedChallenges());
        scoreLeaderboardButton.setOnAction(event -> openScoreLeaderboard());
        logoutButton.setOnAction(event -> handleLogout());
    }

    private TextCategory textCategoryDialog() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/text-category-dialog.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Text Category");
            stage.setScene(scene);

//            takeTestButton.getScene().getWindow().hide();

            stage.showAndWait();


            TextCategoryDialogController controller = fxmlLoader.getController();
            //            System.out.println(textCategory);

            return controller.getTextCategory();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void openTest(boolean isChallenge) {
        try {
            TextCategory textCategory;

            TypingTestController typingTestController;
            if (isChallenge) {
                typingTestController = new TypingTestController(mainService);
            } else {
                textCategory = textCategoryDialog();
                if (textCategory == null) {
                    return;
                }
                typingTestController = new TypingTestController(mainService, textCategory);
            }

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/typing-test-view.fxml"));
            fxmlLoader.setController(typingTestController);

            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

            Stage stage = new Stage();
            stage.setTitle("Typing Test");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openWPMLeaderboard() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/leaderboard-view.fxml"));

            List<String> leaderboardEntries = mainService.getWpmLeaderboard();
            LeaderboardController leaderboardController = new LeaderboardController(leaderboardEntries);
            fxmlLoader.setController(leaderboardController);

            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

            Stage stage = new Stage();
            stage.setTitle("WPM Leaderboard");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openAchievements() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/achievements-view.fxml"));

            List<Achievement> achievements = mainService.currentUserGetAchievements();
            AchievementsController achievementsController = new AchievementsController(achievements);
            fxmlLoader.setController(achievementsController);

            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

            Stage stage = new Stage();
            stage.setTitle("Achievements");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openNotifications() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/notifications-view.fxml"));

            List<Notification> notifications = mainService.currentUserGetNotifications().stream().toList();
            NotificationsController notificationsController = new NotificationsController(notifications);
            fxmlLoader.setController(notificationsController);

            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

            Stage stage = new Stage();
            stage.setTitle("Notifications");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openCompletedChallenges() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/completed-challenges-view.fxml"));

            List<Challenge> challenges = mainService.currentUserGetCompletedChallenges();
            CompletedChallengesController challengesController = new CompletedChallengesController(challenges);
            fxmlLoader.setController(challengesController);

            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

            Stage stage = new Stage();
            stage.setTitle("Completed Challenges");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openScoreLeaderboard() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/leaderboard-view.fxml"));

            List<String> leaderboardEntries = mainService.getScoreLeaderboard();
            LeaderboardController leaderboardController = new LeaderboardController(leaderboardEntries);
            fxmlLoader.setController(leaderboardController);

            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

            Stage stage = new Stage();
            stage.setTitle("WPM Leaderboard");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleLogout() {
        Stage stage = (Stage)logoutButton.getScene().getWindow();
        stage.setScene(startScreenScene);
        stage.show();
    }
}