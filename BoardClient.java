import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class BoardClient {
    public static void main(String args[]){
    JFrame frame = new JFrame("BoardClient");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(400,200);

    JButton connect_button = new JButton("Connect");
    JButton disconnect_button = new JButton("Disconnect");
    JButton post_button = new JButton("POST");
    JButton get_button = new JButton("GET");
    JButton pin_button = new JButton("PIN");
    JButton unpin_button = new JButton("UNPIN");
    JButton clear_button = new JButton("Clear");
    JButton shake_button = new JButton("Shake");

    JTextField server_ip = new JTextField();
    JTextField server_port = new JTextField();
    JTextField post_command = new JTextField();

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

    frame.getContentPane().add(BorderLayout.EAST, connect_button);
    frame.getContentPane().add(BorderLayout.WEST, disconnect_button);
    //frame.getContentPane().add(BorderLayout.CENTER, post_button);
    //frame.getContentPane().add(BorderLayout.CENTER, get_button);
    //frame.getContentPane().add(BorderLayout.CENTER, pin_button);
    //frame.getContentPane().add(BorderLayout.CENTER, unpin_button);
    //frame.getContentPane().add(BorderLayout.SOUTH, clear_button);
    //frame.getContentPane().add(BorderLayout.SOUTH, shake_button);
    frame.getContentPane().add(BorderLayout.NORTH, server_ip);
    frame.getContentPane().add(BorderLayout.NORTH, server_port);
    //frame.getContentPane().add(BorderLayout.CENTER, post_command);
    frame.getContentPane().add(BorderLayout.CENTER, results);



    frame.setVisible(true);
    
    }

    static void openConnection(String ip, int port) throws IOException{
        Socket kkSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            kkSocket = new Socket(ip, port);
            out = new PrintWriter(kkSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: taranis.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: taranis.");
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;

        while ((fromServer = in.readLine()) != null) {
            System.out.println("Server: " + fromServer);
            if (fromServer.equals("Bye."))
                break;
		    
        }

        out.close();
        in.close();
        stdIn.close();
        kkSocket.close();

    }

}
