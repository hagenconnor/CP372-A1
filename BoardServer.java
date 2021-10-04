import java.net.*;
import java.io.*;
import java.util.ArrayList;


public class BoardServer {
    //Board storage. This is our data structure for all notes, server-wide.
    //Use BoardSession.java to make changes.
    public static ArrayList <Note> noteboard = new ArrayList<Note>();
    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt(args[0]); //Use this later for specifying port number.
        int board_width = Integer.parseInt(args[1]);
        int board_height = Integer.parseInt(args[2]);
        ArrayList <String> avail_colours = new ArrayList<String>();
        for (int i=3; i< args.length; i++){
            avail_colours.add(args[i]);
        }

        System.out.println(port);
        System.out.println(board_width);
        System.out.println(board_height);
        for (int i=0; i < avail_colours.size(); i++){
            System.out.println(avail_colours.get(i));
        }
        
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(port);
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