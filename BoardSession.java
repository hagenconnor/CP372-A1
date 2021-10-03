import java.io.*;
import java.net.*;
import java.util.*;

public class BoardSession implements Runnable{
    Socket socket;
    public static void main(String args[]){
        
    }
    public void run() {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("Connected successfully");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public BoardSession (Socket socket) throws Exception{
        this.socket = socket;
    }
}
