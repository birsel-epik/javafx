package com.birselepik.javafx.controller;

import com.birselepik.javafx.dto.UserDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ProfileController {

    @FXML
    private Label usernameLabel;
    @FXML
    private Label emailLabel;

    public void setUserInfo(UserDTO user) {
        usernameLabel.setText("Kullanıcı Adı: " + user.getUsername());
        emailLabel.setText("E-posta: " + user.getEmail());
    }
}
