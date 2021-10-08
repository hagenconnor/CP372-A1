import java.io.*;
import java.net.*;
import java.util.*;

public class BoardSession implements Runnable{
    Socket socket;
    InputStream is;
    OutputStream os;
    BufferedReader stdIn;
    PrintWriter out;
    public static void main(String args[]){
        
    }
    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            stdIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            try {
                boardDetails();
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
    public void boardDetails(){
        String output = "-----Online Bulletin Board----\n ver1.0\n Connected successfully.\nBoard Width: " 
        + BoardServer.board_width + "\nBoard Height: " + BoardServer.board_height + "\nAvailable colors: " + BoardServer.avail_colours + "\n";
        out.println(output);
    }

    public void startBoard() throws Exception{
        String fromClient;
        while (true){
            if ((fromClient = stdIn.readLine()) != null){
                //ADD BOARD CODE HERE -- this is our BOARD mechanics
                //System.out.println("Get pressed -- Test message to server");

                String[] tokens = fromClient.split(" ");
                String command = tokens[0];
                System.out.println(command);
                
                if (command.equals("POST")){
                    if (tokens.length == 1){
                        out.println("ERROR - Invalid command. Please try again.\n");
                    }
                    else{
                        int[] LL_coord = new int[2];
                        LL_coord[0] = Integer.parseInt(tokens[1]);
                        LL_coord[1] = Integer.parseInt(tokens[2]);
                        ArrayList<String> message = new ArrayList<String>();
    
                        
                        int width = Integer.parseInt(tokens[3]);
                        int height = Integer.parseInt(tokens[4]);
                        String colour = tokens[5];
                        for (int i=6; i< tokens.length; i++){
                            message.add(tokens[i]);
                        }
                        String joinedString = "";
                        for (String s : message) {
                            joinedString += s + " ";
                        }
                        if (BoardServer.avail_colours.contains(colour)){
                            int total_width = LL_coord[0] + width;
                            int total_height = LL_coord[1] + height;
                            if ((total_width > BoardServer.board_width) | (total_height > BoardServer.board_height)){
                                out.println("ERROR -- Note is too large. Please try again.\n");
                            } 
                            else {
                                Note addNote = new Note(LL_coord, width, height, colour, joinedString, 0);
                                addToNoteboard(addNote);
                                //BoardServer.noteboard.add(addNote);
                                out.println(addNote + "\n");
                            }
                        }
                        else{
                            out.println("ERROR -- Color is not allowed.\n");
                        }
                    }
                }
                if (command.equals("GET")){

                    //If GET is used without any arguments.
                    if (tokens.length == 1) {
                        String toResults = "";
                        for (int i = 0; i < BoardServer.noteboard.size(); i++) {
                            toResults = toResults + " | " + BoardServer.noteboard.get(i);
                        }
                        out.println(toResults + "\n");
                    }
                    //Check for PINS command.
                    else if (tokens[1].equals("PINS")) {
                        out.println(BoardServer.pin_list+ "\n");
                    }
                    //If contains is used as an argument.
                    else if (tokens[1].equals("contains=")) {
                        int x = Integer.parseInt(tokens[4]);
                        int y = Integer.parseInt(tokens[5]);
                        int[] coord = new int[2];
                        coord[0] = x;
                        coord[1] = y;
                        out.println(BoardServer.searchBoard(null, coord, null) + "\n");
                    }
                    //If refersTo is used as an arument.
                    else if (tokens[1].equals("refersTo=")) {
                        out.println(BoardServer.searchBoard(null, null, tokens[2]) + "\n");
                    }
                    //If Colour is used as an argument.
                    else if (tokens[1].equals("color=")) {
                        Boolean isContained = BoardServer.avail_colours.contains(tokens[2]);

                        if (isContained) {
                            out.println(BoardServer.searchBoard(tokens[2], null, null) + "\n");
                        } else {
                            out.println("Color that was given is not permitted.\n");
                            System.err.println("Color that was given is not permitted.\n");
                        }

                    } else if (tokens[1].equals("color=") & tokens[3].equals("refersTo=")) {
                        Boolean isContained = BoardServer.avail_colours.contains(tokens[2]);

                        if (isContained) {
                            out.println(BoardServer.searchBoard(tokens[2], null, tokens[3]) + "\n");
                        } else {
                            out.println("Color that was given is not permitted.\n");
                            System.err.println("Color that was given is not permitted.\n");
                        }
                    }
                    else if (tokens[1].equals("color=") & tokens[3].equals("contains=")) {
                        int x = Integer.parseInt(tokens[4]);
                        int y = Integer.parseInt(tokens[5]);
                        int[] coord = new int[2];
                        coord[0] = x;
                        coord[1] = y;
                        Boolean isContained = BoardServer.avail_colours.contains(tokens[2]);

                        if (isContained) {
                            out.println(BoardServer.searchBoard(tokens[2], coord, null) + "\n");
                        } else {
                            out.println("Color that was given is not permitted.\n");
                            System.err.println("Color that was given is not permitted.\n");
                        }
                    }
                    else if (tokens[1].equals("refersTo=") & tokens[3].equals("contains=")) {
                        int x = Integer.parseInt(tokens[4]);
                        int y = Integer.parseInt(tokens[5]);
                        int[] coord = new int[2];
                        coord[0] = x;
                        coord[1] = y;
                        out.println(BoardServer.searchBoard(null, coord, tokens[2]) + "\n");
                    }
                    //All conditions are selected.
                    else if (tokens.length == 7) {
                        if (tokens[1].equals("color=") & tokens[3].equals("contains=") & tokens[6].equals("refersTo=")){
                            int x = Integer.parseInt(tokens[4]);
                            int y = Integer.parseInt(tokens[5]);
                            int[] coord = new int[2];
                            coord[0] = x;
                            coord[1] = y;
                            out.println(BoardServer.searchBoard(tokens[2], coord, tokens[7]) + "\n");
                        }
                    }
                    
                    //Bad input. Send error.
                    else{
                        out.println("Command that was entered into the system does not exist.\n");
                        System.err.println("This entered command does not exist.\n");
                    }

                }
                if (command.equals("PIN")){
                    if (tokens.length == 1){
                        out.println("ERROR - Invalid PIN coordinates. Please try again.\n");
                    }
                    else {
                        String[] temp =  fromClient.split(" ");
                        String[] pin_loc = temp[1].split(",");
                        int x = Integer.parseInt(pin_loc[0]);
                        int y = Integer.parseInt(pin_loc[1]);
                        Pin newPin = new Pin(x,y);
                        BoardServer.mapPins(newPin);
                        out.println("Pin added.\n");

                        BoardServer.debug(); //Used for debugging.

                }
            }
                if (command.equals("UNPIN")){
                    if (tokens.length == 1){
                        out.println("ERROR - Invalid PIN coordinates. Please try again.\n");
                    }
                    else {
                        String[] temp =  fromClient.split(" ");
                        String[] pin_loc = temp[1].split(",");
                        int x = Integer.parseInt(pin_loc[0]);
                        int y = Integer.parseInt(pin_loc[1]);
                        Pin newPin = new Pin(x,y);
                        if (!(BoardServer.pin_list.contains(newPin))){
                            out.println("Pin not found.\n");
                        }
                        else{
                            BoardServer.demapPins(newPin);
                            out.println("Pin removed.\n");
                        }

                        BoardServer.debug(); //Used for debugging.

                }

                }
                if (command.equals("SHAKE")){
                    shakeNoteboard();
                    out.println("Shake -- All notes without pins were removed.\n");
                }
                if (command.equals("CLEAR")){
                    clearNoteboard();
                    //BoardServer.noteboard.clear();
                    out.println("Board cleared.\n");
                }
                if (command.equals("DISCONNECT")){
                    //Disconnect gracefully. Helps prevent infinite/runaway loops.
                    Thread.currentThread().interrupt();
                    break;
                }
                
            }
        }
        

    }
    synchronized public void addToNoteboard(Note note){
        BoardServer.noteboard.add(note);
    }
    synchronized public void shakeNoteboard(){
        for (int i=0; i < BoardServer.noteboard.size(); i++){
            if (BoardServer.noteboard.get(i).getPinStatus() == 0){
                BoardServer.noteboard.remove(i);
            }
        }
    }
    synchronized public void clearNoteboard(){
        BoardServer.noteboard.clear();
    }
}
