import com.sun.source.tree.CompilationUnitTree;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.lang.String;
import java.util.regex.Pattern;

public class Board {
    int turn;
    private int red_units;
    private int blue_units;
    String[][] board;
    private List<Piece> pieces_list = new LinkedList<Piece>();
    private List<Piece> red_pieces_list = new LinkedList<Piece>();
    private List<Piece> blue_pieces_list = new LinkedList<Piece>();
    private Field[][] fields;

    //How to use colors example
    //System.out.println(ConsoleColors.BLUE + "RED COLORED" + ConsoleColors.RED + " NORMAL");

    // valid_moves_method


    Board() {
        turn = 1;
        board = new String[9][9];
        fields = new Field[9][9];
        //this.white_units = 12;
        //this.black_units = 12;
    }

    public void display_board() {

        board[0][0] = "X ";
        board[0][1] = "1 ";
        board[0][2] = "2 ";
        board[0][3] = "3 ";
        board[0][4] = "4 ";
        board[0][5] = "5 ";
        board[0][6] = "6 ";
        board[0][7] = "7 ";
        board[0][8] = "8 ";

        board[1][0] = "A ";
        board[2][0] = "B ";
        board[3][0] = "C ";
        board[4][0] = "D ";
        board[5][0] = "E ";
        board[6][0] = "F ";
        board[7][0] = "G ";
        board[8][0] = "H ";

        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {

                for (int i = 0; i < blue_pieces_list.size(); i++) {
                    if (blue_pieces_list.get(i).get_row() == row && blue_pieces_list.get(i).get_col() == col) {
                        board[row][col] = (ConsoleColors.BLUE + "o " + ConsoleColors.RESET);
                    }
                }

                for (int i = 0; i < red_pieces_list.size(); i++) {
                    if (red_pieces_list.get(i).get_row() == row && red_pieces_list.get(i).get_col() == col) {
                        board[row][col] = (ConsoleColors.RED + "o " + ConsoleColors.RESET);
                    }
                }

                if (fields[row][col].get_is_taken() == false) board[row][col] = "- ";
            }
        }

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                System.out.print(board[row][col]);
            }
            System.out.print("\n");
        }
    }

    public void setup_board() {

        // Make a list of pieces and fields

        for (int row = 1; row < 9; row++)
            for (int col = 1; col < 9; col++) {

                if (row <= 3 && ((row % 2 != 0 && col % 2 == 0) || (row % 2 == 0 && col % 2 != 0))) {
                    Piece p = new Piece("red", col, row);
                    pieces_list.add(p);
                    red_pieces_list.add(p);
                    fields[row][col] = new Field(row, col, true);
                    //System.out.print("pole Row: "+ fields[row][col].get_row() + " Col: " + fields[row][col].get_col() + " taken: " + fields[row][col].get_is_taken() + " \n");
                    //System.out.print( "row: " + row + " " + "col: "+ col + "\n");
                } else if (row >= 6 && ((row % 2 == 0 && col % 2 != 0) || (row % 2 != 0 && col % 2 == 0))) {
                    Piece p = new Piece("blue", col, row);
                    pieces_list.add(p);
                    blue_pieces_list.add(p);
                    fields[row][col] = new Field(row, col, true);
                    //System.out.print("pole Row: "+ fields[row][col].get_row() + " Col: " + fields[row][col].get_col() + " taken: " + fields[row][col].get_is_taken() + " \n");
                    //System.out.print( "row: " + row + " " + "col: "+ col + "\n");
                } else {
                    fields[row][col] = new Field(row, col, false);
                    //System.out.print("pole Row: "+ fields[row][col].get_row() + " Col: " + fields[row][col].get_col() + " taken: " + fields[row][col].get_is_taken() + " \n");
                }
            }
    }

    /*public List<Field> valid_moves(Piece piece){

        int row = piece.get_row();
        int col = piece.get_col();
        String color = piece.get_color();
        boolean is_king = piece.is_king();

        List<Field> valid_moves_list = new LinkedList<Field>();

        if(is_king == false){
            if(color.equals("white")){

                if( (row + 1 <= 7) && (col + 1 <= 7) && !(fields[row + 1][col + 1].get_is_taken()) )
                    valid_moves_list.add(fields[row + 1][col + 1]);

                if( (row + 1 <= 7) && (col - 1 >= 0) && !(fields[row + 1][col - 1].get_is_taken()) )
                    valid_moves_list.add(fields[row + 1][col - 1]);
            }
            else {

                if( (row - 1 >= 0) && (col + 1 <= 7) && !(fields[row - 1][col + 1].get_is_taken()) )
                    valid_moves_list.add(fields[row - 1][col + 1]);

                if( (row - 1 >= 0) && (col - 1 >= 0) && !(fields[row - 1][col - 1].get_is_taken()) )
                    valid_moves_list.add(fields[row - 1][col - 1]);

            }
        }

        if(valid_moves_list.size() == 0){
            System.out.print("No valid moves");
        }
        else{
            for(int i=0; i<valid_moves_list.size(); i++){
                System.out.println("Valid move in row: " + valid_moves_list.get(i).get_row() + " and col: " + valid_moves_list.get(i).get_col());
            }
        }

        return valid_moves_list;
    }*/

    public void move_piece(Piece piece) {

        if (fields[piece.get_row()][piece.get_col()].get_is_taken()) { // Wywala błąd tablicy fields przy używaniu pustego pola

            //List<Field> valid_moves = valid_moves(piece);
            //List<Integer> new_position = select_position(1, 2);

            //while (select_position().get(0) != valid_moves)

        } else {
            System.out.print("Piece doesn't exist on this field");
            return;
        }


    }

    public Piece select_piece() { // tymczasowe argumenrty, tu będziemy przekazywać kliknięcie

        List<Integer> position_of_piece = new LinkedList<Integer>();
        Scanner scan = new Scanner(System.in);
        String cords = " ";
        boolean valid = true;

        while(valid){

            System.out.println("Type cords of piece which you want to move (for example: A4, a4)");
            cords = scan.nextLine();

            Pattern pattern = Pattern.compile("([A-H]|[a-h])[1-8]");

            valid = !(pattern.matcher(cords).matches());
        }

        System.out.println("Your cords are: " + cords.charAt(0) + ", " + cords.charAt(1));

        position_of_piece = convertCords(cords);
        System.out.println("Your cords are: " + position_of_piece.get(0) + ", " + position_of_piece.get(1));

        if(whose_move().equals("blue")){
            for(int i = 0; i < blue_pieces_list.size(); i++){
                if(blue_pieces_list.get(i).get_row() == position_of_piece.get(0) && blue_pieces_list.get(i).get_row() == position_of_piece.get(1)){
                    System.out.println("Piece: row: " + blue_pieces_list.get(i).get_row() + ", col: " + blue_pieces_list.get(i).get_col() + ", color: " + blue_pieces_list.get(i).get_color());
                    return blue_pieces_list.get(i);
                }
            }
        } else if(whose_move().equals("red")){
            for(int i = 0; i < red_pieces_list.size(); i++){
                if(red_pieces_list.get(i).get_row() == position_of_piece.get(0) && red_pieces_list.get(i).get_row() == position_of_piece.get(1)){
                    System.out.println("Piece: row: " + red_pieces_list.get(i).get_row() + ", col: " + red_pieces_list.get(i).get_col() + ", color: " + red_pieces_list.get(i).get_color());
                    return red_pieces_list.get(i);
                }
            }
        }

        //new_position.add(row);
        //new_position.add(col);
        // row on index 0, col on index 1

        return new Piece("transparent", -1, -1);
    }

    /*public Piece get_piece(int row, int col, List<Integer> integers){

        for( int i = 0; i < pieces_list.size(); i++){
            if(pieces_list.get(i).get_row() == row && pieces_list.get(i).get_col() == col)
                return pieces_list.get(i);
        }

        return new Piece("wrong_cords", -1, -1);
    } */

    public String whose_move(){
        String who;

        if(turn % 2 != 0) who = "blue";
        else who = "red";

        turn++;

        return who;
    }

    public List<Integer> convertCords(String cords){

        List<Integer> coordinates = new LinkedList<Integer>();

        int row, col;

        switch(cords.charAt(0)){
            case 'A':
            case 'a':
                row = 1;
                break;
            case 'B':
            case 'b':
                row = 2;
                break;
            case 'C':
            case 'c':
                row = 3;
                break;
            case 'D':
            case 'd':
                row = 4;
                break;
            case 'E':
            case 'e':
                row = 5;
                break;
            case 'F':
            case 'f':
                row = 6;
                break;
            case 'G':
            case 'g':
                row = 7;
                break;
            case 'H':
            case 'h':
                row = 8;
                break;
            default:
                System.out.println("Something goes wrong!");
                row = -1;
        }

        col = (int)cords.charAt(1) - 48;

        // row on index 0, col on index 1
        coordinates.add(row);
        coordinates.add(col);

        return coordinates;
    }
}
