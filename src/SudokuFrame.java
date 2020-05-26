import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuFrame extends JFrame {
    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_Height = 400;
    private static final int GRID_WIDTH = 9;
    private static final int GRID_HEIGHT = 9;
    private Tile[][] tiles;
    private JPanel panel;
    private JPanel buttonPanel;
    private JPanel largeGrid;
    private JButton goButton;
    private JCheckBox showAlgorithm;

    public SudokuFrame() {
        this.initTiles();
        this.addLargeGrid();
        this.initComponents();
        this.setSize(400, 400);
    }

    private void initTiles() {
        this.tiles = new Tile[9][9];

        for(int i = 0; i < 9; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.tiles[i][j] = new Tile();
            }
        }

    }

    public void setDigit(int x, int y, int dig) {
        this.tiles[x][y].setText("" + dig);
    }

    public byte[][] getBoard() {
        byte[][] board = new byte[9][9];

        for(int i = 0; i < 9; ++i) {
            for(int j = 0; j < 9; ++j) {
                try {
                    board[i][j] = Byte.parseByte(this.tiles[i][j].getText());
                } catch (NumberFormatException var5) {
                    board[i][j] = 0;
                }
            }
        }

        return board;
    }

    private void addLargeGrid() {
        this.largeGrid = new JPanel(new GridLayout(3, 3));
        this.largeGrid.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for(int smallGridRow = 0; smallGridRow < 3; ++smallGridRow) {
            for(int smallGridColumn = 0; smallGridColumn < 3; ++smallGridColumn) {
                SmallGrid grid = new SmallGrid();
                grid.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));

                for(int i = smallGridRow * 3; i < smallGridRow * 3 + 3; ++i) {
                    for(int j = smallGridColumn * 3; j < smallGridColumn * 3 + 3; ++j) {
                        grid.add(this.tiles[i][j]);
                    }
                }

                this.largeGrid.add(grid);
            }
        }

    }

    private void initComponents() {
        this.goButton = new JButton("Solve");
        this.goButton.addActionListener(new SudokuFrame.SolveListener(this));
        this.showAlgorithm = new JCheckBox("Show Solving");
        this.panel = new JPanel(new BorderLayout());
        this.buttonPanel = new JPanel();
        this.buttonPanel.add(this.goButton);
        this.buttonPanel.add(this.showAlgorithm);
        this.panel.add(this.largeGrid, "Center");
        this.panel.add(this.buttonPanel, "South");
        this.add(this.panel);
    }

    class SolveListener implements ActionListener {
        SolveListener(SudokuFrame sudokuFrame) {
        }

        public void actionPerformed(ActionEvent e) {
            SudokuSolver solver = new SudokuSolver(SudokuFrame.this.getBoard(), SudokuFrame.this, SudokuFrame.this.showAlgorithm.isSelected());
            if (solver.solve()) {
                SudokuFrame.this.setBackground(Color.GREEN);
            }

            if (!SudokuFrame.this.showAlgorithm.isSelected()) {
                for(int i = 0; i < 9; ++i) {
                    for(int j = 0; j < 9; ++j) {
                        SudokuFrame.this.tiles[i][j].setText("" + solver.getTile((byte)i, (byte)j));
                    }
                }
            }

        }
    }
}
