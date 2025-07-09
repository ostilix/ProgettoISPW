package com.nestaway.dao.jdbc;

import com.nestaway.dao.HostDAO;
import com.nestaway.dao.jdbc.queries.HostQueries;
import com.nestaway.exception.EncryptionException;
import com.nestaway.exception.dao.DAOException;
import com.nestaway.model.Host;
import com.nestaway.model.Notification;
import com.nestaway.model.Stay;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static com.nestaway.exception.dao.TypeDAOException.*;

public class HostJDBC implements HostDAO {

    private static final String COLUMN_USERNAME = "Username";
    private static final String COLUMN_PASSWORD = "Password";
    private static final String COLUMN_FIRSTNAME = "FirstName";
    private static final String COLUMN_LASTNAME = "LastName";
    private static final String COLUMN_EMAIL = "EmailAddress";
    private static final String COLUMN_PAYPAL = "InfoPayPal";

    @Override
    public Host selectHost(String idHost) throws DAOException {
        Host host = null;
        try (Statement stmt = SingletonConnector.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ResultSet rs = HostQueries.selectHost(stmt, idHost);
            if (rs.first()) {
                host = fromResultSet(rs);
            }
            rs.close();
            addNotifAndStays(host);
            return host;
        } catch (SQLException | EncryptionException e) {
            throw new DAOException("Error in selectHost: " + e.getMessage(), e, GENERIC);
        }
    }

    @Override
    public Host selectHost(String username, String password) throws DAOException {
        Host host = null;
        try (Statement stmt = SingletonConnector.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ResultSet rs = HostQueries.selectHost(stmt, username, password);
            if (rs.first()) {
                host = fromResultSet(rs);
            }
            rs.close();
            addNotifAndStays(host);
            return host;
        } catch (SQLException | EncryptionException e) {
            throw new DAOException("Error in selectHost: " + e.getMessage(), e, GENERIC);
        }
    }

    @Override
    public void insertHost(Host host) throws DAOException {
        try (Statement stmt = SingletonConnector.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            HostQueries.insertHost(stmt, host.getUsername(), host.getPassword(), host.getFirstName(), host.getLastName(), host.getEmailAddress(), host.getInfoPayPal());
        } catch (SQLException e) {
            if(e.getErrorCode() == 1061) {
                throw new DAOException("Username already exists", DUPLICATE);
            } else if (e.getErrorCode() == 1062) {
                throw new DAOException("Host already exists", DUPLICATE);
            }
            throw new DAOException("Error in insertHost: " + e.getMessage(), e, GENERIC);
        }
    }

    private Host fromResultSet(ResultSet rs) throws SQLException, EncryptionException {
        return new Host(rs.getString(COLUMN_FIRSTNAME), rs.getString(COLUMN_LASTNAME), rs.getString(COLUMN_EMAIL), rs.getString(COLUMN_USERNAME), rs.getString(COLUMN_PAYPAL), rs.getString(COLUMN_PASSWORD));
    }

    private void addNotifAndStays(Host host) throws DAOException {
        if(host == null) {
            return;
        }
        NotificationJDBC notificationJDBC = new NotificationJDBC();
        StayJDBC stayJDBC = new StayJDBC();
        List<Stay> stays = stayJDBC.selectStayByHost(host.getUsername());
        List<Notification> notifications = notificationJDBC.selectNotifications(host.getUsername());
        host.addStay(stays);
        host.addNotification(notifications);
    }
}


