package main;

import ai.PathFinder;
import data.SaveLoad;
import entity.Entity;
import entity.Player;
import environment.EnvironmentManager;
import tile.Map;
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
    public int MAX_WORLD_COL;
    public  int MAX_WORLD_ROW;
    public final int maxMap = 10;
    public int currentMap = 0;

    // Full Screen Settings
    public int fullScreenWidth = SCREEN_WIDTH;
    public int fullScreenHeight = SCREEN_HEIGHT;
    BufferedImage tempScreen = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = (Graphics2D)tempScreen.getGraphics();
    public boolean fullScreenOn = false;

    // FPS
    final int FPS = 60;

    // System
    public TileManager tManager = new TileManager(this);
    public KeyHandler kHandler = new KeyHandler(this);
    Sound se = new Sound();
    Sound music = new Sound();
    public CollisionHandler cHandler = new CollisionHandler(this);
    public ObjectHandler oHandler = new ObjectHandler(this);
    public UserInterface ui = new UserInterface(this);
    public EventHandler eHandler = new EventHandler(this);
    Config config = new Config(this);
    public PathFinder pFinder = new PathFinder(this);
    public EnvironmentManager eManager = new EnvironmentManager(this);
    Map map = new Map(this);
    SaveLoad saveLoad = new SaveLoad(this);
    public EntityGenerator eGenerator = new EntityGenerator(this);
    public CutsceneManager cManager = new CutsceneManager(this);
    Thread gameLoop;

    // Entity and Object
    public Player player = new Player(this, kHandler);
    public Entity obj[][] = new Entity[maxMap][20];
    public Entity npc[][] = new Entity[maxMap][10];
    public Entity monster[][] = new Entity[maxMap][20];
    public InteractiveTile iTile[][] = new InteractiveTile[maxMap][50];
    public Entity projectile[][] = new Entity[maxMap][20];
    public ArrayList<Entity> particleList = new ArrayList<>();
    public ArrayList<Entity> entityList = new ArrayList<>();

    // Game States
    public int gameState;
    public final int GS_PLAY = 0;
    public final int GS_PAUSE = 1;
    public final int GS_DIALOGUE = 2;
    public final int GS_TITLE_SCREEN = 3;
    public final int GS_INVENTORY = 4;
    public final int GS_SETTINGS = 5;
    public final int GS_GAME_OVER = 6;
    public final int GS_TRANSITION = 7;
    public final int GS_TRADE = 8;
    public final int GS_SLEEP = 9;
    public final int GS_MAP = 10;
    public final int GS_CUTSCENE = 11;

    // others
    public boolean bossBattleOn = false;

    // area state
    public int currentArea;
    public int nextArea;
    public final int AREA_MAIN = 0;
    public final int AREA_STORE = 1;
    public final int AREA_DUNGEON = 2;

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
        eManager.setup();
        currentArea = AREA_MAIN;

        if (fullScreenOn) {setFullScreen();}
    }

    public void resetGame(boolean restart) {
        stopMusic();
        currentArea = AREA_MAIN;
        removeTempEntity();
        bossBattleOn = false;
        player.setDefaultPosition();
        player.restoreStatus();
        player.resetCounter();
        oHandler.setNPC();
        oHandler.setMonster();
        ui.message.clear();

        if (restart) {
            player.setDefaultValues();
            oHandler.setObject();
            oHandler.setInteractiveTile();
            eManager.lighting.resetDay();
        }
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

            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    npc[currentMap][i].update();
                }
            }

            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    if (monster[currentMap][i].alive && !monster[currentMap][i].dying) {
                        monster[currentMap][i].update();
                    }
                    if (!monster[currentMap][i].alive) {
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    }

                }
            }

            for (int i = 0; i < projectile[1].length; i++) {
                if (projectile[currentMap][i] != null) {
                    if (projectile[currentMap][i].alive) {projectile[currentMap][i].update();}
                    if (!projectile[currentMap][i].alive) {
                        projectile[currentMap][i] = null;
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

            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].update();
                }
            }

            eManager.update();
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

        // map screen
        else if (gameState == GS_MAP) {
            map.drawFullMapScreen(g2);
        }

        else {
            // Tile
            tManager.draw(g2);

            // Interactive Tile
            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].draw(g2);
                }
            }

            // add entities to the list
            entityList.add(player);

            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    entityList.add(npc[currentMap][i]);
                }
            }

            for (int i = 0; i < obj[1].length; i++) {
                if (obj[currentMap][i] != null) {
                    entityList.add(obj[currentMap][i]);
                }
            }

            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    entityList.add(monster[currentMap][i]);
                }
            }

            for (int i = 0; i < projectile[1].length; i++) {
                if (projectile[currentMap][i] != null) {
                    entityList.add(projectile[currentMap][i]);
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

            // environment
            eManager.draw(g2);

            // mini map
            map.drawMiniMap(g2);

            // cutscene
            cManager.draw(g2);

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

            g2.drawString("God Mode On: " + kHandler.godMode, x, y);
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

    public void changeArea() {

        if (nextArea != currentArea) {
            stopMusic();

            switch (nextArea) {
                case AREA_MAIN: playMusic(0); break;
                case AREA_STORE: playMusic(22); break;
                case AREA_DUNGEON: playMusic(23); break;
            }

            oHandler.setNPC();
        }

        currentArea = nextArea;
        oHandler.setMonster();
    }

    public void removeTempEntity() {

        // scan the obj array and remove all entities that have the temp flag as true
        for (int mapNum = 0; mapNum < maxMap; mapNum++) {

            for (int i = 0; i < obj[1].length; i++) {
                if (obj[mapNum][i] != null && obj[mapNum][i].temp) {
                    obj[mapNum][i] = null;
                }
            }
        }
    }
}
