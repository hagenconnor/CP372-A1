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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pin pin = (Pin) o;
        return x == pin.x &&
                y == pin.y;
    }
}
