public class Piece {
    private String color;
    private int row;
    private int col;
    private boolean is_king;

    Piece(String color, int col, int row){
        this.color = color;
        this.row = row;
        this.col = col;

        this.is_king = false;
    }


    public int get_row() {
        return row;
    }

    public int get_col() {
        return col;
    }

    public String get_color() {
        return color;
    }

    public boolean is_king() {
        return is_king;
    }



    public void set_row(int row) {
        this.row = row;
    }

    public void set_col(int col) {
        this.col = col;
    }

    public void set_is_king(boolean is_king){ this.is_king = is_king; }

    public void set_color(String color) {
        this.color = color;
    }

}
