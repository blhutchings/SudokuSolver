import java.awt.*;

public class SudokuSolver {
    private byte[][] board;
    private SudokuFrame frame;
    private boolean showUpdates;

    public SudokuSolver(byte[][] board) {
        this.board = board;
    }

    public SudokuSolver(byte[][] board, SudokuFrame frame, boolean updates) {
        this.frame = frame;
        this.board = board;
        this.showUpdates = updates;
    }

    public boolean solve() {
        return this.checkBoard() ? this.solver() : false;
    }

    private boolean solver() {
        Point tile = this.findEmpty();
        if (tile.getY() == -1.0D) {
            return true;
        } else {
            for(byte i = 1; i <= 9; ++i) {
                this.board[(byte)((int)tile.getX())][(byte)((int)tile.getY())] = i;
                if (this.showUpdates) {
                    this.frame.setDigit((int)tile.getX(), (int)tile.getY(), i);
                }

                if (this.examine((byte)((int)tile.getX()), (byte)((int)tile.getY()))) {
                    if (this.solve()) {
                        return true;
                    }
                } else {
                    this.board[(byte)((int)tile.getX())][(byte)((int)tile.getY())] = 0;
                    if (this.showUpdates) {
                        this.frame.setDigit((int)tile.getX(), (int)tile.getY(), 0);
                    }
                }
            }

            this.board[(byte)((int)tile.getX())][(byte)((int)tile.getY())] = 0;
            return false;
        }
    }

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

    private boolean examine(byte row, byte column) {
        byte squareRow;
        for(squareRow = 0; squareRow < 9; ++squareRow) {
            if (squareRow != row && this.getTile(row, column) == this.getTile(squareRow, column)) {
                return false;
            }
        }

        for(squareRow = 0; squareRow < 9; ++squareRow) {
            if (squareRow != column && this.getTile(row, column) == this.getTile(row, squareRow)) {
                return false;
            }
        }

        squareRow = (byte)((byte)(row / 3) * 3);
        byte squareCol = (byte)((byte)(column / 3) * 3);

        for(byte i = squareRow; i < squareRow + 3; ++i) {
            for(byte j = squareCol; j < squareCol + 3; ++j) {
                if (i != row && j != column && this.getTile(row, column) == this.getTile(i, j)) {
                    return false;
                }
            }
        }

        return true;
    }

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
