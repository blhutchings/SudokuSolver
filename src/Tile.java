import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Tile extends JTextField {
    private static final int WIDTH = 30;
    private static final int HEIGHT = 30;

    public Tile() {
        this.setPreferredSize(new Dimension(30, 30));
        this.setFont(new Font("Comic Sans MS", 0, 24));
        this.setHorizontalAlignment(0);
        this.addKeyListener(new Tile.BoxListener(this));
    }

    class BoxListener implements KeyListener {
        BoxListener(Tile tile) {
        }

        public void keyTyped(KeyEvent e) {
            if (!Character.isDigit(e.getKeyChar())) {
                e.consume();
            }

            if (Tile.this.getText().length() >= 1) {
                Tile.this.setText("");
            }

        }

        public void keyPressed(KeyEvent e) {
        }

        public void keyReleased(KeyEvent e) {
        }
    }
}
