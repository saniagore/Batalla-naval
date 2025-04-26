module com.example.sesion4 {
    requires transitive javafx.controls;
    requires javafx.fxml;

    exports com.example.sesion4 to javafx.graphics;
    exports com.example.sesion4.controller to javafx.fxml;
    exports com.example.sesion4.view;
    exports com.example.sesion4.model;
    opens com.example.sesion4.controller to javafx.fxml;
    opens com.example.sesion4.view to javafx.fxml;
}