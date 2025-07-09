package com.nestaway.dao.demo;

import com.nestaway.dao.StayDAO;
import com.nestaway.dao.AvailabilityDAO;
import com.nestaway.dao.ReviewDAO;
import com.nestaway.exception.dao.DAOException;
import com.nestaway.model.Availability;
import com.nestaway.model.Review;
import com.nestaway.model.Stay;
import com.nestaway.utils.dao.MemoryDatabase;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.nestaway.exception.dao.TypeDAOException.GENERIC;

public class StayDEMO implements StayDAO {

    @Override
    public Stay selectStay(Integer idStay) throws DAOException {
        try {
            Stay stay = MemoryDatabase.getStays().stream().filter(s -> idStay != null && idStay.equals(s.getIdStay())).findFirst().orElse(null);

            if (stay != null) {
                stay.setTransientParams();
                addReviewsAndAvailability(stay);
            }

            return stay;
        } catch (NoSuchElementException | DAOException e) {
            throw new DAOException("Error in selectStay", e, GENERIC);
        }
    }

    @Override
    public List<Stay> selectStayByCity(String city) throws DAOException {
        try {
            List<Stay> result = MemoryDatabase.getStays().stream().filter(s -> s.getCity().equalsIgnoreCase(city)).collect(Collectors.toList());

            for (Stay stay : result) {
                stay.setTransientParams();
                addReviewsAndAvailability(stay);
            }

            return result;
        } catch (Exception e) {
            throw new DAOException("Error in selectStayByCity", e, GENERIC);
        }
    }

    @Override
    public List<Stay> selectStayByHost(String hostUsername) throws DAOException {
        try {
            List<Stay> result = MemoryDatabase.getStays().stream().filter(s -> s.getHostUsername().equalsIgnoreCase(hostUsername)).collect(Collectors.toList());

            for (Stay stay : result) {
                stay.setTransientParams();
                addReviewsAndAvailability(stay);
            }

            return result;
        } catch (Exception e) {
            throw new DAOException("Error in selectStayByHost", e, GENERIC);
        }
    }

    // Helper method
    private void addReviewsAndAvailability(Stay stay) throws DAOException {
        ReviewDAO reviewDAO = new ReviewDEMO();
        AvailabilityDAO availabilityDAO = new AvailabilityDEMO();

        List<Review> reviews = reviewDAO.selectByStay(stay.getIdStay());
        List<Availability> availabilities = availabilityDAO.selectByStay(stay.getIdStay());

        stay.addReviews(reviews);
        stay.addAvailabilities(availabilities);
    }
}
