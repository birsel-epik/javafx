package com.birselepik.javafx.utils;

import lombok.Getter;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class LanguageManager {
    @Getter
    public static Locale currentLocale = new Locale("tr"); // Başlangıç dili
    @Getter
    private static ResourceBundle bundle = ResourceBundle.getBundle("lang", currentLocale);

    public static void changeLanguage(String languageCode) {
        currentLocale = new Locale(languageCode);
        bundle = ResourceBundle.getBundle("lang", currentLocale);
    }

    public static String get(String key) {
        return bundle.getString(key);
    }

    public static String getOrDefault(String key, String defaultValue) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return defaultValue;
        }
    }

}
