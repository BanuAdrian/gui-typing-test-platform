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
    private final TypingTestRepository typingTestRepository;

    public TypingTestService(TypingTestRepository typingTestRepository) {
        this.typingTestRepository = typingTestRepository;
    }

    public void createTypingTest(TypingTest typingTest) {
        try(Connection connection = ConnectionProvider.getConnection()){
            typingTestRepository.createTypingTest(typingTest, connection);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }


    public List<TypingTest> getTypingTests() {
        try(Connection connection = ConnectionProvider.getConnection()) {
            Optional<List<TypingTest>> typingTestsOptional = typingTestRepository.getTypingTests(connection);
            return typingTestsOptional.orElse(Collections.emptyList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
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
