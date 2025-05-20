package models.Achievement;

import models.User;

public class SpeedAchievement extends Achievement {
    private final int requiredWpm;

    public SpeedAchievement(int id, String name, String description, int requiredWpm) {
        super(id, name, description);
        this.requiredWpm = requiredWpm;
    }

    public SpeedAchievement(int requiredWpm) {
        super(Integer.toString(requiredWpm) + "+ WPM", "Score more than " + Integer.toString(requiredWpm) + "WPM.");
        this.requiredWpm = requiredWpm;
    }

    @Override
    public boolean isAchieved(User user) {
//        return user.getTypingTests().getLast().getWpm() >= requiredWpm;
        return user
                .getTypingTests()
                .stream()
                .anyMatch(typingTest -> typingTest.getWpm() >= requiredWpm);
    }
}
