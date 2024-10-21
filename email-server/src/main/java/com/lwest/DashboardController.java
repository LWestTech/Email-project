package com.lwest;

import java.io.IOException;

import com.lwest.EmailServer.DebugState;

import javafx.animation.Animation.Status;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class DashboardController {
    EmailServer server = ServerClient.emailServer;
    @FXML private Label status;
    @FXML private Label uptime;
    
    @FXML
    private void switchToData() throws IOException {
        ServerClient.setRoot("data");
    }
    

    @FXML
    public void initialize() {
        server.uptimeProperty().addListener((observable, oldValue, newValue) -> {
            uptime.setText(formatUptime(newValue));
        });

        // Bind the Text's textProperty to the currentStatus's value
        status.textProperty().bind(server.debugStateProperty().asString());
        server.debugStateProperty().addListener((observable, oldValue, newValue) -> {
            uptime.textFillProperty().set(debugStateColor(observable.getValue()));
        });
    }

    private Color debugStateColor(DebugState state) {
        switch(state) {
            case CONNECTING:
                return Color.rgb(118, 63, 63);
            case CONNECTED:
                return Color.RED;
            case PAUSED:
                return Color.rgb(183, 162, 0);
            default:
                return Color.BLACK;
        }
    }
    
    private String formatUptime(long uptime) {
        long seconds = (uptime / 1000) % 60;
        long minutes = (uptime / (1000 * 60)) % 60;
        long hours = (uptime / (1000 * 60 * 60)) % 24;
        long days = (uptime / (1000 * 60 * 60 * 24));

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    @FXML
    private void start() throws IOException {
        server.start(12345);
    }
    @FXML
    private void stop() throws IOException {
        server.stop();
    }
    @FXML
    private void pause() throws IOException {
        server.pause();
    }
}
