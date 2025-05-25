package models.Achievement;

import models.Listable;
import models.User;

public abstract class Achievement implements Listable {
    protected int id;
    protected String name;
    protected String description;

    public Achievement(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Achievement(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public abstract boolean isAchieved(User user);

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String textWhenSelected() {
        return description;
    }

    @Override
    public String textInList() {
        return name;
    }
}