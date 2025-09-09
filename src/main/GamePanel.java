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

/**
* This is the world manager of the game.
*
* It renders the game screen, first to an off-screen buffer, then blits to the panel.
* It also has global managers arrays per map, for different aspects of the game, such tiles, objects, collisions, UI, events, etc.
* It also has a fixed-step game loop that runs at 60 FPS.
*/

public class GamePanel extends JPanel implements Runnable {

    // CUSTOM SCREEN SETTINGS

    private final int TILE = 16;
    private final int SCALE = 3;

    // size of one tile (48 x 48 px)
    public final int TILE_SIZE = TILE*SCALE;

    // viewport width & height
    public final int MAX_COL = 20;
    public final int MAX_ROW = 12;

    // viewport width & height (960 x 576 px)
    public final int SCREEN_WIDTH = TILE_SIZE * MAX_COL;
    public final int SCREEN_HEIGHT = TILE_SIZE * MAX_ROW;

    // WORLD SETTINGS

    // world width & height in tiles (set by TileManager after map load)
    public int MAX_WORLD_COL;
    public  int MAX_WORLD_ROW;

    // maxinum number of maps this game supports
    public final int maxMap = 10;

    // currently active map index
    public int currentMap = 0;

    // FULL SCREEN SETTINGS

    // fullscreen target width & height (defaults to windowed size)
    public int fullScreenWidth = SCREEN_WIDTH;
    public int fullScreenHeight = SCREEN_HEIGHT;

