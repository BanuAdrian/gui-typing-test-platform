package models;

import models.Challenge.Challenge;

public class UserChallenge {
    private int userId;
    private int challengeId;
    private boolean isCompleted;

//    private User user;
    private Challenge challenge;

    public UserChallenge(int userId, int challengeId, boolean isCompleted, Challenge challenge) {
        this.userId = userId;
        this.challengeId = challengeId;
        this.isCompleted = isCompleted;
//        this.user = user;
        this.challenge = challenge;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(int challengeId) {
        this.challengeId = challengeId;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

    public Challenge getChallenge() {
        return challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }
}


