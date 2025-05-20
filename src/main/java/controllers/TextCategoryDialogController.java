package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import models.enums.TextCategory;

public class TextCategoryDialogController {
    @FXML
    private Button quotesButton, repeatingWordsButton, randomWordsButton;

    private TextCategory textCategory;

    @FXML
    private void initialize() {
        quotesButton.setOnAction(event -> chooseTextCategory(TextCategory.QUOTE));
        repeatingWordsButton.setOnAction(event -> chooseTextCategory(TextCategory.REPEATING_WORDS));
        randomWordsButton.setOnAction(event -> chooseTextCategory(TextCategory.RANDOM_WORDS));
    }

    private void chooseTextCategory(TextCategory textCategory) {
        this.textCategory = textCategory;
        closeDialog();
    }

    private void closeDialog() {
        Stage stage = (Stage) quotesButton.getScene().getWindow();
        stage.close();
    }

    public TextCategory getTextCategory() {
        return textCategory;
    }
}
