package com.nestaway.dao.demo;

import com.nestaway.dao.NotificationDAO;
import com.nestaway.exception.dao.DAOException;
import com.nestaway.model.Notification;
import com.nestaway.model.Stay;
import com.nestaway.utils.dao.MemoryDatabase;

import java.util.List;
import java.util.stream.Collectors;

import static com.nestaway.exception.dao.TypeDAOException.*;

public class NotificationDEMO implements NotificationDAO {

    @Override
    public List<Notification> selectNotifications(String idHost) throws DAOException {
        try {
            return MemoryDatabase.notifications.stream().filter(n -> {
                Stay stay = MemoryDatabase.stays.stream().filter(s -> s.getName().equals(n.getNameStay())).findFirst().orElse(null);
                return stay != null && stay.getHostUsername().equals(idHost);
            }).collect(Collectors.toList());
        } catch (Exception e) {
            throw new DAOException("Error in selectNotifications", e, GENERIC);
        }
    }

    @Override
    public void addNotification(String idHost, Notification notification) throws DAOException {
        try {
            boolean exists = MemoryDatabase.notifications.stream().anyMatch(n -> n.getType() == notification.getType() && n.getNameStay().equals(notification.getNameStay()) && n.getBookingCode().equals(notification.getBookingCode()) && n.getDateAndTime().equals(notification.getDateAndTime()));

            if (exists) {
                throw new DAOException("Notification already exists", DUPLICATE);
            }

            MemoryDatabase.notifications.add(notification);
        } catch (Exception e) {
            throw new DAOException("Error in addNotification", e, GENERIC);
        }
    }

    @Override
    public void deleteNotification(String idHost, List<Notification> notifsToDelete) throws DAOException {
        try {
            MemoryDatabase.notifications.removeIf(n -> {
                Stay stay = MemoryDatabase.stays.stream().filter(s -> s.getName().equals(n.getNameStay())).findFirst().orElse(null);

                if (stay == null || !stay.getHostUsername().equals(idHost)) {
                    return false;
                }

                return notifsToDelete.stream().anyMatch(del -> del.getType() == n.getType() && del.getNameStay().equals(n.getNameStay()) && del.getBookingCode().equals(n.getBookingCode()) && del.getDateAndTime().equals(n.getDateAndTime()));
            });
        } catch (Exception e) {
            throw new DAOException("Error in deleteNotification", e, GENERIC);
        }
    }


    @Override
    public void deleteNotificationByHost(String idHost) throws DAOException {
        try {
            MemoryDatabase.notifications.removeIf(n -> {
                Stay stay = MemoryDatabase.stays.stream().filter(s -> s.getName().equals(n.getNameStay())).findFirst().orElse(null);
                return stay != null && stay.getHostUsername().equals(idHost);
            });
        } catch (Exception e) {
            throw new DAOException("Error in deleteNotificationByHost", e, GENERIC);
        }
    }
}
