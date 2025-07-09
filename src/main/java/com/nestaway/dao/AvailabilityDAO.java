package com.nestaway.dao;

import com.nestaway.exception.dao.DAOException;
import com.nestaway.model.Availability;

import java.time.LocalDate;
import java.util.List;

public interface AvailabilityDAO {
    public List<Availability> selectByStay(Integer idStay) throws DAOException;
    public List<Availability> selectInRange(Integer idStay, LocalDate from, LocalDate to) throws DAOException;
    public void deleteInRange(LocalDate checkIn, LocalDate checkOut, Integer idStay) throws DAOException;
    public void deleteAvailability(Integer idStay, LocalDate date) throws DAOException;
    public void deleteAllByStay(Integer idStay) throws DAOException;
}
