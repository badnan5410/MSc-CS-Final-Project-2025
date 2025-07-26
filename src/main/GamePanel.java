package main;

import entity.Player;
import object.ParentObject;
import tile.TileManager;

import java.awt.*;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

    // Custom Screen Settings
    final int TILE = 16; // 16X16 tile
    final int SCALE = 3; // 16X3 = 48

    public final int TILE_SIZE = TILE * SCALE; // 48X48 tile
    public final int MAX_COL = 16;
    public final int MAX_ROW = 12; // col:row = 16:12 or 4:3

    public final int SCREEN_WIDTH = TILE_SIZE * MAX_COL; // 16x48 = 768 pixels
    public final int SCREEN_HEIGHT = TILE_SIZE * MAX_ROW; // 12x48 = 576 pixels

    // World Settings
    public final int MAX_WORLD_COL = 50;
    public final int MAX_WORLD_ROW = 50;

    // FPS
    final int FPS = 60;

    TileManager tm = new TileManager(this);
    KeyHandler kHandler = new KeyHandler();
    Sound se = new Sound();
    Sound music = new Sound();
    public CollisionHandler cHandler = new CollisionHandler(this);
    public ObjectHandler oHandler = new ObjectHandler(this);
    public UserInterface ui = new UserInterface(this);
    Thread gameLoop;
    public Player player = new Player(this, kHandler);
    public ParentObject[] obj = new ParentObject[10];

    // Class constructor
    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(kHandler);
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    public void gameSetup() {
        oHandler.setObject();
        playMusic(0);
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
        // Tile
        tm.draw(g2);

        // Object
        for (ParentObject object : obj) {
            if (object != null) {
                object.draw(g2, this);
            }
        }

        // Player
        player.draw(g2);

        // User Interface
        ui.draw(g2);

        g2.dispose();
    }

    public void playMusic(int i) {
        music.fileSetter(i);
        music.playAudio();
        music.loopAudio();
    }

    public void stopMusic() {
        music.stopAudio();
    }

    public void soundEffect(int i) {
        se.fileSetter(i);
        se.playAudio();
    }
}
