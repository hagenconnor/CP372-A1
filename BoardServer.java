import java.net.*;
import java.io.*;
import java.util.ArrayList;


public class BoardServer {
    //Board storage. This is our data structure for all notes, server-wide.
    //Use BoardSession.java to make changes.
    public static ArrayList <Note> noteboard = new ArrayList<Note>();
    static ArrayList <String> avail_colours = new ArrayList<String>();
    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt(args[0]); //Use this later for specifying port number.
        int board_width = Integer.parseInt(args[1]);
        int board_height = Integer.parseInt(args[2]);

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
            System.out.println("New connection established.");
            BoardSession newRequest = new BoardSession(connection);
            Thread thread = new Thread(newRequest);
            thread.start();
        }

    }

    public static ArrayList<Note> searchBoard(String color, String[] contains, String refersTo){
        ArrayList<Note> results = new ArrayList<Note>();
        if (color == null & contains == null){
            //Check only refersTo
            for (int i=0; i< noteboard.size(); i++){
                if ((noteboard.get(i).toString()).contains(refersTo)){
                    results.add(noteboard.get(i));
                }
            }
        }
        else if (contains == null & refersTo == null){
            //Check for only color.
            for (int i=0; i< noteboard.size(); i++){
                if ((noteboard.get(i).getColour()).equals(color)){
                    results.add(noteboard.get(i));
                }
            }
        }
        else if (refersTo == null & color == null){
            //Check for only contains.
            for (int i=0; i< noteboard.size(); i++){
                if ((noteboard.get(i).getCoords().equals(contains))){
                    results.add(noteboard.get(i));
                }
            }
        }
        else{
            //Check for all conditions.
            for (int i=0; i< noteboard.size(); i++){
                if ((noteboard.get(i).getColour()).equals(color) & (noteboard.get(i).getCoords().equals(contains)) & (noteboard.get(i).toString()).contains(refersTo)){
                    results.add(noteboard.get(i));
                }
            }
        }
        
        return results;
    }
}