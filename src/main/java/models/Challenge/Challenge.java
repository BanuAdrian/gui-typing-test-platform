package models.Challenge;

import models.ChallengeText;
import models.Text;
import models.TypingSession;

import java.util.ArrayList;
import java.util.List;

public abstract class Challenge {
    private int id;
    private final String name;
    private final String description;
    private int score;
    private List<ChallengeText> challengeTextList = new ArrayList<>();
//    private boolean isCompleted;
//    private int score;


    public Challenge(int id, String name, String description, int score) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.score = score;
    }

    public Challenge(String name, String description, int score) {
        this.name = name;
        this.description = description;
        this.score = score;
//        this.isCompleted = false;
    }

    public abstract boolean isCompleted(TypingSession typingSession);

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return name;
    }

    public List<Text> getTexts() {
        return challengeTextList
                .stream()
                .map(ChallengeText::getText)
                .toList();
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ChallengeText> getChallengeTextList() {
        return challengeTextList;
    }

    public void setChallengeTextList(List<ChallengeText> challengeTextList) {
        this.challengeTextList = challengeTextList;
    }

//    @Override
//    public String toString() {
//        return "Challenge{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", description='" + description + '\'' +
//                ", score=" + score +
//                ", challengeTextList=" + challengeTextList +
//                '}';
//    }
}
