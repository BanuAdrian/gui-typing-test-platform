package models;

import models.Achievement.Achievement;
import models.Challenge.Challenge;

import java.util.*;

public class User {
    private int id;
    private String username;
//    private List<Achievement> achievements = new ArrayList<>();
    private List<UserAchievement> userAchievementList = new ArrayList<>();
    private List<Challenge> completedChallenges = new ArrayList<>();
    private List<Challenge> challenges = new ArrayList<>();
    private List<UserChallenge> userChallengeList = new ArrayList<>();
    private List<TypingTest> typingTests = new ArrayList<>();
    private Set<Notification> notifications = new LinkedHashSet<>();

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }

    public User(String username) {
        this.username = username;
    }

    public User(int id, String username) {
        this.id = id;
        this.username = username;
//        achievements = new ArrayList<>();
//        typingTests = new ArrayList<>();
//        notifications = new LinkedHashSet<>();
//        completedChallenges = new ArrayList<>();
    }

    public List<UserChallenge> getUserChallengeList() {
        return userChallengeList;
    }

    public void setUserChallengeList(List<UserChallenge> userChallengeList) {
        this.userChallengeList = userChallengeList;
    }

    public List<UserAchievement> getUserAchievementList() {
        return userAchievementList;
    }

    public void setUserAchievementList(List<UserAchievement> userAchievementList) {
        this.userAchievementList = userAchievementList;
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

    public List<Achievement> getAchievements() {
//        return achievements;
        return userAchievementList
                .stream()
                .map(UserAchievement::getAchievement)
                .toList();
    }

    public void addAchievement(Achievement achievement) {
//        if (achievement != null) {
//            achievements.add(achievement);
//        }
        userAchievementList.add(new UserAchievement(id, achievement.getId(), this, achievement));
    }

    public List<TypingTest> getTypingTests() {
        return typingTests;
    }

    public void addTypingTest(TypingTest typingTest) {
        if (typingTest != null) {
            typingTests.add(typingTest);
        }
    }

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public void removeNotification(int index) {
        Notification notificationToRemove = null;
        int count = 0;
        for (Notification notification : notifications) {
            if (count == index) {
                notificationToRemove = notification;
            }
            count++;
        }
        notifications.remove(notificationToRemove);
    }

    public void addNotification(Notification notification) {
        notifications.add(notification);
    }

    public int calculateBestWpm() {
        if (!typingTests.isEmpty()) {
            List<TypingTest> sortedTypingTests = new ArrayList<>(typingTests);
            sortedTypingTests.sort((t1, t2) -> (t2.getWpm() - t1.getWpm()));
//            System.out.println("USER " + username + " BEST WPM = " + typingTestsCopy.getFirst().getWpm());
            return sortedTypingTests.getFirst().getWpm();
        }
        return 0;
    }

    public List<Challenge> getCompletedChallenges() {
//        return completedChallenges;
        return userChallengeList
                .stream()
                .filter(UserChallenge::isCompleted)
                .map(UserChallenge::getChallenge)
                .toList();
    }

    public void addCompletedChallenge(Challenge challenge) {
        completedChallenges.add(challenge);
    }

    public int calculateScore() {
        int score = 0;
        List<Challenge> completedChallenges = getCompletedChallenges();
        for (Challenge challenge : completedChallenges) {
            score += challenge.getScore();
        }
        return score;
    }

    public List<Challenge> getChallenges() {
        return challenges;
    }

    public void setChallenges(List<Challenge> challenges) {
        this.challenges = challenges;
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
}
