public class Main {
    public static void main(String[] args) {

        Board board = new Board();
        board.setup_board();

        while(board.get_number_of_blue_units() > 0 && board.get_number_of_red_units() > 0){
            board.display_board();
            board.move_piece();
        }
    }
}
