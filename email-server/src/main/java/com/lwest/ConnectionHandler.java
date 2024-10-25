package com.lwest;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ConnectionHandler {
    private ServerSocket serverSocket;
    private ConnectionState state;
    private Thread handlerThread;

    ConnectionHandler(int port, ConnectionState state) {
        System.out.println("new ConnectionHandler");
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(10000);
        } catch (BindException e) {
            System.out.println("Shits already bound on " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        handlerThread = new Thread(new HandlerThread(state));
        start();
        this.state = state;
    }

    public void start() {handlerThread.start(); System.out.println("starting at ConnectionHandler");}

    public void stop() {
        System.out.println("stopping at ConnectionHandler");
        handlerThread.interrupt();
        state.offline();
        System.out.println("Interrupt Completed");
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private class HandlerThread implements Runnable{
        private ConnectionState state;
        private HandlerThread(ConnectionState state) {
            this.state = state;
        }
        @Override
        public void run() {
            state.connecting();
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(state.get());
                // try {
                    // System.out.println("Server:\tWaiting for client on port " + serverSocket.getLocalPort() + "…");
                    System.out.println("Looking for " + state.getConnectingTime().get());
                    // System.out.println(Thread.currentThread().isInterrupted());
                    
                    // Socket socket = serverSocket.accept();
                    // System.out.println("Server:\tJust connected to " + socket.getRemoteSocketAddress());
                    // state.online();
                    // DataInputStream in = new DataInputStream(socket.getInputStream());
                    // System.out.println("Server:\t\t Got Data: " + in.readUTF());                           
                    // socket.close();    
                // } catch (SocketTimeoutException s) {
                //     System.out.println("Socket timed out!");
                //     state.offline();
                //     break;
                // } catch (IOException e) {
                //     e.printStackTrace();
                //     break;
                // }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
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