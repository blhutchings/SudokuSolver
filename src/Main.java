import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SudokuFrame game = new SudokuFrame();
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setTitle("Sudoku Solver");
        game.setResizable(false);
        game.setVisible(true);
    }
}