public class Field {

    private int row;
    private int col;
    private boolean is_taken;

    Field(int row, int col, boolean is_taken){
        this.row = row;
        this.col = col;
        this.is_taken = is_taken;
    }



    public int get_row() {
        return row;
    }

    public int get_col() {
        return col;
    }

    public boolean get_is_taken(){
        return is_taken;
    }

    public void set_is_taken(boolean is_taken) {
        this.is_taken = is_taken;
    }
}

