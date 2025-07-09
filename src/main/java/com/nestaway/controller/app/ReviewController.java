package com.nestaway.controller.app;

import com.nestaway.bean.ReviewBean;
import com.nestaway.dao.BookingDAO;
import com.nestaway.dao.ReviewDAO;
import com.nestaway.exception.DuplicateEntryException;
import com.nestaway.exception.NotFoundException;
import com.nestaway.exception.OperationFailedException;
import com.nestaway.exception.dao.DAOException;
import com.nestaway.model.Booking;
import com.nestaway.model.Review;
import com.nestaway.utils.ToBeanConverter;
import com.nestaway.utils.dao.factory.FactorySingletonDAO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReviewController {

    private final ReviewDAO reviewDAO;
    private final BookingDAO bookingDAO;

    public ReviewController() {
        this.reviewDAO = FactorySingletonDAO.getDefaultDAO().getReviewDAO();
        this.bookingDAO = FactorySingletonDAO.getDefaultDAO().getBookingDAO();
    }

    public void addReview(ReviewBean reviewBean) throws OperationFailedException, NotFoundException, DuplicateEntryException {
        try {
            Booking booking = bookingDAO.selectBookingByCode(reviewBean.getBookingCode());
            if (booking == null) {
                throw new NotFoundException("No booking found with code: " + reviewBean.getBookingCode());
            }

            if (LocalDate.now().isBefore(booking.getCheckOutDate())) {
                throw new OperationFailedException("You can write a review only after your stay ends.");
            }

            Review review = new Review(reviewBean.getBookingCode(), reviewBean.getRating(),
                    reviewBean.getComment(), reviewBean.getDate(), reviewBean.getIdStay());

            reviewDAO.insertReview(review);

        } catch (DAOException e) {
            if (e.getTypeException().equals(com.nestaway.exception.dao.TypeDAOException.DUPLICATE)) {
                throw new DuplicateEntryException("You have already submitted a review.");
            } else {
                Logger.getGlobal().log(Level.WARNING, e.getMessage(), e.getCause());
                throw new OperationFailedException("Failed to submit review. Try again later.");
            }
        }
    }

    public List<ReviewBean> getReviewsByStay(Integer idStay) throws OperationFailedException {
        try {
            List<Review> reviews = reviewDAO.selectByStay(idStay);
            List<ReviewBean> result = new ArrayList<>();
            for (Review r : reviews) {
                result.add(ToBeanConverter.fromReviewToReviewBean(r));
            }
            return result;
        } catch (DAOException e) {
            Logger.getGlobal().log(Level.WARNING, e.getMessage(), e.getCause());
            throw new OperationFailedException("Unable to retrieve reviews at the moment.");
        }
    }
}

