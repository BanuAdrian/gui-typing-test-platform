package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import models.Achievement.Achievement;
import models.LeaderboardEntry;

import java.util.List;

public class AchievementsController {
    @FXML
    private ListView<Achievement> achievementsListView;

    @FXML
    private Label achievementLabel;

    private List<Achievement> achievements;
    private Achievement currentAchievement;


    public AchievementsController(List<Achievement> achievements) {
        this.achievements = achievements;
    }

    @FXML
    private void initialize() {
        achievementsListView.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Achievement entry, boolean empty) {
                super.updateItem(entry, empty);
                if (empty || entry == null) {
                    setText(null);
                } else {
                    setText(entry.textInList());
                }
            }
        });

        achievementsListView.getItems().addAll(achievements);

        achievementsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Achievement>() {
            @Override
            public void changed(ObservableValue<? extends Achievement> observableValue, Achievement achievement, Achievement t1) {
                currentAchievement = achievementsListView.getSelectionModel().getSelectedItem();
                achievementLabel.setText(currentAchievement.textWhenSelected());
            }
        });
    }
}
