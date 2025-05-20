package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import models.Challenge.Challenge;

import java.util.List;

public class CompletedChallengesController {
    @FXML
    private ListView<Challenge> challengesListView;

    @FXML
    private Label challengeLabel;

    private List<Challenge> challenges;
    private Challenge currentChallenge;


    public CompletedChallengesController(List<Challenge> challenges) {
        this.challenges = challenges;
    }

    @FXML
    private void initialize() {
        challengesListView.getItems().addAll(challenges);

        challengesListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Challenge>() {
            @Override
            public void changed(ObservableValue<? extends Challenge> observableValue, Challenge achievement, Challenge t1) {
                currentChallenge = challengesListView.getSelectionModel().getSelectedItem();
                challengeLabel.setText(currentChallenge.getDescription());
            }
        });
    }
}
