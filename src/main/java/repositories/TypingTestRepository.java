package repositories;

import models.TypingTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TypingTestRepository {
    private static TypingTestRepository instance;

    private TypingTestRepository() {}

    public static TypingTestRepository getInstance() {
        if (instance == null) {
            instance = new TypingTestRepository();
        }
        return instance;
    }

    public void createTypingTest(TypingTest typingTest, Connection connection) {
        String sql = """
                insert into typing_tests
                (user_id, text_id, wpm, correct_words)
                values
                (?, ?, ?, ?)
                """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, typingTest.getUserId());
            preparedStatement.setInt(2, typingTest.getTextId());
            preparedStatement.setInt(3, typingTest.getWpm());
            preparedStatement.setInt(4, typingTest.getCorrectWords());

            preparedStatement.executeUpdate();

            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    int generatedId = resultSet.getInt(1);
                    typingTest.setId(generatedId);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<List<TypingTest>> getTypingTestsForUser(int userId, Connection connection) {
        String sql = """
                select *
                from typing_tests
                where user_id = ?
                """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<TypingTest> typingTestList = new ArrayList<>();
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
//                    int userId = resultSet.getInt(2);
                    int textId = resultSet.getInt(3);
                    int wpm = resultSet.getInt(4);
                    int correctWords = resultSet.getInt(5);
                    typingTestList.add(new TypingTest(id, userId, textId, wpm, correctWords));
                }
                return Optional.of(typingTestList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


}
