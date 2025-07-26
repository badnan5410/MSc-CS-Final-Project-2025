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
    public int mapArray[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];
        mapArray = new int[gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];
        getTileImage();
        mapLoader("/maps/world_01.txt");
    }

    public void getTileImage() {
        setup(0, "grass1", false);
        setup(1, "wall", true);
        setup(2, "water", true);
        setup(3, "earth", false);
        setup(4, "tree", true);
        setup(5, "sand", false);
        setup(6, "grass2", false);
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
        } catch (IOException e) {
            e.printStackTrace();
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

                g2.drawImage(tile[currentTile].image, screenX, screenY, null);
            }

            col++;

            if (col == gp.MAX_WORLD_COL) {
                col = 0;
                row++;
            }
        }
    }
}