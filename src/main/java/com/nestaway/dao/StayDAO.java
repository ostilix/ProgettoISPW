package com.nestaway.dao;

import com.nestaway.exception.dao.DAOException;
import com.nestaway.model.Stay;

import java.util.List;

public interface StayDAO {
    public Stay selectStay(Integer idStay) throws DAOException;
    public List<Stay> selectStayByCity(String city) throws DAOException;
    public List<Stay> selectStayByHost(String hostUsername) throws DAOException;
}
