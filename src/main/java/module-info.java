module org.agendadecontatos {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.agendadecontatos to javafx.fxml;
    exports org.agendadecontatos;
    exports org.agendadecontatos.controller;
    opens org.agendadecontatos.controller to javafx.fxml;
}