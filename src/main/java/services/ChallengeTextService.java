package services;

import config.ConnectionProvider;
import models.ChallengeText;
import repositories.ChallengeTextRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ChallengeTextService {
    private static ChallengeTextService instance;
    private final ChallengeTextRepository challengeTextRepository;

    private ChallengeTextService(ChallengeTextRepository challengeTextRepository) {
        this.challengeTextRepository = challengeTextRepository;
    }

    public static ChallengeTextService getInstance() {
        if (instance == null) {
            instance = new ChallengeTextService(ChallengeTextRepository.getInstance());
        }
        return instance;
    }

    public List<ChallengeText> getChallengeTexts(int challengeId) {
        try(Connection connection = ConnectionProvider.getConnection()) {
            Optional<List<ChallengeText>> challengeTextsOptional = challengeTextRepository.getChallengeTexts(challengeId, connection);
            return challengeTextsOptional.orElse(Collections.emptyList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
