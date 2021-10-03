import java.net.*;
import java.io.*;


public class BoardServer {
    public static void main(String[] args) throws Exception {
        //int port = Integer.parseInt(args[1]); Use this later for specifying port number.
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(4444);
        } catch (Exception e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(1);
        }

        while(true){
            Socket connection = socket.accept();
            BoardSession newRequest = new BoardSession(connection);
            Thread thread = new Thread(newRequest);
            thread.start();
        }

    }
}