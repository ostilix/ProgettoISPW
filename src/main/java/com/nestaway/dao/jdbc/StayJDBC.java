package com.nestaway.dao.jdbc;

import com.nestaway.dao.StayDAO;
import com.nestaway.dao.jdbc.queries.StayQueries;
import com.nestaway.exception.dao.DAOException;
import com.nestaway.model.Stay;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.nestaway.exception.dao.TypeDAOException.GENERIC;

public class StayJDBC implements StayDAO {

    private static final String COLUMN_ID = "IdStay";
    private static final String COLUMN_NAME = "NameStay";
    private static final String COLUMN_DESCRIPTION = "Description";
    private static final String COLUMN_CITY = "City";
    private static final String COLUMN_ADDRESS = "Address";
    private static final String COLUMN_PRICE = "PricePerNight";
    private static final String COLUMN_MAXGUESTS = "MaxGuests";
    private static final String COLUMN_NUMROOMS = "NumRooms";
    private static final String COLUMN_NUMBATHROOMS = "NumBathrooms";
    private static final String COLUMN_HOSTUSERNAME = "HostUsername";

    @Override
    public List<Stay> selectStayByCity(String city) throws DAOException {
        Statement stmt;
        List<Stay> stays = new ArrayList<>();
        try {
            stmt = SingletonConnector.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = StayQueries.selectStayByCity(stmt, city);
            while (rs.next()) {
                Stay stay = fromResultSet(rs);
                stays.add(stay);
            }
            rs.close();
            stmt.close();
            return stays;
        } catch (SQLException e) {
            throw new DAOException("Error in selectStayByCity: " + e.getMessage(), e, GENERIC);
        }
    }

    @Override
    public List<Stay> selectStayByHost(String idHost) throws DAOException {
        Statement stmt;
        List<Stay> stays = new ArrayList<>();
        try {
            stmt = SingletonConnector.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = StayQueries.selectStayByHost(stmt, idHost);
            while (rs.next()) {
                Stay stay = fromResultSet(rs);
                stays.add(stay);
            }
            rs.close();
            stmt.close();
            return stays;
        } catch (SQLException e) {
            throw new DAOException("Error in selectStayByHost: " + e.getMessage(), e, GENERIC);
        }
    }

    @Override
    public Stay selectStay(Integer idStay) throws DAOException {
        Statement stmt;
        Stay stay = null;
        try {
            stmt = SingletonConnector.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = StayQueries.selectStay(stmt, idStay);
            if (rs.next()) {
                stay = fromResultSet(rs);
            }
            rs.close();
            stmt.close();
            return stay;
        } catch (SQLException e) {
            throw new DAOException("Error in selectStay: " + e.getMessage(), e, GENERIC);
        }
    }


    private Stay fromResultSet(ResultSet rs) throws SQLException {
        return new Stay(rs.getInt(COLUMN_ID), rs.getString(COLUMN_NAME), rs.getString(COLUMN_DESCRIPTION), rs.getString(COLUMN_CITY), rs.getString(COLUMN_ADDRESS), rs.getDouble(COLUMN_PRICE), rs.getInt(COLUMN_MAXGUESTS), rs.getInt(COLUMN_NUMROOMS), rs.getInt(COLUMN_NUMBATHROOMS), rs.getString(COLUMN_HOSTUSERNAME));
    }
}


