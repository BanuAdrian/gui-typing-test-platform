package repositories;

import models.Notification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NotificationRepository {
    private static NotificationRepository instance;

    private NotificationRepository() {}

    public static NotificationRepository getInstance() {
        if (instance == null) {
            instance = new NotificationRepository();
        }
        return instance;
    }

    public Optional<List<Notification>> getUserNotifications(int userId, Connection connection) {
        String sql = """
                select *
                from notifications
                where user_id = ?
                """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Notification> notificationList = new ArrayList<>();

                while (resultSet.next()) {
                    int notificationId = resultSet.getInt(1);
                    String message = resultSet.getString(3);

                    notificationList.add(new Notification(notificationId, userId, message));
                }

                return Optional.of(notificationList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void createNotification(Notification notification, Connection connection) {
        String sql = """
                insert into notifications(user_id, message)
                values(?, ?)
                """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, notification.getUserId());
            preparedStatement.setString(2, notification.getMessage());

            preparedStatement.executeUpdate();

            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    int generatedNotificationId = resultSet.getInt(1);
                    notification.setNotificationId(generatedNotificationId);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteNotification(int notificationId, Connection connection) {
        String sql = """
                delete from notifications
                where id = ?
                """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, notificationId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
