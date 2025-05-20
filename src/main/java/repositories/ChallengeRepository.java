package repositories;

import models.Challenge.Challenge;
import models.Challenge.SpeedChallenge;
import models.Challenge.TimeChallenge;
import models.enums.ChallengeType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//public class ChallengeRepository {
//    private final List<Challenge> challenges = new ArrayList<>();
//
//    public ChallengeRepository() {
//        challenges.add(new SpeedChallenge(10, 50));
//        challenges.add(new SpeedChallenge(15, 60));
//        challenges.add(new SpeedChallenge(25, 70));
//        challenges.add(new SpeedChallenge(50, 80));
//        challenges.add(new SpeedChallenge(75, 90));
//        challenges.add(new SpeedChallenge(100, 100));
//        challenges.add(new TimeChallenge(100, 10));
//        challenges.add(new TimeChallenge(75, 15));
//        challenges.add(new TimeChallenge(50, 20));
//        challenges.add(new TimeChallenge(25, 25));
//    }
//
//    public Challenge getRandomChallenge() {
//        return challenges.get(new Random().nextInt(challenges.size()));
//    }
//}


public class ChallengeRepository {

    public Optional<List<Challenge>> getChallenges(Connection connection) {
        String sql = """
                select *
                from challenges
                """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Challenge> challengeList = new ArrayList<>();
                while (resultSet.next()) {
                    int challengeId = resultSet.getInt(1);
                    String challengeName = resultSet.getString(2);
                    String description = resultSet.getString(3);
                    ChallengeType challengeType = ChallengeType.valueOf(resultSet.getString(4));
                    int score = resultSet.getInt(5);
                    if (challengeType == ChallengeType.SPEED) {
                        int targetWpm = resultSet.getInt(6);
                        challengeList.add(new SpeedChallenge(challengeId, challengeName, description, score, targetWpm));
                    } else if (challengeType == ChallengeType.TIME) {
                        int targetSeconds = resultSet.getInt(7);
                        challengeList.add(new TimeChallenge(challengeId, challengeName, description, score, targetSeconds));
                    }
                }
                return Optional.of(challengeList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}