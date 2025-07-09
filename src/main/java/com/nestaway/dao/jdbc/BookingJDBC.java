package com.nestaway.dao.jdbc;

import com.nestaway.dao.BookingDAO;
import com.nestaway.dao.jdbc.queries.BookingQueries;
import com.nestaway.exception.dao.DAOException;
import com.nestaway.model.Booking;

import static com.nestaway.exception.dao.TypeDAOException.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookingJDBC implements BookingDAO {

    private static final String COLUMN_FIRSTNAME = "FirstName";
    private static final String COLUMN_LASTNAME = "LastName";
    private static final String COLUMN_EMAIL = "EmailAddress";
    private static final String COLUMN_TELEPHONE = "Telephone";
    private static final String COLUMN_CHECKIN = "CheckInDate";
    private static final String COLUMN_CHECKOUT = "CheckOutDate";
    private static final String COLUMN_NUMGUESTS = "NumGuests";
    private static final String COLUMN_ONLINE_PAYMENT = "OnlinePayment";
    private static final String COLUMN_CODE_BOOKING = "CodeBooking";

    @Override
    public Booking addBooking(Integer idStay, Booking booking) throws DAOException {
        int id;
        try (Statement stmt = SingletonConnector.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ResultSet rs = BookingQueries.countBookings(stmt, idStay);
            if (rs.next()) {
                id = rs.getInt(1) + 1;
                booking.setIdAndCodeBooking(id);
            } else {
                throw new DAOException("Unable to calculate booking ID", GENERIC);
            }

            BookingQueries.insertBooking(stmt, booking.getCodeBooking(), booking.getFirstName(), booking.getLastName(), booking.getEmailAddress(), booking.getTelephone(), booking.getCheckInDate(), booking.getCheckOutDate(), booking.getNumGuests(), (Boolean.TRUE.equals(booking.getOnlinePayment()) ? 1 : 0), idStay);
            rs.close();
            return booking;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                throw new DAOException("Booking already exists", DUPLICATE);
            } else {
                throw new DAOException("Error in addBooking" + e.getMessage(), e, GENERIC);
            }
        }
    }

    @Override
    public List<Booking> selectBookingByStay(Integer idStay) throws DAOException {
        List<Booking> bookings = new ArrayList<>();
        try (Statement stmt = SingletonConnector.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ResultSet rs = BookingQueries.selectBookingByStay(stmt, idStay);
            while (rs.next()) {
                Booking booking = fromResultSet(rs);
                bookings.add(booking);
            }
            rs.close();
            return bookings;
        } catch (SQLException e) {
            throw new DAOException("Error in selectBookingByStay " + e.getMessage(), e, GENERIC);
        }
    }

    @Override
    public Booking selectBookingByCode(String codeBooking) throws DAOException {
        try (Statement stmt = SingletonConnector.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ResultSet rs = BookingQueries.selectBookingByCode(stmt, codeBooking);
            if (rs.next()) {
                Booking booking = fromResultSet(rs);
                rs.close();
                return booking;
            } else {
                rs.close();
                return null;
            }
        } catch (SQLException e) {
            throw new DAOException("Error in selectBookingByCode " + e.getMessage(), e, GENERIC);
        }
    }


    private Booking fromResultSet(ResultSet rs) throws SQLException {
        Booking booking = new Booking( rs.getString(COLUMN_FIRSTNAME), rs.getString(COLUMN_LASTNAME), rs.getString(COLUMN_EMAIL), rs.getString(COLUMN_TELEPHONE), rs.getDate(COLUMN_CHECKIN).toLocalDate(), rs.getDate(COLUMN_CHECKOUT).toLocalDate(), rs.getInt(COLUMN_NUMGUESTS), rs.getBoolean(COLUMN_ONLINE_PAYMENT));
        booking.setIdAndCodeBooking(rs.getString(COLUMN_CODE_BOOKING));
        return booking;
    }
}

