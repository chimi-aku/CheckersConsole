import com.sun.source.tree.CompilationUnitTree;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.lang.String;
import java.util.regex.Pattern;

public class Board {
    int turn;
    String move_or_attack;
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
        move_or_attack = "move";
        turn = 1;
        board = new String[9][9];
        fields = new Field[9][9];
        this.red_units = 12;
        this.blue_units = 12;
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

            /*for(int i = 0; i < blue_pieces_list.size(); i++){
                System.out.println("Piece: row: " + blue_pieces_list.get(i).get_row() + ", col: " + blue_pieces_list.get(i).get_col() + ", color: " + blue_pieces_list.get(i).get_color());

                System.out.println("Piece: row: " + red_pieces_list.get(i).get_row() + ", col: " + red_pieces_list.get(i).get_col() + ", color: " + red_pieces_list.get(i).get_color());
            }*/
    }

    public List<Field> valid_moves(Piece piece){

        int row = piece.get_row();
        int col = piece.get_col();
        String color = piece.get_color();
        boolean is_king = piece.is_king();

        List<Field> valid_moves_list = new LinkedList<Field>();

        if(is_king == false){
            if(color.equals("blue")){

                if((row - 1 >= 1) && (col + 1 <= 8) && !(fields[row - 1][col + 1].get_is_taken()))
                    valid_moves_list.add(fields[row - 1][col + 1]);

                if((row - 1 >= 1) && (col - 1 >= 1) && !(fields[row - 1][col - 1].get_is_taken()))
                    valid_moves_list.add(fields[row - 1][col - 1]);
            }
            else {

                if((row + 1 <= 8) && (col + 1 <= 8) && !(fields[row + 1][col + 1].get_is_taken()))
                    valid_moves_list.add(fields[row + 1][col + 1]);

                if( (row + 1 <= 8) && (col - 1 >= 1) && !(fields[row + 1][col - 1].get_is_taken()) )
                    valid_moves_list.add(fields[row + 1][col - 1]);
            }
        }

        if(valid_moves_list.size() == 0){
            System.out.print("No valid moves");
        }
        else{
            for(int i = 0; i < valid_moves_list.size(); i++){
                System.out.println("Valid move in row: " + valid_moves_list.get(i).get_row() + " and col: " + valid_moves_list.get(i).get_col());
            }
        }

        return valid_moves_list;
    }

    public List<Field> valid_attacks(Piece piece) {

        int row = piece.get_row();
        int col = piece.get_col();
        String color = piece.get_color();
        boolean is_king = piece.is_king();

        List<Field> valid_attacks_list = new LinkedList<Field>();

        if (is_king == false) {
            if (color.equals("blue")) {

                if ((row - 1 >= 1) && (col + 1 <= 8) && (row - 2 >= 1) && (col + 2 <= 8) && !(fields[row - 2][col + 2].get_is_taken()) && fields[row - 1][col + 1].get_is_taken()) {
                    for (int i = 0; i < red_pieces_list.size(); i++) {
                        if (fields[row - 1][col + 1].get_row() == red_pieces_list.get(i).get_row() && fields[row - 1][col + 1].get_col() == red_pieces_list.get(i).get_col()) {
                            valid_attacks_list.add(fields[row - 2][col + 2]);
                        }
                    }
                }

                if ((row - 1 >= 1) && (col - 1 >= 1) && (row - 2 >= 1) && (col - 2 >= 1) && !(fields[row - 2][col - 2].get_is_taken()) && fields[row - 1][col - 1].get_is_taken()) {
                    for (int i = 0; i < red_pieces_list.size(); i++) {
                        if (fields[row - 1][col + 1].get_row() == red_pieces_list.get(i).get_row() && fields[row - 1][col + 1].get_col() == red_pieces_list.get(i).get_col()) {
                            valid_attacks_list.add(fields[row - 2][col - 2]);
                        }
                    }
                }

            } else {

                if ((row + 1 <= 8) && (col + 1 <= 8) && (row + 2 <= 8) && (col + 2 <= 8) && !(fields[row + 2][col + 2].get_is_taken()) && fields[row + 1][col + 1].get_is_taken()) {
                    for (int i = 0; i < blue_pieces_list.size(); i++) {
                        if (fields[row + 1][col + 1].get_row() == blue_pieces_list.get(i).get_row() && fields[row + 1][col + 1].get_col() == blue_pieces_list.get(i).get_col()) {
                            valid_attacks_list.add(fields[row + 2][col + 2]);
                        }
                    }
                }

                if ((row + 1 <= 8) && (col - 1 >= 1) && (row + 2 <= 8) && (col - 2 >= 1) && !(fields[row + 2][col - 2].get_is_taken()) && fields[row + 1][col - 1].get_is_taken()) {
                    for (int i = 0; i < blue_pieces_list.size(); i++) {
                        if (fields[row + 1][col - 1].get_row() == blue_pieces_list.get(i).get_row() && fields[row + 1][col - 1].get_col() == blue_pieces_list.get(i).get_col()) {
                            valid_attacks_list.add(fields[row + 2][col - 2]);
                        }
                    }
                }
            }

            if (valid_attacks_list.size() == 0) System.out.println("No valid attacks!");
            else {

                for (int i = 0; i < valid_attacks_list.size(); i++) {
                    System.out.println("Valid attack in row: " + valid_attacks_list.get(i).get_row() + " and col: " + valid_attacks_list.get(i).get_col());
                }
            }
        }

        return valid_attacks_list;
    }

    public void change_score(){

    }

    public void move_piece(){
        Piece piece = select_piece();
        List<Field> valid_moves = valid_moves(piece);
        List<Field> valid_attacks = valid_attacks(piece);
        Field choosed = select_field(valid_moves, valid_attacks);

        if(move_or_attack.equals("move")) execute_move(piece, choosed);
        else if(move_or_attack.equals("attack")) execute_attack(piece, choosed);
    }

    public void execute_move(Piece piece, Field choose){
        fields[piece.get_row()][piece.get_col()].set_is_taken(false);
        piece.set_row(choose.get_row());
        piece.set_col(choose.get_col());
        choose.set_is_taken(true);
    }

    public void execute_attack(Piece piece, Field choose){
        //fields[choose.get_row()][piece.get_col()].set_is_taken(false);
        fields[piece.get_row()][piece.get_col()].set_is_taken(false);
        piece.set_row(choose.get_row());
        piece.set_col(choose.get_col());
        choose.set_is_taken(true);
    }

    public Field select_field(List<Field> valid_moves, List<Field> valid_attacks){
        Scanner scan = new Scanner(System.in);
        String cords = " ";
        boolean valid = true;

        while(valid){
            System.out.println("Choose the field where you want to move: (for example: A4, a4)");
            List<Integer> position_of_field = new LinkedList<Integer>();
            cords = scan.nextLine();

            Pattern pattern = Pattern.compile("([A-H]|[a-h])[1-8]");

            position_of_field = convertCords(cords);

            if(pattern.matcher(cords).matches()){
                for(int i = 0; i < valid_moves.size(); i++){
                    if(valid_moves.get(i).get_row() == position_of_field.get(0) && valid_moves.get(i).get_col() == position_of_field.get(1)){
                        move_or_attack("move");
                        return valid_moves.get(i);
                    }
                }

                for(int i = 0; i < valid_attacks.size(); i++){
                    if(valid_attacks.get(i).get_row() == position_of_field.get(0) && valid_attacks.get(i).get_col() == position_of_field.get(1)){
                        move_or_attack("attack");
                        return valid_attacks.get(i);
                    } else {
                        System.out.println("This move can't be execute, choose another field!\n");
                    }
                }
            }
        }
        return new Field(-1, -1, false);
    }

    public Piece select_piece() {

        String who = whose_move();
        List<Integer> position_of_piece = new LinkedList<Integer>();
        Scanner scan = new Scanner(System.in);
        String cords = " ";
        boolean valid = true;

        while(valid){

            System.out.println("Type cords of piece which you want to move (for example: A4, a4)");
            cords = scan.nextLine();

            Pattern pattern = Pattern.compile("([A-H]|[a-h])[1-8]");

            position_of_piece = convertCords(cords);

            if(pattern.matcher(cords).matches()){

                if(!(fields[position_of_piece.get(0)][position_of_piece.get(1)].get_is_taken())){
                    System.out.println("There is no piece on this field!");
                }

                if(who.equals("blue")) {

                    for (int i = 0; i < red_pieces_list.size(); i++) {
                        if (red_pieces_list.get(i).get_row() == position_of_piece.get(0) && red_pieces_list.get(i).get_col() == position_of_piece.get(1)){
                            System.out.println("You have choosen piece of your opponent!");
                            break;
                        }
                    }

                    for (int i = 0; i < blue_pieces_list.size(); i++) {
                        if (blue_pieces_list.get(i).get_row() == position_of_piece.get(0) && blue_pieces_list.get(i).get_col() == position_of_piece.get(1)){
                            valid = false;
                            break;
                        }
                    }

                } else if(who.equals("red")){

                    for (int i = 0; i < blue_pieces_list.size(); i++) {
                        if (blue_pieces_list.get(i).get_row() == position_of_piece.get(0) && blue_pieces_list.get(i).get_col() == position_of_piece.get(1)){
                            System.out.println("You have choosen piece of your opponent!");
                            break;
                        }
                    }

                    for (int i = 0; i < red_pieces_list.size(); i++) {
                        if (red_pieces_list.get(i).get_row() == position_of_piece.get(0) && red_pieces_list.get(i).get_col() == position_of_piece.get(1)){
                            valid = false;
                            break;
                        }
                    }
                }
            }
        }

        System.out.println("Your cords are: " + cords.charAt(0) + ", " + cords.charAt(1));

        System.out.println("Your cords are: " + position_of_piece.get(0) + ", " + position_of_piece.get(1));

        if(who.equals("blue")){
            for(int i = 0; i < blue_pieces_list.size(); i++){
                if(blue_pieces_list.get(i).get_row() == position_of_piece.get(0) && blue_pieces_list.get(i).get_col() == position_of_piece.get(1)){
                    System.out.println("Piece: row: " + blue_pieces_list.get(i).get_row() + ", col: " + blue_pieces_list.get(i).get_col() + ", color: " + blue_pieces_list.get(i).get_color());
                    return blue_pieces_list.get(i);
                }
            }
        } else if(who.equals("red")){
            for(int i = 0; i < red_pieces_list.size(); i++){
                if(red_pieces_list.get(i).get_row() == position_of_piece.get(0) && red_pieces_list.get(i).get_col() == position_of_piece.get(1)){
                    System.out.println("Piece: row: " + red_pieces_list.get(i).get_row() + ", col: " + red_pieces_list.get(i).get_col() + ", color: " + red_pieces_list.get(i).get_color());
                    return red_pieces_list.get(i);
                }
            }
        }

        return new Piece("transparent", -1, -1);
    }

    public String whose_move(){
        String who;

        if(turn % 2 != 0) who = "blue";
        else who = "red";

        System.out.println("\nNow is " + who + " turn!\n");

        turn++;

        return who;
    }

    public void move_or_attack(String choose){

        if(choose.equals("move")) move_or_attack = "move";
        else move_or_attack = "attack";
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

    public int get_number_of_blue_units() {
        return blue_units;
    }

    public int get_number_of_red_units() {
        return red_units;
    }
}
