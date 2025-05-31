package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Achievement.Achievement;
import models.Challenge.Challenge;
import models.LeaderboardEntry;
import models.Notification;
import models.Text;
import models.enums.ActionType;
import models.enums.TextCategory;
import org.kordamp.bootstrapfx.BootstrapFX;
import services.*;

import java.io.IOException;
import java.util.*;

public class MainMenuController {
    private static final LoggingService loggingService = LoggingService.getInstance();

    private final MainService mainService;
    private String typedUsername;
    private Scene startScreenScene;

    @FXML
    private Label helloLabel;

    @FXML
    private Label notificationsNumberLabel;

    @FXML
    private ImageView logo;

    @FXML
    private VBox contentArea;

    @FXML
    private Button takeTestButton, wpmLeaderboardButton, achievementsButton,
            notificationsButton, randomChallengeButton, completedChallengesButton,
            scoreLeaderboardButton, logoutButton;

    public MainMenuController(String typedUsername, Scene startScreenScene) {
        mainService = new MainService(typedUsername);
        this.typedUsername = typedUsername;
        this.startScreenScene = startScreenScene;
    }

    public Label getNotificationsLabel() {
        return notificationsNumberLabel;
    }

    @FXML
    private void initialize() {
        logo.setImage(new Image(getClass().getResource("/images/typing.png").toExternalForm()));
        helloLabel.setText(typedUsername);

        int notificationsNumber = mainService.currentUserGetNotifications().size();
        if (notificationsNumber == 1) {
            notificationsNumberLabel.setText("1 notification");
        } else {
            notificationsNumberLabel.setText(notificationsNumber + " notifications");
        }

        takeTestButton.setOnAction(event -> openTest(false));
        wpmLeaderboardButton.setOnAction(event -> openWPMLeaderboard());
        achievementsButton.setOnAction(event -> openAchievements());
        notificationsButton.setOnAction(event -> openNotifications());
        randomChallengeButton.setOnAction(event -> openTest(true));
        completedChallengesButton.setOnAction(event -> openCompletedChallenges());
        scoreLeaderboardButton.setOnAction(event -> openScoreLeaderboard());
        logoutButton.setOnAction(event -> handleLogout());

        contentArea.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

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

            loggingService.log(ActionType.CHOOSE_TEXT_CATEGORY);
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

            contentArea.getChildren().clear();
            contentArea.getChildren().add(fxmlLoader.load());

//            Scene scene = new Scene(fxmlLoader.load());
//            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
//            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
//
//            Stage stage = new Stage();
//            stage.setTitle("Typing Test");
//            stage.setScene(scene);
//            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openWPMLeaderboard() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/leaderboard-view.fxml"));

            List<LeaderboardEntry> leaderboardEntries = mainService.getWpmLeaderboard();
            LeaderboardController leaderboardController = new LeaderboardController(leaderboardEntries);
            fxmlLoader.setController(leaderboardController);

            contentArea.getChildren().clear();
            contentArea.getChildren().add(fxmlLoader.load());

            loggingService.log(ActionType.VIEW_WPM_LEADERBOARD);

//            Scene scene = new Scene(fxmlLoader.load());
//            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
//            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
//
//            Stage stage = new Stage();
//            stage.setTitle("WPM Leaderboard");
//            stage.setScene(scene);
//            stage.show();

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

            contentArea.getChildren().clear();
            contentArea.getChildren().add(fxmlLoader.load());

            loggingService.log(ActionType.VIEW_ACHIEVEMENTS);
//            Scene scene = new Scene(fxmlLoader.load());
//            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
//            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
//
//            Stage stage = new Stage();
//            stage.setTitle("Achievements");
//            stage.setScene(scene);
//            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openNotifications() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/notifications-view.fxml"));

            List<Notification> notifications = mainService.currentUserGetNotifications().stream().toList();
            NotificationsController notificationsController = new NotificationsController(notifications, notificationsNumberLabel, mainService);
            fxmlLoader.setController(notificationsController);


            contentArea.getChildren().clear();
            contentArea.getChildren().add(fxmlLoader.load());

            loggingService.log(ActionType.VIEW_NOTIFICATIONS);
//            Scene scene = new Scene(fxmlLoader.load());
//            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
//            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
//
//            Stage stage = new Stage();
//            stage.setTitle("Notifications");
//            stage.setScene(scene);
//            stage.show();

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

            contentArea.getChildren().clear();
            contentArea.getChildren().add(fxmlLoader.load());

            loggingService.log(ActionType.VIEW_COMPLETED_CHALLENGES);
//            Scene scene = new Scene(fxmlLoader.load());
//            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
//            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
//
//            Stage stage = new Stage();
//            stage.setTitle("Completed Challenges");
//            stage.setScene(scene);
//            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openScoreLeaderboard() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/leaderboard-view.fxml"));

            List<LeaderboardEntry> leaderboardEntries = mainService.getScoreLeaderboard();
            LeaderboardController leaderboardController = new LeaderboardController(leaderboardEntries);
            fxmlLoader.setController(leaderboardController);

            contentArea.getChildren().clear();
            contentArea.getChildren().add(fxmlLoader.load());

            loggingService.log(ActionType.VIEW_SCORE_LEADERBOARD);
//            Scene scene = new Scene(fxmlLoader.load());
//            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
//            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
//
//            Stage stage = new Stage();
//            stage.setTitle("WPM Leaderboard");
//            stage.setScene(scene);
//            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleLogout() {
        Stage stage = (Stage)logoutButton.getScene().getWindow();
        stage.setScene(startScreenScene);
        stage.setTitle("Typing Test Platform");
        stage.show();
        loggingService.log(ActionType.LOGOUT);
    }
}