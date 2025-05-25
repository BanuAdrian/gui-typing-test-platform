package services;

import models.Achievement.Achievement;
import models.Challenge.Challenge;
import models.*;
import models.enums.LeaderboardType;
import models.enums.TextCategory;
import repositories.*;

import java.io.IOException;
import java.util.*;

public class MainService {
    private static final UserService userService = new UserService(new UserRepository());
    private static final TextService textService = new TextService(new TextRepository());
    private static final TypingTestService typingTestService = new TypingTestService(new TypingTestRepository());
    private static final AchievementService achievementService = new AchievementService(new AchievementRepository());
    private static final UserAchievementService userAchievementsService = new UserAchievementService(new UserAchievementRepository());
    private static final ChallengeService challengeService = new ChallengeService(new ChallengeRepository());
    private static final ChallengeTextService challengeTextService = new ChallengeTextService(new ChallengeTextRepository());
    private static final UserChallengeService userChallengeService = new UserChallengeService(new UserChallengeRepository());
    private static final NotificationService notificationService = new NotificationService(new NotificationRepository());

    private User currentUser;
    private final Map<String, User> users = new HashMap<>();
    private final Map<TextCategory, List<Text>> texts = new HashMap<>();
    private final List<Achievement> achievements = new ArrayList<>();
    private final List<Challenge> challenges = new ArrayList<>();

    private final Map<Integer, List<String>> wpmLeaderboard = new TreeMap<>(Comparator.reverseOrder());
    private final Map<Integer, List<String>> scoreLeaderboard = new TreeMap<>(Comparator.reverseOrder());

    public MainService(String username) {
        initExistingUsers();
        initTexts();
        initAchievements();
        initChallenges();
        initCurrentUser(username);
        updateWpmLeaderboard();
        updateScoreLeaderboard();
    }

    public void initExistingUsers() {
        List<User> existingUsers = userService.getUsers();
        for (var user : existingUsers) {
            users.put(user.getUsername(), user);
            user.getTypingTests().addAll(typingTestService.getTypingTestsForUser(user.getId()));
            user.getUserChallengeList().addAll(userChallengeService.getUserChallenges(user.getId()));
        }
        System.out.println(existingUsers);
    }

    public void initTexts() {
        for (TextCategory category : TextCategory.values()) {
            texts.put(category, new ArrayList<>());
        }

        List<Text> textList = textService.getTexts();
        for (var text : textList) {
            texts.get(text.getTextCategory()).add(text);
        }
    }

    public void initAchievements() {
        achievements.addAll(achievementService.getAchievements());
    }

    public void initChallenges() {
        challenges.addAll(challengeService.getChallenges());
        for (var challenge : challenges) {
            challenge.getChallengeTextList().addAll(challengeTextService.getChallengeTexts(challenge.getId()));
        }
    }

    public void initCurrentUser(String username) {
        if (users.get(username) != null) {
            currentUser = users.get(username);
            currentUserInitNotifications();
            currentUserInitAchievements();
            System.out.println("Welcome back, " + currentUser.getUsername() + "!");
        } else {
            currentUser = new User(username);
            userService.createUser(currentUser);
            users.put(currentUser.getUsername(), currentUser);
            System.out.println("Welcome, " + currentUser.getUsername() + "!");
        }
    }

    public void currentUserInitNotifications() {
        List<Notification> notificationList = notificationService.getUserNotifications(currentUser.getId());
        currentUser.getNotifications().addAll(notificationList);

        if (!currentUser.getNotifications().isEmpty()) {
            int numNotifications = currentUser.getNotifications().size();
            if (numNotifications == 1) {
                System.out.println("You have 1 notification waiting for you!");
            } else {
                System.out.println("You have " + currentUser.getNotifications().size() + " notifications waiting for you!");
            }
        }
    }

    public void currentUserInitAchievements() {
        currentUser.getUserAchievementList().addAll(userAchievementsService.getUserAchievements(currentUser.getId()));
    }

    public void updateScoreLeaderboard() {
//        scoreLeaderboard.put(currentUser.calculateScore(), currentUser.getUsername());
        scoreLeaderboard.clear();
        for (User user : users.values()) {
            int userScore = user.calculateScore();
            if (userScore > 0) {
                if (scoreLeaderboard.get(userScore) == null) {
                    scoreLeaderboard.put(userScore, new ArrayList<>(List.of(user.getUsername())));
                } else {
                    scoreLeaderboard.get(userScore).add(user.getUsername());
                }
            }
        }
    }

    public void updateWpmLeaderboard() {
        boolean initUpdate = wpmLeaderboard.isEmpty();
        int currentUserLastPlace = currentUserGetLeaderboardPlace();
        wpmLeaderboard.clear();

        for (User user : users.values()) {
            int userBestWpm = user.calculateBestWpm();
            if (userBestWpm > 0) {
                if (wpmLeaderboard.get(userBestWpm) == null) {
                    wpmLeaderboard.put(userBestWpm, new ArrayList<>(List.of(user.getUsername())));
                } else {
                    wpmLeaderboard.get(userBestWpm).add(user.getUsername());
                }
            }
        }

        int currentUserCurrentPlace = currentUserGetLeaderboardPlace();
        if (!initUpdate && (currentUserCurrentPlace < currentUserLastPlace)) {
            sendNotifications(currentUserCurrentPlace, currentUserLastPlace);
        }
    }

