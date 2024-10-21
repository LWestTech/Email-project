package com.lwest;

import java.util.Arrays;

public class ConnectionState {
    private State state;
    private long connectingTime;
    private long onlineTime;
    private Thread timerThread;

    public ConnectionState() {this.state = State.OFFLINE;}
    
    public void connecting() {
        state = State.CONNECTING;
        timerThread = new Thread(new TimerThread(this::setConnectingTime));
        timerThread.start();
    }

    public void online() {
        state = State.ONLINE;
    }
    
    public void offline() {
        state = State.OFFLINE;
    }

    private void setConnectingTime(long t) {connectingTime = t;}
    private void setOnlineTime(long t) {onlineTime = t;}

    public enum State {
        CONNECTING,
        ONLINE,
        OFFLINE;
    }
    private class TimerThread implements Runnable {
        private long anchorTime;
        private java.util.function.Consumer<Long> setTime;
        private TimerThread(java.util.function.Consumer<Long> setTime) {
        private TimerThread(Runnable setTime) {
            this.setTime = setTime;
            anchorTime = System.currentTimeMillis();
        }
        @Override
        public void run() {
            try {
                Thread.sleep(1000 / 3);
                connectingTime = System.currentTimeMillis() - anchorTime;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }
}
