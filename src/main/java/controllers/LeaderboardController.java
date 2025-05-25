package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import models.LeaderboardEntry;
import models.Notification;

import java.util.List;

public class LeaderboardController {
    @FXML
    private ListView<LeaderboardEntry> leaderboardListView;

    @FXML
    private Label leaderboardLabel;

    private List<LeaderboardEntry> leaderboardEntries;
    private LeaderboardEntry currentEntry;


    public LeaderboardController(List<LeaderboardEntry> leaderboardEntries) {
        this.leaderboardEntries = leaderboardEntries;
    }

    @FXML
    private void initialize() {
        leaderboardListView.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(LeaderboardEntry entry, boolean empty) {
                super.updateItem(entry, empty);
                if (empty || entry == null) {
                    setText(null);
                } else {
                    setText(entry.textInList());
                }
            }
        });

        leaderboardListView.getItems().addAll(leaderboardEntries);

        leaderboardListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<LeaderboardEntry>() {
            @Override
            public void changed(ObservableValue<? extends LeaderboardEntry> observableValue, LeaderboardEntry entry, LeaderboardEntry t1) {
                currentEntry = leaderboardListView.getSelectionModel().getSelectedItem();
                leaderboardLabel.setText(currentEntry.textWhenSelected());
            }
        });
    }
}
