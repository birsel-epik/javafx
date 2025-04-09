package com.birselepik.javafx.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
//@NoArgsConstructor
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

    // 📅 Oluşturulma tarihi
    private LocalDate createdDate;

    // 📅 Güncellenme tarihi
    private LocalDate updatedDate;

    // 📂 Kategori (Örn: "Kişisel", "İş", "Okul")
    private String category;

    // 📌 Sabitlenmiş not mu?
    private Boolean pinned;

    // 👤 Kullanıcı bilgileri (Composition)
    private UserDTO userDTO;


    // ✅ Geçerlilik kontrolü
    public boolean isValid() {
        return title != null && !title.isEmpty() && content != null && !content.isEmpty() && userDTO != null;
    }

    // Constructorlar
    public NotebookDTO() {
    }


    // 📄 Dışa aktarım için sade metin formatı
    public String toExportString() {
        return String.format("""
                Başlık     : %s
                İçerik     : %s
                Oluşturulma Tarihi: %s
                Güncellenme Tarihi: %s
                Kategori   : %s
                Sabitlenmiş: %s
                Kullanıcı   : %s
                """,
                title,
                content,
                createdDate,
                updatedDate,
                category,
                pinned ? "Evet" : "Hayır",
                userDTO != null ? userDTO.toString() : "-"
        );
    }


/*    public NotebookDTO(Integer id, String title, String content, LocalDateTime createdDate, LocalDateTime updatedDate, String category, boolean pinned) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.category = category;
        this.pinned = pinned;
    }*/


    // Getter And Setter
    /*public Integer getId() {
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

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean getPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
*/


}
