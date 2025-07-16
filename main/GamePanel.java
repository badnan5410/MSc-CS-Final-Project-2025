package main;

import entity.Player;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // Custom Screen Settings
    final int GAME_TILE = 16; // 16X16 tile
    final int SCALE = 3; // 16X3 = 48
    public final int TRUE_TILE_SIZE = GAME_TILE * SCALE; // 48X48 tile

    final int MAX_COL = 16;
    final int MAX_ROW = 12; // col:row = 16:12 or 4:3

    final int SCREEN_WIDTH = TRUE_TILE_SIZE * MAX_COL; // 16x48 = 768 pixels
    final int SCREEN_HEIGHT = TRUE_TILE_SIZE * MAX_ROW; // 12x48 = 576 pixels

    final int FPS = 60;

    KeyHandler keyHandler = new KeyHandler();
    Thread gameLoop;
    Player player = new Player(this, keyHandler);

    // Player character default position
    int playerX = 100;
    int playerY = 100;
    int player_speed = 4;

    // Class constructor
    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    public void startGameLoop() {
        gameLoop = new Thread(this);
        gameLoop.start(); // calls the run() method below
    }

    @Override
    public void run() {
        double time_per_frame = (double) 1000000000 /FPS;
        double frame_tracker = 0;
        long previousTime = System.nanoTime();
        long currentTime;

        while (gameLoop != null) {
            currentTime = System.nanoTime();
            frame_tracker += (currentTime-previousTime) / time_per_frame;
            previousTime = currentTime;

            if (frame_tracker >= 1) {
                update();
                repaint();
                frame_tracker--;
            }

        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        player.draw(g2);
        g2.dispose();
    }
}
