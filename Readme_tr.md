## KDV ve Kullanıcı Yönetim Paneli - JavaFX Uygulaması

### 🎯 Proje Amacı
Bu proje, JavaFX kullanarak geliştirilmiş bir masaüstü uygulamasıdır. Uygulama, KDV hesaplamalarını yapabilen, kullanıcı yönetimini sağlayan ve sistem ayarlarını yöneten bir yönetim paneli sunmaktadır. Şirketlerde veri giriş personeli, muhasebeciler ve yöneticiler için kullanılabilecek bir uygulamadır.

▶ [Bitirme Projesi Sunumu: KDV ve Kullanıcı Yönetim Paneli](https://github.com/birsel-epik/javafx/tree/main/pdf/JavaFX-KDV-ve-Kullanici-Yonetim-Paneli.pdf)  

---
### ✨ Özellikler
**Rol Bazlı Yetkilendirme:** Kullanıcılar ADMIN, OPERATOR, USER gibi farklı rollerle giriş yapabilir ve her rol için farklı erişim seviyeleri sağlanır.

- **Kullanıcı Yönetimi:** Kullanıcılar eklenebilir, silinebilir, güncellenebilir ve şifreleri sıfırlanabilir.

- **KDV Hesaplama ve Yönetimi:** KDV hesaplamaları anında yapılabilir ve veriler TXT, PDF, ve EXCEL dosyalarına aktarılabilir.

- **Tema ve Dil Desteği:** Karanlık ve açık tema seçenekleri ve Türkçe ile İngilizce dil desteği mevcuttur.

- **Bildirim Sistemi:** Kullanıcıya başarı, hata ve uyarı mesajları bildirim olarak gösterilebilir.

- **Profil Yönetimi:** Kullanıcılar, şifre değişikliği yapabilir ve profil bilgilerini güncelleyebilir.

- **Yedekleme ve Geri Yükleme:** Veritabanı yedeği alınabilir ve geri yüklenebilir.

- **Saat ve Tarih Gösterimi:** Anlık saat ve tarih bilgisi kullanıcıya gösterilir.

- **Not Defteri:** Kullanıcılar kendi notlarını oluşturabilir ve zamanlayıcı ile notlarını takip edebilirler.

---

### 🧰 Kullanılan Teknolojiler
Java Core

JavaFX (FXML + Internal CSS)

H2 Database (JDBC ile)

SQL (Veritabanı işlemleri için)

BCrypt (Şifre güvenliği için)

Apache POI, PDFBox (Veri dışa aktarımı için)

JavaMail API (Opsiyonel, e-posta gönderimi için)

Timeline/ScheduledExecutorService (Zamanlayıcı kullanımı için)

JavaFX Chart (Grafiksel raporlama için)

---

### 🛠 Kurulum ve Çalıştırma
#### Gereksinimler:
Java 8 veya daha yeni bir sürüm

IntelliJ IDEA (veya başka bir Java IDE)

H2 Database (Veritabanı için)

---

### 📋 Adımlar
**1.** Bu repository'yi klonlayın:  [Java FX GitHub Address](https://github.com/birsel-epik/javafx)

```sh 
git clone https://github.com/birsel-epik/javafx
```

**2.** Proje dosyalarını açın ve gerekli bağımlılıkları yükleyin.

**3.** H2 veritabanını başlatın (veritabanı bağlantısı yapılandırması SingletonPropertiesDBConnection.java dosyasında yer almaktadır).

**4.** Uygulamayı çalıştırmak için HelloApplication.java dosyasını çalıştırın:

```sh 
java -jar maven-wrapper.jar
```

**5.** Uygulama açıldıktan sonra kullanıcı adı ve şifreniz ile giriş yapabilirsiniz (varsayılan kullanıcı: admin/root).

---

### 📦 Modüller
- **Login/Register:** Kullanıcı giriş ve kayıt işlemleri.

- **Kullanıcı Yönetimi:** Kullanıcı ekleme, güncelleme, silme işlemleri.

- **KDV Hesaplama ve Yönetimi:** KDV hesaplama ve verileri dışa aktarma.

- **Tema ve Dil Desteği:** Karanlık/Açık tema ve Türkçe/İngilizce dil desteği.
  
- **Bildirim Sistemi:** Bildirim mesajlarını gösterme.

- **Profil Yönetimi:** Kullanıcı profil bilgilerini güncelleme.

- **Yedekleme & Geri Yükleme:** Veritabanı yedekleme ve geri yükleme işlemleri.

---

### 🚀 Ekstra Özellikler
- **Grafiksel Raporlama:** Aylık KDV dağılımını PieChart veya BarChart ile gösterme.

- **WebView Entegrasyonu:** Yardım dökümanı veya kılavuz gösterimi.

---

### 🗂 Proje Yapısı

```sh 
src/
├── controller/
│   └── AdminController.java, LoginController.java, RegisterController.java, ProfileController.java, KdvController.java, NotebookController.java, NotificationPopupController.java   
├── dao/
│   └── UserDAO.java, KdvDAO.java, NotebookDAO.java, NotificationDAO.java ...
├── database/
│   └── SingletonPropertiesDBConnection.java
├── dto/
│   └── UserDTO.java, KdvDTO.java, NotebookDTO.java
├── exceptions/
│   └── RegisterNotFoundException
├── iofiles/
│   └── IFileHandlerInterface, SpecialFileHandler
├── utils/
│   └── ERole, ENoteCategory, FXMLPath, LanguageManager, SceneHelper, SpecialColor
├── view/
│   └── admin.fxml, login.fxml, register.fxml, kdv.fxml, notebook.fxml, notification-popup.fxml, profile.fxml
└── HelloApplication.java
```

---
### 🤝 Katkılar
Bu projeye katkı sağlamak için pull request gönderebilir veya önerilerinizi Issues kısmından bildirebilirsiniz.