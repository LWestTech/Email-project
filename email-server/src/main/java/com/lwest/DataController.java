package com.lwest;

import java.io.IOException;
import javafx.fxml.FXML;

public class DataController {

    @FXML
    private void switchToDashboard() throws IOException {
        ServerApp.setRoot("dashboard");
    }
}