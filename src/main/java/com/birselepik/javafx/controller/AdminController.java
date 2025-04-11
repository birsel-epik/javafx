package com.birselepik.javafx.controller;

import com.birselepik.javafx.dao.KdvDAO;
import com.birselepik.javafx.dao.NotebookDAO;
import com.birselepik.javafx.dao.UserDAO;
import com.birselepik.javafx.dto.KdvDTO;
import com.birselepik.javafx.dto.NotebookDTO;
import com.birselepik.javafx.dto.UserDTO;
import com.birselepik.javafx.utils.ERole;
import com.birselepik.javafx.utils.FXMLPath;
import com.birselepik.javafx.utils.LanguageManager;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.birselepik.javafx.utils.LanguageManager.currentLocale;


public class AdminController implements Initializable {

    private UserDAO userDAO;
    private KdvDAO kdvDAO;
    private NotebookDAO notebookDAO;

    public AdminController() {
        userDAO = new UserDAO();
        kdvDAO = new KdvDAO();
        notebookDAO = new NotebookDAO();
    }

    // User için
    @FXML private TableView<UserDTO> userTable;
    @FXML private TableColumn<UserDTO, Integer> idColumn;
    @FXML private TableColumn<UserDTO, String> usernameColumn;
    @FXML private TableColumn<UserDTO, String> emailColumn;
    @FXML private TableColumn<UserDTO, String> passwordColumn;
    @FXML private TableColumn<UserDTO, String> roleColumn;
    //@FXML private ComboBox<String> roleComboBox; //// Sayfa açılır açılmaz geliyor
    @FXML private TextField searchField;
    @FXML private ComboBox<ERole> filterRoleComboBox;


    // KDV için
    @FXML private TableView<KdvDTO> kdvTable;
    @FXML private TableColumn<KdvDTO, Integer> idColumnKdv;
    @FXML private TableColumn<KdvDTO, Double> amountColumn;
    @FXML private TableColumn<KdvDTO, Double> kdvRateColumn;
    @FXML private TableColumn<KdvDTO, Double> kdvAmountColumn;
    @FXML private TableColumn<KdvDTO, Double> totalAmountColumn;
    @FXML private TableColumn<KdvDTO, String> receiptColumn;
    @FXML private TableColumn<KdvDTO, LocalDate> dateColumn;
    @FXML private TableColumn<KdvDTO, String> descColumn;
    @FXML private TextField searchKdvField;


    // NOTEBOOK için
    @FXML private TableView<NotebookDTO> notebookTable;
    @FXML private TableColumn<NotebookDTO, Integer> idColumnNotebook;
    @FXML private TableColumn<NotebookDTO, String> titleColumn;
    @FXML private TableColumn<NotebookDTO, String> contentColumn;
    @FXML private TableColumn<NotebookDTO, String> categoryColumn;
    @FXML private TableColumn<NotebookDTO, String> userDTOColumn;
    @FXML private TableColumn<NotebookDTO, String> pinnedColumn;
    @FXML private TextField searchNotebookField;


    // Language (TR - EN) - ResourceBundle
    @FXML private Menu menuFile;
    @FXML private Menu menuKDVTransactions;
    @FXML private Menu menuOtherTransactions;
    @FXML private Menu menuHelp;

    @FXML private MenuItem menuLogout;
    @FXML private MenuItem menuUser;
    @FXML private MenuItem menuAddUser;
    @FXML private MenuItem menuUpdateUser;
    @FXML private MenuItem menuDeleteUser;
    @FXML private MenuItem menuAddKDV;
    @FXML private MenuItem menuUpdateKDV;
    @FXML private MenuItem menuDeleteKDV;
    @FXML private MenuItem menuCalculator;
    @FXML private MenuItem menuNotebook;
    @FXML private MenuItem menuAbout;

    @FXML private Label labelTitle;
    @FXML private Label labelUserManagement;
    @FXML private Label labelTaxCalculation;
    @FXML private Label labelNotes;

    @FXML private Button themeToggleButton;
    @FXML private Button btnLanguage;
    @FXML private Button btnNotifications;
    @FXML private Button btnBackup;
    @FXML private Button btnRestore;
    @FXML private Button btnNotebook;
    @FXML private Button btnProfile;
    @FXML private Button btnLogout;
    @FXML private Button btnAddUser;
    @FXML private Button btnUpdateUser;
    @FXML private Button btnDeleteUser;
    @FXML private Button btnPrintUser;
    @FXML private Button btnAddKDV;
    @FXML private Button btnUpdateKDV;
    @FXML private Button btnDeleteKDV;
    @FXML private Button btnPrintKDV;
    @FXML private Button btnAddNote;
    @FXML private Button btnUpdateNote;
    @FXML private Button btnDeleteNote;
    @FXML private Button btnExportTxt;
    @FXML private Button btnExportPdf;
    @FXML private Button btnExportExcel;
    @FXML private Button btnMail;

