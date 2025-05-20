package models;

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
//    private StringBuilder typedWord = new StringBuilder();
    private String typedWord;
    private boolean started = false;
    private int numCharacters;
    private int currentWordIndex;

    public TypingSession(Text text) {
        this.text = text;
        this.testTimer = new TestTimer();
        words = new LinkedList<>(List.of(text.getContent().split("\\s")));
    }

    public void start() {
        testTimer.start();
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
            System.out.println("current word = " + currentWord);
            if (typedWord.trim().equals(currentWord)) {
                correctWords++;
                typedWord = "";
                words.removeFirst();
                currentWordIndex++;
            }

//            if (words.isEmpty()) {
//                stop();
//            }
        }
    }

    public void stop() {
        testTimer.stop();
        elapsedTimeMin = testTimer.getElapsedTimeMin();
        System.out.println("elapsedTimeMin = " + elapsedTimeMin);
        wpm = (int) ((float) (numCharacters) / 5 / elapsedTimeMin);
        System.out.println("wpm = " + wpm);
        System.out.println("sec = " + getElapsedTimeSec());
        System.out.println("num characters = " + numCharacters);
    }

    public String getTargetWord() {
        return words.isEmpty()  ? null : words.getFirst();
    }

    public int getCurrentWordIndex() {
        return currentWordIndex;
    }

    public String getTypedWord() {
        return typedWord;
    }

//    public void start() throws IOException {
//        String textString = text.getContent();
//        String[] words = textString.split("\\s");
//        StringBuilder typedWord = new StringBuilder();
//        System.out.println("Type the following text:\n" + textString);
//        testTimer.start();
//        int index = 0;
//        int numCharacters = 0;
//        while (index < words.length) {
//            String currentWord = words[index];
//            char keyPressed = (char) System.in.read();
//            if (Character.isWhitespace(keyPressed)) {
//                if (typedWord.toString().trim().equals(currentWord)) {
//                    correctWords++;
//                    numCharacters += currentWord.length();
//                }
//                typedWord.setLength(0);
//                index++;
//            } else {
//                typedWord.append(keyPressed);
//            }
//        }
//        testTimer.stop();
//        elapsedTimeMin = testTimer.getElapsedTimeMin();
//        wpm = (int) ((float) (numCharacters) / 5 / elapsedTimeMin);
//    }

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
