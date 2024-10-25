package com.lwest;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Server {
    private ConnectionState state;
    private ConnectionHandler handler;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public Server() {
        state = new ConnectionState();
    }

    public void start(int port) throws IOException {
        handler = new ConnectionHandler(12345, state);
        System.out.println("starting at Server");
    }

    public ConnectionState getConnectionState() {return state;}

    // public void pause() {
    //     running = false;
    //     setDebugState(DebugState.PAUSED);
    //     if (uptimeTimeline != null) {
    //         uptimeTimeline.stop();
    //         System.out.println("pause().updateTimeline.stop()");
    //     }
    // }
    
    public void stop() throws IOException {
        System.out.println("stopping at Server");
        handler.stop();
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
