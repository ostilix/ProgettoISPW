package com.nestaway.dao;

import com.nestaway.exception.dao.DAOException;
import com.nestaway.model.Host;

public interface HostDAO {
    public Host selectHost(String idHost) throws DAOException;
    public Host selectHost(String username, String password) throws DAOException;
    public void insertHost(Host host) throws DAOException;
}
