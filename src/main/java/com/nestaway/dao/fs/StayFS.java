package com.nestaway.dao.fs;

import com.nestaway.dao.StayDAO;
import com.nestaway.exception.dao.DAOException;
import com.nestaway.model.Availability;
import com.nestaway.model.Review;
import com.nestaway.model.Stay;
import com.nestaway.utils.dao.CSVHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import static com.nestaway.exception.dao.TypeDAOException.*;

public class StayFS implements StayDAO {

    private static final String FILE_PATH = "src/main/resources/data/Stay.csv";
    private final CSVHandler csvHandler = new CSVHandler(FILE_PATH, ",");

    @Override
    public Stay selectStay(Integer idStay) throws DAOException {
        try {
            List<String[]> rows = csvHandler.readAll();
            for (String[] r : rows) {
                if (Integer.parseInt(r[0]) == idStay) {
                    Stay stay = fromCsvRecord(r);
                    stay.setTransientParams();
                    addReviewsAndAvailability(stay);
                    return stay;
                }
            }
            return null;
        } catch (IOException | NumberFormatException e) {
            throw new DAOException("Error in selectStay: " + e.getMessage(), e, GENERIC);
        }
    }

    @Override
    public List<Stay> selectStayByCity(String city) throws DAOException {
        try {
            List<Stay> stays = new ArrayList<>();
            for (String[] r : csvHandler.readAll()) {
                if (r[3].equalsIgnoreCase(city)) {
                    Stay stay = fromCsvRecord(r);
                    stay.setTransientParams();
                    addReviewsAndAvailability(stay);
                    stays.add(stay);
                }
            }
            return stays;
        } catch (IOException e) {
            throw new DAOException("Error in selectStayByCity: " + e.getMessage(), e, GENERIC);
        }
    }

    @Override
    public List<Stay> selectStayByHost(String hostUsername) throws DAOException {
        try {
            List<Stay> stays = new ArrayList<>();
            for (String[] r : csvHandler.readAll()) {
                if (r.length >= 10 && r[9].equals(hostUsername)) { // corretto
                    Stay stay = fromCsvRecord(r);
                    stay.setTransientParams();
                    addReviewsAndAvailability(stay);
                    stays.add(stay);
                }
            }
            return stays;
        } catch (IOException e) {
            throw new DAOException("Error in selectStayByHost: " + e.getMessage(), e, GENERIC);
        }
    }


    public void insertStay(Stay stay) throws DAOException {
        try {
            List<String[]> allRows = csvHandler.readAll();
            for (String[] r : allRows) {
                if (r[0].equals(stay.getName()) && r[3].equals(stay.getCity())) {
                    throw new DAOException("Stay already exists", DUPLICATE);
                }
            }

            int newId = allRows.isEmpty() ? 1 : allRows.stream()
                    .mapToInt(r -> Integer.parseInt(r[1]))
                    .max()
                    .orElse(0) + 1;

            stay.setIdStay(newId);
            csvHandler.writeAll(Collections.singletonList(toCsvRecord(stay)));

        } catch (IOException | NumberFormatException e) {
            throw new DAOException("Error in insertStay: " + e.getMessage(), e, GENERIC);
        }
    }

    private String[] toCsvRecord(Stay stay) {
        return new String[]{
                stay.getName(),
                stay.getIdStay().toString(),
                stay.getDescription(),
                stay.getCity(),
                stay.getAddress(),
                stay.getPricePerNight().toString(),
                stay.getMaxGuests().toString(),
                stay.getNumRooms().toString(),
                stay.getNumBathrooms().toString(),
                stay.getHostUsername()
        };
    }

    private Stay fromCsvRecord(String[] r) {
        return new Stay(
                Integer.parseInt(r[0]),
                r[1],
                r[2],
                r[3],
                r[4],
                Double.parseDouble(r[5]),
                Integer.parseInt(r[6]),
                Integer.parseInt(r[7]),
                Integer.parseInt(r[8]),
                r[9]
        );
    }

    private void addReviewsAndAvailability(Stay stay) throws DAOException {
        if (stay == null) return;

        ReviewFS reviewFS = new ReviewFS();
        AvailabilityFS availabilityFS = new AvailabilityFS();

        List<Review> reviews = reviewFS.selectByStay(stay.getIdStay());
        List<Availability> availability = availabilityFS.selectByStay(stay.getIdStay());

        stay.addReviews(reviews);
        stay.addAvailabilities(availability);
    }
}