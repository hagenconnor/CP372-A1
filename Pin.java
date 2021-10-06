import java.util.ArrayList;

public class Pin {
    private int x;
    private int y;
    private ArrayList<Note> affectedNotes;

    public Pin (int x, int y){
        this.x = x;
        this.y = y;
        this.affectedNotes = null;
    }

    public void addNote(Note note){
        affectedNotes.add(note);
    }
    public void deleteNote(Note note){
        affectedNotes.remove(note);
    }

    @Override
    public String toString()
    {   
        String text = "Pin coord: x: " + x + " y: " + y;
        return text;
    }

}
