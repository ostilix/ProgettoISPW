package com.nestaway.dao.jdbc.queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class StayQueries {

    private StayQueries() {
        throw new IllegalStateException("Utility class");
    }

    public static ResultSet selectStay(Statement stmt, Integer idStay) throws SQLException {
        String query = String.format("SELECT * FROM Stay WHERE IdStay = %d", idStay);
        return stmt.executeQuery(query);
    }

    public static ResultSet selectStayByCity(Statement stmt, String city) throws SQLException {
        String query = String.format("SELECT * FROM Stay WHERE City = '%s'", city);
        return stmt.executeQuery(query);
    }

    public static ResultSet selectStayByHost(Statement stmt, String idHost) throws SQLException {
        String query = String.format("SELECT * FROM Stay WHERE HostUsername = '%s'", idHost);
        return stmt.executeQuery(query);
    }
}
