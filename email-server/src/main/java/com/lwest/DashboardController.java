package com.lwest;

import java.io.IOException;


import javafx.animation.Animation.Status;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class DashboardController {
    Server server = ServerApp.server;
    @FXML private Label status;
    @FXML private Label uptime;
    @FXML private Button stopButton;
    @FXML private Button startButton;
    
    @FXML
    private void switchToData() throws IOException {
        ServerApp.setRoot("data");
    }
    

    @FXML
    public void initialize() {
        stopButton.setDisable(true);
        server.getConnectionState().addListener((observable, oldValue, newValue) -> {
            // System.out.println("skfjslk " + newValue);
        });
        server.getConnectionState().getConnectingTime().addListener((observable, oldValue, newValue) -> {
            status.textProperty().bind(server.getConnectionState().asString());
        });
        // server.uptimeProperty().addListener((observable, oldValue, newValue) -> {
        //     uptime.setText(formatUptime(newValue));
        // });

        // // Bind the Text's textProperty to the currentStatus's value
        // status.textProperty().bind(server.debugStateProperty().asString());
        // server.debugStateProperty().addListener((observable, oldValue, newValue) -> {
        //     uptime.textFillProperty().set(debugStateColor(observable.getValue()));
        // });
    }

    private Color debugStateColor(ConnectionState state) {
        // switch(state) {
        //     case CONNECTING:
        //         return Color.rgb(118, 63, 63);
        //     case CONNECTED:
        //         return Color.RED;
        //     case PAUSED:
        //         return Color.rgb(183, 162, 0);
        //     default:
        //         return Color.BLACK;
        // }
        return Color.BLACK;
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
        uptime.setText("00:00:00");
        startButton.setDisable(true);
        stopButton.setDisable(false);
        server.start(12345);
    }
    @FXML
    private void stop() throws IOException {
        stopButton.setDisable(true);
        startButton.setDisable(false);
        server.stop();
    }
    // @FXML
    // private void pause() throws IOException {
    //     server.pause();
    // }
}
