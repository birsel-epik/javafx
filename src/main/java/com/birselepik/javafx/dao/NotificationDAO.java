package com.birselepik.javafx.dao;

import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {
    public static boolean hasUnread() {
        return true;
    }

    public static void markAllAsRead() {
        System.out.println("🔔 Tüm bildirimler okundu olarak işaretlendi.");
    }

    public static List<String> getUnreadMessages() {
        List<String> notifications = new ArrayList<>();
        notifications.add("Yeni kullanıcı kaydı yapıldı.");
        notifications.add("Veritabanı yedeği başarıyla alındı.");
        notifications.add("3 yeni not defteri eklendi.");
        return notifications;
    }

}
