public class Note {
    private int[] bottom_coord;
    private int width;
    private int height;
    private String colour;
    private String message;
    private int pin_status;
    public int start_x;
    public int start_y;

    public Note (int[] bottom_coord, int width, int height, String colour, String message, int pin_status){
        this.bottom_coord = bottom_coord;
        this.width = width;
        this.height = height;
        this.colour = colour;
        this.message = message;
        this.pin_status = pin_status;

        this.start_y = bottom_coord[1] + height;
        this.start_x = bottom_coord[0] + width;
    }

    public String get_note_details(){
        
        return null;
    }
    public void upPinStatus(){
        this.pin_status++;
    }
    public void downPinStatus(){
        this.pin_status--;
    }

    public int getPinStatus() {
        return this.pin_status;
    }

    @Override
    public String toString()
    {   
        String note_info = "bottom_coord: " + this.bottom_coord[0] + this.bottom_coord[1] + "width: " + this.width + "height: " + this.height + "message: " + this.message + "pinStatus: " + this.pin_status;
        return note_info;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public String getColour() {
        return this.colour;
    }

    public int[] getCoords() {
        return this.bottom_coord;
    }

}
