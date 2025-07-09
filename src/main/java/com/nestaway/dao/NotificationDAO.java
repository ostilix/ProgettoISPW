package com.nestaway.dao;

import com.nestaway.exception.dao.DAOException;
import com.nestaway.model.Notification;

import java.util.List;

public interface NotificationDAO {
    public List<Notification> selectNotifications(String idHost) throws DAOException;
    public void addNotification(String idHost, Notification notification) throws DAOException;
    public void deleteNotification(String idHost, List<Notification> notification) throws DAOException;
    public void deleteNotificationByHost(String idHost) throws DAOException;
}
