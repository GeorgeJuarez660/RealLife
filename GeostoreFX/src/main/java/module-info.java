module info {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires transitive org.xerial.sqlitejdbc;

    opens org.controller to javafx.fxml;
    exports org.controller;
    exports org.services;
    opens org.services to javafx.fxml;
    exports org.models;
    opens org.models to javafx.fxml;
    exports org.controller.item;
    opens org.controller.item to javafx.fxml;
    exports org.controller.mask;
    opens org.controller.mask to javafx.fxml;
    exports org.utility;
    opens org.utility to javafx.fxml;
}