package models.AchievementFactory;

import models.Achievement.Achievement;
import models.Achievement.TestsTakenAchievement;

public class TestsTakenAchievementFactory extends AchievementFactory {
    @Override
    public Achievement createAchievement(int achievementId, String achievementName, String achievementDescription, int requiredAmount) {
        return new TestsTakenAchievement(achievementId, achievementName, achievementDescription, requiredAmount);
    }
}
