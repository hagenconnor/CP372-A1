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
                //System.out.println("Get pressed -- Test message to server");

                String[] tokens = fromClient.split(" ");
                String command = tokens[0];
                System.out.println(command);
                
                if (command.equals("POST")){
                    String[] LL_coord = new String[2];
                    LL_coord[0] = tokens[1];
                    LL_coord[1] = tokens[2];
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

                    Note addNote = new Note(LL_coord, width, height, colour, joinedString, 0);
                    BoardServer.noteboard.add(addNote);
                    out.println(addNote);

                }
                if (command.equals("GET")){

                    if (tokens.length == 1) {
                        String toResults = "";
                        for (int i = 0; i < BoardServer.noteboard.size(); i++) {
                            toResults = toResults + " | " + BoardServer.noteboard.get(i);
                        }
                        out.println(toResults);
                    }                    
                    //if (tokens[1].equals("PINS")) {
                        // if ((existNote.getPinStatus())) {

                        // }
                    //}

                }
                if (command.equals("PIN")){

                }
                if (command.equals("UNPIN")){

                }
                if (command.equals("SHAKE")){

                }
                if (command.equals("CLEAR")){
                    BoardServer.noteboard.clear();
                    out.println("Board cleared.");
                }
                if (command.equals("DISCONNECT")){
                    //Disconnect gracefully. Helps prevent infinite/runaway loops.
                    Thread.currentThread().interrupt();
                    break;
                }
                
            }
        }
        

    }
}
