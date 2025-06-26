package main;

import javax.swing.JPanel;
import java.awt.*;

public class Screen extends JPanel {

    // Custom Screen Settings
    final int GAME_TILE = 16; // 16X16 tile
    final int SCALE = 3; // 16X3 = 48
    final int TRUE_TILE_SIZE = GAME_TILE * SCALE; // 48X48 tile
    final int COLUMN = 16;
    final int ROW = 12; // col:row = 16:12 or 4:3
    final int SCREEN_WIDTH = TRUE_TILE_SIZE * COLUMN; // 16x48 = 768 pixels
    final int SCREEN_HEIGHT = TRUE_TILE_SIZE * ROW; // 12x48 = 576 pixels

    // Class constructor
    public Screen() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
    }
}
