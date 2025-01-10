module edu.umb.uaspbo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires org.apache.commons.codec;
    requires de.jensd.fx.glyphs.fontawesome;
    requires de.jensd.fx.glyphs.commons;
    requires jasperreports;

    opens edu.umb.uaspbo to javafx.fxml;
    exports edu.umb.uaspbo;
    exports edu.umb.uaspbo.controller;
    exports edu.umb.uaspbo.dto;
    exports edu.umb.uaspbo.entity;
    opens edu.umb.uaspbo.controller to javafx.fxml;
}