package com.nestaway.dao.jdbc.queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public final class ReviewQueries {

    private ReviewQueries() {
        throw new IllegalStateException("Utility class");
    }

    public static void insertReview(Statement stmt, String bookingCode, int rating, String comment, LocalDate date, int idStay) throws SQLException {
        String query = String.format("INSERT INTO Review (BookingCode, Rating, Comment, Date, idStay) VALUES ('%s', %d, '%s', '%s', %d);", bookingCode, rating, comment, date.toString(), idStay);
        stmt.executeUpdate(query);
    }

    public static ResultSet selectByStay(Statement stmt, Integer idStay) throws SQLException {
        String query = String.format("SELECT * FROM Review WHERE idStay = %d;", idStay);
        return stmt.executeQuery(query);
    }
}
