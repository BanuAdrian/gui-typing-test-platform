package models.Challenge;

import models.TypingSession;

public class SpeedChallenge extends Challenge {
    private int targetWpm;

    public SpeedChallenge(int id, String name, String description, int score, int targetWpm) {
        super(id, name, description, score);
        this.targetWpm = targetWpm;
    }

    @Override
    public boolean isCompleted(TypingSession typingSession) {
        return typingSession.getWpm() >= targetWpm;
    }
}
