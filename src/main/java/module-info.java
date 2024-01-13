module com.slither.cpooprojet {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens com.slither.cpooprojet.Model to com.google.gson;
    opens com.slither.cpooprojet to javafx.fxml;
    exports com.slither.cpooprojet;
    exports com.slither.cpooprojet.View;
    opens com.slither.cpooprojet.View to javafx.fxml;
}