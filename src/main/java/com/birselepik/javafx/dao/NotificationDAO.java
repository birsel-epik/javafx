package com.birselepik.javafx.dao;

import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {
    public static boolean hasUnread() {
        return true;
    }

    public static void markAllAsRead() {
    }

    public static List<String> getUnreadMessages() {
        List<String> messages = new ArrayList<>();
        messages.add("Yeni kullanıcı eklendi.");
        messages.add("Yedekleme başarıyla tamamlandı.");
        return messages;
    }

}
