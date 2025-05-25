package models.Challenge;

import models.TypingSession;

public class TimeChallenge extends Challenge {
    private int targetSeconds;

    public TimeChallenge(int id, String name, String description, int score, int targetSeconds) {
        super(id, name, description, score);
        this.targetSeconds = targetSeconds;
    }

    @Override
    public boolean isCompleted(TypingSession typingSession) {
        return typingSession.getElapsedTimeSec() <= targetSeconds;
    }

}