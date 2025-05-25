package models;

import models.Achievement.Achievement;
import models.Challenge.Challenge;

import java.util.*;

public class User {
    private int id;
    private String username;
    private List<UserChallenge> userChallengeList = new ArrayList<>();
    private List<UserAchievement> userAchievementList = new ArrayList<>();
    private List<TypingTest> typingTests = new ArrayList<>();
    private Set<Notification> notifications = new LinkedHashSet<>();

    public User(String username) {
        this.username = username;
    }

    public User(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<UserChallenge> getUserChallengeList() {
        return userChallengeList;
    }

    public List<Challenge> getCompletedChallenges() {
        return userChallengeList
                .stream()
                .filter(UserChallenge::isCompleted)
                .map(UserChallenge::getChallenge)
                .toList();
    }

    public List<UserAchievement> getUserAchievementList() {
        return userAchievementList;
    }

    public List<Achievement> getAchievements() {
        return userAchievementList
                .stream()
                .map(UserAchievement::getAchievement)
                .toList();
    }

    public List<TypingTest> getTypingTests() {
        return typingTests;
    }

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public int calculateBestWpm() {
        if (!typingTests.isEmpty()) {
            List<TypingTest> sortedTypingTests = new ArrayList<>(typingTests);
            sortedTypingTests.sort((t1, t2) -> (t2.getWpm() - t1.getWpm()));
            return sortedTypingTests.getFirst().getWpm();
        }
        return 0;
    }

    public int calculateScore() {
        int score = 0;
        List<Challenge> completedChallenges = getCompletedChallenges();
        for (Challenge challenge : completedChallenges) {
            score += challenge.getScore();
        }
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
