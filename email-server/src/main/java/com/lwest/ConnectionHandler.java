package com.lwest;

import java.net.Socket;

public class ConnectionHandler implements Runnable {
    private final Socket clientSocket;
    private final ConnectionState connectionState;

    ConnectionHandler(Socket clientSocket, ConnectionState connectionState) {
        this.clientSocket = clientSocket;
        this.connectionState = connectionState;
    }
    
    @Override
    public void run() {
        System.out.println("run");
        while (!Thread.currentThread().isInterrupted()) {

        }
    }
}

        // System.out.println("run()");
        // try {
        //     System.out.println("Is Interrupted: " + Thread.currentThread().isInterrupted());
        //     while (running && !Thread.currentThread().isInterrupted()) {
        //         startTime = System.currentTimeMillis();
        //         startUptimeCounter();
        //         System.out.println("Awaiting connection");
        //         setDebugState(DebugState.CONNECTING);

        //         clientSocket = serverSocket.accept(); // waits until connection is established, but is interruptable
        //         startTime = System.currentTimeMillis();
        //         startUptimeCounter();

        //         setDebugState(DebugState.CONNECTED);
        //         System.out.println("Client connected");
                
        //         in = new ObjectInputStream(clientSocket.getInputStream());
        //         out = new ObjectOutputStream(clientSocket.getOutputStream());

        //         while (running && !Thread.currentThread().isInterrupted()) {
        //             if (in.available() != 0) {handleInput();}
        //         }
        //     }
        // } catch (SocketException e) {
        //     System.out.println("Stopping... Server socket closed.");
        //     Thread.currentThread().interrupt();
        // } catch (IOException e) {
        //     e.printStackTrace();
        // } finally {
        //     System.out.println("Exiting run()");
        //     setDebugState(DebugState.OFFLINE);
        // }
