package models.Achievement;

import models.User;

public class TestsTakenAchievement extends Achievement {
    private final int requiredTests;

    public TestsTakenAchievement(int id, String name, String description, int requiredTests) {
        super(id, name, description);
        this.requiredTests = requiredTests;
    }

    @Override
    public boolean isAchieved(User user) {
        return user.getTypingTests().size() >= requiredTests;
    }
}
