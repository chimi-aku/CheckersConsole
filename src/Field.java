public class Field {

    private int row;
    private int col;
    private boolean is_taken;

    Field(int row, int col, boolean is_taken){ // <- this method is a constructor of field
        this.row = row;
        this.col = col;
        this.is_taken = is_taken;
    }

    public int get_row() {
        return row;
    } // <- this method is a getter of row attribute

    public int get_col() {
        return col;
    } // <- this method is a getter of col attribute

    public boolean get_is_taken(){
        return is_taken;
    } // <- this method is a getter of is_taken attribute

    public void set_is_taken(boolean is_taken) { // <- this method is a setter of is_taken attribute
        this.is_taken = is_taken;
    }
}

