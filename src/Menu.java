import java.util.Scanner;

import static java.lang.System.exit;

public class Menu {

    public void display_menu(){ // <- this method displays menu in console

        Scanner scan = new Scanner(System.in);

        while(true){

            char choose = '0';

            System.out.println("\n---------- MENU ----------");
            System.out.println("1. Classic checkers");
            System.out.println("2. First blood");
            System.out.println("3. 50 / 50");
            System.out.println("4. Credits");
            System.out.println("5. \"KONIEC\"");

            System.out.println("Choose game mode: ");
            choose = scan.next().charAt(0);

            switch(choose){
                case '1':
                    classic_checkers();
                    break;
                case '2':
                    first_blood();
                    break;
                case '3':
                    fifty_fifty();
                    break;
                case '4':
                    credits();
                    break;
                case '5':
                    System.out.println("Jeszcze tu wrócę!");
                    exit(0);
                default:
                    System.out.println("Something goes wrong!");
            }
        }
    }

    public void credits(){ //<- this method displays who created a game in console
        System.out.println("\nAuthors: ");
        System.out.println("Marcin \"The Real Slim Stefan\" Stefanowicz");
        System.out.println("Łukasz \"Chimi Aku Aku\" Terpiłowsky\n");
    }

    public void classic_checkers(){ // <- this method starts classic checkers game mode
        Board board = new Board();
        board.setup_board();

        while(board.get_number_of_blue_units() > 0 && board.get_number_of_red_units() > 0){
            board.display_board();
            board.move_piece();
        }

        board.display_board();
    }

    public void first_blood(){ // <- this method starts first blood game mode
        Board board = new Board();
        board.setup_board();

        boolean condition = false;

        while(!condition){
            board.display_board();
            board.move_piece();

            if(board.get_number_of_red_units() == 11){
                System.out.println("Blue player wins!");
                condition = true;
            } else if(board.get_number_of_blue_units() == 11){
                System.out.println("Red player wins!");
                condition = true;
            }
        }

        board.display_board();
    }

    public void fifty_fifty(){ // <- this method starts fifty fifty game mode
        Board board = new Board();
        board.setup_board();

        boolean condition = false;

        while(!condition){
            board.display_board();
            board.move_piece();

            if(board.get_number_of_red_units() == 6){
                System.out.println("Blue player wins!");
                condition = true;
            } else if(board.get_number_of_blue_units() == 6){
                System.out.println("Red player wins!");
                condition = true;
            }
        }

        board.display_board();
    }
}
