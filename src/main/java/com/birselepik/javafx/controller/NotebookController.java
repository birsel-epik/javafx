package com.birselepik.javafx.controller;

import com.birselepik.javafx.dao.UserDAO;
import com.birselepik.javafx.dto.NotebookDTO;
import com.birselepik.javafx.dto.UserDTO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

public class NotebookController {

    @FXML private TextField titleField;
    @FXML private TextArea contentField;
    @FXML private ComboBox<String> categoryField;
    @FXML private CheckBox pinnedField;
    @FXML private ComboBox<UserDTO> userField;
    @FXML private Button saveButton;

    private NotebookDTO notebookDTO;
    @Getter
    private boolean saved = false;


    public void initialize() {
        categoryField.getItems().addAll("Kişisel", "İş", "Okul");
        loadUsers();

        userField.setCellFactory(cb -> new ListCell<>() {
            @Override
            protected void updateItem(UserDTO user, boolean empty) {
                super.updateItem(user, empty);
                setText(empty || user == null ? null : user.getUsername());
            }
        });

        userField.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(UserDTO user, boolean empty) {
                super.updateItem(user, empty);
                setText(empty || user == null ? null : user.getUsername());
            }
        });
    }


    private void loadUsers() {
        UserDAO userDAO = new UserDAO();
        Optional<List<UserDTO>> usersOpt = userDAO.list();  // 👈 Bunu kullan
        if (usersOpt.isPresent()) {
            userField.setItems(FXCollections.observableArrayList(usersOpt.get()));
        }
    }



    // AdminController bu metotla veri gönderir
    public void setNotebook(NotebookDTO notebookDTO) {
        this.notebookDTO = notebookDTO;

        if (notebookDTO != null) {
            titleField.setText(notebookDTO.getTitle());
            contentField.setText(notebookDTO.getContent());
            categoryField.setValue(notebookDTO.getCategory());
            pinnedField.setSelected(notebookDTO.getPinned());
            userField.setValue(notebookDTO.getUsername());
        }
    }

    public NotebookDTO getNotebook() {
        return notebookDTO;
    }

    @FXML
    private void handleSave(ActionEvent event) {
        String title = titleField.getText().trim();
        String content = contentField.getText().trim();
        String category = categoryField.getValue();
        Boolean pinned = pinnedField.isSelected();
        UserDTO selectedUser = userField.getValue();
        System.out.println("Seçilen kullanıcı: " + selectedUser);

        if (title.isEmpty() || category == null || selectedUser == null) {
            showAlert("Hata", "Başlık, kategori ve kullanıcı seçilmelidir.", Alert.AlertType.ERROR);
            return;
        }

        if (notebookDTO == null) {
            notebookDTO = new NotebookDTO();
        }

        notebookDTO.setTitle(title);
        notebookDTO.setContent(content);
        notebookDTO.setCategory(category);
        notebookDTO.setPinned(pinned);
        notebookDTO.setUsername(selectedUser);

        saved = true;
        closeWindow();
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
