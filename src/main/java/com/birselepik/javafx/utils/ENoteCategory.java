package com.birselepik.javafx.utils;

/**
 * 📌 Notebook Kategorisini Tanımlayan Enum
 */
public enum ENoteCategory {
    USER("Kişisel"),
    MODERATOR("İş"),
    ADMIN("Okul");

    private final String description;

    ENoteCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 📌 String’den Enum’a güvenli dönüşüm yapar.
     */
    public static ENoteCategory fromString(String role) {
        try {
            return ENoteCategory.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("❌ Geçersiz kategori: " + role);
        }
    }

    @Override
    public String toString() {
        return description; // ComboBox’ta görünen metin
    }
}
