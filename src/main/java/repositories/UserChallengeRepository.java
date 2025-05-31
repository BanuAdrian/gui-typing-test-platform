package repositories;

import models.Challenge.SpeedChallenge;
import models.Challenge.TimeChallenge;
import models.ChallengeFactory.SpeedChallengeFactory;
import models.ChallengeFactory.TimeChallengeFactory;
import models.UserChallenge;
import models.enums.ChallengeType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserChallengeRepository {
    private static UserChallengeRepository instance;
    private SpeedChallengeFactory speedChallengeFactory = new SpeedChallengeFactory();
    private TimeChallengeFactory timeChallengeFactory = new TimeChallengeFactory();

    private UserChallengeRepository() {}

    public static UserChallengeRepository getInstance() {
        if (instance == null) {
            instance = new UserChallengeRepository();
        }
        return instance;
    }

    public Optional<List<UserChallenge>> getUserChallenges(int userId, Connection connection) {
        String sql = """
                select *
                from user_challenges uc
                join challenges c on c.id = uc.challenge_id
                where uc.user_id = ?
                """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                List<UserChallenge> userChallengeList = new ArrayList<>();

                while (resultSet.next()) {
                    int challengeId = resultSet.getInt(3);
                    boolean isCompleted = resultSet.getBoolean(4);
                    String challengeName = resultSet.getString(6);
                    String description = resultSet.getString(7);
                    ChallengeType challengeType = ChallengeType.valueOf(resultSet.getString(8));
                    int score = resultSet.getInt(9);
                    if (challengeType == ChallengeType.SPEED) {
                        int targetWpm = resultSet.getInt(10);
                        userChallengeList.add(new UserChallenge(userId, challengeId, isCompleted, speedChallengeFactory.createChallenge(challengeId, challengeName, description, score, targetWpm)));
                    } else if (challengeType == ChallengeType.TIME) {
                        int targetSeconds = resultSet.getInt(11);
                        userChallengeList.add(new UserChallenge(userId, challengeId, isCompleted, timeChallengeFactory.createChallenge(challengeId, challengeName, description, score, targetSeconds)));
                    }
                }

                return Optional.of(userChallengeList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void createUserChallenge(int userId, int challengeId, boolean isCompleted, Connection connection) {
        String sql = """
                insert into user_challenges(user_id, challenge_id, is_completed)
                values(?, ?, ?)
                """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, challengeId);
            preparedStatement.setBoolean(3, isCompleted);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
