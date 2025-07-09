package com.nestaway.dao;

import com.nestaway.exception.dao.DAOException;
import com.nestaway.model.Booking;

import java.util.List;

public interface BookingDAO {
    public Booking addBooking(Integer idStay, Booking booking) throws DAOException;
    public List<Booking> selectBookingByStay(Integer idStay) throws DAOException;
    public Booking selectBookingByCode(String codeBooking) throws DAOException;
}
