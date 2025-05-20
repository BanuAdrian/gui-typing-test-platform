package repositories;

import models.ChallengeText;
import models.Text;
import models.enums.TextCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChallengeTextRepository {
    public Optional<List<ChallengeText>> getChallengeTexts(int challengeId, Connection connection) {
        String sql = """
                select t.id, t.text_category, t.content
                from challenge_texts ct
                join challenges c on c.id = ct.challenge_id
                join texts t on t.id = ct.text_id
                where ct.challenge_id = ?
                """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, challengeId);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                List<ChallengeText> challengeTextList = new ArrayList<>();

                while (resultSet.next()) {
                    int textId = resultSet.getInt(1);
                    TextCategory textCategory = TextCategory.valueOf(resultSet.getString(2));
                    String content = resultSet.getString(3);
                    challengeTextList.add(new ChallengeText(challengeId, textId, new Text(textId, content, textCategory)));
                }

                return Optional.of(challengeTextList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


}
