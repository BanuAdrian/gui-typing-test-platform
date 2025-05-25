package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import models.Challenge.Challenge;
import models.LeaderboardEntry;

import java.util.List;

public class CompletedChallengesController {
    @FXML
    private ListView<Challenge> challengesListView;

//    @FXML
//    private Label challengeLabel;

    @FXML
    private Text challengeLabel;


    private List<Challenge> challenges;
    private Challenge currentChallenge;


    public CompletedChallengesController(List<Challenge> challenges) {
        this.challenges = challenges;
    }

    @FXML
    private void initialize() {
        challengesListView.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Challenge entry, boolean empty) {
                super.updateItem(entry, empty);
                if (empty || entry == null) {
                    setText(null);
                } else {
                    setText(entry.textInList());
                }
            }
        });

        challengesListView.getItems().addAll(challenges);

        challengesListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Challenge>() {
            @Override
            public void changed(ObservableValue<? extends Challenge> observableValue, Challenge achievement, Challenge t1) {
                currentChallenge = challengesListView.getSelectionModel().getSelectedItem();
                challengeLabel.setText(currentChallenge.textWhenSelected());
            }
        });
    }
}
