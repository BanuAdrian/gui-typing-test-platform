package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import models.Notification;

import java.util.List;

public class NotificationsController {
    @FXML
    private ListView<Notification> notificationsListView;

    @FXML
    private Label notificationLabel;

    private List<Notification> notifications;
    private Notification currentNotification;


    public NotificationsController(List<Notification> notifications) {
        this.notifications = notifications;
    }

    @FXML
    private void initialize() {
        notificationsListView.getItems().addAll(notifications);

        notificationsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Notification>() {
            @Override
            public void changed(ObservableValue<? extends Notification> observableValue, Notification notification, Notification t1) {
                currentNotification = notificationsListView.getSelectionModel().getSelectedItem();
                notificationLabel.setText(currentNotification.getMessage());
            }
        });
    }
}
