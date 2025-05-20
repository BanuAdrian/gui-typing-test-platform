package models;

import models.Challenge.Challenge;

public class ChallengeText {
    private int challengeId;
    private int textId;
    private Challenge challenge;
    private Text text;

    public ChallengeText(int challengeId, int textId, Text text) {
        this.challengeId = challengeId;
        this.textId = textId;
        this.text = text;
    }

    public ChallengeText(int challengeId, int textId, Challenge challenge, Text text) {
        this.challengeId = challengeId;
        this.textId = textId;
        this.challenge = challenge;
        this.text = text;
    }

    public int getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(int challengeId) {
        this.challengeId = challengeId;
    }

    public int getTextId() {
        return textId;
    }

    public void setTextId(int textId) {
        this.textId = textId;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }
}
