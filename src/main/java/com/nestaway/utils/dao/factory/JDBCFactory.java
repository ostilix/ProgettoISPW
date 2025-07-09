package com.nestaway.utils.dao.factory;

import com.nestaway.dao.*;
import com.nestaway.dao.jdbc.*;

public class JDBCFactory extends FactorySingletonDAO {

    public AvailabilityDAO getAvailabilityDAO(){
        return new AvailabilityJDBC();
    }
    public BookingDAO getBookingDAO(){
        return new BookingJDBC();
    }
    public HostDAO getHostDAO(){
        return new HostJDBC();
    }
    public NotificationDAO getNotificationDAO(){
        return new NotificationJDBC();
    }
    public ReviewDAO getReviewDAO(){
        return new ReviewJDBC();
    }
    public StayDAO getStayDAO(){
        return new StayJDBC();
    }

}
