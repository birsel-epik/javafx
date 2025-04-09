package com.birselepik.javafx.controller;

import com.birselepik.javafx.dao.NotebookDAO;
import com.birselepik.javafx.dto.NotebookDTO;
import com.birselepik.javafx.dto.UserDTO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class NotebookController {

    private final NotebookDAO notebookDAO = new NotebookDAO();

    @FXML private TableView<NotebookDTO> notebookTable;
    @FXML private TableColumn<NotebookDTO, Integer> idColumnNotebook;
    @FXML private TableColumn<NotebookDTO, String> titleColumn;
    @FXML private TableColumn<NotebookDTO, String> contentColumn;
    @FXML private TableColumn<NotebookDTO, LocalDate> createdDateColumn;
    //@FXML private TableColumn<NotebookDTO, LocalDate> updatedDateColumn;
    @FXML private TableColumn<NotebookDTO, String> categoryColumn;
    @FXML private TableColumn<NotebookDTO, Boolean> pinnedColumn;
    @FXML private TableColumn<NotebookDTO, UserDTO> userDTOColumn;
    @FXML private TextField searchField;


    @FXML
    public void initialize() {
        idColumnNotebook.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        contentColumn.setCellValueFactory(new PropertyValueFactory<>("content"));
        createdDateColumn.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
        //updatedDateColumn.setCellValueFactory(new PropertyValueFactory<>("updatedDate"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        pinnedColumn.setCellValueFactory(new PropertyValueFactory<>("pinned"));
        userDTOColumn.setCellValueFactory(new PropertyValueFactory<>("userDTO"));

        searchField.textProperty().addListener((obs, oldVal, newVal) -> applyFilter());
        refreshNotebookTable();
    }


    // tabloyu yenile
    public void refreshNotebookTable() {
        Optional<List<NotebookDTO>> list = notebookDAO.list();
        list.ifPresent(data -> notebookTable.setItems(FXCollections.observableArrayList(data)));
    }

    //
    private void applyFilter() {
        String keyword = searchField.getText().trim().toLowerCase();
        Optional<List<NotebookDTO>> all = notebookDAO.list();
        List<NotebookDTO> filtered = all.orElse(List.of()).stream()
                .filter(note -> note.getTitle().toLowerCase().contains(keyword))
                .toList();
        notebookTable.setItems(FXCollections.observableArrayList(filtered));
    }


    // clearFilters
    @FXML
    public void clearFilters() {
        searchField.clear();
        refreshNotebookTable();
    }


    // addNotebook
    @FXML
    public void addNotebook(ActionEvent event) {
        NotebookDTO newNotebook = showNotebookForm(null);
        if (newNotebook != null && newNotebook.isValid()) {
            notebookDAO.create(newNotebook);
            refreshNotebookTable();
            showAlert("Başarılı", "Not eklendi.", Alert.AlertType.INFORMATION);
        }
    }


    // updaNotebook
    @FXML
    public void updateNotebook(ActionEvent event) {
        NotebookDTO selected = notebookTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Uyarı", "Güncellenecek bir kayıt seçin.", Alert.AlertType.WARNING);
            return;
        }
        NotebookDTO updated = showNotebookForm(selected);
        if (updated != null && updated.isValid()) {
            notebookDAO.update(selected.getId(), updated);
            refreshNotebookTable();
            showAlert("Başarılı", "Not güncellendi.", Alert.AlertType.INFORMATION);
        }
    }


    // deleteKdv
    @FXML
    public void deleteNotebook(ActionEvent event) {
        NotebookDTO selected = notebookTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Uyarı", "Silinecek bir kayıt seçin.", Alert.AlertType.WARNING);
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Silmek istiyor musunuz?", ButtonType.OK, ButtonType.CANCEL);
        confirm.setHeaderText("Kayıt: " + selected.getTitle());
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            notebookDAO.delete(selected.getId());
            refreshNotebookTable();
            showAlert("Silindi", "Not silindi.", Alert.AlertType.INFORMATION);
        }
    }


    //showAlert
    private void showAlert(String title, String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }


    // showNotebookForm
    private NotebookDTO showNotebookForm(NotebookDTO existing) {
        Dialog<NotebookDTO> dialog = new Dialog<>();
        dialog.setTitle(existing == null ? "Yeni Not Ekle" : "Not Güncelle");

        TextField titleField = new TextField();
        TextField contentField = new TextField();
        DatePicker createdDateField = new DatePicker(LocalDate.now());
        CheckBox pinnedField = new CheckBox();
        TextField userDTOField = new TextField();
        ComboBox<String> categoryCombo = new ComboBox<>();
        categoryCombo.getItems().addAll("Kişisel", "İş", "Okul");
        categoryCombo.setValue("Kişisel");

        if (existing != null) {
            titleField.setText(String.valueOf(existing.getTitle()));
            contentField.setText(String.valueOf(existing.getContent()));
            categoryCombo.setValue(existing.getCategory());
        }

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        grid.addRow(0, new Label("Başlık:"), titleField);
        grid.addRow(1, new Label("İçerik:"), contentField);
        grid.addRow(2, new Label("Kategori:"), categoryCombo);
        grid.addRow(3, new Label("Tarih:"), createdDateField);
        grid.addRow(4, new Label("Sabitle:"), pinnedField);
        grid.addRow(5, new Label("Kullanıcı:"), userDTOField);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(btn -> {
            if (btn == ButtonType.OK) {
                try {
                    return NotebookDTO.builder()
                            .title(titleField.getText())
                            .content(contentField.getText())
                            .category(categoryCombo.getValue())
                            .pinned(Boolean.parseBoolean(pinnedField.getTypeSelector()))
                            //.userDTO(userDTOField.getUserData())
                            .build();
                } catch (Exception e) {
                    showAlert("Hata", "Geçersiz veri girdiniz!", Alert.AlertType.ERROR);
                }
            }
            return null;
        });

        Optional<NotebookDTO> result = dialog.showAndWait();
        return result.orElse(null);
    }



}
