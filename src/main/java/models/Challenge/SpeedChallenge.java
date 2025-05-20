package models.Challenge;

import models.TypingSession;

public class SpeedChallenge extends Challenge {
    private int targetWpm;

    public SpeedChallenge(int id, String name, String description, int score, int targetWpm) {
        super(id, name, description, score);
//        super("Speed Challenge", "Finish with at least " + Integer.toString(targetWpm) + " WPM!", score);
        this.targetWpm = targetWpm;
    }

    public SpeedChallenge(int score, int targetWpm) {
        super("Speed Challenge", "Finish with at least " + Integer.toString(targetWpm) + " WPM!", score);
        this.targetWpm = targetWpm;
    }
    public boolean isCompleted(TypingSession typingSession) {

        return typingSession.getWpm() >= targetWpm;
    }
}
