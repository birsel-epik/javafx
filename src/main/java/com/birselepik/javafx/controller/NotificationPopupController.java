package com.birselepik.javafx.controller;

import com.birselepik.javafx.dao.NotificationDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class NotificationPopupController {

    public Label btnNotifications;

    @FXML
    private ListView<String> notificationListView;

    private static Stage popupStage;

    @FXML
    public void initialize() {
        List<String> notifications = NotificationDAO.getUnreadMessages();
        notificationListView.getItems().addAll(notifications);
    }

    @FXML
    private void closePopup() {
        if (popupStage != null) {
            popupStage.close();
        }
    }

    public static void show() {
        try {
            FXMLLoader loader = new FXMLLoader(NotificationPopupController.class.getResource("/com/birselepik/javafx/view/notification-popup.fxml"));
            Parent root = loader.load();

            popupStage = new Stage();
            popupStage.setTitle("Bildirimler");
            popupStage.setScene(new Scene(root));
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setAlwaysOnTop(true);
            popupStage.setResizable(false);
            popupStage.showAndWait();

            NotificationDAO.markAllAsRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
