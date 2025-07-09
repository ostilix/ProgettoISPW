package com.nestaway.dao.demo;

import com.nestaway.dao.ReviewDAO;
import com.nestaway.exception.dao.DAOException;
import com.nestaway.model.Review;
import com.nestaway.utils.dao.MemoryDatabase;

import java.util.List;
import java.util.stream.Collectors;

import static com.nestaway.exception.dao.TypeDAOException.DUPLICATE;
import static com.nestaway.exception.dao.TypeDAOException.GENERIC;

public class ReviewDEMO implements ReviewDAO {

    @Override
    public void insertReview(Review review) throws DAOException {
        try {
            boolean exists = MemoryDatabase.reviews.stream().anyMatch(r -> r.getBookingCode().equals(review.getBookingCode()));

            if (exists) {
                throw new DAOException("Review already exists", DUPLICATE);
            }

            MemoryDatabase.reviews.add(review);
        } catch (Exception e) {
            throw new DAOException("Error in insertReview DEMO", e, GENERIC);
        }
    }

    @Override
    public List<Review> selectByStay(Integer idStay) throws DAOException {
        try {
            return MemoryDatabase.reviews.stream().filter(r -> r.getIdStay() != null && r.getIdStay().equals(idStay)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new DAOException("Error in selectByStay DEMO", e, GENERIC);
        }
    }
}
