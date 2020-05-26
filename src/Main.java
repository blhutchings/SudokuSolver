public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        SudokuFrame game = new SudokuFrame();
        game.setDefaultCloseOperation(3);
        game.setTitle("Sudoku Solver");
        game.setResizable(false);
        game.setVisible(true);
    }
}