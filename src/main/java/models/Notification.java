package models;

import java.util.Objects;

public class Notification implements Listable {
    private int notificationId;
    private int userId;

    private String message;

    public Notification(int userId, String message) {
        this.userId = userId;
        this.message = message;
    }

    public Notification(int notificationId, int userId, String message) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.message = message;
    }


    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }

    @Override
    public String textWhenSelected() {
        return message;
    }

    @Override
    public String textInList() {
        return "Notification from " + message.split("\\s")[1];
    }
}
