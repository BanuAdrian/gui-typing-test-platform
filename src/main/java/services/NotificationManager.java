package services;

import models.NotificationListener;

import java.util.ArrayList;
import java.util.List;

public class NotificationManager {
    private List<NotificationListener> listeners = new ArrayList<>();

    void subscribe(NotificationListener listener) {
        listeners.add(listener);
    }

    void unsubscribe(NotificationListener listener) {
        listeners.remove(listener);
    }

    void unsubscribeAll() {
        listeners.clear();
    }

    void notify(String usernameWithBetterWpm, NotificationService notificationService, LoggingService loggingService) {
        for (var listener : listeners) {
            listener.update(usernameWithBetterWpm, notificationService, loggingService);
        }
    }
}
