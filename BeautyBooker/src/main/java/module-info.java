module com.beautybooker.beautybooker {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.jfoenix;
    requires com.google.gson;
    requires jjwt.api;
    requires com.fasterxml.jackson.databind;

    opens com.beautybooker.beautybooker to javafx.fxml;
    exports com.beautybooker.beautybooker;
    exports com.beautybooker.beautybooker.interfaces;
    opens com.beautybooker.beautybooker.interfaces to javafx.fxml;
    exports com.beautybooker.beautybooker.controllers;
    opens com.beautybooker.beautybooker.controllers to javafx.fxml;
    exports com.beautybooker.beautybooker.models.bean;
    opens com.beautybooker.beautybooker.models.bean to com.google.gson;
}