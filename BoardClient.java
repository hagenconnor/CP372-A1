import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class BoardClient {

    static Socket boardSocket = null;
    static PrintWriter out = null;
    static BufferedReader in = null;


    public static void main(String args[]){
    JFrame frame = new JFrame("BoardClient");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800,400);
    
    //Connection panel.
    JPanel connection = new JPanel();
    JButton connect_button = new JButton("Connect");
    JButton disconnect_button = new JButton("Disconnect");
    JTextField server_ip = new JTextField();
    server_ip.setPreferredSize( new Dimension( 200, 24 ) );
    JLabel ip_label = new JLabel("IP Address: ");
    JTextField server_port = new JTextField();
    server_port.setPreferredSize( new Dimension( 200, 24 ) );
    JLabel port_label = new JLabel("Port #: ");

    //Results panel.
    JPanel interaction = new JPanel();
    JLabel results_label = new JLabel("Results: ");
    JTextArea results = new JTextArea();
    results.setPreferredSize( new Dimension( 400, 200 ) );
    results.setLineWrap(true);

    
    //Board panel.
    JPanel board_panel = new JPanel();
    JLabel command_label = new JLabel("Command: ");
    JTextField commands = new JTextField();
    commands.setPreferredSize( new Dimension( 200, 24 ) );
    JButton post = new JButton("POST");
    JButton get = new JButton("GET");
    JButton pin = new JButton("PIN");
    JButton unpin = new JButton("UNPIN");
    JButton clear = new JButton("CLEAR");
    JButton shake = new JButton("SHAKE");

    connect_button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed (ActionEvent e){
            try {
                String status = openConnection(server_ip.getText(), Integer.parseInt(server_port.getText()));
                results.setText(status);
            } catch (NumberFormatException e1) {
                // TODO Auto-generated catch block
                results.setText("ERROR - Please enter a valid IP address/Port Number to connect.");
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                results.setText("ERROR -- unable to initiate connection. Please try again.");
            }
        }
    });

    disconnect_button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed (ActionEvent e){
            try {
                closeConnection();
                results.setText("Connection closed.");
            } catch (NumberFormatException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    });
    get.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed (ActionEvent e){
            out.println("GET" + " " + commands.getText());
            try {
                results.setText(listenForResponse());
                commands.setText(null);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    });
    post.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed (ActionEvent e){
            out.println("POST" + " " + commands.getText());
            try {
                results.setText(listenForResponse());
                commands.setText(null);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    });

    pin.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed (ActionEvent e){
            out.println("PIN" + " " + commands.getText());
            try {
                results.setText(listenForResponse());
                commands.setText(null);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    });

    unpin.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed (ActionEvent e){
            out.println("UNPIN" + " " + commands.getText());
            try {
                results.setText(listenForResponse());
                commands.setText(null);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    });

    clear.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed (ActionEvent e){
            out.println("CLEAR");
        }
    });

    shake.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed (ActionEvent e){
            out.println("SHAKE");
        }
    });

    //Connection panel.
    connection.add(connect_button);
    connection.add(disconnect_button);
    connection.add(ip_label);
    connection.add(server_ip);
    connection.add(port_label);
    connection.add(server_port);

    //Results panel
    interaction.add(results_label);
    interaction.add(results);

    //Command panel
    board_panel.add(command_label);
    board_panel.add(commands);
    board_panel.add(post);
    board_panel.add(get);
    board_panel.add(pin);
    board_panel.add(unpin);
    board_panel.add(clear);
    board_panel.add(shake);
    
    //Add panels to frame.
    frame.getContentPane().add(BorderLayout.NORTH,connection);
    frame.getContentPane().add(BorderLayout.CENTER,interaction);
    frame.getContentPane().add(BorderLayout.SOUTH, board_panel);

    frame.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
            try {
                closeConnection();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                System.exit(0);
            }
        }
    });
    

    frame.setVisible(true);
    
    }

    static String openConnection(String ip, int port) throws IOException{
        try {
            boardSocket = new Socket(ip, port);
            boardSocket.setSoTimeout(5*1000); //Set a timeout to prevent system hang if server does not respond.
            out = new PrintWriter(boardSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(boardSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Unable to find host.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Unable to obtain resources to connect.");
            System.exit(1);
        }
        String fromServer;
        
        if ((fromServer = in.readLine()) != null) {
            String text = fromServer;
            return text;
        }
        else{
            return "error connecting";
        }

    }
    static void closeConnection() throws IOException{
        out.println("DISCONNECT");
        out.close();
        in.close();
        boardSocket.close();

        System.out.println("Connection closed");
    }
    private static String listenForResponse() throws IOException{
        String fromServer = null;
        try {
            while ((fromServer = in.readLine()) == null) {
            //Infinite loop to wait for server response.
            //Exception is thrown from socket if response > 5 secs.
            }
        } catch (SocketTimeoutException e){
            fromServer = "Timeout -- No response from server. Please try again.";
        }
        return fromServer;
    }
}
