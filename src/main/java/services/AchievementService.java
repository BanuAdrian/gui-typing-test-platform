package services;

import config.ConnectionProvider;
import models.Achievement.Achievement;
import repositories.AchievementRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AchievementService {
    private static AchievementService instance;
    private final AchievementRepository achievementRepository;

    private AchievementService(AchievementRepository achievementRepository) {
        this.achievementRepository = achievementRepository;
    }

    public static AchievementService getInstance() {
        if (instance == null) {
            instance = new AchievementService(AchievementRepository.getInstance());
        }
        return instance;
    }

    public List<Achievement> getAchievements() {
        try (Connection connection = ConnectionProvider.getConnection()) {
            Optional<List<Achievement>> achievementsOptional = achievementRepository.getAchievements(connection);
            return achievementsOptional.orElse(Collections.emptyList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }
}
