package services;

import config.ConnectionProvider;
import models.Notification;
import repositories.NotificationRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> getUserNotifications(int userId) {
        try(Connection connection = ConnectionProvider.getConnection()) {
            Optional<List<Notification>> notificationsOptional = notificationRepository.getUserNotifications(userId, connection);
            return notificationsOptional.orElse(Collections.emptyList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createNotification(Notification notification) {
        try(Connection connection = ConnectionProvider.getConnection()) {
            notificationRepository.createNotification(notification, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
