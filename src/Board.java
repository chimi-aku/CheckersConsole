import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.lang.String;
import java.util.regex.Pattern;

public class Board {
    String who;
    int turn;
    String move_or_attack;
    List<Integer> coordinates_to_remove = new LinkedList<Integer>();
    private int red_units;
    private int blue_units;
    String[][] board;
    private List<Piece> pieces_list = new LinkedList<Piece>();
    private List<Piece> red_pieces_list = new LinkedList<Piece>();
    private List<Piece> blue_pieces_list = new LinkedList<Piece>();
    private Field[][] fields;

    //How to use colors example
    //System.out.println(ConsoleColors.BLUE + "RED COLORED" + ConsoleColors.RED + " NORMAL");

    public List<Piece> get_red_pieces_list(){ return red_pieces_list; }
    public List<Piece> get_blue_pieces_list(){ return red_pieces_list; }

    Board() { // <- this method is a constructor of Board class
        who = "blue";
        move_or_attack = "move";
        turn = 1;
        board = new String[9][9];
        fields = new Field[9][9];
        this.red_units = 12;
        this.blue_units = 12;
    }

    public void display_board() { // <- this method displays board in console

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

        System.out.println("\nBlue units left: " + blue_units);
        System.out.println("Red units left: " + red_units + "\n");

        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {

                for (int i = 0; i < blue_pieces_list.size(); i++) {
                    if (blue_pieces_list.get(i).get_row() == row && blue_pieces_list.get(i).get_col() == col) {
                        if(blue_pieces_list.get(i).is_king() == true) board[row][col] = (ConsoleColors.BLUE + "Q " + ConsoleColors.RESET);
                        else board[row][col] = (ConsoleColors.BLUE + "o " + ConsoleColors.RESET);
                    }
                }

                for (int i = 0; i < red_pieces_list.size(); i++) {
                    if (red_pieces_list.get(i).get_row() == row && red_pieces_list.get(i).get_col() == col) {
                        if(red_pieces_list.get(i).is_king() == true) board[row][col] = (ConsoleColors.RED + "Q " + ConsoleColors.RESET);
                        else board[row][col] = (ConsoleColors.RED + "o " + ConsoleColors.RESET);
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

    public void setup_board() { // <- this method prepare board to start a game

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

    public void move_piece(){ // <- this method is responsible for moving pieces
        Piece piece;
        List<Field> valid_moves;
        List<Field> valid_attacks;

        do {

            piece = select_piece();
            valid_moves = valid_moves(piece);
            valid_attacks = valid_attacks(piece);

        } while(valid_moves.size() == 0 && valid_attacks.size() == 0);

        Field choosed = select_field(valid_moves, valid_attacks);

        if(move_or_attack.equals("move")) execute_move(piece, choosed);
        else if(move_or_attack.equals("attack")) execute_attack(piece, choosed);

        transform_to_king(piece);
        change_turn();
    }

    public List<Field> valid_moves(Piece piece){ // <- this method checks if piece which is given in arguments has possible moves

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
        } else if(is_king == true) {

            if((row - 1 >= 1) && (col + 1 <= 8) && !(fields[row - 1][col + 1].get_is_taken()))
                valid_moves_list.add(fields[row - 1][col + 1]);

            if((row - 1 >= 1) && (col - 1 >= 1) && !(fields[row - 1][col - 1].get_is_taken()))
                valid_moves_list.add(fields[row - 1][col - 1]);

            if((row + 1 <= 8) && (col + 1 <= 8) && !(fields[row + 1][col + 1].get_is_taken()))
                valid_moves_list.add(fields[row + 1][col + 1]);

            if((row + 1 <= 8) && (col - 1 >= 1) && !(fields[row + 1][col - 1].get_is_taken()))
                valid_moves_list.add(fields[row + 1][col - 1]);

        }

        /*if(valid_moves_list.size() == 0){
            System.out.println("No valid moves!");
        }
        else{
            for(int i = 0; i < valid_moves_list.size(); i++){
                //System.out.println("Valid move in row: " + valid_moves_list.get(i).get_row() + " and col: " + valid_moves_list.get(i).get_col());
            }
        }*/

        return valid_moves_list;
    }

    public List<Field> valid_attacks(Piece piece) { // <- this method checks if piece which is given in arguments has possible attacks

        coordinates_to_remove.clear();

        int row = piece.get_row();
        int col = piece.get_col();
        String color = piece.get_color();
        boolean is_king = piece.is_king();

        List<Field> valid_attacks_list = new LinkedList<Field>();

        if (is_king == false) {
            if (color.equals("blue")) {

                if ((row - 1 >= 1) && (row - 2 >= 1) && (col + 1 <= 8) && (col + 2 <= 8) && !(fields[row - 2][col + 2].get_is_taken()) && fields[row - 1][col + 1].get_is_taken()) {
                    for (int i = 0; i < red_pieces_list.size(); i++) {
                        if (fields[row - 1][col + 1].get_row() == red_pieces_list.get(i).get_row() && fields[row - 1][col + 1].get_col() == red_pieces_list.get(i).get_col()) {
                            valid_attacks_list.add(fields[row - 2][col + 2]);
                            coordinates_to_remove.add(row - 1);
                            coordinates_to_remove.add(col + 1);
                        }
                    }
                }

                if ((row - 1 >= 1) && (row - 2 >= 1) && (col - 1 >= 1) && (col - 2 >= 1) && !(fields[row - 2][col - 2].get_is_taken()) && fields[row - 1][col - 1].get_is_taken()) {
                    for (int i = 0; i < red_pieces_list.size(); i++) {
                        if (fields[row - 1][col - 1].get_row() == red_pieces_list.get(i).get_row() && fields[row - 1][col - 1].get_col() == red_pieces_list.get(i).get_col()) {
                            valid_attacks_list.add(fields[row - 2][col - 2]);
                            coordinates_to_remove.add(row - 1);
                            coordinates_to_remove.add(col - 1);
                        }
                    }
                }

            } else {

                if ((row + 1 <= 8) && (row + 2 <= 8) && (col + 1 <= 8) && (col + 2 <= 8) && !(fields[row + 2][col + 2].get_is_taken()) && fields[row + 1][col + 1].get_is_taken()) {
                    for (int i = 0; i < blue_pieces_list.size(); i++) {
                        if (fields[row + 1][col + 1].get_row() == blue_pieces_list.get(i).get_row() && fields[row + 1][col + 1].get_col() == blue_pieces_list.get(i).get_col()) {
                            valid_attacks_list.add(fields[row + 2][col + 2]);
                            coordinates_to_remove.add(row + 1);
                            coordinates_to_remove.add(col + 1);
                        }
                    }
                }

                if ((row + 1 <= 8) && (row + 2 <= 8) && (col - 1 >= 1) && (col - 2 >= 1) && !(fields[row + 2][col - 2].get_is_taken()) && fields[row + 1][col - 1].get_is_taken()) {
                    for (int i = 0; i < blue_pieces_list.size(); i++) {
                        if (fields[row + 1][col - 1].get_row() == blue_pieces_list.get(i).get_row() && fields[row + 1][col - 1].get_col() == blue_pieces_list.get(i).get_col()) {
                            valid_attacks_list.add(fields[row + 2][col - 2]);
                            coordinates_to_remove.add(row + 1);
                            coordinates_to_remove.add(col - 1);
                        }
                    }
                }
            }
        } else if(is_king == true) {

            if ((row - 1 >= 1) && (col + 1 <= 8) && (row - 2 >= 1) && (col + 2 <= 8) && !(fields[row - 2][col + 2].get_is_taken()) && fields[row - 1][col + 1].get_is_taken()) {
                for (int i = 0; i < red_pieces_list.size(); i++) {
                    if (fields[row - 1][col + 1].get_row() == red_pieces_list.get(i).get_row() && fields[row - 1][col + 1].get_col() == red_pieces_list.get(i).get_col()) {
                        valid_attacks_list.add(fields[row - 2][col + 2]);
                        coordinates_to_remove.add(row - 1);
                        coordinates_to_remove.add(col + 1);
                    }
                }

                for (int i = 0; i < blue_pieces_list.size(); i++) {
                    if (fields[row - 1][col + 1].get_row() == blue_pieces_list.get(i).get_row() && fields[row - 1][col + 1].get_col() == blue_pieces_list.get(i).get_col()) {
                        valid_attacks_list.add(fields[row - 2][col + 2]);
                        coordinates_to_remove.add(row - 1);
                        coordinates_to_remove.add(col + 1);
                    }
                }
            }

            if ((row - 1 >= 1) && (col - 1 >= 1) && (row - 2 >= 1) && (col - 2 >= 1) && !(fields[row - 2][col - 2].get_is_taken()) && fields[row - 1][col - 1].get_is_taken()) {
                for (int i = 0; i < red_pieces_list.size(); i++) {
                    if (fields[row - 1][col - 1].get_row() == red_pieces_list.get(i).get_row() && fields[row - 1][col - 1].get_col() == red_pieces_list.get(i).get_col()) {
                        valid_attacks_list.add(fields[row - 2][col - 2]);
                        coordinates_to_remove.add(row - 1);
                        coordinates_to_remove.add(col - 1);
                    }
                }

                for (int i = 0; i < blue_pieces_list.size(); i++) {
                    if (fields[row - 1][col - 1].get_row() == blue_pieces_list.get(i).get_row() && fields[row - 1][col - 1].get_col() == blue_pieces_list.get(i).get_col()) {
                        valid_attacks_list.add(fields[row - 2][col - 2]);
                        coordinates_to_remove.add(row - 1);
                        coordinates_to_remove.add(col - 1);
                    }
                }
            }

            if ((row + 1 <= 8) && (col + 1 <= 8) && (row + 2 <= 8) && (col + 2 <= 8) && !(fields[row + 2][col + 2].get_is_taken()) && fields[row + 1][col + 1].get_is_taken()) {
                for (int i = 0; i < red_pieces_list.size(); i++) {
                    if (fields[row + 1][col + 1].get_row() == red_pieces_list.get(i).get_row() && fields[row + 1][col + 1].get_col() == red_pieces_list.get(i).get_col()) {
                        valid_attacks_list.add(fields[row + 2][col + 2]);
                        coordinates_to_remove.add(row + 1);
                        coordinates_to_remove.add(col + 1);
                    }
                }

                for (int i = 0; i < blue_pieces_list.size(); i++) {
                    if (fields[row + 1][col + 1].get_row() == blue_pieces_list.get(i).get_row() && fields[row + 1][col + 1].get_col() == blue_pieces_list.get(i).get_col()) {
                        valid_attacks_list.add(fields[row + 2][col + 2]);
                        coordinates_to_remove.add(row + 1);
                        coordinates_to_remove.add(col + 1);
                    }
                }
            }

            if ((row + 1 <= 8) && (col - 1 >= 1) && (row + 2 <= 8) && (col - 2 >= 1) && !(fields[row + 2][col - 2].get_is_taken()) && fields[row + 1][col - 1].get_is_taken()) {
                for (int i = 0; i < blue_pieces_list.size(); i++) {
                    if (fields[row + 1][col - 1].get_row() == blue_pieces_list.get(i).get_row() && fields[row + 1][col - 1].get_col() == blue_pieces_list.get(i).get_col()) {
                        valid_attacks_list.add(fields[row + 2][col - 2]);
                        coordinates_to_remove.add(row + 1);
                        coordinates_to_remove.add(col - 1);
                    }
                }

                for (int i = 0; i < red_pieces_list.size(); i++) {
                    if (fields[row + 1][col - 1].get_row() == red_pieces_list.get(i).get_row() && fields[row + 1][col - 1].get_col() == red_pieces_list.get(i).get_col()) {
                        valid_attacks_list.add(fields[row + 2][col - 2]);
                        coordinates_to_remove.add(row + 1);
                        coordinates_to_remove.add(col - 1);
                    }
                }
            }
        }

        return valid_attacks_list;
    }

    public void execute_move(Piece piece, Field choose){ // <- this method executes move if it is possible
        fields[piece.get_row()][piece.get_col()].set_is_taken(false);
        piece.set_row(choose.get_row());
        piece.set_col(choose.get_col());
        choose.set_is_taken(true);
    }

    public void execute_attack(Piece piece, Field choose){ // <- this method executes attack if it is possible
        fields[coordinates_to_remove.get(0)][coordinates_to_remove.get(1)].set_is_taken(false);

        if(who == "blue") {
            for(int i = 0; i < red_pieces_list.size(); i++){
                if(red_pieces_list.get(i).get_row() == coordinates_to_remove.get(0) && red_pieces_list.get(i).get_col() == coordinates_to_remove.get(1)){
                    red_pieces_list.remove(i);
                    red_units--;
                }
            }
        } else if(who == "red"){
            for(int i = 0; i < blue_pieces_list.size(); i++){
                if(blue_pieces_list.get(i).get_row() == coordinates_to_remove.get(0) && blue_pieces_list.get(i).get_col() == coordinates_to_remove.get(1)){
                    blue_pieces_list.remove(i);
                    blue_units--;
                }
            }
        }

        fields[piece.get_row()][piece.get_col()].set_is_taken(false);
        piece.set_row(choose.get_row());
        piece.set_col(choose.get_col());
        choose.set_is_taken(true);
    }

    public Field select_field(List<Field> valid_moves, List<Field> valid_attacks){ // <- this method is responsible for selecting fields
        Scanner scan = new Scanner(System.in);
        String cords = " ";
        boolean valid = true;

        while(valid){
            System.out.println("Choose correct field where you want to move: (for example: A4, a4)");
            List<Integer> position_of_field = new LinkedList<Integer>();
            cords = scan.nextLine();

            Pattern pattern = Pattern.compile("([A-H]|[a-h])[1-8]");

            position_of_field = convertCords(cords);

            if(pattern.matcher(cords).matches()){

                for(int i = 0; i < valid_attacks.size(); i++){
                    if(valid_attacks.get(i).get_row() == position_of_field.get(0) && valid_attacks.get(i).get_col() == position_of_field.get(1)){
                        move_or_attack("attack");
                        return valid_attacks.get(i);
                    }
                }

                for(int i = 0; i < valid_moves.size(); i++){
                    if(valid_moves.get(i).get_row() == position_of_field.get(0) && valid_moves.get(i).get_col() == position_of_field.get(1)){
                        move_or_attack("move");
                        return valid_moves.get(i);
                    } else {
                        //System.out.println(ConsoleColors.RED + "This move can't be execute, choose another field!\n" + ConsoleColors.RESET);
                    }
                }
            }
        }
        return new Field(-1, -1, false);
    }

    public Piece select_piece() { // <- this method is responsible for selecting pieces
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

                if(who.equals("blue")){

                    for (int i = 0; i < red_pieces_list.size(); i++) {
                        if (red_pieces_list.get(i).get_row() == position_of_piece.get(0) && red_pieces_list.get(i).get_col() == position_of_piece.get(1)){
                            System.out.println(ConsoleColors.RED + "You have choosen piece of your opponent!" + ConsoleColors.RESET);
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
                            System.out.println(ConsoleColors.RED + "You have choosen piece of your opponent!" + ConsoleColors.RESET);
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

        //System.out.println("Your cords are: " + cords.charAt(0) + ", " + cords.charAt(1));

        //System.out.println("Your cords are: " + position_of_piece.get(0) + ", " + position_of_piece.get(1));

        if(who.equals("blue")){
            for(int i = 0; i < blue_pieces_list.size(); i++){
                if(blue_pieces_list.get(i).get_row() == position_of_piece.get(0) && blue_pieces_list.get(i).get_col() == position_of_piece.get(1)){
                    //System.out.println("Piece: row: " + blue_pieces_list.get(i).get_row() + ", col: " + blue_pieces_list.get(i).get_col() + ", color: " + blue_pieces_list.get(i).get_color());
                    return blue_pieces_list.get(i);
                }
            }
        } else if(who.equals("red")){
            for(int i = 0; i < red_pieces_list.size(); i++){
                if(red_pieces_list.get(i).get_row() == position_of_piece.get(0) && red_pieces_list.get(i).get_col() == position_of_piece.get(1)){
                    //System.out.println("Piece: row: " + red_pieces_list.get(i).get_row() + ", col: " + red_pieces_list.get(i).get_col() + ", color: " + red_pieces_list.get(i).get_color());
                    return red_pieces_list.get(i);
                }
            }
        }

        return new Piece("transparent", -1, -1);
    }

    public String whose_move(){ // <- this method is responsible for returning whose move is now

        if(turn % 2 != 0) who = "blue";
        else who = "red";

        System.out.println("\nNow is " + who + " turn!\n");

        return who;
    }

    public void change_turn(){ turn++; } // <- this method is responsible for counting turns

    public void move_or_attack(String choose){ // <- this method decides what to do, move or attack

        if(choose.equals("move")) move_or_attack = "move";
        else move_or_attack = "attack";
    }

    public List<Integer> convertCords(String cords){ // <- this method converts cords on numbers

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
                System.out.println(ConsoleColors.RED + "Something goes wrong!" + ConsoleColors.RESET);
                row = -1;
        }

        col = (int)cords.charAt(1) - 48;

        // row on index 0, col on index 1
        coordinates.add(row);
        coordinates.add(col);

        return coordinates;
    }

    public void transform_to_king(Piece piece){ // <- this method is transforming normal piece to king piece which has diffrent possible moves
        if(piece.get_color() == "blue" && piece.get_row() == 1) piece.set_is_king(true);
        else if(piece.get_color() == "red" && piece.get_row() == 8) piece.set_is_king(true);
    }

    public int get_number_of_blue_units() { // <- this method is a getter of blue units value
        return blue_units;
    }

    public int get_number_of_red_units() {
        return red_units;
    } // <- this method is a getter of red units value
}
