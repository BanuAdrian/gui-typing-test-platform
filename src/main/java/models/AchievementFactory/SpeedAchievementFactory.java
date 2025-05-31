package models.AchievementFactory;

import models.Achievement.Achievement;
import models.Achievement.SpeedAchievement;

public class SpeedAchievementFactory extends AchievementFactory {
    @Override
    public Achievement createAchievement(int achievementId, String achievementName, String achievementDescription, int requiredAmount) {
        return new SpeedAchievement(achievementId, achievementName, achievementDescription, requiredAmount);
    }
}
