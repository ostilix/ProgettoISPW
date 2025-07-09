package com.nestaway.dao.jdbc;

import com.nestaway.dao.AvailabilityDAO;
import com.nestaway.dao.jdbc.queries.AvailabilityQueries;
import com.nestaway.exception.dao.DAOException;
import com.nestaway.model.Availability;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.nestaway.exception.dao.TypeDAOException.*;

public class AvailabilityJDBC implements AvailabilityDAO {

    private static final String COLUMN_ID = "IdAvailability";
    private static final String COLUMN_DATE = "Date";
    private static final String COLUMN_IS_AVAILABLE = "IsAvailable";
    private static final String COLUMN_ID_STAY = "IdStay";

    @Override
    public List<Availability> selectByStay(Integer idStay) throws DAOException {
        Statement stmt;
        List<Availability> availabilities = new ArrayList<>();
        try {
            stmt = SingletonConnector.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = AvailabilityQueries.selectByStay(stmt, idStay);
            while (rs.next()) {
                availabilities.add(fromResultSet(rs));
            }
            rs.close();
            return availabilities;
        } catch (SQLException e) {
            throw new DAOException("Error in selectByStay: " + e.getMessage(), e, GENERIC);
        }
    }

    @Override
    public List<Availability> selectInRange(Integer idStay, LocalDate from, LocalDate to) throws DAOException {
        Statement stmt;
        List<Availability> availabilities = new ArrayList<>();
        try {
            stmt = SingletonConnector.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = AvailabilityQueries.selectInRange(stmt, idStay, from, to);
            while (rs.next()) {
                availabilities.add(fromResultSet(rs));
            }
            rs.close();
            return availabilities;
        } catch (SQLException e) {
            throw new DAOException("Error in selectInRange: " + e.getMessage(), e, GENERIC);
        }
    }

    @Override
    public void deleteInRange(LocalDate checkIn, LocalDate checkOut, Integer idStay) throws DAOException {
        Statement stmt;
        try {
            stmt = SingletonConnector.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            AvailabilityQueries.deleteInRange(stmt, checkIn, checkOut, idStay);
        } catch (SQLException e) {
            throw new DAOException("Error in deleteInRange " + e.getMessage(), e, GENERIC);
        }
    }


    @Override
    public void deleteAvailability(Integer idStay, LocalDate date) throws DAOException {
        Statement stmt;
        try {
            stmt = SingletonConnector.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            AvailabilityQueries.deleteAvailability(stmt, idStay, date);
        } catch (SQLException e) {
            throw new DAOException("Error in deleteAvailability: " + e.getMessage(), e, GENERIC);
        }
    }

    @Override
    public void deleteAllByStay(Integer idStay) throws DAOException {
        Statement stmt;
        try {
            stmt = SingletonConnector.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            AvailabilityQueries.deleteAllByStay(stmt, idStay);
        } catch (SQLException e) {
            throw new DAOException("Error in deleteAllByStay: " + e.getMessage(), e, GENERIC);
        }
    }

    private Availability fromResultSet(ResultSet rs) throws SQLException {
        return new Availability(
                rs.getInt(COLUMN_ID),
                rs.getDate(COLUMN_DATE).toLocalDate(),
                rs.getBoolean(COLUMN_IS_AVAILABLE),
                rs.getInt(COLUMN_ID_STAY)
        );
    }
}

