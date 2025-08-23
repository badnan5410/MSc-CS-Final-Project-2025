package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;
import tile_interactive.InteractiveTile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {

    // Custom Screen Settings
    private final int TILE = 16;
    private final int SCALE = 3;
    public final int TILE_SIZE = TILE*SCALE;
    public final int MAX_COL = 20;
    public final int MAX_ROW = 12;
    public final int SCREEN_WIDTH = TILE_SIZE * MAX_COL;
    public final int SCREEN_HEIGHT = TILE_SIZE * MAX_ROW;

    // World Settings
    public final int MAX_WORLD_COL = 50;
    public final int MAX_WORLD_ROW = 50;

    // Full Screen Settings
    public int fullScreenWidth = SCREEN_WIDTH;
    public int fullScreenHeight = SCREEN_HEIGHT;
    BufferedImage tempScreen = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = (Graphics2D)tempScreen.getGraphics();
    public boolean fullScreenOn = false;

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
    public EventHandler eHandler = new EventHandler(this);
    Config config = new Config(this);
    Thread gameLoop;

    // Entity and Object
    public Player player = new Player(this, kHandler);
    public Entity obj[] = new Entity[20];
    public Entity npc[] = new Entity[10];
    public Entity monster[] = new Entity[20];
    public InteractiveTile iTile[] = new InteractiveTile[50];
    public ArrayList<Entity> projectileList = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();
    public ArrayList<Entity> entityList = new ArrayList<>();

    // Game States
    public int gameState;
    public final int GS_PLAY = 0;
    public final int GS_PAUSE = 1;
    public final int GS_DIALOGUE = 2;
    public final int GS_TITLE_SCREEN = 3;
    public final int GS_CHARACTER_STATE = 4;
    public final int GS_SETTINGS_STATE = 5;
    public final int GS_END_STATE = 6;

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
        oHandler.setMonster();
        oHandler.setInteractiveTile();

        if (fullScreenOn) {setFullScreen();}
    }

    public void retry() {
        player.setDefaultPosition();
        player.restoreLifeAndMana();
        player.coins = (int)player.coins/2;
        oHandler.setNPC();
        oHandler.setMonster();
    }

    public void restart() {
        player.setDefaultValues();
        player.setInventory();
        oHandler.setObject();
        oHandler.setNPC();
        oHandler.setMonster();
        oHandler.setInteractiveTile();
    }

    public void setFullScreen() {
        GraphicsEnvironment gEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gDevice = gEnvironment.getDefaultScreenDevice();
        gDevice.setFullScreenWindow(Main.window);

        fullScreenWidth = Main.window.getWidth();
        fullScreenHeight = Main.window.getHeight();
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
                drawTempScreen();
                drawGameScreen();
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

            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    if (monster[i].alive && !monster[i].dying) {
                        monster[i].update();
                    }
                    if (!monster[i].alive) {
                        monster[i].checkDrop();
                        monster[i] = null;
                    }

                }
            }

            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {
                    if (projectileList.get(i).alive) {projectileList.get(i).update();}
                    if (!projectileList.get(i).alive) {
                        projectileList.remove(i);
                    }
                }
            }

            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    if (particleList.get(i).alive) {particleList.get(i).update();}
                    if (!particleList.get(i).alive) {
                        particleList.remove(i);
                    }
                }
            }

            for (int i = 0; i < iTile.length; i++) {
                if (iTile[i] != null) {
                    iTile[i].update();
                }
            }

        }
        if (gameState == GS_PAUSE) {
            // don't update player information while
            // game is paused, sprite cannot move
        }
    }

    public void drawTempScreen() {
        // Debug
        long drawStart = 0;
        if (kHandler.toggleDebug) {
            drawStart = System.nanoTime();
        }

        // Title Screen
        if (gameState == GS_TITLE_SCREEN) {
            ui.draw(g2);
        }
        else {
            // Tile
            tm.draw(g2);

            // Interactive Tile
            for (int i = 0; i < iTile.length; i++) {
                if (iTile[i] != null) {
                    iTile[i].draw(g2);
                }
            }

            // add entities to the list
            entityList.add(player);

            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    entityList.add(npc[i]);
                }
            }

            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    entityList.add(obj[i]);
                }
            }

            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    entityList.add(monster[i]);
                }
            }

            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {
                    entityList.add(projectileList.get(i));
                }
            }

            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    entityList.add(particleList.get(i));
                }
            }

            // sort
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            // draw entities
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }

            // empty entity list
            entityList.clear();

            // User Interface
            ui.draw(g2);
        }

        // Debug
        if (kHandler.toggleDebug) {
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20));
            g2.setColor(Color.white);
            int x = 10;
            int y = 400;
            int lineHeight = 20;

            g2.drawString("Pos: (" + player.worldX + ", " + player.worldY + ")", x, y);
            y += lineHeight;
            g2.drawString("Tile: (" + (player.worldX + player.rect.x)/TILE_SIZE + ", " + (player.worldY + player.rect.y)/TILE_SIZE + ")", x, y);
            y += lineHeight;
        }

    }

    public void drawGameScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, fullScreenWidth, fullScreenHeight, null);
        g.dispose();
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
