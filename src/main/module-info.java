module com.example.projetcpoo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.projetcpoo to javafx.fxml;
    exports com.example.projetcpoo;
}