module NestAway {
    requires java.logging;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.sql;
    requires java.desktop;

    opens com.nestaway.controller.gui.fx;
    opens com.nestaway.utils.view.fx;
    opens com.nestaway.bean;
    opens com.nestaway.engineering.payment;
    opens com.nestaway.utils.dao;

    exports com.nestaway.controller.gui.fx;
    exports com.nestaway.bean;
    exports com.nestaway.utils.view.fx;
    exports com.nestaway.utils.dao;
    exports com.nestaway.engineering.payment;
    exports com.nestaway.exception;
    exports com.nestaway;
    exports com.nestaway.utils;
    opens com.nestaway.utils;
}