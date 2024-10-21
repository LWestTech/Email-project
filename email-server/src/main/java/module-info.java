module com.lwest {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.lwest to javafx.fxml;
    exports com.lwest;
}
