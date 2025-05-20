package services;

import models.Achievement.Achievement;
import models.Challenge.Challenge;
import models.*;
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
    private Map<String, User> users = new HashMap<>();
    private Map<TextCategory, List<Text>> texts = new HashMap<>();
    private List<Achievement> achievements = new ArrayList<>();
    private List<Challenge> challenges = new ArrayList<>();

    private Map<Integer, String> wpmLeaderboard = new TreeMap<>(Comparator.reverseOrder());
    private Map<Integer, List<String>> scoreLeaderboard = new TreeMap<>(Comparator.reverseOrder());

//    private Map<User, Integer> leaderboard = new TreeMap<>((o1, o2) -> {
//        int compareValue = Integer.compare(o2.calculateBestWpm(), o1.calculateBestWpm());
//        if (compareValue == 0) {
//            compareValue = o1.getUsername().compareToIgnoreCase(o2.getUsername());
//        }
//        return compareValue;
//    });

//    private Map<User, Integer> scoreLeaderboard = new TreeMap<>((o1, o2) -> {
//        int compareValue = Integer.compare(o2.calculateScore(), o1.calculateScore());
//        if (compareValue == 0) {
//            compareValue = o1.getUsername().compareToIgnoreCase(o2.getUsername());
//        }
//        return compareValue;
//    });

    public MainService(String username) {
        List<User> userList = userService.getUsers();
        for (var user : userList) {
            users.put(user.getUsername(), user);

            List<TypingTest> typingTestList = typingTestService.getTypingTestsForUser(user.getId());
            user.getTypingTests().addAll(typingTestList);

            List<UserChallenge> userChallengeList = userChallengeService.getUserChallenges(user.getId());
            user.getUserChallengeList().addAll(userChallengeList);
        }
        System.out.println(userList);


        for (TextCategory category : TextCategory.values()) {
            texts.put(category, new ArrayList<>());
        }

        List<Text> textList = textService.getTexts();
        for (var text : textList) {
            texts.get(text.getTextCategory()).add(text);
        }

        achievements.addAll(achievementService.getAchievements());

        challenges.addAll(challengeService.getChallenges());
        for (var challenge : challenges) {
            challenge.getChallengeTextList().addAll(challengeTextService.getChallengeTexts(challenge.getId()));
        }

        if (users.get(username) != null) {
            currentUser = users.get(username);
            System.out.println("Welcome back, " + currentUser.getUsername() + "!");

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
            List<UserAchievement> userAchievementList = userAchievementsService.getUserAchievements(currentUser.getId());
            currentUser.getUserAchievementList().addAll(userAchievementList);
        } else {
            currentUser = new User(username);
            userService.createUser(currentUser);
            users.put(currentUser.getUsername(), currentUser);
            System.out.println("Welcome, " + currentUser.getUsername() + "!");
        }

        updateLeaderboard();
        updateScoreLeaderboard();
    }

