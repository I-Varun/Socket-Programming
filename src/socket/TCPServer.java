package socket;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TCPServer {

    public void start() throws Exception{
      
    	System.out.println("Server waiting.......");
    	
        ServerSocket welcomeSocket = new ServerSocket(3000);

        while(true){

            Socket connectionSocket = welcomeSocket.accept();
            
            System.out.println("Connection Established");

            BufferedReader inFromClient = new BufferedReader(new

                    InputStreamReader(connectionSocket.getInputStream()));
            
            String content , message;
            PrintWriter writer = new PrintWriter(connectionSocket.getOutputStream(), true);
            
            //Reading data from file sent by client
            content = inFromClient.readLine();
            
            File file = new File(content);
            
            if (!file.exists() || !file.isFile()) {
                System.out.println("Invalid path or file does not exist.");
            }
            try {
                              
            	 BufferedReader buf  = new BufferedReader(new FileReader(file));
            	 
                 String data;
                 
                 //Sending the read data as a response to client
                 while ((data = buf.readLine()) != null) {
                     writer.println(data);
                 }
                 buf.close();
                 
                 
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            
            connectionSocket.close();
            

    }

    }

}
