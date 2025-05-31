package services;

import config.ConnectionProvider;
import models.UserAchievement;
import repositories.UserAchievementRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserAchievementService {
    private static UserAchievementService instance;
    private final UserAchievementRepository userAchievementsRepository;

    private UserAchievementService(UserAchievementRepository userAchievementsRepository) {
        this.userAchievementsRepository = userAchievementsRepository;
    }

    public static UserAchievementService getInstance() {
        if (instance == null) {
            instance = new UserAchievementService(UserAchievementRepository.getInstance());
        }
        return instance;
    }

    public void createUserAchievement(int userId, int achievementId) {
        try(Connection connection = ConnectionProvider.getConnection()) {
            userAchievementsRepository.createUserAchievement(userId, achievementId, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<UserAchievement> getUserAchievements(int userId) {
        try(Connection connection = ConnectionProvider.getConnection()) {
            Optional<List<UserAchievement>> userAchievementsOptional = userAchievementsRepository.getUserAchievements(userId, connection);
            return userAchievementsOptional.orElse(Collections.emptyList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }

    public List<Integer> getUserAchievementIds(int userId) {
        try(Connection connection = ConnectionProvider.getConnection()) {
            Optional<List<Integer>> userAchievementIdsOptional = userAchievementsRepository.getUserAchievementIds(userId, connection);
            return userAchievementIdsOptional.orElse(Collections.emptyList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }
}
