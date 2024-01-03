package socket;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class FileReader extends JFrame implements ActionListener {

    Socket clientSocket;
    OutputStream outToServer;
    PrintWriter writer;
    
    BufferedReader inFromServer;
    private JLabel ipLabel, portLabel, fileLabel;
    private JTextField ipTextField, portTextField, pathTextField;
    private JButton readButton;
    private JTextArea resultTextArea;

    public FileReader() {
        this.setTitle("FileReader");
        setSize(600,800);
        setLocation(500, 200);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents(){
        ipLabel = new JLabel("Input IP:");
        ipLabel.setBounds(20, 20, 80, 25);
        add(ipLabel);

        ipTextField = new JTextField();
        ipTextField.setBounds(120, 20, 200, 25);
        add(ipTextField);

        portLabel = new JLabel("Port:");
        portLabel.setBounds(20, 60, 80, 25);
        add(portLabel);

        portTextField = new JTextField();
        portTextField.setBounds(120, 60, 200, 25);
        add(portTextField);

        fileLabel = new JLabel("File Location:");
        fileLabel.setBounds(20, 100, 100, 25);
        add(fileLabel);

        pathTextField = new JTextField();
        pathTextField.setBounds(120, 100, 200, 25);
        add(pathTextField);

        readButton = new JButton("Read");
        readButton.setBounds(20, 140, 80, 25);
        readButton.addActionListener(this);
        add(readButton);

        resultTextArea = new JTextArea();
        resultTextArea.setLineWrap(true);
        resultTextArea.setWrapStyleWord(true);
        resultTextArea.setBounds(20, 180, 500, 500);
        add(resultTextArea);
    }
    public static void main(String[] args) throws Exception {
        FileReader fr = new FileReader();
        TCPServer tserver = new TCPServer();
        fr.setVisible(true);
        tserver.start();
    }


    public void actionPerformed (ActionEvent e){
        

        try {
        	// Creating socket for client
            clientSocket = new Socket(ipTextField.getText(),Integer.parseInt(portTextField.getText()));

            writer = new PrintWriter(clientSocket.getOutputStream(), true);
            inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            
            // Abstracting the filepath
            String Filepath = pathTextField.getText();

            //Send the filepath to server
            writer.println(Filepath);


            // Reading the response from the server
            String data;
            StringBuilder response = new StringBuilder();
            while ((data =  inFromServer.readLine()) != null) {
                response.append(data + "\n");
            }
            //Showing in the jframe
            resultTextArea.setText(response.toString());
            
            clientSocket.close();
            inFromServer.close();
            writer.close();
            
          
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }
}
