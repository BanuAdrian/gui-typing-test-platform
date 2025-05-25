package models;

import exceptions.BadTimerHandlingException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class TypingSession {
    private TestTimer testTimer;
    private Text text;
    private int wpm;
    private float elapsedTimeMin;
    private int correctWords;
    private List<String> words;
    private String typedWord;
    private int numCharacters;

    public TypingSession(Text text) {
        this.text = text;
        this.testTimer = new TestTimer();
        words = new LinkedList<>(List.of(text.getContent().split("\\s")));
    }

    public void start() {
        try {
            testTimer.start();
        } catch (BadTimerHandlingException e) {
            System.out.println(e.getMessage());
        }
    }

    public void processText(String text) {
        typedWord = text;
        if (!text.isEmpty()) {
            char lastTypedChar = typedWord.charAt(text.length() - 1);
            if (lastTypedChar == ' ') {
                verifyWord();
            }
            numCharacters++;
        }
    }

    public void verifyWord() {
        if (!words.isEmpty()) {
            String currentWord = words.getFirst();
//            System.out.println("current word = " + currentWord);
            if (typedWord.trim().equals(currentWord)) {
                correctWords++;
                typedWord = "";
                words.removeFirst();
            }
        }
    }

    public void stop() {
        try {
            testTimer.stop();
            elapsedTimeMin = testTimer.getElapsedTimeMin();
            System.out.println("elapsedTimeMin = " + elapsedTimeMin);
            wpm = (int) ((float) (numCharacters) / 5 / elapsedTimeMin);
            System.out.println("wpm = " + wpm);
            System.out.println("sec = " + getElapsedTimeSec());
            System.out.println("num characters = " + numCharacters);
        } catch (BadTimerHandlingException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getTargetWord() {
        return words.isEmpty()  ? null : words.getFirst();
    }

    public String getTypedWord() {
        return typedWord;
    }

    public int getWpm() {
        return wpm;
    }

    public float getElapsedTimeMin() {
        return elapsedTimeMin;
    }

    public int getElapsedTimeSec() {
        return (int)testTimer.getElapsedTimeSec();
    }

    public int getCorrectWords() {
        return correctWords;
    }

    public Text getText() {
        return text;
    }
}
