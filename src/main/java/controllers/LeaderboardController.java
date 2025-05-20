package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import models.Notification;

import java.util.List;

public class LeaderboardController {
    @FXML
    private ListView<String> leaderboardListView;

    @FXML
    private Label leaderboardLabel;

    private List<String> leaderboardEntries;
    private String currentEntry;


    public LeaderboardController(List<String> leaderboardEntries) {
        this.leaderboardEntries= leaderboardEntries;
    }

    @FXML
    private void initialize() {
        leaderboardListView.getItems().addAll(leaderboardEntries);

        leaderboardListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String entry, String t1) {
                currentEntry = leaderboardListView.getSelectionModel().getSelectedItem();
                leaderboardLabel.setText(currentEntry);
            }
        });
    }
}
