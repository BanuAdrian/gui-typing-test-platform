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
    private final ChallengeTextRepository challengeTextRepository;

    public ChallengeTextService(ChallengeTextRepository challengeTextRepository) {
        this.challengeTextRepository = challengeTextRepository;
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
