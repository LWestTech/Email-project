package com.lwest;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        ClientApp.setRoot("secondary");
    }
}