    public int currentUserGetLeaderboardPlace() {
        if (!wpmLeaderboard.isEmpty()) {
            int currentUserPlace = 1;
            for (var entry : wpmLeaderboard.entrySet()) {
                for (var username : entry.getValue()) {
                    if (username.equals(currentUser.getUsername())) {
                        return currentUserPlace;
                    }
                    currentUserPlace++;
                }
            }
        }
        return wpmLeaderboard
                .values()
                .stream()
                .flatMap(List::stream)
                .toList()
                .size() + 1;
    }

    public void sendNotifications(int currentUserCurrentPlace, int currentUserLastPlace) {
        var leaderboardUsernames = wpmLeaderboard
                .values()
                .stream()
                .flatMap(List::stream)
                .toList()
                .toArray();
        for (int i = currentUserCurrentPlace; i < currentUserLastPlace; ++i) {
            User userToNotify = users.get((String)leaderboardUsernames[i]);
            Notification notification = new Notification(userToNotify.getId(), "User " + currentUser.getUsername() + " beat your " + userToNotify.calculateBestWpm() + " WPM record!");
            if (userToNotify.getNotifications().add(notification)) {
                notificationService.createNotification(notification);
            }
        }
//        for (int i = currentUserCurrentPlace - 2; i >= 0; --i) {
//            User userToNotify = users.get((String)leaderboardUsernames[i]);
//
//            if (!Objects.equals(userToNotify, currentUser)) {
//                break;
//            } else {
//                Notification notification = new Notification(userToNotify.getId(), "User " + currentUser.getUsername() + " beat your " + userToNotify.calculateBestWpm() + " WPM record!");
//                if (userToNotify.getNotifications().add(notification)) {
//                    notificationService.createNotification(notification);
//                }
//            }
//        }
    }

    public List<LeaderboardEntry> getWpmLeaderboard() {
        List<LeaderboardEntry> leaderboardEntries = new ArrayList<>();
        int place = 1;
        for (var entry : wpmLeaderboard.entrySet()) {
            for (var username : entry.getValue()) {
                leaderboardEntries.add(new LeaderboardEntry(place, username, entry.getKey(), LeaderboardType.WPM));
                place++;
            }
        }
        return leaderboardEntries;
    }

    public List<LeaderboardEntry> getScoreLeaderboard() {
        List<LeaderboardEntry> leaderboardEntries = new ArrayList<>();
        int place = 1;
        for (var entry : scoreLeaderboard.entrySet()) {
            for (var username : entry.getValue()) {
                leaderboardEntries.add(new LeaderboardEntry(place, username, entry.getKey(), LeaderboardType.SCORE));
                place++;
            }
        }
        return leaderboardEntries;
    }

    public void currentUserRemoveNotification(Notification notificationToRemove) {
        currentUser.getNotifications().remove(notificationToRemove);
        notificationService.deleteNotification(notificationToRemove.getNotificationId());
    }

    public void checkAchievements() {
        List<Integer> userAchievementIds = userAchievementsService.getUserAchievementIds(currentUser.getId());
        for (Achievement achievement : achievements) {
            if (!userAchievementIds.contains(achievement.getId()) && achievement.isAchieved(currentUser)) {
                currentUser.getUserAchievementList().add(new UserAchievement(currentUser.getId(), achievement.getId(), currentUser, achievement));
                userAchievementsService.createUserAchievement(currentUser.getId(), achievement.getId());
            }
        }

    }

    public List<Achievement> currentUserGetAchievements() {
        return currentUser.getAchievements();
    }

    public List<Challenge> currentUserGetCompletedChallenges() {
        return currentUser.getCompletedChallenges();
    }


    public Set<Notification> currentUserGetNotifications() {
        return currentUser.getNotifications();
    }

    public void currentUserAddTypingTest(TypingSession typingSession) {
        TypingTest typingTest = new TypingTest(currentUser.getId(),typingSession.getText().getId(), typingSession.getWpm(), typingSession.getCorrectWords());
        currentUser.getTypingTests().add(typingTest);
        typingTestService.createTypingTest(typingTest);
        for (TypingTest test : currentUser.getTypingTests()) {
            System.out.println(test);
        }
        checkAchievements();
        updateWpmLeaderboard();
    }

    public void currentUserAddChallenge(Challenge challenge, TypingSession typingSession) {
        boolean challengeCompleted = challenge.isCompleted(typingSession);
        userChallengeService.createUserChallenge(currentUser.getId(), challenge.getId(), challengeCompleted);
        currentUser.getUserChallengeList().add(new UserChallenge(currentUser.getId(), challenge.getId(), challengeCompleted, challenge));
        if (challengeCompleted) {
            int scoreWon = challenge.getScore();
            System.out.println("Congratulations! You just won " + scoreWon + " score!");
//            currentUser.addCompletedChallenge(challenge);
            updateScoreLeaderboard();
        }
        System.out.println("wpm = " + typingSession.getWpm() + " sec = " + typingSession.getElapsedTimeSec());
    }

    public Text getRandomTextByCategory(TextCategory textCategory) {
        List<Text> textList = texts.get(textCategory);
        if (textList.isEmpty()) {
            return null;
        } else {
            return textList.get(new Random().nextInt(textList.size()));
        }
    }

    public Challenge getRandomChallenge() {
        if (challenges.isEmpty()) {
            return null;
        } else {
            return challenges.get(new Random().nextInt(challenges.size()));
        }
    }

    public Text getRandomChallengeText(Challenge challenge) {
        if (challenge.getTexts().isEmpty()) {
            return null;
        } else {
            return challenge.getTexts().get(new Random().nextInt(challenge.getTexts().size()));
        }
    }
}
