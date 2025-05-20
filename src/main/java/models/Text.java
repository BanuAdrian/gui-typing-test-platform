package models;

import models.enums.TextCategory;

public class Text {
    private int id;
    private final String content;
    private final TextCategory textCategory;


    public Text(int id, String content, TextCategory textCategory) {
        this.id = id;
        this.content = content;
        this.textCategory = textCategory;
    }

    public Text(String content, TextCategory textCategory) {
        this.content = content;
        this.textCategory = textCategory;
    }

    public String getContent() {
        return content;
    }

    public TextCategory getTextCategory() {
        return textCategory;
    }

    @Override
    public String toString() {
        return "Text{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", textCategory=" + textCategory +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}