    // off-screen back buffer drawn into each frame
    BufferedImage tempScreen = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);

    // graphics for the back buffer
    Graphics2D g2 = (Graphics2D)tempScreen.getGraphics();

    // true if fullscreen scaling is enabled
    public boolean fullScreenOn = false;

    // FPS

    // fixed update rate
    final int FPS = 60;

    // SYSTEM

    // tile and map rendering
    public TileManager tManager = new TileManager(this);

    // keyboard input
    public KeyHandler kHandler = new KeyHandler(this);

    // sound effects
    Sound se = new Sound();

    // background music
    Sound music = new Sound();

    // tile, object, and entity collision checks
    public CollisionHandler cHandler = new CollisionHandler(this);

    // places objects, NPCs, monsters, and interactive tiles on the map
    public ObjectHandler oHandler = new ObjectHandler(this);

    // all the UI on the screen
    public UserInterface ui = new UserInterface(this);

    // in-game triggers and teleports
    public EventHandler eHandler = new EventHandler(this);

    // persist user config to disk
    Config config = new Config(this);

    // A* grid pathfinding
    public PathFinder pFinder = new PathFinder(this);

    // day-night and overlays
    public EnvironmentManager eManager = new EnvironmentManager(this);

    // map view mode and minimap renderers
    Map map = new Map(this);

    // save & load game state
    SaveLoad saveLoad = new SaveLoad(this);

    // factory for entities
    public EntityGenerator eGenerator = new EntityGenerator(this);

    // cutscene coordinator
    public CutsceneManager cManager = new CutsceneManager(this);

    // game loop thread
    Thread gameLoop;

    // ENTITY & OBJECT

    // the player character
    public Player player = new Player(this, kHandler);

    // objects
    public Entity obj[][] = new Entity[maxMap][20];

    // NPCs
    public Entity npc[][] = new Entity[maxMap][10];

    // monsters
    public Entity monster[][] = new Entity[maxMap][20];

    // interactive tiles
    public InteractiveTile iTile[][] = new InteractiveTile[maxMap][50];

    // projectiles
    public Entity projectile[][] = new Entity[maxMap][20];

    // particle effects
    public ArrayList<Entity> particleList = new ArrayList<>();

    // list of all entities (depth-sorted)
    public ArrayList<Entity> entityList = new ArrayList<>();

    // GAME STATES

    // current game state
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

    // MISCELLANEOUS

    // set true during boss battle
    public boolean bossBattleOn = false;

    // AREA STATES

    // current area for music and spawns
    public int currentArea;

    // next area to transition to
    public int nextArea;
    public final int AREA_MAIN = 0;
    public final int AREA_STORE = 1;
    public final int AREA_DUNGEON = 2;

    /**
    * Builds the panel with fixed size, black background, doubled-buffering, and focusable input, initialising onto the games title screen.
    */
    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(kHandler);
        this.setFocusable(true);
        this.requestFocusInWindow();
        gameState = GS_TITLE_SCREEN;
    }

    /**
    * One-time setup after construction.
    * It places objects, NPCs, monsters, interactive tiles, prepares environment systems, and applies fullscreen if needed.
    */
    public void gameSetup() {
        oHandler.setObject();
        oHandler.setNPC();
        oHandler.setMonster();
        oHandler.setInteractiveTile();
        eManager.setup();
        currentArea = AREA_MAIN;

        if (fullScreenOn) {setFullScreen();}
    }

    /**
     * Reset the game state after death or when returning to the title screen.
     *
     * @param restart if true, it restores a fresh run including objects and interactive tile, and resets the day-night cycle.
     * */
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

    /**
     * Switch the top-level frame into fullscreen and cache its size.
     * It keeps the scaled width & height for blitting.
     * */
    public void setFullScreen() {
        GraphicsEnvironment gEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gDevice = gEnvironment.getDefaultScreenDevice();
        gDevice.setFullScreenWindow(Main.window);

        fullScreenWidth = Main.window.getWidth();
        fullScreenHeight = Main.window.getHeight();
    }

    /**
     * Start the fixed-step game loop on a new thread.
     */
    public void startGameLoop() {
        gameLoop = new Thread(this);
        gameLoop.start();
    }

    /**
     * Fixed-step loop {@link #FPS}. It updates game logic, renders into the off-screen buffer, then blits to the screen.
     * It catches up if a frame is late by consuming accumulated time.
     */
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

    /**
     * Advanced one simulation step based on the current game state.
     * In play state it updates the player, NPCs, monsters, projectiles, particles, interactive tiles, and environment.
     */
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

                    if (projectile[currentMap][i].alive) {
                        projectile[currentMap][i].update();
                    }

                    if (!projectile[currentMap][i].alive) {
                        projectile[currentMap][i] = null;
                    }
                }
            }

            for (int i = 0; i < particleList.size(); i++) {

                if (particleList.get(i) != null) {

                    if (particleList.get(i).alive) {
                        particleList.get(i).update();
                    }

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
    }


    /**
     * Draw one full frame into {@link #tempScreen}.
     * It renders tiles, interactive tiles, entities sorted by worldY, environment layers, minimap, cutscenes, and UI.
     * It also shows debug overlays when toggled.
     */
    public void drawTempScreen() {

        // debug
        long drawStart = 0;

        if (kHandler.toggleDebug) {
            drawStart = System.nanoTime();
        }

        // title scene
        if (gameState == GS_TITLE_SCREEN) {
            ui.draw(g2);
        }

        // map screen
        else if (gameState == GS_MAP) {
            map.drawFullMapScreen(g2);
        }

        else {

            // tile
            tManager.draw(g2);

            // interactive tile
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

            // user interface
            ui.draw(g2);
        }

        // debug
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

    /**
     * Blit the off-screen buffer to the panel.
     * Fullscreen scaled using {@link #fullScreenWidth} and {@link fullScreenHeight}.
     */
    public void drawGameScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, fullScreenWidth, fullScreenHeight, null);
        g.dispose();
    }

    /**
     * Start and loop background music
     *
     * @param i index in the sound table
     */
    public void playMusic(int i) {
        music.fileSetter(i);
        music.playAudio();
        music.loopAudio();
    }

    /**
     * Stop background music
     */
    public void stopMusic() {
        music.stopAudio();
    }

    /**
     * Play sound effect
     * @param i index in the sound table
     */
    public void soundEffect(int i) {
        se.fileSetter(i);
        se.playAudio();
    }

    /**
     * Switch area context. It stops current music, play a new track, refresh NPCs, then update spawns for the new area.
     */
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

    /**
     * Remove any temporary objects across all maps.
     * I use this when resetting after death or returning to title.
     */
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
