module lk.ijse.gdse71.finalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires jdk.httpserver;
    requires mysql.connector.j;
    requires java.mail;
    requires net.sf.jasperreports.core;


    opens lk.ijse.gdse71.finalproject.controller to javafx.fxml;
    opens lk.ijse.gdse71.finalproject.dto.tm to javafx.base;
    exports lk.ijse.gdse71.finalproject;
}