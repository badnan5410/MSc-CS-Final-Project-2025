package tile;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Map extends TileManager {
    GamePanel gp;
    BufferedImage worldMap[];
    public boolean miniMapOn = false;

    public Map(GamePanel gp) {
        super(gp);
        this.gp = gp;
        createWorldMap();
    }

    public void createWorldMap() {
        worldMap = new BufferedImage[gp.maxMap];
        int worldMapWidth = gp.TILE_SIZE * gp.MAX_WORLD_COL;
        int worldMapHeight = gp.TILE_SIZE * gp.MAX_WORLD_ROW;

        for (int i = 0; i < gp.maxMap; i++) {
            worldMap[i] = new BufferedImage(worldMapWidth, worldMapHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = (Graphics2D)worldMap[i].createGraphics();
            int col = 0;
            int row = 0;

            while (col < gp.MAX_WORLD_COL && row < gp.MAX_WORLD_ROW) {
                int tileNum = mapArray[i][col][row];
                int x = gp.TILE_SIZE*col;
                int y = gp.TILE_SIZE*row;
                g2.drawImage(tile[tileNum].image, x, y, null);
                col++;

                if (col == gp.MAX_WORLD_COL) {
                    col = 0;
                    row++;
                }
            }
        }
    }

    public void drawFullMapScreen(Graphics2D g2) {

        // background colour
        g2.setColor(Color.black);
        g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);

        // draw map
        final int SIZE = 500;
        int width = SIZE;
        int height = SIZE;
        int x = (gp.SCREEN_WIDTH/2) - (width/2);
        int y = (gp.SCREEN_HEIGHT/2) - (height/2);
        g2.drawImage(worldMap[gp.currentMap], x, y, width, height, null);

        // draw player
        double scale = (double)(gp.TILE_SIZE*gp.MAX_WORLD_COL)/width;
        int playerX = (int)(x+gp.player.worldX/scale);
        int playerY = (int)(y+gp.player.worldY/scale);
        int playerSize = (int)(gp.TILE_SIZE/scale);
        g2.drawImage(gp.player.down1, playerX, playerY, playerSize, playerSize, null);

        // message
        g2.setFont(gp.ui.maruMonica.deriveFont(Font.BOLD, 32f));
        g2.setColor(Color.white);
        g2.drawString("Press M to close", 740, 550);
    }

    public void drawMiniMap(Graphics2D g2) {

        if (miniMapOn) {

            // draw map
            final int SIZE = 200;
            int width = SIZE;
            int height = SIZE;
            int x = gp.SCREEN_WIDTH-width - 50;
            int y = 50;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
            g2.drawImage(worldMap[gp.currentMap], x, y, width, height, null);

            // draw player
            double scale = (double)(gp.TILE_SIZE*gp.MAX_WORLD_COL)/width;
            int playerX = (int)(x+gp.player.worldX/scale);
            int playerY = (int)(y+gp.player.worldY/scale);
            int playerSize = (int)(gp.TILE_SIZE/3);
            g2.drawImage(gp.player.down1, playerX-6, playerY-6, playerSize, playerSize, null);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }
}
