package repositories;

import models.Achievement.SpeedAchievement;
import models.Achievement.TestsTakenAchievement;
import models.AchievementFactory.SpeedAchievementFactory;
import models.AchievementFactory.TestsTakenAchievementFactory;
import models.ChallengeFactory.SpeedChallengeFactory;
import models.User;
import models.UserAchievement;
import models.enums.AchievementType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserAchievementRepository {
    private static UserAchievementRepository instance;
    private SpeedAchievementFactory speedAchievementFactory = new SpeedAchievementFactory();
    private TestsTakenAchievementFactory testsTakenAchievementFactory = new TestsTakenAchievementFactory();

    private UserAchievementRepository() {}

    public static UserAchievementRepository getInstance() {
        if (instance == null) {
            instance = new UserAchievementRepository();
        }
        return instance;
    }

    public void createUserAchievement(int userId, int achievementId, Connection connection) {
        String sql = """
                insert into user_achievements (user_id, achievement_id)
                values (?, ?)
                """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, achievementId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<List<UserAchievement>> getUserAchievements(int userId, Connection connection) {
        String sql = """
                select *
                from user_achievements ua
                join users u on u.id = ua.user_id
                join achievements a on a.id = ua.achievement_id
                where u.id = ?
                """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                List<UserAchievement> userAchievementList = new ArrayList<>();
                User user = null;
                while (resultSet.next()) {
                    int achievementId = resultSet.getInt(2);
                    String username = resultSet.getString(4);
                    if (user == null) {
                        user = new User(userId, username);
                    }
                    String achievementName = resultSet.getString(6);
                    String achievementDescription = resultSet.getString(7);
                    AchievementType achievementType = AchievementType.valueOf(resultSet.getString(8));
                    if (achievementType == AchievementType.SPEED) {
                        int requiredWpm = resultSet.getInt(9);
                        userAchievementList.add(new UserAchievement(userId, achievementId, user, speedAchievementFactory.createAchievement(achievementId, achievementName, achievementDescription, requiredWpm)));
                    } else if (achievementType == AchievementType.TESTS_TAKEN) {
                        int requiredTests = resultSet.getInt(10);
                        userAchievementList.add(new UserAchievement(userId, achievementId, user, testsTakenAchievementFactory.createAchievement(achievementId, achievementName, achievementDescription, requiredTests)));
                    }
                }
                return Optional.of(userAchievementList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<List<Integer>> getUserAchievementIds(int userId, Connection connection) {
        String sql = """
                select achievement_id
                from user_achievements
                where user_id = ?
                """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Integer> userAchievementIds = new ArrayList<>();
                while (resultSet.next()) {
                    int achievementId = resultSet.getInt(1);
                    userAchievementIds.add(achievementId);
                }
                return Optional.of(userAchievementIds);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
