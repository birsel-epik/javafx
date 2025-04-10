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
        return title != null && category != null;
    }

    // Getter and Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getPinned() {
        return pinned;
    }

    public void setPinned(Boolean pinned) {
        this.pinned = pinned;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}