    // for Theme
    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label clockLabel;


    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        // Theme
        URL cssUrl = getClass().getClassLoader().getResource("com/birselepik/javafx/css/light-theme.css");
        if (cssUrl != null) {
            String lightTheme = cssUrl.toExternalForm();
            Scene scene = rootPane.getScene();
            if (scene != null && !scene.getStylesheets().contains(lightTheme)) {
                scene.getStylesheets().add(lightTheme);
            }

            rootPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null && !newScene.getStylesheets().contains(lightTheme)) {
                    newScene.getStylesheets().add(lightTheme);
                }
            });
        } else {
            System.err.println("❌ Tema dosyası bulunamadı: light-theme.css");
        }


        // Language
        updateLanguage();


        // Zaman
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                    clockLabel.setText(now.format(formatter));
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        // KULLANICI İÇİN
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        // Rol filtreleme için ComboBox
        filterRoleComboBox.getItems().add(null); // boş seçenek: tüm roller
        filterRoleComboBox.getItems().addAll(ERole.values());
        filterRoleComboBox.setValue(null); // başlangıçta tüm roller

        // Arama kutusu dinleme
        searchField.textProperty().addListener((observable, oldVal, newVal) -> applyFilters());
        filterRoleComboBox.valueProperty().addListener((obs, oldVal, newVal) -> applyFilters());

        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        passwordColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String password, boolean empty) {
                super.updateItem(password, empty);
                setText((empty || password == null) ? null : "******");
            }
        });

        // Sayfa Açılır açılmaz geliyor
        //roleComboBox.setItems(FXCollections.observableArrayList("USER", "ADMIN", "MODERATOR"));
        //roleComboBox.getSelectionModel().select("USER");
        refreshTable();

        // KDV İÇİN
        // KDV tablosunu hazırla
        idColumnKdv.setCellValueFactory(new PropertyValueFactory<>("id"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        kdvRateColumn.setCellValueFactory(new PropertyValueFactory<>("kdvRate"));
        kdvAmountColumn.setCellValueFactory(new PropertyValueFactory<>("kdvAmount"));
        totalAmountColumn.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        receiptColumn.setCellValueFactory(new PropertyValueFactory<>("receiptNumber"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        searchKdvField.textProperty().addListener((obs, oldVal, newVal) -> applyKdvFilter());
        refreshKdvTable();


        // NOTBOOK İÇİN
        // NOT tablosunu hazırla
        idColumnNotebook.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        contentColumn.setCellValueFactory(new PropertyValueFactory<>("content"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        userDTOColumn.setCellValueFactory(cellData -> {
            UserDTO user = cellData.getValue().getUsername();
            return new SimpleStringProperty(user != null ? user.getUsername() : "");
        });

        pinnedColumn.setCellValueFactory(cellData -> {
            boolean pinnedValue = cellData.getValue().getPinned();
            return new SimpleStringProperty(pinnedValue ? "Evet" : "Hayır");
        });

        searchNotebookField.textProperty().addListener((obs, oldVal, newVal) -> applyNotebookFilter());
        refreshNotebookTable();

    }

    // KULLANICI
    private void applyFilters() {
        String keyword = searchField.getText().toLowerCase().trim();
        ERole selectedRole = filterRoleComboBox.getValue();

        Optional<List<UserDTO>> optionalUsers = userDAO.list();
        List<UserDTO> fullList = optionalUsers.orElseGet(List::of);

        List<UserDTO> filteredList = fullList.stream()
                .filter(user -> {
                    boolean matchesKeyword = keyword.isEmpty() ||
                            user.getUsername().toLowerCase().contains(keyword) ||
                            user.getEmail().toLowerCase().contains(keyword) ||
                            user.getRole().getDescription().toLowerCase().contains(keyword);

                    boolean matchesRole = (selectedRole == null) || user.getRole() == selectedRole;

                    return matchesKeyword && matchesRole;
                })
                .toList();

        userTable.setItems(FXCollections.observableArrayList(filteredList));
    }

    @FXML
    public void clearFilters() {
        searchField.clear();
        filterRoleComboBox.setValue(null);
    }

    // openKdvPane // todo: bunu sil
    @FXML
    public void openKdvPane() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/birselepik/javafx/view/kdv.fxml"));
            loader.setResources(ResourceBundle.getBundle("lang", currentLocale));

            Parent kdvRoot = loader.load();
            Stage stage = new Stage();
            stage.setTitle("KDV Paneli");
            stage.setScene(new Scene(kdvRoot));
            stage.show();
        } catch (IOException e) {
            showAlert("Hata", "KDV ekranı açılamadı!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    // openNotebookPane // todo: bbunu sil
    @FXML
    public void openNotebookPane() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/birselepik/javafx/view/notebook.fxml"));
            loader.setResources(ResourceBundle.getBundle("lang", currentLocale));

            Parent notebookRoot = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Not Paneli");
            stage.setScene(new Scene(notebookRoot));
            stage.show();
        } catch (IOException e) {
            showAlert("Hata", "Not ekranı açılamadı!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }



    @FXML
    private void refreshTable() {
        applyFilters();
        Optional<List<UserDTO>> optionalUsers = userDAO.list();
        List<UserDTO> userDTOList = optionalUsers.orElseGet(List::of);
        ObservableList<UserDTO> observableList = FXCollections.observableArrayList(userDTOList);
        userTable.setItems(observableList);
        showAlert("Bilgi", "Tablo başarıyla yenilendi!", Alert.AlertType.INFORMATION);
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void logout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Çıkış Yap");
        alert.setHeaderText("Oturumdan çıkmak istiyor musunuz?");
        alert.setContentText("Emin misiniz?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLPath.LOGIN));
                loader.setResources(ResourceBundle.getBundle("lang", currentLocale));

                Parent root = loader.load();
                Stage stage = (Stage) userTable.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                showAlert("Hata", "Giriş sayfasına yönlendirme başarısız!", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    public void printTable() {
        Printer printer = Printer.getDefaultPrinter();
        if (printer == null) {
            showAlert("Yazıcı Bulunamadı", "Yazıcı sistemde tanımlı değil.", Alert.AlertType.ERROR);
            return;
        }

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(userTable.getScene().getWindow())) {
            boolean success = job.printPage(userTable);
            if (success) {
                job.endJob();
                showAlert("Yazdırma", "Tablo başarıyla yazdırıldı.", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Yazdırma Hatası", "Yazdırma işlemi başarısız oldu.", Alert.AlertType.ERROR);
            }
        }
    }

    // Eğer uygulaman Linux/macOS'ta çalışabilir olacaksa, şu şekilde platform kontrolü de ekleyebilirsin:
    @FXML
    public void openCalculator() {
        String os = System.getProperty("os.name").toLowerCase();
        try {
            if (os.contains("win")) {
                Runtime.getRuntime().exec("calc");
            } else if (os.contains("mac")) {
                Runtime.getRuntime().exec("open -a Calculator");
            } else if (os.contains("nux")) {
                Runtime.getRuntime().exec("gnome-calculator"); // Linux için
            } else {
                showAlert("Hata", "Bu işletim sistemi desteklenmiyor!", Alert.AlertType.ERROR);
            }
        } catch (IOException e) {
            showAlert("Hata", "Hesap makinesi açılamadı.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }


    // todo: bunu sil
    @FXML
    public void openKdvCalculator() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("KDV Hesapla");
        dialog.setHeaderText("KDV Hesaplayıcı");

        TextField amountField = new TextField();
        ComboBox<String> kdvBox = new ComboBox<>();
        kdvBox.getItems().addAll("1%", "8%", "18%", "Özel");
        kdvBox.setValue("18%");
        TextField customKdv = new TextField(); customKdv.setDisable(true);
        TextField receiptField = new TextField();
        DatePicker datePicker = new DatePicker();
        Label resultLabel = new Label();

        kdvBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            customKdv.setDisable(!"Özel".equals(newVal));
            if (!"Özel".equals(newVal)) customKdv.clear();
        });

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        grid.addRow(0, new Label("Tutar:"), amountField);
        grid.addRow(1, new Label("KDV Oranı:"), kdvBox);
        grid.addRow(2, new Label("Özel Oran:"), customKdv);
        grid.addRow(3, new Label("Fiş No:"), receiptField);
        grid.addRow(4, new Label("Tarih:"), datePicker);
        grid.add(resultLabel, 0, 5, 2, 1);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(
                new ButtonType("Hesapla", ButtonBar.ButtonData.OK_DONE), ButtonType.CLOSE);

        dialog.setResultConverter(button -> {
            if (button.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                try {
                    double amount = Double.parseDouble(amountField.getText());
                    double rate = switch (kdvBox.getValue()) {
                        case "1%" -> 1;
                        case "8%" -> 8;
                        case "18%" -> 18;
                        default -> Double.parseDouble(customKdv.getText());
                    };
                    double kdv = amount * rate / 100;
                    double total = amount + kdv;

                    String result = String.format("""
                            Fiş No: %s
                            Tarih: %s
                            Ara Toplam: %.2f ₺
                            KDV (%%%.1f): %.2f ₺
                            Genel Toplam: %.2f ₺
                            """,
                            receiptField.getText(), datePicker.getValue(),
                            amount, rate, kdv, total);

                    resultLabel.setText(result);
                    showExportOptions(result);
                } catch (Exception e) {
                    showAlert("Hata", "Geçersiz giriş.", Alert.AlertType.ERROR);
                }
            }
            return null;
        });

        dialog.showAndWait();
    }


    // todo: burayı kontrol et
    private void showExportOptions(String content) {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("TXT", "TXT", "PDF", "EXCEL", "MAIL");
        dialog.setTitle("Dışa Aktar");
        dialog.setHeaderText("KDV sonucu nasıl dışa aktarılsın?");
        dialog.setContentText("Format:");
        dialog.showAndWait().ifPresent(choice -> {
            switch (choice) {
                case "TXT" -> exportAsTxt(content);
                case "PDF" -> exportAsPdf(content);
                case "EXCEL" -> exportAsExcel(content);
                case "MAIL" -> sendMail(content);
            }
        });
    }


    // todo: burayı kontrol et
    private void sendMail(String content) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("E-Posta Gönder");
        dialog.setHeaderText("KDV sonucunu göndereceğiniz e-posta adresini girin:");
        dialog.setContentText("E-posta:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(receiver -> {
            String senderEmail = "seninmailin@gmail.com"; // değiştir todo: burayı kontrol et
            String senderPassword = "uygulama-sifresi"; // değiştir todo: burayı kontrol et
            String host = "smtp.gmail.com";
            int port = 587;

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", port);

            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(senderEmail, senderPassword);
                }
            });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(senderEmail));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
                message.setSubject("KDV Hesaplama Sonucu");
                message.setText(content);

                Transport.send(message);

                showAlert("Başarılı", "Mail başarıyla gönderildi!", Alert.AlertType.INFORMATION);
            } catch (MessagingException e) {
                e.printStackTrace();
                showAlert("Hata", "Mail gönderilemedi.", Alert.AlertType.ERROR);
            }
        });
    }


    // todo: burayı kontrol et
    private void exportAsTxt(String content) {
        try {
            Path path = Paths.get(System.getProperty("user.home"), "Desktop",
                    "kdv_" + System.currentTimeMillis() + ".txt");
            Files.writeString(path, content);
            showAlert("Başarılı", "TXT masaüstüne kaydedildi", Alert.AlertType.INFORMATION);
        } catch (IOException e) {
            showAlert("Hata", "TXT kaydedilemedi.", Alert.AlertType.ERROR);
        }
    }

    // todo: burayı kontrol et
    private void exportAsPdf(String content) {
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);
            PDPageContentStream stream = new PDPageContentStream(doc, page);
            stream.beginText();
            stream.setFont(PDType1Font.HELVETICA, 12);
            stream.setLeading(14.5f);
            stream.newLineAtOffset(50, 750);

            for (String line : content.split("\n")) {
                String safeLine = line.replace("\t", "    ");
                stream.showText(safeLine);
                stream.newLine();
            }

            stream.endText();
            stream.close();

            File file = new File(System.getProperty("user.home") + "/Desktop/kdv_" + System.currentTimeMillis() + ".pdf");
            doc.save(file);
            showAlert("Başarılı", "PDF masaüstüne kaydedildi", Alert.AlertType.INFORMATION);
        } catch (IOException e) {
            showAlert("Hata", "PDF kaydedilemedi.", Alert.AlertType.ERROR);
        }
    }

    // todo: burayı kontrol et
    private void exportAsExcel(String content) {
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("KDV");

            // Stil tanımı (isteğe bağlı)
            var headerStyle = wb.createCellStyle();
            var font = wb.createFont();
            font.setBold(true);
            headerStyle.setFont(font);

            // Başlıkları yaz
            Row header = sheet.createRow(0);
            String[] headers = {"ID", "Tutar", "KDV Oranı", "KDV Tutarı", "Toplam", "Fiş No", "Tarih", "Açıklama"};
            for (int i = 0; i < headers.length; i++) {
                var cell = header.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Satırları yaz
            int rowNum = 1;
            for (KdvDTO kdv : kdvTable.getItems()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(kdv.getId());
                row.createCell(1).setCellValue(kdv.getAmount());
                row.createCell(2).setCellValue(kdv.getKdvRate());
                row.createCell(3).setCellValue(kdv.getKdvAmount());
                row.createCell(4).setCellValue(kdv.getTotalAmount());
                row.createCell(5).setCellValue(kdv.getReceiptNumber());
                row.createCell(6).setCellValue(String.valueOf(kdv.getTransactionDate()));
                row.createCell(7).setCellValue(kdv.getDescription());
            }

            // Otomatik sütun genişliği ayarla
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Kaydet
            File file = new File(System.getProperty("user.home") + "/Desktop/kdv_" + System.currentTimeMillis() + ".xlsx");
            try (FileOutputStream fos = new FileOutputStream(file)) {
                wb.write(fos);
            }

            showAlert("Başarılı", "Excel masaüstüne kaydedildi", Alert.AlertType.INFORMATION);
        } catch (IOException e) {
            showAlert("Hata", "Excel kaydedilemedi.", Alert.AlertType.ERROR);
        }
    }

    // todo: burayı kontrol et
    @FXML
    public void exportKdvAsTxt() {
        exportAsTxt(generateKdvSummary());
    }

    // todo: burayı kontrol et
    @FXML
    public void exportKdvAsPdf() {
        exportAsPdf(generateKdvSummary());
    }

    // todo: burayı kontrol et
    @FXML
    public void exportKdvAsExcel() {
        exportAsExcel(generateKdvSummary());
    }

    // todo: burayı kontrol et
    @FXML
    public void printKdvTable() {
        // Kdv tablosunu yazdır
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(kdvTable.getScene().getWindow())) {
            boolean success = job.printPage(kdvTable);
            if (success) {
                job.endJob();
                showAlert("Yazdırma", "KDV tablosu yazdırıldı.", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Hata", "Yazdırma başarısız.", Alert.AlertType.ERROR);
            }
        }
    }

    // todo: burayı kontrol et
    @FXML
    public void sendKdvByMail() {
        sendMail(generateKdvSummary());
    }


    // todo: burayı kontrol et
    private String generateKdvSummary() {
        StringBuilder builder = new StringBuilder();
        builder.append("ID\tTutar\tKDV Oranı\tKDV Tutarı\tToplam\tFiş No\tTarih\tAçıklama\n");
        for (KdvDTO kdv : kdvTable.getItems()) {
            builder.append(String.format("%d\t%.2f\t%.2f%%\t%.2f\t%.2f\t%s\t%s\t%s\n",
                    kdv.getId(),
                    kdv.getAmount(),
                    kdv.getKdvRate(),
                    kdv.getKdvAmount(),
                    kdv.getTotalAmount(),
                    kdv.getReceiptNumber(),
                    kdv.getTransactionDate(),
                    kdv.getDescription()));
        }
        return builder.toString();
    }

    // todo: burayı sil
    @FXML
    private void handleNew() {
        System.out.println("Yeni oluşturuluyor...");
    }

    // todo: burayı sil
    @FXML
    private void handleOpen() {
        System.out.println("Dosya açılıyor...");
    }

    // todo: burayı sil
    @FXML
    private void handleExit() {
        Platform.exit();
    }

    // todo: burayı sil
    @FXML
    private void goToUsers(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/path/to/user.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    // todo: burayı sil
    @FXML
    private void goToSettings(ActionEvent event) throws IOException {
       /* Parent root = FXMLLoader.load(getClass().getResource("/path/to/settings.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();*/
    }

    @FXML
    private void showAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(LanguageManager.get("alert.title.info"));
        alert.setHeaderText(LanguageManager.get("alert.header.success"));
        alert.setContentText(LanguageManager.get("alert.content.saved"));

    }


    ///  USER  //////////////////////////////////////////////////////////
    private static class AddUserDialog extends Dialog<UserDTO> {
        private final TextField usernameField = new TextField();
        private final PasswordField passwordField = new PasswordField();
        private final TextField emailField = new TextField();
        private final ComboBox<String> roleComboBox = new ComboBox<>();

        public AddUserDialog() {
            setTitle("Yeni Kullanıcı Ekle");
            setHeaderText("Yeni kullanıcı bilgilerini girin");

            // Manuel Ekleme
            //roleComboBox.getItems().addAll("USER", "ADMIN", "MODERATOR");
            //roleComboBox.setValue("USER");

            ComboBox<ERole> roleComboBox = new ComboBox<>();
            roleComboBox.getItems().addAll(ERole.values());
            roleComboBox.setValue(ERole.USER); // Varsayılan seçim


            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            grid.add(new Label("Kullanıcı Adı:"), 0, 0);
            grid.add(usernameField, 1, 0);
            grid.add(new Label("Şifre:"), 0, 1);
            grid.add(passwordField, 1, 1);
            grid.add(new Label("E-posta:"), 0, 2);
            grid.add(emailField, 1, 2);
            grid.add(new Label("Rol:"), 0, 3);
            grid.add(roleComboBox, 1, 3);

            getDialogPane().setContent(grid);

            ButtonType addButtonType = new ButtonType("Ekle", ButtonBar.ButtonData.OK_DONE);
            getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

            setResultConverter(dialogButton -> {
                if (dialogButton == addButtonType) {
                    return UserDTO.builder()
                            .username(usernameField.getText().trim())
                            .password(passwordField.getText().trim())
                            .email(emailField.getText().trim())
                            .role(roleComboBox.getValue())
                            .build();
                }
                return null;
            });
        }
    }

    @FXML
    public void addUser(ActionEvent actionEvent) {
        AddUserDialog dialog = new AddUserDialog();
        Optional<UserDTO> result = dialog.showAndWait();

        result.ifPresent(newUser -> {
            if (newUser.getUsername().isEmpty() || newUser.getPassword().isEmpty() || newUser.getEmail().isEmpty()) {
                showAlert("Hata", "Tüm alanlar doldurulmalı!", Alert.AlertType.ERROR);
                return;
            }

            if (userDAO.isUsernameExists(newUser.getUsername())) {
                showAlert("Uyarı", "Bu kullanıcı adı zaten kayıtlı!", Alert.AlertType.WARNING);
                return;
            }

            if (userDAO.isEmailExists(newUser.getEmail())) {
                showAlert("Uyarı", "Bu e-posta zaten kayıtlı!", Alert.AlertType.WARNING);
                return;
            }

            Optional<UserDTO> createdUser = userDAO.create(newUser);
            if (createdUser.isPresent()) {
                showAlert("Başarılı", "Kullanıcı başarıyla eklendi!", Alert.AlertType.INFORMATION);
                refreshTable();
            } else {
                showAlert("Hata", "Kullanıcı eklenemedi!", Alert.AlertType.ERROR);
            }
        });
    }


    // todo: burayı sil
    @FXML
    public void addUserEski(ActionEvent actionEvent) {
        // Sayfa açılır açılmaz geliyor
        //String role = roleComboBox.getValue();

        TextInputDialog usernameDialog = new TextInputDialog();
        usernameDialog.setTitle("Kullanıcı Ekle");
        usernameDialog.setHeaderText("Kullanıcı Adı");
        usernameDialog.setContentText("Yeni kullanıcı adı giriniz:");
        Optional<String> optionalUsername = usernameDialog.showAndWait();
        if (optionalUsername.isEmpty()) return;
        String username = optionalUsername.get().trim();

        if (userDAO.isUsernameExists(username)) {
            showAlert("Uyarı", "Bu kullanıcı adı zaten kayıtlı!", Alert.AlertType.WARNING);
            return;
        }

        TextInputDialog passwordDialog = new TextInputDialog();
        passwordDialog.setTitle("Kullanıcı Ekle");
        passwordDialog.setHeaderText("Şifre");
        passwordDialog.setContentText("Yeni şifre giriniz:");
        Optional<String> optionalPassword = passwordDialog.showAndWait();
        if (optionalPassword.isEmpty()) return;
        String password = optionalPassword.get().trim();

        TextInputDialog emailDialog = new TextInputDialog();
        emailDialog.setTitle("Kullanıcı Ekle");
        emailDialog.setHeaderText("E-posta");
        emailDialog.setContentText("Yeni e-posta giriniz:");
        Optional<String> optionalEmail = emailDialog.showAndWait();
        if (optionalEmail.isEmpty()) return;
        String email = optionalEmail.get().trim();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            showAlert("Hata", "Lütfen tüm alanları doldurun!", Alert.AlertType.ERROR);
            return;
        }

        if (userDAO.isEmailExists(email)) {
            showAlert("Uyarı", "Bu e-posta zaten kayıtlı!", Alert.AlertType.WARNING);
            return;
        }

        UserDTO newUser = UserDTO.builder()
                .username(username)
                .password(password)
                .email(email)
                //.role(role) //// Sayfa açılır açılmaz geliyor
                .build();

        Optional<UserDTO> createdUser = userDAO.create(newUser);
        if (createdUser.isPresent()) {
            showAlert("Başarılı", "Kullanıcı başarıyla eklendi!", Alert.AlertType.INFORMATION);
            refreshTable();
        } else {
            showAlert("Hata", "Kullanıcı eklenirken hata oluştu!", Alert.AlertType.ERROR);
        }
    }

    private static class UpdateUserDialog extends Dialog<UserDTO> {
        private final TextField usernameField = new TextField();
        private final PasswordField passwordField = new PasswordField();
        private final TextField emailField = new TextField();
        private final ComboBox<ERole> roleComboBox = new ComboBox<>();

        public UpdateUserDialog(UserDTO existingUser) {
            setTitle("Kullanıcı Güncelle");
            setHeaderText("Kullanıcı bilgilerini düzenleyin");

            usernameField.setText(existingUser.getUsername());
            emailField.setText(existingUser.getEmail());

            // 🔥 ENUM kullanımıyla rol listesi
            roleComboBox.getItems().addAll(ERole.values());

            // 🔥 Mevcut role'u enum olarak seç
            try {
                roleComboBox.setValue(ERole.fromString(String.valueOf(existingUser.getRole())));
            } catch (RuntimeException e) {
                roleComboBox.setValue(ERole.USER); // Yedek: varsayılan rol
            }

            // Layout
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            grid.add(new Label("Kullanıcı Adı:"), 0, 0);
            grid.add(usernameField, 1, 0);
            grid.add(new Label("Yeni Şifre:"), 0, 1);
            grid.add(passwordField, 1, 1);
            grid.add(new Label("E-posta:"), 0, 2);
            grid.add(emailField, 1, 2);
            grid.add(new Label("Rol:"), 0, 3);
            grid.add(roleComboBox, 1, 3);

            getDialogPane().setContent(grid);

            ButtonType updateButtonType = new ButtonType("Güncelle", ButtonBar.ButtonData.OK_DONE);
            getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

            // Sonuç döndür
            setResultConverter(dialogButton -> {
                if (dialogButton == updateButtonType) {
                    return UserDTO.builder()
                            .username(usernameField.getText().trim())
                            .password(passwordField.getText().trim().isEmpty()
                                    ? existingUser.getPassword()
                                    : passwordField.getText().trim())
                            .email(emailField.getText().trim())
                            .role(ERole.valueOf(roleComboBox.getValue().name())) // Enum’dan string’e dönüşüm
                            .build();
                }
                return null;
            });
        }
    }


    // todo: burayı sil
    @FXML
    public void updateUserEski(ActionEvent actionEvent) {
        UserDTO selectedUser = userTable.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            showAlert("Uyarı", "Lütfen güncellenecek bir kullanıcı seçin!", Alert.AlertType.WARNING);
            return;
        }

        TextInputDialog usernameDialog = new TextInputDialog(selectedUser.getUsername());
        usernameDialog.setTitle("Kullanıcı Adı Güncelle");
        usernameDialog.setHeaderText("Yeni kullanıcı adını girin:");
        Optional<String> newUsername = usernameDialog.showAndWait();
        if (newUsername.isEmpty()) return;

        TextInputDialog passwordDialog = new TextInputDialog();
        passwordDialog.setTitle("Şifre Güncelle");
        passwordDialog.setHeaderText("Yeni şifreyi girin:");
        Optional<String> newPassword = passwordDialog.showAndWait();
        if (newPassword.isEmpty()) return;

        TextInputDialog emailDialog = new TextInputDialog(selectedUser.getEmail());
        emailDialog.setTitle("Email Güncelle");
        emailDialog.setHeaderText("Yeni e-posta adresini girin:");
        Optional<String> newEmail = emailDialog.showAndWait();
        if (newEmail.isEmpty()) return;

        // Sayfa açılır açılmaz geliyor
        //String role = roleComboBox.getValue();

        UserDTO updatedUser = UserDTO.builder()
                .username(newUsername.get())
                .password(newPassword.get())
                .email(newEmail.get())
                //.role(role) //// Sayfa açılır açılmaz geliyor
                .build();

        Optional<UserDTO> result = userDAO.update(selectedUser.getId(), updatedUser);
        if (result.isPresent()) {
            showAlert("Başarılı", "Kullanıcı başarıyla güncellendi!", Alert.AlertType.INFORMATION);
            refreshTable();
        } else {
            showAlert("Hata", "Güncelleme sırasında hata oluştu!", Alert.AlertType.ERROR);
        }
    }


    @FXML
    public void updateUser(ActionEvent actionEvent) {
        UserDTO selectedUser = userTable.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            showAlert("Uyarı", "Lütfen güncellenecek bir kullanıcı seçin!", Alert.AlertType.WARNING);
            return;
        }

        UpdateUserDialog dialog = new UpdateUserDialog(selectedUser);
        Optional<UserDTO> result = dialog.showAndWait();

        result.ifPresent(updatedUser -> {
            if (updatedUser.getUsername().isEmpty() || updatedUser.getPassword().isEmpty() || updatedUser.getEmail().isEmpty()) {
                showAlert("Hata", "Tüm alanlar doldurulmalı!", Alert.AlertType.ERROR);
                return;
            }

            Optional<UserDTO> updated = userDAO.update(selectedUser.getId(), updatedUser);
            if (updated.isPresent()) {
                showAlert("Başarılı", "Kullanıcı güncellendi!", Alert.AlertType.INFORMATION);
                refreshTable();
            } else {
                showAlert("Hata", "Güncelleme işlemi başarısız!", Alert.AlertType.ERROR);
            }
        });
    }


    @FXML
    public void deleteUser(ActionEvent actionEvent) {
        Optional<UserDTO> selectedUser = Optional.ofNullable(userTable.getSelectionModel().getSelectedItem());
        selectedUser.ifPresent(user -> {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Silme Onayı");
            confirmationAlert.setHeaderText("Kullanıcıyı silmek istiyor musunuz?");
            confirmationAlert.setContentText("Silinecek kullanıcı: " + user.getUsername());
            Optional<ButtonType> isDelete = confirmationAlert.showAndWait();
            if (isDelete.isPresent() && isDelete.get() == ButtonType.OK) {
                Optional<UserDTO> deleteUser = userDAO.delete(user.getId());
                if (deleteUser.isPresent()) {
                    showAlert("Başarılı", "Kullanıcı başarıyla silindi", Alert.AlertType.INFORMATION);
                    refreshTable();
                } else {
                    showAlert("Başarısız", "Silme işlemi başarısız oldu", Alert.AlertType.ERROR);
                }
            }
        });
    }


    ///  KDV  //////////////////////////////////////////////////////////
    // 📄 Listeyi yenile
    private void refreshKdvTable() {
        Optional<List<KdvDTO>> list = kdvDAO.list();
        list.ifPresent(data -> kdvTable.setItems(FXCollections.observableArrayList(data)));
    }

    // 🔎 Arama filtreleme
    private void applyKdvFilter() {
        String keyword = searchKdvField.getText().trim().toLowerCase();
        Optional<List<KdvDTO>> all = kdvDAO.list();
        List<KdvDTO> filtered = all.orElse(List.of()).stream()
                .filter(kdv -> kdv.getReceiptNumber().toLowerCase().contains(keyword))
                .toList();
        kdvTable.setItems(FXCollections.observableArrayList(filtered));
    }

    // ➕ KDV ekle
    @FXML
    public void addKdv() {
        KdvDTO newKdv = showKdvForm(null);
        if (newKdv != null && newKdv.isValid()) {
            kdvDAO.create(newKdv);
            refreshKdvTable();
            showAlert("Başarılı", "KDV kaydı eklendi.", Alert.AlertType.INFORMATION);
        }
    }

    // ✏️ KDV güncelle
    @FXML
    public void updateKdv() {
        KdvDTO selected = kdvTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Uyarı", "Güncellenecek bir kayıt seçin.", Alert.AlertType.WARNING);
            return;
        }

        KdvDTO updated = showKdvForm(selected);
        if (updated != null && updated.isValid()) {
            kdvDAO.update(selected.getId(), updated);
            refreshKdvTable();
            showAlert("Başarılı", "KDV kaydı güncellendi.", Alert.AlertType.INFORMATION);
        }
    }

    // ❌ KDV sil
    @FXML
    public void deleteKdv() {
        KdvDTO selected = kdvTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Uyarı", "Silinecek bir kayıt seçin.", Alert.AlertType.WARNING);
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Silmek istiyor musunuz?", ButtonType.OK, ButtonType.CANCEL);
        confirm.setHeaderText("Fiş: " + selected.getReceiptNumber());
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            kdvDAO.delete(selected.getId());
            refreshKdvTable();
            showAlert("Silindi", "KDV kaydı silindi.", Alert.AlertType.INFORMATION);
        }
    }

    // 💬 Ortak form (ekle/güncelle)
    private KdvDTO showKdvForm(KdvDTO existing) {
        Dialog<KdvDTO> dialog = new Dialog<>();
        dialog.setTitle(existing == null ? "Yeni KDV Ekle" : "KDV Güncelle");

        TextField amountField = new TextField();
        TextField rateField = new TextField();
        TextField receiptField = new TextField();
        DatePicker datePicker = new DatePicker(LocalDate.now());
        TextField descField = new TextField();
        ComboBox<String> exportCombo = new ComboBox<>();
        exportCombo.getItems().addAll("TXT", "PDF", "EXCEL");
        exportCombo.setValue("TXT");

        if (existing != null) {
            amountField.setText(String.valueOf(existing.getAmount()));
            rateField.setText(String.valueOf(existing.getKdvRate()));
            receiptField.setText(existing.getReceiptNumber());
            datePicker.setValue(existing.getTransactionDate());
            descField.setText(existing.getDescription());
            exportCombo.setValue(existing.getExportFormat());
        }

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        grid.addRow(0, new Label("Tutar:"), amountField);
        grid.addRow(1, new Label("KDV Oranı (%):"), rateField);
        grid.addRow(2, new Label("Fiş No:"), receiptField);
        grid.addRow(3, new Label("Tarih:"), datePicker);
        grid.addRow(4, new Label("Açıklama:"), descField);
        grid.addRow(5, new Label("Format:"), exportCombo);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(btn -> {
            if (btn == ButtonType.OK) {
                try {
                    return KdvDTO.builder()
                            .amount(Double.parseDouble(amountField.getText()))
                            .kdvRate(Double.parseDouble(rateField.getText()))
                            .receiptNumber(receiptField.getText())
                            .transactionDate(datePicker.getValue())
                            .description(descField.getText())
                            .exportFormat(exportCombo.getValue())
                            .build();
                } catch (Exception e) {
                    showAlert("Hata", "Geçersiz veri!", Alert.AlertType.ERROR);
                }
            }
            return null;
        });

        Optional<KdvDTO> result = dialog.showAndWait();
        return result.orElse(null);
    }



    // BİTİRME PROJESİ

    // toggleTheme (light or dark)

    private Boolean isDarkMode = false;

    @FXML
    private void toggleTheme(ActionEvent event) {
        Scene scene = ((Node) event.getSource()).getScene();

        // CSS'leri temizle
        scene.getStylesheets().clear();

        // Tema yolunu ayarla
        String cssPath = isDarkMode ? "/com/birselepik/javafx/css/light-theme.css" : "/com/birselepik/javafx/css/dark-theme.css";
        URL cssUrl = getClass().getResource(cssPath);

        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
            isDarkMode = !isDarkMode;
            themeToggleButton.setText(isDarkMode ? "\uD83C\uDF1E Aydınlık Mod" : "\uD83C\uDF19 Karanlık Mod");
        } else {
            System.err.println("❌ Tema dosyası bulunamadı: " + cssPath);
        }
    }



    // Uygulamanın dili değiştirilecek (TR/EN)
    @FXML
    private void languageTheme(ActionEvent event) {
        String newLang = LanguageManager.getCurrentLocale().getLanguage().equals("tr") ? "en" : "tr";
        LanguageManager.changeLanguage(newLang);

        try {
            URL fxmlLocation = getClass().getResource("/com/birselepik/javafx/view/Admin.fxml");
            if (fxmlLocation == null) {
                throw new IOException("FXML dosyası bulunamadı!");
            }

            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            loader.setResources(ResourceBundle.getBundle("lang", currentLocale));
            Parent root = loader.load();

            Stage stage = (Stage) btnLanguage.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Translations
    private void updateLanguage() {
        menuFile.setText(LanguageManager.get("menu.file"));
        menuKDVTransactions.setText(LanguageManager.get("menu.KDVTransactions"));
        menuOtherTransactions.setText(LanguageManager.get("menu.otherTransactions"));
        menuHelp.setText(LanguageManager.get("menu.help"));

        menuLogout.setText(LanguageManager.get("menu.logout"));
        menuUser.setText(LanguageManager.get("menu.user"));
        menuAddUser.setText(LanguageManager.get("menu.addUser"));
        menuUpdateUser.setText(LanguageManager.get("menu.updateUser"));
        menuDeleteUser.setText(LanguageManager.get("menu.deleteUser"));
        menuAddKDV.setText(LanguageManager.get("menu.addKDV"));
        menuUpdateKDV.setText(LanguageManager.get("menu.updateKDV"));
        menuDeleteKDV.setText(LanguageManager.get("menu.deleteKDV"));
        menuCalculator.setText(LanguageManager.get("menu.calculator"));
        menuNotebook.setText(LanguageManager.get("menu.notebook"));
        menuAbout.setText(LanguageManager.get("menu.about"));

        labelTitle.setText(LanguageManager.get("panel.title"));
        labelUserManagement.setText(LanguageManager.get("panel.userManagement"));
        labelTaxCalculation.setText(LanguageManager.get("panel.taxCalculation"));
        labelNotes.setText(LanguageManager.get("panel.notes"));

        themeToggleButton.setText(LanguageManager.get("mode.dark"));
        btnLanguage.setText(LanguageManager.get("button.language"));
        btnNotifications.setText(LanguageManager.get("button.notifications"));

        btnBackup.setText(LanguageManager.get("button.backup"));
        btnRestore.setText(LanguageManager.get("button.restore"));
        btnNotebook.setText(LanguageManager.get("button.notebook"));
        btnProfile.setText(LanguageManager.get("button.profile"));
        btnLogout.setText(LanguageManager.get("button.logout"));

        btnAddUser.setText(LanguageManager.get("button.addUser"));
        btnUpdateUser.setText(LanguageManager.get("button.updateUser"));
        btnDeleteUser.setText(LanguageManager.get("button.deleteUser"));
        btnPrintUser.setText(LanguageManager.get("button.printUser"));

        btnAddKDV.setText(LanguageManager.get("button.addKDV"));
        btnUpdateKDV.setText(LanguageManager.get("button.updateKDV"));
        btnDeleteKDV.setText(LanguageManager.get("button.deleteKDV"));
        btnPrintKDV.setText(LanguageManager.get("button.printKDV"));

        btnAddNote.setText(LanguageManager.get("button.addNote"));
        btnUpdateNote.setText(LanguageManager.get("button.updateNote"));
        btnDeleteNote.setText(LanguageManager.get("button.deleteNote"));

        btnExportTxt.setText(LanguageManager.get("button.exportTxt"));
        btnExportPdf.setText(LanguageManager.get("button.exportPdf"));
        btnExportExcel.setText(LanguageManager.get("button.exportExcel"));
        btnMail.setText(LanguageManager.get("button.mail"));
    }


    @FXML
    private void showNotifications(ActionEvent event) {
        // Bildirimleri gösteren popup veya panel açılacak
    }

    @FXML
    private void showProfile(ActionEvent event) {
        // Kullanıcı profil bilgileri gösterilecek pencere
    }

    @FXML
    private void backupData(ActionEvent event) {
        // Veritabanı yedekleme işlemleri burada yapılacak
    }

    @FXML
    private void restoreData(ActionEvent event) {
        // Daha önce alınmış bir yedek dosyadan veri geri yüklenecek
    }

    ///  NOTEBOOK  //////////////////////////////////////////////////////////
    // 📄 Listeyi yenile
    private void refreshNotebookTable() {
        Optional<List<NotebookDTO>> list = notebookDAO.list();

        if (list.isPresent()) {
            List<NotebookDTO> data = list.get();
            System.out.println("Veritabanından gelen not sayısı: " + data.size());
            notebookTable.setItems(FXCollections.observableArrayList(data));
        } else {
            System.out.println("Veritabanından not listesi alınamadı.");
        }
    }


    // 🔎 Arama filtreleme
    private void applyNotebookFilter() {
        String keyword = searchNotebookField.getText().trim().toLowerCase();
        Optional<List<NotebookDTO>> all = notebookDAO.list();
        List<NotebookDTO> filtered = all.orElse(List.of()).stream()
                .filter(note -> note.getTitle().toLowerCase().contains(keyword))
                .toList();
        notebookTable.setItems(FXCollections.observableArrayList(filtered));
    }


    // ➕ NOT ekle
    @FXML
    public void addNotebook() {
        NotebookDTO newNotebook = openNotebookForm(null);
        if (newNotebook != null && newNotebook.isValid()) {
            notebookDAO.create(newNotebook);
            refreshNotebookTable();
            showAlert("Başarılı", "Not kaydı eklendi.", Alert.AlertType.INFORMATION);
        }
    }

    // ✏️ NOT güncelle
    @FXML
    public void updateNotebook() {
        NotebookDTO selected = notebookTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Uyarı", "Güncellenecek bir kayıt seçin.", Alert.AlertType.WARNING);
            return;
        }

        NotebookDTO updated = openNotebookForm(selected);
        if (updated != null && updated.isValid()) {
            notebookDAO.update(updated.getId(), updated);
            refreshNotebookTable();
            showAlert("Başarılı", "Not güncellendi.", Alert.AlertType.INFORMATION);
        }
    }

    // ❌ NOT sil
    @FXML
    public void deleteNotebook() {
        NotebookDTO selected = notebookTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Uyarı", "Silinecek bir kayıt seçin.", Alert.AlertType.WARNING);
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Silmek istiyor musunuz?", ButtonType.OK, ButtonType.CANCEL);
        confirm.setHeaderText("Title: " + selected.getTitle());
       /* Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle(LanguageManager.get("dialog.title.confirm"));
        confirm.setHeaderText(LanguageManager.get("dialog.header.delete"));
        confirm.setContentText(LanguageManager.get("dialog.content.areYouSure"));*/


        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            notebookDAO.delete(selected.getId());
            refreshNotebookTable();
            showAlert("Silindi", "Not kaydı silindi.", Alert.AlertType.INFORMATION);
        }
    }


    // 💬 Ortak form (ekle/güncelle)
    private NotebookDTO openNotebookForm(NotebookDTO existing) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/birselepik/javafx/view/notebook.fxml"));
            loader.setResources(ResourceBundle.getBundle("lang", currentLocale));
            Parent root = loader.load();

            NotebookController controller = loader.getController();
            controller.setNotebook(existing);

            Stage stage = new Stage();
            stage.setTitle(existing == null ? "Yeni Not" : "Not Güncelle");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            if (controller.isSaved()) {
                return controller.getNotebook();
            }
        } catch (IOException e) {
            showAlert("Hata", "Form yüklenemedi.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
        return null;
    }


}
