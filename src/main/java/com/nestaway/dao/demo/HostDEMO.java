package com.nestaway.dao.demo;

import com.nestaway.dao.HostDAO;
import com.nestaway.exception.dao.DAOException;
import com.nestaway.model.Host;
import com.nestaway.model.Notification;
import com.nestaway.model.Stay;
import com.nestaway.utils.dao.MemoryDatabase;

import java.util.List;

import static com.nestaway.exception.dao.TypeDAOException.*;

public class HostDEMO implements HostDAO {

    @Override
    public Host selectHost(String idHost) throws DAOException {
        try {
            Host host = MemoryDatabase.hosts.stream().filter(h -> h.getUsername().equals(idHost)).findFirst().orElse(null);

            if (host != null) {
                host.setTransientParams();
                addNotifAndStays(host);
            }

            return host;
        } catch (Exception e) {
            throw new DAOException("Error in selectHost", e, GENERIC);
        }
    }

    @Override
    public Host selectHost(String username, String password) throws DAOException {
        try {
            Host host = MemoryDatabase.hosts.stream().filter(h -> h.getUsername().equals(username) && h.getPassword().equals(password)).findFirst().orElse(null);

            if (host != null) {
                host.setTransientParams();
                addNotifAndStays(host);
            }

            return host;
        } catch (Exception e) {
            throw new DAOException("Error in selectHost", e, GENERIC);
        }
    }

    @Override
    public void insertHost(Host host) throws DAOException {
        try {
            boolean usernameExists = MemoryDatabase.hosts.stream().anyMatch(h -> h.getUsername().equals(host.getUsername()));
            if (usernameExists) {
                throw new DAOException("Username already exists", DUPLICATE);
            }

            boolean emailExists = MemoryDatabase.hosts.stream().anyMatch(h -> h.getEmailAddress().equals(host.getEmailAddress()));
            if (emailExists) {
                throw new DAOException("Host already exists", DUPLICATE);
            }

            MemoryDatabase.hosts.add(host);
        } catch (Exception e) {
            throw new DAOException("Error in insertHost", e, GENERIC);
        }
    }

    private void addNotifAndStays(Host host) throws DAOException {
        if (host == null) return;

        try {
            List<Stay> stays = MemoryDatabase.stays.stream().filter(s -> s.getHostUsername().equals(host.getUsername())).toList();

            List<Notification> notifications = MemoryDatabase.notifications.stream().filter(n -> stays.stream().anyMatch(s -> s.getName().equals(n.getNameStay()))).toList();

            host.addStay(stays);
            host.addNotification(notifications);
        } catch (Exception e) {
            throw new DAOException("Error in addNotifAndStays", e, GENERIC);
        }
    }
}
