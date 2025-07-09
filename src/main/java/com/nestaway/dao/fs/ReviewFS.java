package com.nestaway.dao.fs;

import com.nestaway.dao.ReviewDAO;
import com.nestaway.exception.dao.DAOException;
import com.nestaway.model.Review;
import com.nestaway.utils.dao.CSVHandler;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.nestaway.exception.dao.TypeDAOException.GENERIC;

public class ReviewFS implements ReviewDAO {

    private static final String FILE_PATH = "src/main/resources/data/Review.csv";
    private final CSVHandler csvHandler = new CSVHandler(FILE_PATH, ",");

    @Override
    public void insertReview(Review review) throws DAOException {
        try {
            csvHandler.writeAll(Collections.singletonList(toCsvRecord(review)));
        } catch (IOException e) {
            throw new DAOException("Error writing review to FS: " + e.getMessage(), e, GENERIC);
        }
    }

    @Override
    public List<Review> selectByStay(Integer idStay) throws DAOException {
        try {
            List<String[]> found = csvHandler.find(r -> r[5].equals(String.valueOf(idStay)));
            return found.stream().map(this::fromCsvRecord).collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            throw new DAOException("Error reading reviews from FS: " + e.getMessage(), e, GENERIC);
        }
    }

    private Review fromCsvRecord(String[] r) {
        Review review = new Review(
                r[1],
                Integer.parseInt(r[2]),
                r[3],
                LocalDate.parse(r[4]),
                Integer.parseInt(r[5])
        );
        review.setIdReview(Integer.parseInt(r[0]));
        return review;
    }


    private String[] toCsvRecord(Review review) {
        return new String[] {
                review.getIdReview().toString(),
                review.getBookingCode(),
                review.getRating().toString(),
                review.getComment(),
                review.getDate().toString(),
                review.getIdStay().toString()
        };
    }
}