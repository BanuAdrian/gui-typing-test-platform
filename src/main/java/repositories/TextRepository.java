package repositories;//package repositories;
//
//import models.Text;
//import models.enums.TextCategory;
//
//import java.util.*;
//
//public class TextRepository {
//    private Map<TextCategory, List<Text>> standardTexts;
//
//    public TextRepository() {
//        standardTexts = new HashMap<>();
//        for (TextCategory category : TextCategory.values()) {
//            standardTexts.put(category, new ArrayList<>());
//        }
//        standardTexts.get(TextCategory.QUOTE).add(new Text("It is not despair, for despair is only for those who see the end beyond all doubt. We do not.", TextCategory.QUOTE));
//        standardTexts.get(TextCategory.QUOTE).add(new Text("He who opens a school door, closes a prison. Education is the most powerful weapon which you can use to change the world.", TextCategory.QUOTE));
//        standardTexts.get(TextCategory.QUOTE).add(new Text("It was the best of times, it was the worst of times, it was the age of wisdom, it was the age of foolishness.", TextCategory.QUOTE));
//        standardTexts.get(TextCategory.QUOTE).add(new Text("The world breaks everyone, and afterward, many are strong at the broken places. But those that will not break it kills.", TextCategory.QUOTE));
//        standardTexts.get(TextCategory.QUOTE).add(new Text("Hope is a good thing, maybe the best of things, and no good thing ever dies.", TextCategory.QUOTE));
//        standardTexts.get(TextCategory.RANDOM_WORDS).add(new Text("harmony whisper venture dusk illuminate breeze summit wander serendipity gratitude", TextCategory.RANDOM_WORDS));
//        standardTexts.get(TextCategory.REPEATING_WORDS).add(new Text("toy toy toy toy toy toy toy toy toy toy toy toy toy toy toy toy toy toy toy toy", TextCategory.REPEATING_WORDS));
//    }
//
//    public Text getRandomTextByCategory(TextCategory textCategory) {
//        List<Text> texts = standardTexts.get(textCategory);
//        if (texts.isEmpty()) {
//            return null;
//        } else {
//            return texts.get(new Random().nextInt(texts.size()));
//        }
//    }
//
////    public void addCommunityText(model.Text communityText) {
////        communityTexts.add(communityText);
////    }
//}


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