## KDV and User Management Panel - JavaFX Application

### 🎯 Project Purpose
This project is a desktop application developed using JavaFX. The application provides an administration panel that can perform KDV calculations, provide user management and manage system settings. It is an application that can be used for data entry personnel, accountants and managers in companies.

▶ [Graduation Project Presentation: KDV and User Management Panel](https://github.com/birsel-epik/javafx/tree/main/pdf/JavaFX-VAT-and-User-Management-Panel.pdf)


---
### ✨ Features
**Role-Based Authorization:** Users can log in with different roles such as ADMIN, OPERATOR, USER and different access levels are provided for each role.

- **User Management:** Users can be added, deleted, updated and their passwords can be reset.

- **KDV Calculation and Management:** KDV calculations can be made instantly and data can be exported to TXT, PDF, and EXCEL files.

- **Theme and Language Support:** Dark and light theme options and Turkish and English language support are available.

- **Notification System:** Success, error and warning messages can be displayed to the user as notifications.

- **Profile Management:** Users can change their passwords and update their profile information.

- **Backup and Restore:** Database backups can be taken and restored.

- **Time and Date Display:** Instant time and date information is displayed to the user.

- **Notepad:** Users can create their own notes and track their notes with a timer.

---

### 🧰 Technologies Used
Java Core

JavaFX (FXML + Internal CSS)

H2 Database (with JDBC)

SQL (For database operations)

BCrypt (For password security)

Apache POI, PDFBox (For data export)

JavaMail API (Optional, for email sending)

Timeline/ScheduledExecutorService (For scheduler usage)

JavaFX Chart (For graphical reporting)

---

### 🛠 Installation and Operation
#### Requirements:
Java 8 or later

IntelliJ IDEA (or other Java IDE)

H2 Database (For database)

---

### 📋 Steps
**1.** Clone this repository: [Java FX GitHub Address](https://github.com/birsel-epik/javafx)

```sh
git clone https://github.com/birsel-epik/javafx
```

**2.** Open the project files and install the necessary dependencies.

**3.** Start the H2 database (database connection configuration is in the SingletonPropertiesDBConnection.java file).

**4.** Run the HelloApplication.java file to run the application:

```sh
java -jar maven-wrapper.jar
```

**5.** After the application is opened, you can log in with your username and password (default user: admin/root).

---

### 📦 Modules
- **Login/Register:** User login and registration operations.

- **User Management:** User adding, updating, deleting operations.

- **KDV Calculation and Management:** KDV calculation and data export.

- **Theme and Language Support:** Dark/Light theme and Turkish/English language support.

- **Notification System:** Show notification messages.

- **Profile Management:** Update user profile information.

- **Backup & Restore:** Database backup and restore operations.

---

### 🚀 Extra Features
- **Graphical Reporting:** Show monthly KDV distribution with PieChart or BarChart.

- **WebView Integration:** Help document or guide display.

---

### 🗂 Project Structure

```sh
src/
├── controller/
│ └── AdminController.java, LoginController.java, RegisterController.java, ProfileController.java, KdvController.java, NotebookController.java, NotificationPopupController.java
├── dao/
│ └── UserDAO.java, KdvDAO.java, NotebookDAO.java, NotificationDAO.java ...
├── database/
│ └── SingletonPropertiesDBConnection.java
├── dto/
│ └── UserDTO.java, KdvDTO.java, NotebookDTO.java
├── exceptions/
│ └── RegisterNotFoundException
├── iofiles/
│ └── IFileHandlerInterface, SpecialFileHandler
├── utils/
│ └── ERole, ENoteCategory, FXMLPath, LanguageManager, SceneHelper, SpecialColor
├── view/
│ └── admin.fxml, login.fxml, register.fxml, kdv.fxml, notebook.fxml, notification-popup.fxml, profile.fxml
└── HelloApplication.java
└─ pdf
└── project-presentation.pdf
```

---
### 🤝 Contributions
Pull to contribute to this project You can send a request or submit your suggestions in the Issues section.