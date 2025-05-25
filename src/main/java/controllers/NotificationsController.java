package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import models.Notification;
import services.MainService;

import javax.swing.*;
import java.util.List;

public class NotificationsController {
    @FXML
    private ListView<Notification> notificationsListView;

    @FXML
    private Label notificationLabel;

    private List<Notification> notifications;
    private Notification currentNotification;
    private MainService mainService;
    private Label notificationsNumberLabel;


    public NotificationsController(List<Notification> notifications, Label notificationsNumberLabel,  MainService mainService) {
        this.notifications = notifications;
        this.notificationsNumberLabel = notificationsNumberLabel;
        this.mainService = mainService;
    }

    @FXML
    private void initialize() {
        notificationsListView.setCellFactory(listView -> new ListCell<Notification>() {
            private final Button deleteButton = new Button("❌");

            @Override
            protected void updateItem(Notification entry, boolean empty) {
                super.updateItem(entry, empty);

                if (empty || entry == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Label notificationText = new Label(entry.textInList());
                    deleteButton.setOnAction(event -> {
                        getListView().getItems().remove(entry);
//                        deleteNotification(entry);
                        mainService.currentUserRemoveNotification(entry);
                        int notificationsNumber = mainService.currentUserGetNotifications().size();
                        if (notificationsNumber == 1) {
                            notificationsNumberLabel.setText("1 notification");
                        } else {
                            notificationsNumberLabel.setText(notificationsNumber + " notifications");
                        }
                    });

                    HBox hbox = new HBox(10, deleteButton, notificationText);
                    setGraphic(hbox);
                }
            }
        });

        notificationsListView.getItems().addAll(notifications);

        notificationsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Notification>() {
            @Override
            public void changed(ObservableValue<? extends Notification> observableValue, Notification notification, Notification t1) {
                currentNotification = notificationsListView.getSelectionModel().getSelectedItem();
                notificationLabel.setText(currentNotification.textWhenSelected());

            }
        });
    }
}
