package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapArray[][][];
    boolean drawPath = true;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[50];
        mapArray = new int[gp.maxMap][gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];
        getTileImage();
        mapLoader("/maps/world_02.txt", 0);
        mapLoader("/maps/interior_01.txt", 1);
    }

    public void getTileImage() {

        // Placeholder Tiles
        setup(0, "grass/grass00", false);
        setup(1, "grass/grass00", false);
        setup(2, "grass/grass00", false);
        setup(3, "grass/grass00", false);
        setup(4, "grass/grass00", false);
        setup(5, "grass/grass00", false);
        setup(6, "grass/grass00", false);
        setup(7, "grass/grass00", false);
        setup(8, "grass/grass00", false);
        setup(9, "grass/grass00", false);

        // Grass Tiles
        setup(10, "grass/grass00", false);
        setup(11, "grass/grass01", false);

        // Water Tiles
        setup(12, "water/water00", true);
        setup(13, "water/water01", true);
        setup(14, "water/water02", true);
        setup(15, "water/water03", true);
        setup(16, "water/water04", true);
        setup(17, "water/water05", true);
        setup(18, "water/water06", true);
        setup(19, "water/water07", true);
        setup(20, "water/water08", true);
        setup(21, "water/water09", true);
        setup(22, "water/water10", true);
        setup(23, "water/water11", true);
        setup(24, "water/water12", true);
        setup(25, "water/water13", true);

        // Road Tiles
        setup(26, "road/road00", false);
        setup(27, "road/road01", false);
        setup(28, "road/road02", false);
        setup(29, "road/road03", false);
        setup(30, "road/road04", false);
        setup(31, "road/road05", false);
        setup(32, "road/road06", false);
        setup(33, "road/road07", false);
        setup(34, "road/road08", false);
        setup(35, "road/road09", false);
        setup(36, "road/road10", false);
        setup(37, "road/road11", false);
        setup(38, "road/road12", false);

        // Earth Tiles
        setup(39, "earth/earth", false);
        setup(40, "wall/wall", true);
        setup(41, "tree/tree", true);

        setup(42, "hut", false);
        setup(43, "floor", false);
        setup(44, "table", true);
    }

    public void setup(int i, String imageName, boolean collision) {
        UtilityTool uTool = new UtilityTool();

        try {
            tile[i] = new Tile();
            tile[i].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
            tile[i].image = uTool.scaleImage(tile[i].image, gp.TILE_SIZE, gp.TILE_SIZE);
            tile[i].collision = collision;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mapLoader(String filepath, int map) {
        try {
            InputStream is = getClass().getResourceAsStream(filepath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.MAX_WORLD_COL && row < gp.MAX_WORLD_ROW) {
                String line = br.readLine();

                while (col < gp.MAX_WORLD_COL) {
                    String tileNumbers[] = line.split(" ");
                    int currentTile = Integer.parseInt(tileNumbers[col]);
                    mapArray[map][col][row] = currentTile;
                    col++;
                }

                if (col == gp.MAX_WORLD_COL) {
                    col = 0;
                    row++;
                }
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int col = 0;
        int row = 0;

        while (col < gp.MAX_WORLD_COL && row < gp.MAX_WORLD_ROW) {
            int currentTile = mapArray[gp.currentMap][col][row];
            int worldX = col * gp.TILE_SIZE;
            int worldY = row * gp.TILE_SIZE;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.screenY) {

                g2.drawImage(tile[currentTile].image, screenX, screenY, null);
            }

            col++;

            if (col == gp.MAX_WORLD_COL) {
                col = 0;
                row++;
            }
        }

        // show entity path
        /*if (drawPath) {
            g2.setColor(new Color(255, 0, 0, 70));

            for (int i = 0; i < gp.pFinder.pathList.size(); i++) {
                int worldX = gp.pFinder.pathList.get(i).col * gp.TILE_SIZE;
                int worldY = gp.pFinder.pathList.get(i).row * gp.TILE_SIZE;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                g2.fillRect(screenX, screenY, gp.TILE_SIZE, gp.TILE_SIZE);
            }
        }*/
    }
}