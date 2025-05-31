package models.AchievementFactory;

import models.Achievement.Achievement;

public abstract class AchievementFactory {
    public abstract Achievement createAchievement(int achievementId, String achievementName, String achievementDescription, int requiredAmount);
}
