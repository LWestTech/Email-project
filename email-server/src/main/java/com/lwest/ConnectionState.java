package com.lwest;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.function.Consumer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

/**
 * Wrapper class for the state of connection to maintain timers for each state
 */
public class ConnectionState extends SimpleObjectProperty<State>{
    private State state;
    private ObjectProperty<Long> connectingTime;
    private ObjectProperty<Long> onlineTime;
    private ZonedDateTime offlineTime;
    private Timeline counter;

    /**
     * Constructs a connection state
     * @param displayedTime property to bind a counter display to
     */
    public ConnectionState() {
        this.connectingTime = new SimpleObjectProperty<>(0L);
        this.onlineTime = new SimpleObjectProperty<>(0L);
        super.set(State.OFFLINE);
        offline();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getBean() {
        return state;
    }
    
    public void connecting() {
        System.out.println("CONNECTING");
        super.set(State.CONNECTING);
        startCounter(connectingTime);
    }
    
    public void online() {
        System.out.println("ONLINE");
        super.set(State.ONLINE);
        startCounter(onlineTime);
    }
    
    public void offline() {
        System.out.println("OFFLINE");
        super.set(State.OFFLINE);
        stopCounter();
        offlineTime = ZonedDateTime.now();
    }

    public State getState() {return state;}
    public ObjectProperty<Long> getConnectingTime() {return connectingTime;}
    public ObjectProperty<Long> getOnlineTime() {return onlineTime;}
    public ZonedDateTime getOfflineTime() {return offlineTime;}
    
    private void startCounter(ObjectProperty<Long> time) {
        final long anchorTime = System.currentTimeMillis();
        // Create automatically running script
        time.set((long)0);
        counter = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            time.set(System.currentTimeMillis() - anchorTime); // That sets the time to the time since the anchorTime
        }));
        counter.setCycleCount(Timeline.INDEFINITE);
        counter.play();
    }

    private void stopCounter() {
        if (counter != null) counter.stop();
        counter = null;
    }
}