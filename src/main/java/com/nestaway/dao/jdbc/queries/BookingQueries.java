package com.nestaway.dao.jdbc.queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public final class BookingQueries {

    private BookingQueries() {
        throw new IllegalStateException("Utility class");
    }

    public static void insertBooking(Statement stmt, String codeBooking, String firstName, String lastName, String emailAddress, String telephone, LocalDate checkInDate, LocalDate checkOutDate, int numGuests, int onlinePayment, Integer idStay) throws SQLException {

        String query = String.format("INSERT INTO Booking (CodeBooking, FirstName, LastName, EmailAddress, Telephone, CheckInDate, CheckOutDate, NumGuests, OnlinePayment, Stay) " + "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', %d, %d, %d)", codeBooking, firstName, lastName, emailAddress, telephone, checkInDate.toString(), checkOutDate.toString(), numGuests, onlinePayment, idStay);
        stmt.executeUpdate(query);
    }

    public static ResultSet countBookings(Statement stmt, Integer idStay) throws SQLException {
        String query = String.format("SELECT COUNT(*) FROM Booking WHERE Stay = %d", idStay);
        return stmt.executeQuery(query);
    }

    public static ResultSet selectBookingByStay(Statement stmt, Integer idStay) throws SQLException {
        String query = String.format("SELECT * FROM Booking WHERE Stay = %d", idStay);
        return stmt.executeQuery(query);
    }
    public static ResultSet selectBookingByCode(Statement stmt, String codeBooking) throws SQLException {
        String query = String.format("SELECT * FROM Booking WHERE CodeBooking = '%s'", codeBooking);
        return stmt.executeQuery(query);
    }
}
