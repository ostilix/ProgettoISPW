package com.nestaway.dao.jdbc.queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class HostQueries {

    private HostQueries() {
        throw new IllegalStateException("Utility class");
    }

    public static ResultSet selectHost(Statement stmt, String idHost) throws SQLException {
        String query = String.format("SELECT * FROM Host WHERE Username = '%s';", idHost);
        return stmt.executeQuery(query);
    }

    public static ResultSet selectHost(Statement stmt, String username, String password) throws SQLException {
        String query = String.format("SELECT * FROM Host WHERE Username = '%s' AND Password = '%s';", username, password);
        return stmt.executeQuery(query);
    }

    public static void insertHost(Statement stmt, String username, String password, String firstName, String lastName, String email, String infoPayPal) throws SQLException {
        String query = String.format("INSERT INTO Host (Username, Password, FirstName, LastName, EmailAddress, InfoPayPal) " + "VALUES ('%s', '%s', '%s', '%s', '%s', '%s');", username, password, firstName, lastName, email, infoPayPal);
        stmt.executeUpdate(query);
    }
}
