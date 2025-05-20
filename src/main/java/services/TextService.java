package services;

import config.ConnectionProvider;
import models.Text;
import repositories.TextRepository;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class TextService {
    private final TextRepository textRepository;

    public TextService(TextRepository textRepository) {
        this.textRepository = textRepository;
    }

    public List<Text> getTexts() {
        try (Connection connection = ConnectionProvider.getConnection()) {
            Optional<List<Text>> textsOptional = textRepository.getTexts(connection);
            if (textsOptional.isPresent()) {
                return textsOptional.get();
            } else {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }
}
