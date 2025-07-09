package com.nestaway.dao.jdbc.queries;

import java.sql.*;

public final class NotificationQueries {

    private NotificationQueries() {
        throw new IllegalStateException("Utility class");
    }

    public static ResultSet selectNotifications(Statement stmt, String idHost) throws SQLException {
        String query = String.format("SELECT * FROM Notification WHERE Host = '%s';", idHost);
        return stmt.executeQuery(query);
    }

    public static void addNotification(Statement stmt, Integer type, String nameStay, String idHost, String bookingCode, Timestamp timestamp) throws SQLException {
        String query = String.format("INSERT INTO Notification (Type, NameStay, Host, BookingCode, DateTime) " + "VALUES (%d, '%s', '%s', '%s', '%s');", type, nameStay, idHost, bookingCode, timestamp);
        stmt.executeUpdate(query);
    }

    public static void deleteNotification(Statement stmt, String idHost, String nameStay, String bookingCode, Timestamp timestamp) throws SQLException {
        String query = String.format("DELETE FROM Notification WHERE Host = '%s' AND NameStay = '%s' AND BookingCode = '%s' AND DateTime = '%s';", idHost, nameStay, bookingCode, timestamp);
        stmt.executeUpdate(query);
    }

    public static void deleteNotificationByHost(Statement stmt, String idHost) throws SQLException {
        String query = String.format("DELETE FROM Notification WHERE Host = '%s'", idHost);
        stmt.executeUpdate(query);
    }
}

