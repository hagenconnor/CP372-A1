public class Pin {
    public int x;
    public int y;

    public Pin (int x, int y){
        this.x = x;
        this.y = y;
    }

    public int get_x(Note note){
        return this.x;
    }
    public int get_y(Note note){
        return this.y;
    }

    @Override
    public String toString()
    {   
        String text = "Pin coord: x: " + x + " y: " + y;
        return text;
    }

}
