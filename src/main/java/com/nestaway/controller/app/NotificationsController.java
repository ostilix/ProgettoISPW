package com.nestaway.controller.app;

import com.nestaway.bean.HostBean;
import com.nestaway.bean.NotificationBean;
import com.nestaway.dao.NotificationDAO;
import com.nestaway.exception.NotFoundException;
import com.nestaway.exception.OperationFailedException;
import com.nestaway.exception.dao.DAOException;
import com.nestaway.model.Host;
import com.nestaway.model.Notification;
import com.nestaway.model.TypeNotif;
import com.nestaway.utils.ToBeanConverter;
import com.nestaway.utils.dao.factory.FactorySingletonDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.nestaway.exception.dao.TypeDAOException.DUPLICATE;

public class NotificationsController {

    private final NotificationDAO notificationDAO;

    public NotificationsController() {
        this.notificationDAO = FactorySingletonDAO.getDefaultDAO().getNotificationDAO();
    }

    protected void notifyHost(Notification notif, Host host) {
        try{
            notificationDAO.addNotification(host.getUsername(), notif);
        } catch (DAOException e) {
            if (e.getTypeException().equals(DUPLICATE)) {
                Logger.getGlobal().log(Level.SEVERE, e.getMessage(), e.getCause());
            } else {
                Logger.getGlobal().log(Level.WARNING, e.getMessage(), e.getCause());
            }
        }
    }

    public void deleteNotifications(List<NotificationBean> notifBean, HostBean hostBean) throws OperationFailedException {
        try {
            List<Notification> notifs = new ArrayList<>();
            for (NotificationBean notif : notifBean) {
                notifs.add(new Notification(TypeNotif.valueOf(notif.getType()), notif.getNameStay(), notif.getBookingCode(), notif.getDateAndTime().toLocalDateTime()));
            }
            notificationDAO.deleteNotification(hostBean.getUsername(), notifs);
        } catch (DAOException e) {
            Logger.getGlobal().log(Level.WARNING, e.getMessage(), e.getCause());
            throw new OperationFailedException();
        }
    }

    public void deleteAllNotifications(HostBean hostBean) throws OperationFailedException {
        try {
            notificationDAO.deleteNotificationByHost(hostBean.getUsername());
        } catch (DAOException e) {
            Logger.getGlobal().log(Level.WARNING, e.getMessage(), e.getCause());
            throw new OperationFailedException();
        }
    }

    public List<NotificationBean> getNotifications(HostBean hostBean) throws OperationFailedException, NotFoundException {
        try {
            List<Notification> notifs = notificationDAO.selectNotifications(hostBean.getUsername());
            if (notifs.isEmpty()) {
                throw new NotFoundException("No notifications found.");
            }
            List<NotificationBean> notifBean = new ArrayList<>();
            for (Notification notif : notifs) {
                notifBean.add(ToBeanConverter.fromNotificationToNotificationBean(notif));
            }

            return notifBean;
        } catch (DAOException e) {
            Logger.getGlobal().log(Level.WARNING, e.getMessage(), e.getCause());
            throw new OperationFailedException();
        }
    }
}

