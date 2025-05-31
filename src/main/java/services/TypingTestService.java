package services;

import config.ConnectionProvider;
import models.TypingTest;
import repositories.TypingTestRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TypingTestService {
    private static TypingTestService instance;
    private final TypingTestRepository typingTestRepository;

    private TypingTestService(TypingTestRepository typingTestRepository) {
        this.typingTestRepository = typingTestRepository;
    }

    public static TypingTestService getInstance() {
        if (instance == null) {
            instance = new TypingTestService(TypingTestRepository.getInstance());
        }
        return instance;
    }

    public void createTypingTest(TypingTest typingTest) {
        try(Connection connection = ConnectionProvider.getConnection()){
            typingTestRepository.createTypingTest(typingTest, connection);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public List<TypingTest> getTypingTestsForUser(int userId) {
        try(Connection connection = ConnectionProvider.getConnection()) {
            Optional<List<TypingTest>> typingTestsOptional = typingTestRepository.getTypingTestsForUser(userId, connection);
            return typingTestsOptional.orElse(Collections.emptyList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }


}
