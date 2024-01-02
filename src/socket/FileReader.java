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

    private static String readFileContents(String filePath) {
        StringBuilder content = new StringBuilder();
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            return "Invalid path or file does not exist.";
        }
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()){
                content.append(sc.nextLine()+" ");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return content.toString();
    }


    public void actionPerformed (ActionEvent e){
        String modifiedSentence;

        try {
            clientSocket = new Socket(ipTextField.getText(),Integer.parseInt(portTextField.getText()));
            outToServer = clientSocket.getOutputStream();
            inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String data = readFileContents(pathTextField.getText());
            outToServer.write((data+"\n").getBytes());
            modifiedSentence = inFromServer.readLine();
            resultTextArea.setText(modifiedSentence);
            clientSocket.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }
}