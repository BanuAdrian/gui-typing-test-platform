package services;

import config.ConnectionProvider;
import models.Challenge.Challenge;
import repositories.ChallengeRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ChallengeService {
    private static ChallengeService instance;
    private final ChallengeRepository challengeRepository;

    private ChallengeService(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }

    public static ChallengeService getInstance() {
        if (instance == null) {
            instance = new ChallengeService(ChallengeRepository.getInstance());
        }
        return instance;
    }

    public List<Challenge> getChallenges() {
        try(Connection connection = ConnectionProvider.getConnection()) {
            Optional<List<Challenge>> challengesOptional = challengeRepository.getChallenges(connection);
            return challengesOptional.orElse(Collections.emptyList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();

    }
}
