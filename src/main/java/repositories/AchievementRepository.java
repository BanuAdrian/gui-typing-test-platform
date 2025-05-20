package repositories;

import models.Achievement.Achievement;
import models.Achievement.SpeedAchievement;
import models.Achievement.TestsTakenAchievement;
import models.enums.AchievementType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//public class AchievementRepository {
//    private final List<Achievement> achievements;
//
//    public AchievementRepository() {
//        achievements = new ArrayList<>();
//        achievements.add(new SpeedAchievement(50));
//        achievements.add(new SpeedAchievement(60));
//        achievements.add(new SpeedAchievement(70));
//        achievements.add(new SpeedAchievement(80));
//        achievements.add(new SpeedAchievement(90));
//        achievements.add(new SpeedAchievement(100));
//        achievements.add(new TestsTakenAchievement(1));
//        achievements.add(new TestsTakenAchievement(5));
//        achievements.add(new TestsTakenAchievement(10));
//    }
//
//    public List<Achievement> getAchievements() {
//        return achievements;
//    }
//}

public class AchievementRepository {
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
                        achievementList.add(new SpeedAchievement(achievementId, name, description, requiredWpm));
                    } else if (achievementType == AchievementType.TESTS_TAKEN) {
                        int requiredTests = resultSet.getInt(6);
                        achievementList.add(new TestsTakenAchievement(achievementId, name, description, requiredTests));
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