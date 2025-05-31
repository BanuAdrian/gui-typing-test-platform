package repositories;

import models.Text;
import models.enums.TextCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TextRepository {
    private static TextRepository instance;

    private TextRepository() {}

    public static TextRepository getInstance() {
        if (instance == null) {
            instance = new TextRepository();
        }
        return instance;
    }

    public Optional<List<Text>> getTexts(Connection connection) {
        String sql = """
                select *
                from texts
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Text> textList = new ArrayList<>();
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String textCategory = resultSet.getString(2);
                    String content = resultSet.getString(3);
                    textList.add(new Text(id, content, TextCategory.valueOf(textCategory)));
                }
                return Optional.of(textList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}