//    public void testMenu() throws IOException {
//        boolean inMenu = true;
//        while (inMenu) {
//            Scanner scanner = new Scanner(System.in);
//            System.out.println("========== Test Menu ============");
//            System.out.println("1. Quotes");
//            System.out.println("2. Randomly Generated Words");
//            System.out.println("3. Repeating Words");
//            System.out.println("4. Main Menu");
//            System.out.println("=================================");
//            System.out.print("Enter your choice: ");
//            int choice = scanner.nextInt();
//            switch (choice) {
//                case 1:
//                    takeTest(getRandomTextByCategory(TextCategory.QUOTE));
//                    break;
//                case 2:
//                    takeTest(getRandomTextByCategory(TextCategory.RANDOM_WORDS));
//                    break;
//                case 3:
//                    takeTest(getRandomTextByCategory(TextCategory.REPEATING_WORDS));
//                    break;
//                case 4:
//                    inMenu = false;
//                    break;
//                default:
//                    System.out.println("Invalid choice!");
//                    break;
//            }
//        }
//    }

    public Text getRandomTextByCategory(TextCategory textCategory) {
        List<Text> textList = texts.get(textCategory);
        if (textList.isEmpty()) {
            return null;
        } else {
            return textList.get(new Random().nextInt(textList.size()));
        }
    }

    public void currentUserAddTypingTest(TypingSession typingSession) {
        TypingTest typingTest = new TypingTest(currentUser.getId(),typingSession.getText().getId(), typingSession.getWpm(), typingSession.getCorrectWords());
        currentUser.getTypingTests().add(typingTest);
        typingTestService.createTypingTest(typingTest);
        for (TypingTest test : currentUser.getTypingTests()) {
            System.out.println(test);
        }
        checkAchievements();
        updateLeaderboard();
    }

    public void takeChallenge() throws IOException {
//        Text text = getRandomTextByCategory(TextCategory.QUOTE);
//        Challenge challenge = challengeRepository.getRandomChallenge();
//        System.out.println(challenge);
//        TypingSession typingSession = new TypingSession(text);
//        typingSession.start();
//        if (challenge.isCompleted(typingSession)) {
//            int scoreWon = challenge.getScore();
//            System.out.println("Congratulations! You just won " + scoreWon + " score!");
//            currentUser.addCompletedChallenge(challenge);
//            updateScoreLeaderboard();
//        }
//        System.out.println("wpm = " + typingSession.getWpm() + " sec = " + typingSession.getElapsedTimeSec());
        Challenge challenge = getRandomChallenge();
        Text text = getRandomChallengeText(challenge);
        System.out.println(challenge);
        TypingSession typingSession = new TypingSession(text);
        typingSession.start();
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

    public void printCompletedChallenges() {
        List<Challenge> completedChallenges = currentUser.getCompletedChallenges();
        System.out.println("===== Completed Challenges ======");
        if (completedChallenges.isEmpty()) {
            System.out.println("You haven't completed any challenge yet!");
        } else {
            int index = 1;
            for (Challenge challenge : completedChallenges) {
                System.out.println(index + ". " + challenge);
                index++;
            }
        }
        System.out.println("=================================");
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

//    public void printScoreLeaderboard() {
//        System.out.println("======= Score Leaderboard ========");
//        if (scoreLeaderboard.isEmpty()) {
//            System.out.println("Score leaderboard is currently empty!");
//        } else {
//            int index = 1;
//            for (Map.Entry<User, Integer> leaderboardEntry : scoreLeaderboard.entrySet()) {
//                System.out.println(index + ". " + leaderboardEntry.getKey().getUsername() + " with " + leaderboardEntry.getValue() + " score");
//                index++;
//            }
//        }
//        System.out.println("=================================");
//    }

    public int currentUserGetLeaderboardPlace() {
        int currentUserPlace = 1;
        if (!wpmLeaderboard.isEmpty()) {
            for (var entry : wpmLeaderboard.entrySet()) {
                if (entry.getValue().equals(currentUser.getUsername())) {
                    break;
                }
                currentUserPlace++;
            }
        }
        return currentUserPlace;

//        var leaderboardUsers = leaderboard.keySet();
//        int currentUserPlace = 1;
//        if (!leaderboardUsers.isEmpty()) {
//            for (User user : leaderboardUsers) {
//                if (user == currentUser) {
//                    break;
//                }
//                currentUserPlace++;
//            }
//        }
//        return currentUserPlace;
    }

    public void sendNotifications(int currentUserCurrentPlace, int currentUserLastPlace) {
//        System.out.println("last place = " + currentUserLastPlace + " current place = " + currentUserCurrentPlace);
//        var leaderboardUsers = leaderboard.keySet().toArray();
//        for (int i = currentUserCurrentPlace; i < currentUserLastPlace; ++i) {
//            User userToNotify = (User)leaderboardUsers[i];
////            userToNotify.addNotification(new Notification("User " + currentUser.getUsername() + " beat your " + leaderboard.get(userToNotify) + " WPM record!"));
//            Notification notification = new Notification(userToNotify.getId(), "User " + currentUser.getUsername() + " beat your " + leaderboard.get(userToNotify) + " WPM record!");
//            if (userToNotify.getNotifications().add(notification)) {
//                notificationService.createNotification(notification);
//            }
//            //            userToNotify.getNotifications().add(new Notification(userToNotify.getId(), "User " + cu))
//        }
//        for (int i = currentUserCurrentPlace - 2; i >= 0; --i) {
//            User userToNotify = (User)leaderboardUsers[i];
//            if (!Objects.equals(leaderboard.get(userToNotify), leaderboard.get(currentUser))) {
//                break;
//            } else {
////                userToNotify.addNotification(new Notification("User " + currentUser.getUsername() + " beat your " + leaderboard.get(userToNotify) + " WPM record!"));
//                Notification notification = new Notification(userToNotify.getId(), "User " + currentUser.getUsername() + " beat your " + leaderboard.get(userToNotify) + " WPM record!");
//                if (userToNotify.getNotifications().add(notification)) {
//                    notificationService.createNotification(notification);
//                }
//            }
//        }

        var leaderboardUsernames = wpmLeaderboard.values().toArray();
        for (int i = currentUserCurrentPlace; i < currentUserLastPlace; ++i) {
            User userToNotify = users.get((String)leaderboardUsernames[i]);
            Notification notification = new Notification(userToNotify.getId(), "User " + currentUser.getUsername() + " beat your " + userToNotify.calculateBestWpm() + " WPM record!");
            if (userToNotify.getNotifications().add(notification)) {
                notificationService.createNotification(notification);
            }
        }
        for (int i = currentUserCurrentPlace - 2; i >= 0; --i) {
            User userToNotify = users.get((String)leaderboardUsernames[i]);

            if (!Objects.equals(userToNotify, currentUser)) {
                break;
            } else {
                Notification notification = new Notification(userToNotify.getId(), "User " + currentUser.getUsername() + " beat your " + userToNotify.calculateBestWpm() + " WPM record!");
                if (userToNotify.getNotifications().add(notification)) {
                    notificationService.createNotification(notification);
                }
            }
        }
    }

    public void updateLeaderboard() {
        int currentUserLastPlace = currentUserGetLeaderboardPlace();
        wpmLeaderboard.clear();

        for (User user : users.values()) {
            int userBestWpm = user.calculateBestWpm();
            if (userBestWpm > 0) {
                wpmLeaderboard.put(userBestWpm, user.getUsername());
            }
        }

//        leaderboard.clear();
//        for (User user : users.values()) {
//            if (user.calculateBestWpm() > 0) {
//                leaderboard.put(user, user.calculateBestWpm());
//            }
//        }
        int currentUserCurrentPlace = currentUserGetLeaderboardPlace();
        if (currentUserCurrentPlace < currentUserLastPlace) {
            sendNotifications(currentUserCurrentPlace, currentUserLastPlace);
        }
    }

    public List<String> getWpmLeaderboard() {
        List<String> leaderboardString = new ArrayList<>();
//        for (var entry : leaderboard.entrySet()) {
//            leaderboardString.add(entry.getKey() + ": " + entry.getValue());
//        }
        for (var entry : wpmLeaderboard.entrySet()) {
            leaderboardString.add(entry.getKey() + ": " + entry.getValue());
        }
        return leaderboardString;
    }

    public List<String> getScoreLeaderboard() {
        List<String> leaderboardString = new ArrayList<>();
//        for (var entry : leaderboard.entrySet()) {
//            leaderboardString.add(entry.getKey() + ": " + entry.getValue());
//        }
        for (var entry : scoreLeaderboard.entrySet()) {
            for (var username : entry.getValue()) {
                leaderboardString.add(entry.getKey() + ": " + username);
            }
        }
        return leaderboardString;
    }

//    public void printLeaderboard() {
//        System.out.println("========= Leaderboard ===========");
//        if (leaderboard.isEmpty()) {
//            System.out.println("Leaderboard is currently empty!");
//        } else {
//            int index = 1;
//            for (Map.Entry<User, Integer> leaderboardEntry : leaderboard.entrySet()) {
//                System.out.println(index + ". " + leaderboardEntry.getKey().getUsername() + " with " + leaderboardEntry.getValue() + " WPM");
//                index++;
//            }
//        }
//        System.out.println("=================================");
//    }

    public void printNotifications() {
        System.out.println("========= Notifications =========");
        if (currentUser.getNotifications().isEmpty()) {
            System.out.println("You don't have any new notifications!");
        } else {
            int index = 1;
            for (Notification notification : currentUser.getNotifications()) {
                System.out.println(index + ". " + notification.getMessage());
                index++;
            }
            promptNotificationsChoice();
        }
        System.out.println("=================================");
    }

    public Set<Notification> currentUserGetNotifications() {
        return currentUser.getNotifications();
    }

    public void promptNotificationsChoice() {
        System.out.println("\nType a notification number to read it or 0 to leave.");
        System.out.print("Enter your choice: ");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        if (choice > 0) {
            int notificationIndex = choice - 1;
            if (notificationIndex >= currentUser.getNotifications().size()) {
                System.out.println("Invalid number!");
            } else {
                currentUser.removeNotification(notificationIndex);
            }
        }
    }

    public void checkAchievements() {
//        for (Achievement achievement : achievements) {
//            if (!currentUser.getAchievements().contains(achievement) && achievement.isAchieved(currentUser)) {
//                // ??????// addAchievement vs get + add????
//                currentUser.addAchievement(achievement);
////                userAchievementsService.createUserAchievement(currentUser.getId(), achievement.getId());
//            }
//        }

        List<Integer> userAchievementIds = userAchievementsService.getUserAchievementIds(currentUser.getId());
        for (Achievement achievement : achievements) {
            if (!userAchievementIds.contains(achievement.getId()) && achievement.isAchieved(currentUser)) {
                currentUser.addAchievement(achievement);
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

    public void printAchievements() {
        List<Achievement> achievements = currentUser.getAchievements();
        System.out.println("========= Achievements ==========");
        if (achievements.isEmpty()) {
            System.out.println("You haven't unlocked any achievements yet!");
        } else {
            for (Achievement achievement : achievements) {
                System.out.println(achievement);
            }
        }
        System.out.println("=================================");
    }

}
