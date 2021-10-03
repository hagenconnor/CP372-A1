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

    JPanel connection = new JPanel();

    JButton connect_button = new JButton("Connect");
    JButton disconnect_button = new JButton("Disconnect");

    JTextField server_ip = new JTextField();
    server_ip.setPreferredSize( new Dimension( 200, 24 ) );
    JLabel ip_label = new JLabel("IP Address: ");
    JTextField server_port = new JTextField();
    server_port.setPreferredSize( new Dimension( 200, 24 ) );
    JLabel port_label = new JLabel("Port #: ");

    JPanel interaction = new JPanel();

    JTextArea results = new JTextArea();

    connect_button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed (ActionEvent e){
            try {
                openConnection(server_ip.getText(), Integer.parseInt(server_port.getText()));
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
            } catch (NumberFormatException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    });

    connection.add(connect_button);
    connection.add(disconnect_button);
    connection.add(ip_label);
    connection.add(server_ip);
    connection.add(port_label);
    connection.add(server_port);

    interaction.add(results);

    frame.getContentPane().add(BorderLayout.NORTH,connection);
    frame.getContentPane().add(BorderLayout.CENTER,interaction);

    frame.setVisible(true);
    
    }

    static void openConnection(String ip, int port) throws IOException{
        try {
            boardSocket = new Socket(ip, port);
            out = new PrintWriter(boardSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(boardSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: taranis.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: taranis.");
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;

        if ((fromServer = in.readLine()) != null) {
            System.out.println("Server: " + fromServer);
        }
        stdIn.close();

    }
    static void closeConnection() throws IOException{
        out.close();
        in.close();
        boardSocket.close();

        System.out.println("Connection closed");
    }

}
