import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuFrame extends JFrame {

    //Frame dimensions
    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_Height = 400;

    //Grid size
    private static final int GRID_WIDTH = 9;
    private static final int GRID_HEIGHT = 9;

    //Reference to all the tiles
    private Tile[][] tiles;

    //GUI components
    private JPanel formatPanel;
    private JPanel buttonPanel;
    private JPanel largeGrid;
    private JButton goButton;
    private JButton clearButton;
    private JCheckBox showAlgorithm;

    /**
     * Constructor
     */
    public SudokuFrame() {
        initTiles();
        addLargeGrid();
        initComponents();
        initPanels();
        initListeners();
        this.setSize(FRAME_WIDTH, FRAME_Height);
    }

    /**
     * Creates the tiles
     */
    private void initTiles() {
        tiles = new Tile[GRID_WIDTH][GRID_HEIGHT];

        for (int i = 0; i < GRID_WIDTH; ++i) {
            for (int j = 0; j < GRID_HEIGHT; ++j) {
                tiles[i][j] = new Tile();
            }
        }

    }

    /**
     * Sets the value at a specific tile
     * @param x   The row
     * @param y   The column
     * @param dig The value on the grid
     */
    public void setDigit(int x, int y, String dig) {
        this.tiles[x][y].setText(dig);
    }

    /**
     * Gets all the numbers on the grid
     * @return byte[][] An array of the text of each tile
     */
    public byte[][] getTileNumbers() {
        byte[][] board = new byte[GRID_WIDTH][GRID_HEIGHT];

        for (int i = 0; i < GRID_WIDTH; ++i) {
            for (int j = 0; j < GRID_HEIGHT; ++j) {
                try {
                    board[i][j] = Byte.parseByte(this.tiles[i][j].getText());
                } catch (NumberFormatException e) {
                    board[i][j] = 0;
                }
            }
        }

        return board;
    }

    /**
     * Adds the 3x3 tile grids to the 3x3 main grid
     */
    private void addLargeGrid() {
        //Borders
        MatteBorder gridBorder = BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK);

        largeGrid = new JPanel(new GridLayout(3, 3));
        largeGrid.setBorder(largeGridBorderEditor(new Color(0, 0, 0, Color.TRANSLUCENT)));

        for (int smallGridRow = 0; smallGridRow < 3; ++smallGridRow) {
            for (int smallGridColumn = 0; smallGridColumn < 3; ++smallGridColumn) {
                SmallGrid grid = new SmallGrid();
                grid.setBorder(gridBorder);

                for (int i = smallGridRow * 3; i < smallGridRow * 3 + 3; ++i) {
                    for (int j = smallGridColumn * 3; j < smallGridColumn * 3 + 3; ++j) {
                        grid.add(this.tiles[i][j]);
                    }
                }
                largeGrid.add(grid);
            }
        }

    }

    /**
     * @param select Border color
     * @return CompoundBorder The border combination
     */
    private CompoundBorder largeGridBorderEditor(Color select) {
        return new CompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createMatteBorder(2, 2, 2, 2, select));
    }

    /**
     * Creates all components for the frame
     */
    private void initComponents() {
        clearButton = new JButton("Clear");
        goButton = new JButton("Solve");
        showAlgorithm = new JCheckBox("Show Solving");
        buttonPanel = new JPanel();
        formatPanel = new JPanel(new BorderLayout());
    }

    /**
     * Adds components to panels
     */
    private void initPanels() {
        buttonPanel.add(goButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(showAlgorithm);
        formatPanel.add(largeGrid, "Center");
        formatPanel.add(buttonPanel, "South");
        this.add(formatPanel);
    }


    /**
     * Add listeners to components
     */
    private void initListeners() {
        clearButton.addActionListener(new ClearListener());
        goButton.addActionListener(new SolveListener());
    }


    /**
     * Listener for the clear button
     * -Clears the board
     */
    class ClearListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for (int row = 0; row < GRID_WIDTH; row++) {
                for (int column = 0; column < GRID_HEIGHT; column++) {
                    setDigit(row,column,"");
                }
            }
            largeGrid.setBorder(largeGridBorderEditor(new Color(0, 0, 0, Color.TRANSLUCENT)));
        }

    }



    /**
     * Listener for the solve button
     * -Starts the solving process and edits the board
     */
    class SolveListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            SudokuSolver solver = new SudokuSolver(SudokuFrame.this.getTileNumbers(), SudokuFrame.this, SudokuFrame.this.showAlgorithm.isSelected());
            //If the grid can be solved, the panel holding the grids will get a green border
            if (solver.solve()) {
                largeGrid.setBorder(largeGridBorderEditor(Color.GREEN));
            } else {
                largeGrid.setBorder(largeGridBorderEditor(Color.RED));
            }

            if (!SudokuFrame.this.showAlgorithm.isSelected()) {
                for (int i = 0; i < 9; ++i) {
                    for (int j = 0; j < 9; ++j) {
                        int text = solver.getTile((byte) i, (byte) j);
                        if (text != 0) {
                            SudokuFrame.this.tiles[i][j].setText("" + text);
                        } else {
                            SudokuFrame.this.tiles[i][j].setText("");
                        }
                    }
                }
            }
        }

    }
}

