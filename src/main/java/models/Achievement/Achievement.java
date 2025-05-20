package models.Achievement;

//public class model.Achievement.Achievement {
//    private String name;
//    private String description;
//
//    public model.Achievement.Achievement(String name, String description) {
//        this.name = name;
//        this.description = description;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    @Override
//    public String toString() {
//        return name + " [" + description + "]";
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        model.Achievement.Achievement that = (model.Achievement.Achievement) o;
//        return Objects.equals(name, that.name) && Objects.equals(description, that.description);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(name, description);
//    }
//}

import models.User;

public abstract class Achievement {
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

    @Override
    public String toString() {
        return name;
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
}