package models;

public class TypingTest {
    private int id;
    private int userId;
    private int textId;
    private int wpm;
    private int correctWords;

    public TypingTest(int id, int userId, int textId, int wpm, int correctWords) {
        this.id = id;
        this.userId = userId;
        this.textId = textId;
        this.wpm = wpm;
        this.correctWords = correctWords;
    }

    public TypingTest(int userId, int textId, int wpm, int correctWords) {
        this.userId = userId;
        this.textId = textId;
        this.wpm = wpm;
        this.correctWords = correctWords;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTextId() {
        return textId;
    }

    public void setTextId(int textId) {
        this.textId = textId;
    }

    public int getWpm() {
        return wpm;
    }

    public void setWpm(int wpm) {
        this.wpm = wpm;
    }

    public int getCorrectWords() {
        return correctWords;
    }

    public void setCorrectWords(int correctWords) {
        this.correctWords = correctWords;
    }

    @Override
    public String toString() {
        return "TypingTest{" +
                "id=" + id +
                ", userId=" + userId +
                ", textId=" + textId +
                ", wpm=" + wpm +
                ", correctWords=" + correctWords +
                '}';
    }
}
