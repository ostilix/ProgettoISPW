package com.nestaway.dao.demo;

import com.nestaway.dao.AvailabilityDAO;
import com.nestaway.exception.dao.DAOException;
import com.nestaway.model.Availability;
import com.nestaway.utils.dao.MemoryDatabase;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.nestaway.exception.dao.TypeDAOException.GENERIC;

public class AvailabilityDEMO implements AvailabilityDAO {

    @Override
    public List<Availability> selectByStay(Integer idStay) throws DAOException {
        try {
            return MemoryDatabase.availabilities.stream().filter(a -> a.getIdStay().equals(idStay)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new DAOException("Error in selectByStay DEMO", e, GENERIC);
        }
    }

    @Override
    public List<Availability> selectInRange(Integer idStay, LocalDate from, LocalDate to) throws DAOException {
        try {
            return MemoryDatabase.availabilities.stream().filter(a -> a.getIdStay().equals(idStay) && !a.getDate().isBefore(from) && !a.getDate().isAfter(to)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new DAOException("Error in selectInRange DEMO", e, GENERIC);
        }
    }

    @Override
    public void deleteInRange(LocalDate checkIn, LocalDate checkOut, Integer idStay) throws DAOException {
        try {
            MemoryDatabase.availabilities.removeIf(a ->
                    a.getIdStay().equals(idStay) && !a.getDate().isBefore(checkIn) && a.getDate().isBefore(checkOut));
        } catch (Exception e) {
            throw new DAOException("Error in deleteInRange DEMO", e, GENERIC);
        }
    }

    @Override
    public void deleteAvailability(Integer idStay, LocalDate date) throws DAOException {
        try {
            MemoryDatabase.availabilities.removeIf(a -> a.getIdStay().equals(idStay) && a.getDate().equals(date));
        } catch (Exception e) {
            throw new DAOException("Error in deleteAvailability DEMO", e, GENERIC);
        }
    }

    @Override
    public void deleteAllByStay(Integer idStay) throws DAOException {
        try {
            MemoryDatabase.availabilities.removeIf(a -> a.getIdStay().equals(idStay));
        } catch (Exception e) {
            throw new DAOException("Error in deleteAllByStay DEMO", e, GENERIC);
        }
    }
}
