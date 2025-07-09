package com.nestaway.dao.fs;

import com.nestaway.dao.NotificationDAO;
import com.nestaway.exception.dao.DAOException;
import com.nestaway.model.Notification;
import com.nestaway.model.TypeNotif;
import com.nestaway.utils.dao.CSVHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.nestaway.exception.dao.TypeDAOException.*;

public class NotificationFS implements NotificationDAO {

    private static final String FILE_PATH = "src/main/resources/data/Notification.csv";

    @Override
    public List<Notification> selectNotifications(String idHost) throws DAOException {
        try {
            CSVHandler handler = new CSVHandler(FILE_PATH, ",");
            List<String[]> results = handler.find(r -> r[3].equals(idHost));
            return results.stream().map(this::fromCsvRecord).collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            throw new DAOException("Error in selectNotifications: " + e.getMessage(), e, GENERIC);
        }
    }

    @Override
    public void addNotification(String idHost, Notification notification) throws DAOException {
        try {
            CSVHandler handler = new CSVHandler(FILE_PATH, ",");
            if (!handler.find(uniqueKey(idHost, notification)).isEmpty()) {
                throw new DAOException("Notification already exists", DUPLICATE);
            }

            List<String[]> rows = new ArrayList<>();
            rows.add(toCsvRecord(idHost, notification));
            handler.writeAll(rows);
        } catch (IOException e) {
            throw new DAOException("Error in addNotification: " + e.getMessage(), e, GENERIC);
        }
    }

    @Override
    public void deleteNotification(String idHost, List<Notification> notifications) throws DAOException {
        try {
            CSVHandler handler = new CSVHandler(FILE_PATH, ",");
            List<Predicate<String[]>> predicates = new ArrayList<>();

            for (Notification n : notifications) {
                predicates.add(uniqueKey(idHost, n));
            }

            handler.remove(predicates);
        } catch (IOException e) {
            throw new DAOException("Error in deleteNotification: " + e.getMessage(), e, GENERIC);
        }
    }

    @Override
    public void deleteNotificationByHost(String idHost) throws DAOException {
        try{
            CSVHandler handler = new CSVHandler(FILE_PATH, ",");
            handler.remove(n -> n[3].equals(idHost));
        } catch (IOException e) {
            throw new DAOException("Error in deleteNotification: " + e.getMessage(), e, GENERIC);
        }
    }

    private Predicate<String[]> uniqueKey(String idHost, Notification n) {
        String dateTimeStr = n.getDateAndTime().toString();
        return r -> r[0].equals(n.getType().name()) && r[1].equals(n.getNameStay()) && r[2].equals(n.getBookingCode()) && r[3].equals(idHost) && r[4].equals(dateTimeStr);
    }

    private Notification fromCsvRecord(String[] r) {
        return new Notification(TypeNotif.valueOf(r[0]), r[1], r[2], LocalDateTime.parse(r[4]));
    }

    private String[] toCsvRecord(String idHost, Notification n) {
        return new String[]{n.getType().name(), n.getNameStay(), n.getBookingCode(), idHost, n.getDateAndTime().toString()};
    }
}

