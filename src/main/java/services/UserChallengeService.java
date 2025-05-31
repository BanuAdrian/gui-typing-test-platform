package services;

import config.ConnectionProvider;
import models.UserChallenge;
import repositories.UserChallengeRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserChallengeService {
    private static UserChallengeService instance;
    private final UserChallengeRepository userChallengeRepository;

    private UserChallengeService(UserChallengeRepository userChallengeRepository) {
        this.userChallengeRepository = userChallengeRepository;
    }

    public static UserChallengeService getInstance() {
        if (instance == null) {
            instance = new UserChallengeService(UserChallengeRepository.getInstance());
        }
        return instance;
    }

    public List<UserChallenge> getUserChallenges(int userId) {
        try(Connection connection = ConnectionProvider.getConnection()) {
            Optional<List<UserChallenge>> userChallengesOptional = userChallengeRepository.getUserChallenges(userId, connection);
            return userChallengesOptional.orElse(Collections.emptyList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createUserChallenge(int userId, int challengeId, boolean isCompleted) {
        try(Connection connection = ConnectionProvider.getConnection()) {
            userChallengeRepository.createUserChallenge(userId, challengeId, isCompleted, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
