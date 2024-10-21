module com.example.trabalhoia {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.trabalhoia to javafx.fxml;
    exports com.example.trabalhoia;
}