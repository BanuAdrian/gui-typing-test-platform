package models.Challenge;

import models.TypingSession;

public class TimeChallenge extends Challenge {
    private int targetSeconds;

    public TimeChallenge(int id, String name, String description, int score, int targetSeconds) {
        super(id, name, description, score);
        //        super("Time Challenge", "Finish in " + Integer.toString(targetSeconds) + " seconds!", score);
        this.targetSeconds = targetSeconds;
    }

    public TimeChallenge(int score, int targetSeconds) {
        super("Time Challenge", "Finish in " + Integer.toString(targetSeconds) + " seconds!", score);
        this.targetSeconds = targetSeconds;
    }
    public boolean isCompleted(TypingSession typingSession) {
        return typingSession.getElapsedTimeSec() <= targetSeconds;
    }

}