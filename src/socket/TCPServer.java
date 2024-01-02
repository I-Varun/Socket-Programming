package socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

    public void start() throws Exception{
        String sentence;

        ServerSocket welcomeSocket = new ServerSocket(3000);

        while(true){

            Socket connectionSocket = welcomeSocket.accept();

            BufferedReader inFromClient = new BufferedReader(new

                    InputStreamReader(connectionSocket.getInputStream()));

            OutputStream outToClient = connectionSocket.getOutputStream();

            sentence = inFromClient.readLine();



            outToClient.write((sentence+"\n").getBytes());

            connectionSocket.close();
    }

    }

}