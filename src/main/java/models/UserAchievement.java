package models;

import models.Achievement.Achievement;

public class UserAchievement {
    private int userId;
    private int achievementId;

    private User user;
    private Achievement achievement;

    public UserAchievement(int userId, int achievementId, User user, Achievement achievement) {
        this.userId = userId;
        this.achievementId = achievementId;
        this.user = user;
        this.achievement = achievement;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(int achievementId) {
        this.achievementId = achievementId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Achievement getAchievement() {
        return achievement;
    }

    public void setAchievement(Achievement achievement) {
        this.achievement = achievement;
    }
}
