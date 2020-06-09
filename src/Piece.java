public class Piece {
    private String color;
    private int row;
    private int col;
    private boolean is_king;

    Piece(String color, int col, int row){ // <- this method is a constructor of piece
        this.color = color;
        this.row = row;
        this.col = col;

        this.is_king = false;
    }


    public int get_row() {
        return row;
    } // <- this method is a getter of row attribute

    public int get_col() {
        return col;
    } // <- this method is a getter of col attribute

    public String get_color() {
        return color;
    } // <- this method is a setter of color attribute

    public boolean is_king() {
        return is_king;
    } // <- this method is a getter of is_king attribute


    public void set_row(int row) {
        this.row = row;
    } // <- this method is a setter of row attribute

    public void set_col(int col) {
        this.col = col;
    } // <- this method is a setter of col attribute

    public void set_is_king(boolean is_king){ this.is_king = is_king; } // <- this method is a setter of col attribute
}
