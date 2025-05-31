package models;

import services.LoggingService;
import services.NotificationService;

public interface NotificationListener {
    void update(String usernameWithBetterWpm, NotificationService notificationService, LoggingService loggingService);
}
