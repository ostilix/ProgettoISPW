package com.nestaway.dao.fs;

import com.nestaway.dao.AvailabilityDAO;
import com.nestaway.exception.dao.DAOException;
import com.nestaway.model.Availability;
import com.nestaway.utils.dao.CSVHandler;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.nestaway.exception.dao.TypeDAOException.GENERIC;

public class AvailabilityFS implements AvailabilityDAO {

    private static final String FILE_PATH = "src/main/resources/data/Availability.csv";
    private final CSVHandler handler = new CSVHandler(FILE_PATH, ",");

    @Override
    public List<Availability> selectByStay(Integer idStay) throws DAOException {
        try {
            List<String[]> rows = handler.find(r -> Integer.parseInt(r[3]) == idStay);
            List<Availability> result = new ArrayList<>();
            for (String[] row : rows) {
                result.add(fromCsvRecord(row));
            }
            return result;
        } catch (IOException e) {
            throw new DAOException("Error in selectByStay: " + e.getMessage(), e, GENERIC);
        }
    }

    @Override
    public List<Availability> selectInRange(Integer idStay, LocalDate from, LocalDate to) throws DAOException {
        try {
            List<String[]> rows = handler.find(r -> {
                int stayId = Integer.parseInt(r[3]);
                LocalDate date = LocalDate.parse(r[1]);
                return stayId == idStay && (!date.isBefore(from) && !date.isAfter(to));
            });
            List<Availability> result = new ArrayList<>();
            for (String[] row : rows) {
                result.add(fromCsvRecord(row));
            }
            return result;
        } catch (IOException e) {
            throw new DAOException("Error in selectInRange: " + e.getMessage(), e, GENERIC);
        }
    }

    @Override
    public void deleteInRange(LocalDate checkIn, LocalDate checkOut, Integer idStay) throws DAOException {
        try {
            handler.remove(r -> {
                int stayId = Integer.parseInt(r[3]);
                LocalDate date = LocalDate.parse(r[1]);
                return stayId == idStay && (!date.isBefore(checkIn) && date.isBefore(checkOut));
            });
        } catch (IOException e) {
            throw new DAOException("Error in deleteInRange: " + e.getMessage(), e, GENERIC);
        }
    }

    @Override
    public void deleteAvailability(Integer idStay, LocalDate date) throws DAOException {
        try {
            handler.remove(r -> Integer.parseInt(r[3]) == idStay && LocalDate.parse(r[1]).equals(date));
        } catch (IOException e) {
            throw new DAOException("Error in deleteAvailability: " + e.getMessage(), e, GENERIC);
        }
    }

    @Override
    public void deleteAllByStay(Integer idStay) throws DAOException {
        try {
            handler.remove(r -> Integer.parseInt(r[3]) == idStay);
        } catch (IOException e) {
            throw new DAOException("Error in deleteAllByStay: " + e.getMessage(), e, GENERIC);
        }
    }

    private Availability fromCsvRecord(String[] r) {
        return new Availability(Integer.parseInt(r[0]), LocalDate.parse(r[1]), Boolean.parseBoolean(r[2]), Integer.parseInt(r[3]));
    }

    private String[] toCsvRecord(Integer idStay, Availability availability) {
        return new String[] {
                availability.getIdAvailability().toString(),
                availability.getDate().toString(),
                availability.getIsAvailable().toString(),
                idStay.toString()
        };
    }

}