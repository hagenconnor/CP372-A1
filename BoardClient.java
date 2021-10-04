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
    frame.setSize(800,200);
    
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
    JTextArea results = new JTextArea();
    results.setPreferredSize( new Dimension( 200, 200 ) );
    
    JPanel board_panel = new JPanel();
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
                e1.printStackTrace();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
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
    interaction.add(results);

    //Command panel
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

    frame.setVisible(true);
    
    }

    static String openConnection(String ip, int port) throws IOException{
        try {
            boardSocket = new Socket(ip, port);
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
        out.close();
        in.close();
        boardSocket.close();

        System.out.println("Connection closed");
    }
    private static String listenForResponse() throws IOException{
        String fromServer;
        while ((fromServer = in.readLine()) == null){
        }
        return fromServer;
    }

}
