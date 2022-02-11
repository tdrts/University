module com.a7.a7 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.a7.a7 to javafx.fxml;
    exports com.a7.a7;
}