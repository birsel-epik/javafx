package com.birselepik.javafx.dto;

import com.birselepik.javafx.utils.ERole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor // Parametresiz Constructor
//@AllArgsConstructor // Parametreli Constructor
@Builder
@ToString

public class NotebookDTO {

    // 🔢 Veritabanı için benzersiz ID
    private Integer id;

    // 📝 Notun başlığı
    private String title;

    // 📃 Notun içeriği
    private String content;

    // 📂 Kategori (Örn: "Kişisel", "İş", "Okul")
    private String category;

    // 📌 Sabitlenmiş not mu?
    private Boolean pinned;


    // 👤 Kullanıcı bilgileri (Composition)
    private UserDTO username;


    // ✅ Geçerlilik kontrolü
    public boolean isValid() {
        return title != null && category != null;
    }


    // Parametresiz Constructor
    // Parametreli Constructor
    public NotebookDTO(Integer id, String title, String content, String category, Boolean pinned, UserDTO username) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.pinned = pinned;
        this.username = username;
    }

    // Getter and Setter

}
