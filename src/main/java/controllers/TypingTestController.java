package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import models.Challenge.Challenge;
import models.Challenge.SpeedChallenge;
import models.Challenge.TimeChallenge;
import models.TypingSession;
import models.enums.TextCategory;
import services.MainService;

import java.sql.Time;
import java.util.LinkedList;
import java.util.List;

public class TypingTestController {
    private MainService mainService;
    private TypingSession typingSession;
    private boolean isChallenge = false;
    private Challenge challenge;

    @FXML
    private Label titleLabel;

    @FXML
//    private Text textContainer;
    private TextFlow textContainer;


    @FXML
    private TextField textField;

    @FXML
    private Label statusLabel;

    private List<Text> characterTexts = new LinkedList<>();
    private String targetWord;
    private int cursorIndex;
    private int lastCursorIndex;
    private boolean started = false;

    public TypingTestController(MainService mainService, TextCategory textCategory) {
         this.mainService = mainService;
         models.Text text = mainService.getRandomTextByCategory(textCategory);
         this.typingSession = new TypingSession(text);
         this.isChallenge = false;
    }

    public TypingTestController(MainService mainService) {
        this.mainService = mainService;
        this.isChallenge = true;
        challenge = mainService.getRandomChallenge();
        this.typingSession = new TypingSession(mainService.getRandomChallengeText(challenge));
    }

    @FXML
    private void initialize() {
        statusLabel.setVisible(false);

        if (isChallenge) {
            titleLabel.setText("Challenge: " + challenge.getDescription());
        }

        String fullText = typingSession.getText().getContent();


        for (char c : fullText.toCharArray()) {
            Text text = new Text(String.valueOf(c));
            characterTexts.add(text);
            textContainer.getChildren().add(text);
        }


        targetWord = typingSession.getTargetWord();
        textField.setOnKeyTyped(keyEvent -> processTypedCharacter(keyEvent.getCharacter()));

    }

    private void processTypedCharacter(String character) {
        if (!started) {
            typingSession.start();
            started = true;
        }

        if (targetWord == null) {
            return;
        }

        String typedText = textField.getText();
//        System.out.println("typed text: " + typedText);

        typingSession.processText(typedText);
        cursorIndex = lastCursorIndex + typedText.length();

        if (character.isBlank() && typingSession.getTypedWord().isEmpty()) {
            textField.clear();
            lastCursorIndex = cursorIndex;
            targetWord = typingSession.getTargetWord();
            if (targetWord == null) {
                endTypingSession();
            }
        } else if (!typedText.isEmpty()) {
            updateHighlightedCharacters(typedText);
        }
    }

    private void endTypingSession() {
        typingSession.stop();
        textField.setVisible(false);
        statusLabel.setVisible(true);
        if (isChallenge) {
            mainService.currentUserAddChallenge(challenge, typingSession);
            if (challenge.isCompleted(typingSession)) {
                if (challenge instanceof TimeChallenge) {
                    statusLabel.setText("Challenge completed in " +
                            typingSession.getElapsedTimeSec() + " seconds!\n");
                } else {
                    statusLabel.setText("Challenge completed with " +
                            typingSession.getWpm() + " WPM!\n");
                }
                statusLabel.setText(statusLabel.getText() + "You got " + challenge.getScore() + " points.");
            } else {
                if (challenge instanceof TimeChallenge) {
                    statusLabel.setText("Challenge failed [" +
                            typingSession.getElapsedTimeSec() + " seconds]\n");
                } else {
                    statusLabel.setText("Challenge failed [" +
                            typingSession.getWpm() + " WPM]\n");
                }
                statusLabel.setText(statusLabel.getText() + "Better luck next time!");
            }
        } else {
            statusLabel.setText("Done!\n" + typingSession.getWpm() + " WPM");
            mainService.currentUserAddTypingTest(typingSession);
        }
    }

    private void updateHighlightedCharacters(String typedText) {
        char typedChar = typedText.charAt(typedText.length() - 1);
        int correctCharIndex = typedText.length() > targetWord.length() ? targetWord.length() - 1 : typedText.length() - 1;
        char correctChar = targetWord.charAt(correctCharIndex);

        if (cursorIndex - 1 < characterTexts.size()) {
            if (typedChar == correctChar) {
                characterTexts.get(cursorIndex - 1).setStyle("-fx-fill: green");
            } else {
                characterTexts.get(cursorIndex - 1).setStyle("-fx-fill: red");
            }
        }
    }
}
