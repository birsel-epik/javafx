package com.birselepik.javafx.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.List;

public class NotificationPopupController {

    @FXML
    private ListView<String> notificationListView;


    public void setNotifications(List<String> notifications) {
        notificationListView.getItems().addAll(notifications);
    }

    public static void show(List<String> notifications) {
        try {
            FXMLLoader loader = new FXMLLoader(NotificationPopupController.class.getResource("/com/birselepik/javafx/view/notification-popup.fxml"));
            AnchorPane pane = loader.load();

            NotificationPopupController controller = loader.getController();
            controller.setNotifications(notifications);

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Bildirimler");
            popupStage.setScene(new Scene(pane));
            popupStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
