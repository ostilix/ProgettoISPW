package com.nestaway.dao.fs;

import com.nestaway.dao.BookingDAO;
import com.nestaway.exception.dao.DAOException;
import com.nestaway.model.Booking;
import com.nestaway.utils.dao.CSVHandler;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.nestaway.exception.dao.TypeDAOException.*;

public class BookingFS implements BookingDAO {

    private static final String FILE_PATH = "src/main/resources/data/Booking.csv";

    @Override
    public Booking addBooking(Integer idStay, Booking booking) throws DAOException {
        try {
            CSVHandler handler = new CSVHandler(FILE_PATH, ",");
            int idBooking;
            if (!handler.find(uniquePredicate(String.valueOf(idStay), booking.getEmailAddress(), booking.getTelephone())).isEmpty()) {
                throw new DAOException("Booking already exists", DUPLICATE);
            }

            List<Booking> bookings = selectBookingByStay(idStay);
            if (bookings.isEmpty()) {
                idBooking = 1;
            } else {
                idBooking = bookings.getLast().getIdBooking() + 1;
            }
            booking.setIdAndCodeBooking(idBooking);

            List<String[]> rows = new ArrayList<>();
            rows.add(toCsvRecord(idStay, booking));
            handler.writeAll(rows);
            return booking;
        } catch (IOException e) {
            throw new DAOException("Error in addBooking: " + e.getMessage(), e, GENERIC);
        }
    }

    @Override
    public List<Booking> selectBookingByStay(Integer idStay) throws DAOException {
        try {
            CSVHandler handler = new CSVHandler(FILE_PATH, ",");
            List<String[]> found = handler.find(r -> r[9].equals(String.valueOf(idStay)));
            return found.stream().map(this::fromCsvRecord).collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            throw new DAOException("Error in selectBookingByStay: " + e.getMessage(), e, GENERIC);
        }
    }

    @Override
    public Booking selectBookingByCode(String codeBooking) throws DAOException {
        try {
            CSVHandler handler = new CSVHandler(FILE_PATH, ",");
            List<String[]> found = handler.find(r -> r[3].equals(codeBooking));
            if (found.isEmpty()) {
                return null;
            }
            return fromCsvRecord(found.get(0));
        } catch (IOException e) {
            throw new DAOException("Error in selectBookingByCode: " + e.getMessage(), e, GENERIC);
        }
    }


    private Predicate<String[]> uniquePredicate(String idStay, String email, String telephone) {
        return r -> (r[9].equals(idStay) && (r[4].equals(email) || r[5].equals(telephone)));
    }

    private Booking fromCsvRecord(String[] r) {
        Booking booking = new Booking(r[0], r[1], r[4], r[5], LocalDate.parse(r[6]), LocalDate.parse(r[7]), Integer.parseInt(r[8]), Boolean.parseBoolean(r[10]));
        booking.setIdAndCodeBooking(r[3]);
        return booking;
    }


    private String[] toCsvRecord(Integer idStay, Booking booking) {
        return new String[]{booking.getFirstName(), booking.getLastName(), "", booking.getCodeBooking(), booking.getEmailAddress(), booking.getTelephone(), booking.getCheckInDate().toString(), booking.getCheckOutDate().toString(), booking.getNumGuests().toString(), idStay.toString(), booking.getOnlinePayment().toString()};
    }
}