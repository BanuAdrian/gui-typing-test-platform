module gui.typing.test.platform {
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.controls;
    requires java.sql;

    exports views;
    exports controllers;

    opens controllers to javafx.fxml;
    opens views to javafx.fxml;
    opens css to javafx.fxml;
}