package tile;

import main.GamePanel;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapArray[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];
        mapArray = new int[gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];
        getTileImage();
        mapLoader("/maps/world_01.txt");
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
            tile[4].collision = true;

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void mapLoader(String filepath) {
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
                    mapArray[col][row] = currentTile;
                    col++;
                }

                if (col == gp.MAX_WORLD_COL) {
                    col = 0;
                    row++;
                }
            }

            br.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g2) {
        int col = 0;
        int row = 0;

        while (col < gp.MAX_WORLD_COL && row < gp.MAX_WORLD_ROW) {
            int currentTile = mapArray[col][row];
            int worldX = col * gp.TILE_SIZE;
            int worldY = row * gp.TILE_SIZE;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.screenY) {

                g2.drawImage(tile[currentTile].image, screenX, screenY, gp.TILE_SIZE, gp.TILE_SIZE, null);
            }

            col++;

            if (col == gp.MAX_WORLD_COL) {
                col = 0;
                row++;
            }
        }
    }
}