package com.nestaway.dao.jdbc;

import com.nestaway.dao.ReviewDAO;
import com.nestaway.dao.jdbc.queries.ReviewQueries;
import com.nestaway.exception.dao.DAOException;
import com.nestaway.model.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.nestaway.exception.dao.TypeDAOException.*;

public class ReviewJDBC implements ReviewDAO {

    private static final String COLUMN_CODE_BOOKING = "BookingCode";
    private static final String COLUMN_RATING = "Rating";
    private static final String COLUMN_COMMENT = "Comment";
    private static final String COLUMN_DATE = "Date";
    private static final String COLUMN_ID_STAY = "idStay";

    @Override
    public List<Review> selectByStay(Integer idStay) throws DAOException {
        List<Review> reviews = new ArrayList<>();
        try (Statement stmt = SingletonConnector.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ResultSet rs = ReviewQueries.selectByStay(stmt, idStay);
            while (rs.next()) {
                reviews.add(fromResultSet(rs));
            }
            rs.close();
            return reviews;
        } catch (SQLException e) {
            throw new DAOException("Error in selectByStay: " + e.getMessage(), e, GENERIC);
        }
    }

    @Override
    public void insertReview(Review review) throws DAOException {
        try (Statement stmt = SingletonConnector.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ReviewQueries.insertReview(stmt, review.getBookingCode(), review.getRating(), review.getComment(), review.getDate(), review.getIdStay());

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                throw new DAOException("Review already exists", DUPLICATE);
            }
            throw new DAOException("Error in insertReview: " + e.getMessage(), e, GENERIC);
        }
    }

    private Review fromResultSet(ResultSet rs) throws SQLException {
        return new Review(rs.getString(COLUMN_CODE_BOOKING), rs.getInt(COLUMN_RATING), rs.getString(COLUMN_COMMENT), rs.getDate(COLUMN_DATE).toLocalDate(), rs.getInt(COLUMN_ID_STAY));
    }
}
