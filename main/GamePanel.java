package main;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // Custom Screen Settings
    final int GAME_TILE = 16; // 16X16 tile
    final int SCALE = 3; // 16X3 = 48
    final int TRUE_TILE_SIZE = GAME_TILE * SCALE; // 48X48 tile

    final int MAX_COL = 16;
    final int MAX_ROW = 12; // col:row = 16:12 or 4:3

    final int SCREEN_WIDTH = TRUE_TILE_SIZE * MAX_COL; // 16x48 = 768 pixels
    final int SCREEN_HEIGHT = TRUE_TILE_SIZE * MAX_ROW; // 12x48 = 576 pixels

    Thread gameLoop;

    // Class constructor
    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
    }

    public void startGameLoop() {
        gameLoop = new Thread(this);
        gameLoop.start(); // calls the run() method below
    }

    @Override
    public void run() {
        while (gameLoop != null) {
            //System.out.println("Game loop is running...");
            update();
            repaint();
        }
    }

    public void update() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.white);
        g2.fillRect(100, 100, TRUE_TILE_SIZE, TRUE_TILE_SIZE);
        g2.dispose();
    }
}
