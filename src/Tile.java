import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Tile extends JTextField {
    //Tile Dim
    private static final int WIDTH = 30;
    private static final int HEIGHT = 30;

    /**
     * Constructor
     * Sets the style and adds the limitations on the tile
     */
    public Tile() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
        this.setHorizontalAlignment(0);
        this.addKeyListener(new Tile.BoxListener());
    }

    /**
     * Listener for the Tile
     */
    class BoxListener implements KeyListener {
        /**
         * Limits to 1 char and only digits, so 0 -> 9
         * @param e Key
         */
        public void keyTyped(KeyEvent e) {
            if (!Character.isDigit(e.getKeyChar())) {
                e.consume();
            }

            if (Tile.this.getText().length() >= 1) {
                Tile.this.setText("");
            }

        }
        public void keyPressed(KeyEvent e) {}
        public void keyReleased(KeyEvent e) {}
    }
}
