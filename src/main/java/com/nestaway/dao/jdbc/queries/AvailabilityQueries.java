package com.nestaway.dao.jdbc.queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public final class AvailabilityQueries {

    private AvailabilityQueries() {
        throw new IllegalStateException("Utility class");
    }

    public static ResultSet selectByStay(Statement stmt, Integer idStay) throws SQLException {
        String query = String.format("SELECT * FROM Availability WHERE IdStay = %d;", idStay);
        return stmt.executeQuery(query);
    }

    public static ResultSet selectInRange(Statement stmt, Integer idStay, LocalDate from, LocalDate to) throws SQLException {
        String query = String.format("SELECT * FROM Availability WHERE IdStay = %d AND Date BETWEEN '%s' AND '%s';", idStay, from.toString(), to.toString());
        return stmt.executeQuery(query);
    }

    public static void deleteInRange(Statement stmt, LocalDate checkIn, LocalDate checkOut, Integer idStay) throws SQLException {
        String query = String.format("UPDATE Availability SET IsAvailable = 0 WHERE IdStay = %d AND Date >= '%s' AND Date < '%s'", idStay, checkIn.toString(), checkOut.toString());
        stmt.executeUpdate(query);
    }

    public static void deleteAvailability(Statement stmt, Integer idStay, LocalDate date) throws SQLException {
        String query = String.format("DELETE FROM Availability WHERE IdStay = %d AND Date = '%s';", idStay, date.toString());
        stmt.executeUpdate(query);
    }

    public static void deleteAllByStay(Statement stmt, Integer idStay) throws SQLException {
        String query = String.format("DELETE FROM Availability WHERE IdStay = %d;", idStay);
        stmt.executeUpdate(query);
    }
}
