package repositories;

import models.Achievement.Achievement;
import models.Achievement.SpeedAchievement;
import models.Achievement.TestsTakenAchievement;
import models.AchievementFactory.SpeedAchievementFactory;
import models.AchievementFactory.TestsTakenAchievementFactory;
import models.enums.AchievementType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AchievementRepository {
    private static AchievementRepository instance;
    private SpeedAchievementFactory speedAchievementFactory = new SpeedAchievementFactory();
    private TestsTakenAchievementFactory testsTakenAchievementFactory = new TestsTakenAchievementFactory();

    private AchievementRepository() {}

    public static AchievementRepository getInstance() {
        if (instance == null) {
            instance = new AchievementRepository();
        }
        return instance;
    }

    public Optional<List<Achievement>> getAchievements(Connection connection) {
        String sql = """
                select *
                from achievements
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Achievement> achievementList = new ArrayList<>();

                while (resultSet.next()) {
                    int achievementId = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    String description = resultSet.getString(3);
                    AchievementType achievementType = AchievementType.valueOf(resultSet.getString(4));
                    if (achievementType == AchievementType.SPEED) {
                        int requiredWpm = resultSet.getInt(5);
                        achievementList.add(speedAchievementFactory.createAchievement(achievementId, name, description, requiredWpm));
                    } else if (achievementType == AchievementType.TESTS_TAKEN) {
                        int requiredTests = resultSet.getInt(6);
                        achievementList.add(testsTakenAchievementFactory.createAchievement(achievementId, name, description, requiredTests));
                    }
                }
                return Optional.of(achievementList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}