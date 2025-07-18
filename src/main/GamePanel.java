package main;

import entity.Player;
import tile.TileManager;

import java.awt.*;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

    // Custom Screen Settings
    final int GAME_TILE = 16; // 16X16 tile
    final int SCALE = 3; // 16X3 = 48

    public final int TRUE_TILE_SIZE = GAME_TILE * SCALE; // 48X48 tile
    public final int MAX_COL = 16;
    public final int MAX_ROW = 12; // col:row = 16:12 or 4:3

    public final int SCREEN_WIDTH = TRUE_TILE_SIZE * MAX_COL; // 16x48 = 768 pixels
    public final int SCREEN_HEIGHT = TRUE_TILE_SIZE * MAX_ROW; // 12x48 = 576 pixels

    final int FPS = 60;

    TileManager tileManager = new TileManager(this);
    KeyHandler keyHandler = new KeyHandler();
    Thread gameLoop;
    Player player = new Player(this, keyHandler);

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

        tileManager.draw(g2);
        player.draw(g2);
        g2.dispose();
    }
}
