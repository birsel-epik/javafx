package com.birselepik.javafx.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private UserDTO userDTO;


    // ✅ Geçerlilik kontrolü
    public boolean isValid() {
        return content != null;
    }
}
