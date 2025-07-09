package com.nestaway.dao;

import com.nestaway.exception.dao.DAOException;
import com.nestaway.model.Review;

import java.util.List;

public interface ReviewDAO {
    public void insertReview(Review review) throws DAOException;
    public List<Review> selectByStay(Integer idStay) throws DAOException;
}
