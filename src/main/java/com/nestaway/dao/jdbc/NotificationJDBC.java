package com.nestaway.dao.jdbc;

import com.nestaway.dao.NotificationDAO;
import com.nestaway.dao.jdbc.queries.NotificationQueries;
import com.nestaway.exception.dao.DAOException;
import com.nestaway.model.Notification;
import com.nestaway.model.TypeNotif;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.nestaway.exception.dao.TypeDAOException.*;

public class NotificationJDBC implements NotificationDAO {

    private static final String COLUMN_TYPE = "Type";
    private static final String COLUMN_DATETIME = "DateTime";
    private static final String COLUMN_NAMESTAY = "NameStay";
    private static final String COLUMN_BOOKINGCODE = "BookingCode";

    @Override
    public List<Notification> selectNotifications(String idHost) throws DAOException {
        List<Notification> notifications = new ArrayList<>();
        try (Statement stmt = SingletonConnector.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ResultSet rs = NotificationQueries.selectNotifications(stmt, idHost);
            while (rs.next()) {
                Notification notification = fromResultSet(rs);
                notifications.add(notification);
            }
            rs.close();
            return notifications;
        } catch (SQLException e) {
            throw new DAOException("Error selectNotifications: " + e.getMessage(), e, GENERIC);
        }
    }

    @Override
    public void addNotification(String idHost, Notification notification) throws DAOException {
        try (Statement stmt = SingletonConnector.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            Timestamp timestamp = Timestamp.valueOf(notification.getDateAndTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            NotificationQueries.addNotification(stmt, notification.getType().getId(), notification.getNameStay(), idHost, notification.getBookingCode(), timestamp);
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                throw new DAOException("Notification already exists", DUPLICATE);
            }
            throw new DAOException("Error in addNotification: " + e.getMessage(), e, GENERIC);
        }
    }

    @Override
    public void deleteNotification(String idHost, List<Notification> notifications) throws DAOException {
        try (Statement stmt = SingletonConnector.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            for (Notification notif : notifications) {
                Timestamp timestamp = Timestamp.valueOf(notif.getDateAndTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

                NotificationQueries.deleteNotification(stmt, idHost, notif.getNameStay(), notif.getBookingCode(), timestamp);
            }
        } catch (SQLException e) {
            throw new DAOException("Error deleting notification(s): " + e.getMessage(), e, GENERIC);
        }
    }

    public void deleteNotificationByHost(String idHost) throws DAOException {
        try (Statement stmt = SingletonConnector.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
            NotificationQueries.deleteNotificationByHost(stmt, idHost);
        } catch (SQLException e) {
            throw new DAOException("Error in deleteNotification: " + e.getMessage(), e, GENERIC);
        }
    }

    private Notification fromResultSet(ResultSet rs) throws SQLException {
        return new Notification(TypeNotif.valueOf(rs.getString(COLUMN_TYPE)), rs.getString(COLUMN_NAMESTAY), rs.getString(COLUMN_BOOKINGCODE), rs.getTimestamp(COLUMN_DATETIME).toLocalDateTime());
    }
}


