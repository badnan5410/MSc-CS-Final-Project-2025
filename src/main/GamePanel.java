package main;

import entity.Entity;
import entity.Player;
import object.ParentObject;
import tile.TileManager;

import java.awt.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {

    // Custom Screen Settings
    public final int TILE_SIZE = 48; // 48X48 tile
    public final int MAX_COL = 16;
    public final int MAX_ROW = 12; // col:row = 16:12 or 4:3

    public final int SCREEN_WIDTH = TILE_SIZE * MAX_COL; // 16x48 = 768 pixels
    public final int SCREEN_HEIGHT = TILE_SIZE * MAX_ROW; // 12x48 = 576 pixels

    // World Settings
    public final int MAX_WORLD_COL = 50;
    public final int MAX_WORLD_ROW = 50;

    // FPS
    final int FPS = 60;

    // System
    TileManager tm = new TileManager(this);
    public KeyHandler kHandler = new KeyHandler(this);
    Sound se = new Sound();
    Sound music = new Sound();
    public CollisionHandler cHandler = new CollisionHandler(this);
    public ObjectHandler oHandler = new ObjectHandler(this);
    public UserInterface ui = new UserInterface(this);
    Thread gameLoop;

    // Entity and Object
    public Player player = new Player(this, kHandler);
    public ParentObject[] obj = new ParentObject[10];
    public Entity npc[] = new Entity[10];

    // Icons
    public Icon[] icons = new Icon[3];

    // Game States
    public int gameState;
    public final int GS_PLAY = 0;
    public final int GS_PAUSE = 1;
    public final int GS_DIALOGUE = 2;
    public final int GS_TITLE_SCREEN = 3;

    // Class constructor
    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(kHandler);
        this.setFocusable(true);
        this.requestFocusInWindow();
        gameState = GS_TITLE_SCREEN;
    }

    // Game Setup
    public void gameSetup() {
        oHandler.setObject();
        oHandler.setNPC();
        //playMusic(0);
        //gameState = GS_TITLE_SCREEN;
    }

    public void startGameLoop() {
        gameLoop = new Thread(this);
        gameLoop.start();
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
        if (gameState == GS_PLAY) {
            player.update();

            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].update();
                }
            }
        }
        if (gameState == GS_PAUSE) {
            // don't update player information while
            // game is paused, sprite cannot move
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // Debug
        long drawStart = 0;
        if (kHandler.checkDrawTime) {
            drawStart = System.nanoTime();
        }

        // Title Screen
        if (gameState == GS_TITLE_SCREEN) {
            ui.draw(g2);
        }
        else {
            // Tile
            tm.draw(g2);

            // Object
            for (ParentObject object : obj) {
                if (object != null) {
                    object.draw(g2, this);
                }
            }

            // NPC
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].draw(g2);
                }
            }

            // Player
            player.draw(g2);

            // User Interface
            ui.draw(g2);
        }

        // Debug
        if (kHandler.checkDrawTime) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw Time: "+passed, 10, 400);
            System.out.println("Draw Time: "+passed);
        }

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
