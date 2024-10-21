package com.lwest;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.util.Duration;

public class EmailServer {
    public enum DebugState{
        OFFLINE,
        PAUSED,
        CONNECTING,
        CONNECTED
    }

    private ObjectProperty<DebugState> debugState;
    private ObjectProperty<Long> uptime;
    private ServerSocket serverSocket;
    private Thread serverThread;
    private Socket clientSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private boolean running;
    private long startTime;
    private Timeline uptimeTimeline;

    public EmailServer() {
        this.debugState = new SimpleObjectProperty<>(DebugState.OFFLINE);
        this.uptime = new SimpleObjectProperty<>(0L);
    }

    public ObjectProperty<DebugState> debugStateProperty() {
        return debugState;
    }
    public ObjectProperty<Long> uptimeProperty() {
        return uptime;
    }
    
    public DebugState getDebugState() {
        return debugState.get();
    }

    public void setDebugState(DebugState newState) {
        debugState.set(newState);
    }

    public void start(int port) throws IOException {
        System.out.println("start()");
        running = true;
        setDebugState(DebugState.CONNECTING);

        if (serverSocket == null) {
            System.out.println("Starting new server socket");
            serverSocket = new ServerSocket(port);
        }
        if (serverThread == null) {
            System.out.println("Starting new port watcher server thread");
            serverThread = new Thread(new ConnectionHandler());
        }
        if (!serverThread.isAlive()) {
            serverThread.start();
        }
        if (serverThread.isInterrupted()) {
            serverThread.run();
            System.out.println("thread started from goonville");
        }
        System.out.println("thread alive: " + serverThread.isAlive());
    }

    public void pause() {
        running = false;
        setDebugState(DebugState.PAUSED);
        if (uptimeTimeline != null) {
            uptimeTimeline.stop();
            System.out.println("pause().updateTimeline.stop()");
        }
    }

    public void stop() throws IOException {
        running = false;
        setDebugState(DebugState.OFFLINE);
        if (serverThread != null && serverThread.isAlive()) {
            serverThread.interrupt(); // Interrupt the server thread
        }
        if (serverSocket != null && !serverSocket.isClosed()) {
            serverSocket.close(); // Close the server socket to stop accepting connections
        }
        serverSocket = null;
        serverThread = null;

        if (uptimeTimeline != null) {
            uptimeTimeline.stop();
            System.out.println("stop().updateTimeline.stop()");
        }
    }

    private void startUptimeCounter() {
        uptimeTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            long currentUptime = System.currentTimeMillis() - startTime; // Calculate uptime
            uptime.set(currentUptime);
        }));
        uptimeTimeline.setCycleCount(Timeline.INDEFINITE);
        uptimeTimeline.play(); // Start the timeline
    }

    // private String formatUptime(long uptime) {
    //     long seconds = (uptime / 1000) % 60;
    //     long minutes = (uptime / (1000 * 60)) % 60;
    //     long hours = (uptime / (1000 * 60 * 60)) % 24;
    //     long days = (uptime / (1000 * 60 * 60 * 24));

    //     return String.format("%d days, %02d:%02d:%02d", days, hours, minutes, seconds);
    // }

    public boolean sendObject(Object obj) {
        if (out != null) {
            try {
                out.writeObject(obj);
                out.flush();
                return true;
            } catch (IOException e) {}
        }
        return false;
    }

    private void handleInput() {
        return;
    }

    private Object readObject() throws ClassNotFoundException {
        Object obj = new Object();
        try {
            obj = in.readObject();
        } catch(IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return obj;
    }
}
