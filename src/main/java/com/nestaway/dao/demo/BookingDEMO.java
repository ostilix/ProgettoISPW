package com.nestaway.dao.demo;

import com.nestaway.dao.BookingDAO;
import com.nestaway.exception.dao.DAOException;
import com.nestaway.model.Booking;
import com.nestaway.utils.dao.DemoIndex;
import com.nestaway.utils.dao.MemoryDatabase;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.nestaway.exception.dao.TypeDAOException.*;

public class BookingDEMO implements BookingDAO {

    private final AtomicInteger globalCounter = new AtomicInteger(0);

    @Override
    public Booking addBooking(Integer idStay, Booking booking) throws DAOException {
        try {
            boolean exists = DemoIndex.getBookingStayMap().entrySet().stream().anyMatch(entry -> {
                        String code = entry.getKey();
                        Integer mappedIdStay = entry.getValue();
                        return mappedIdStay.equals(idStay) && MemoryDatabase.getBookings().stream().filter(b -> b.getCodeBooking().equals(code)).anyMatch(b -> b.getEmailAddress().equalsIgnoreCase(booking.getEmailAddress()) && b.getTelephone().equals(booking.getTelephone()));
            });

            if (exists) {
                throw new DAOException("Booking already exists", DUPLICATE);
            }

            int idBooking = nextId(idStay);
            booking.setIdAndCodeBooking(idBooking);

            MemoryDatabase.getBookings().add(booking);
            DemoIndex.getBookingStayMap().put(booking.getCodeBooking(), idStay);

            return booking;
        } catch (Exception e) {
            throw new DAOException("Error in addBooking", e, GENERIC);
        }
    }

    @Override
    public List<Booking> selectBookingByStay(Integer idStay) throws DAOException {
        try {
            Set<String> codes = DemoIndex.getBookingStayMap().entrySet().stream().filter(entry -> entry.getValue().equals(idStay)).map(entry -> entry.getKey()).collect(Collectors.toSet());

            return MemoryDatabase.getBookings().stream().filter(b -> codes.contains(b.getCodeBooking())).toList();
        } catch (Exception e) {
            throw new DAOException("Error in selectBookingByStay", e, GENERIC);
        }
    }

    @Override
    public Booking selectBookingByCode(String codeBooking) throws DAOException {
        try {
            return MemoryDatabase.getBookings().stream().filter(b -> b.getCodeBooking().equals(codeBooking)).findFirst().orElse(null);
        } catch (Exception e) {
            throw new DAOException("Error in selectBookingByCode", e, GENERIC);
        }
    }

    private int nextId(Integer idStay) {
        long count = DemoIndex.getBookingStayMap().values().stream().filter(stayId -> stayId.equals(idStay)).count();
        return (int) count + 1;
    }
}
