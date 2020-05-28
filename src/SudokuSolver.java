import java.awt.*;

public class SudokuSolver {
    private final byte[][] board;
    private SudokuFrame frame;
    private final boolean showUpdates;

    /**
     * Constructor
     * @param board The input sudoku
     */
    public SudokuSolver(byte[][] board) {
        this.board = board;
        showUpdates = false;
    }

    /**
     * Constructor
     * @param board The input sudoku
     * @param frame The frame which will be used
     * @param updates Whether or not it will update the GUI as it solves
     */
    public SudokuSolver(byte[][] board, SudokuFrame frame, boolean updates) {
        this.frame = frame;
        this.board = board;
        this.showUpdates = updates;
    }

    /**
     * Whether the board can be solved
     * @return boolean
     */
    public boolean solve() {
        return this.checkBoard() && this.solver();
    }

    /**
     * Solve helper method
     * @return boolean Whether the board can be solved
     */
    private boolean solver() {
        Point tile = this.findEmpty();
        if (tile.getY() == -1.0D) {
            return true;
        } else {
            for(byte i = 1; i <= 9; ++i) {
                this.board[(byte)((int)tile.getX())][(byte)((int)tile.getY())] = i;

                //If showing the backtracking is enabled
                if (this.showUpdates) {
                    this.frame.setDigit((int)tile.getX(), (int)tile.getY(), ""+i);
                }

                if (this.examine((byte)((int)tile.getX()), (byte)((int)tile.getY()))) {
                    if (this.solve()) {
                        return true;
                    }
                } else {
                    this.board[(byte)((int)tile.getX())][(byte)((int)tile.getY())] = 0;

                    //If showing the backtracking is enabled
                    if (this.showUpdates) {
                        this.frame.setDigit((int)tile.getX(), (int)tile.getY(), "");
                    }
                }
            }

            this.board[(byte)((int)tile.getX())][(byte)((int)tile.getY())] = 0;
            return false;
        }
    }

    /**
     * Attempts to find an empty tile
     * @return Point If -1, no point is found. Anything else, is the coordinates
     */
    private Point findEmpty() {
        for(int row = 0; row < 9; ++row) {
            for(int column = 0; column < 9; ++column) {
                if (this.board[row][column] == 0) {
                    return new Point(row, column);
                }
            }
        }

        return new Point(-1, -1);
    }

    /**
     *
     * @param row The row at which will be examined
     * @param column The column at which will be examined
     * @return boolean If the current number is valid at that location
     */
    private boolean examine(byte row, byte column) {
        byte squareRow;
        byte squareCol;
        //Checks the row
        for(squareRow = 0; squareRow < 9; ++squareRow) {
            if (squareRow != row && this.getTile(row, column) == this.getTile(squareRow, column)) {
                return false;
            }
        }
        //Checks the col
        for(squareCol = 0; squareCol < 9; ++squareCol) {
            if (squareCol != column && this.getTile(row, column) == this.getTile(row, squareCol)) {
                return false;
            }
        }

        squareRow = (byte)((byte)(row / 3) * 3);
        squareCol = (byte)((byte)(column / 3) * 3);

        //Checks the 3x3 quadrant
        for(byte i = squareRow; i < squareRow + 3; ++i) {
            for(byte j = squareCol; j < squareCol + 3; ++j) {
                if (i != row && j != column && this.getTile(row, column) == this.getTile(i, j)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Checks to see if its even a valid board before completing
     * @return boolean If its a valid board
     */
    private boolean checkBoard() {
        for(int i = 0; i < 9; ++i) {
            for(int j = 0; j < 9; ++j) {
                if (this.board[i][j] != 0 && !this.examine((byte)i, (byte)j)) {
                    return false;
                }
            }
        }

        return true;
    }
    public byte getTile(byte row, byte column) {
        return this.board[row][column];
    }

    /**
     * Prints board into console
     */
    public void print() {
        for(int i = 0; i < 9; ++i) {
            System.out.println();

            for(int j = 0; j < 9; ++j) {
                System.out.print(this.board[i][j] + " ");
            }
        }

        System.out.println();
    }
}
