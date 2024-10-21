package com.lwest;

import java.io.*;
import java.net.*;

public class Client {
    public Client() {

    }

    public void startConnection() {
        
    }
}

//         String hostname = "localhost";
//         int port = 12345;
//         while (true) {
//             System.out.println("Connecting...");
//             try (Socket socket = new Socket(hostname, port)) {
//                 System.out.println("Connection Established");
//                 // Get input and output streams
//                 OutputStream output = socket.getOutputStream();
//                 PrintWriter writer = new PrintWriter(output, true);
    
//                 InputStream input = socket.getInputStream();
//                 BufferedReader reader = new BufferedReader(new InputStreamReader(input));
    
//                 // Send message to the server
//                 writer.println("Hello from the client!");
    
//                 // Read the server's response
//                 String response = reader.readLine();
//                 System.out.println("Server response: " + response);
    
//             } catch (UnknownHostException ex) {
//                 System.out.println("Server not found: " + ex.getMessage());
//             } catch (IOException ex) {
//                 System.out.println("I/O error: " + ex.getMessage());
//             }
//         }
