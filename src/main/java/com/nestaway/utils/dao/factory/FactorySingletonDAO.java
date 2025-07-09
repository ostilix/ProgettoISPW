package com.nestaway.utils.dao.factory;

import com.nestaway.dao.*;

public abstract class FactorySingletonDAO {

    protected static FactorySingletonDAO instance = null;

    protected FactorySingletonDAO() {
    }

    public static synchronized FactorySingletonDAO getDefaultDAO() {
        if (instance == null) {
            String daoType = System.getProperty("DAO_TYPE");
            switch (TypeDAO.valueOf(daoType)) {
                case JDBC:
                    instance = new JDBCFactory();
                    break;
                case FS:
                    instance = new FSFactory();
                    break;
                case DEMO:
                    instance = new DEMOFactory();
                    break;
            }
            return instance;
        }
        return instance;
    }

    public abstract AvailabilityDAO getAvailabilityDAO();

    public abstract BookingDAO getBookingDAO();

    public abstract HostDAO getHostDAO();

    public abstract NotificationDAO getNotificationDAO();

    public abstract ReviewDAO getReviewDAO();

    public abstract StayDAO getStayDAO();
}
