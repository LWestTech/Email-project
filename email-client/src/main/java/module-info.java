module com.lwest {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.lwest to javafx.fxml;
    exports com.lwest;
}
