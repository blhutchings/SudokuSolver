import javax.swing.*;
import java.awt.*;

public class SmallGrid extends JPanel {
    //Grid that will hold 3x3 Tiles
    public SmallGrid() {
        this.setLayout(new GridLayout(3, 3));
    }
}