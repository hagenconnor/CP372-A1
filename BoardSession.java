import java.io.*;
import java.net.*;
import java.util.*;

public class BoardSession implements Runnable{
    Socket socket;
    InputStream is;
    OutputStream os;
    BufferedReader stdIn;
    public static void main(String args[]){
        
    }
    public void run() {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("Connected successfully");
            stdIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            try {
                startBoard();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public BoardSession (Socket socket) throws Exception{
        this.socket = socket;
    }

    public void startBoard() throws Exception{
        String fromClient;
        while (true){
            if ((fromClient = stdIn.readLine()) != null){
                //ADD BOARD CODE HERE -- this is our BOARD mechanics
                System.out.println("Get pressed -- Test message to server");
                
            }
        }
        

    }
}
