package tile;

import main.GamePanel;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gamePanel;
    Tile[] tile;
    int mapArray[][];

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tile = new Tile[10];
        mapArray = new int[gamePanel.MAX_WORLD_COL][gamePanel.MAX_WORLD_ROW];
        getTileImage();
        mapLoader("/maps/world_01.txt");
    }

    public void getTileImage() {
        try{
            tile[0] = new Tile();
            tile[0].bufferedImage = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));

            tile[1] = new Tile();
            tile[1].bufferedImage = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));

            tile[2] = new Tile();
            tile[2].bufferedImage = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));

            tile[3] = new Tile();
            tile[3].bufferedImage = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));

            tile[4] = new Tile();
            tile[4].bufferedImage = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));

            tile[5] = new Tile();
            tile[5].bufferedImage = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void mapLoader(String filepath) {
        try{
            InputStream inputStream = getClass().getResourceAsStream(filepath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            int col = 0;
            int row = 0;

            while (col < gamePanel.MAX_WORLD_COL && row < gamePanel.MAX_WORLD_ROW) {
                String line = bufferedReader.readLine();

                while (col < gamePanel.MAX_WORLD_COL) {
                    String tileNumbers[] = line.split(" ");

                    int currentTile = Integer.parseInt(tileNumbers[col]);
                    mapArray[col][row] = currentTile;
                    col++;
                }
                if (col == gamePanel.MAX_WORLD_COL) {
                    col = 0;
                    row++;
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g2) {
        int col = 0;
        int row = 0;

        while (col < gamePanel.MAX_WORLD_COL && row < gamePanel.MAX_WORLD_ROW) {
            int currentTile = mapArray[col][row];
            int worldX = col * gamePanel.TRUE_TILE_SIZE;
            int worldY = row * gamePanel.TRUE_TILE_SIZE;
            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

            if (worldX + gamePanel.TRUE_TILE_SIZE> gamePanel.player.worldX - gamePanel.player.screenX &&                                                        worldX - gamePanel.TRUE_TILE_SIZE< gamePanel.player.worldX + gamePanel.player.screenX &&                                                        worldY + gamePanel.TRUE_TILE_SIZE > gamePanel.player.worldY - gamePanel.player.screenY &&                                                       worldY - gamePanel.TRUE_TILE_SIZE < gamePanel.player.worldY + gamePanel.player.screenY) {

                g2.drawImage(tile[currentTile].bufferedImage, screenX, screenY, gamePanel.TRUE_TILE_SIZE, gamePanel.TRUE_TILE_SIZE, null);
            }

            col++;

            if (col == gamePanel.MAX_WORLD_COL) {
                col = 0;
                row++;
            }
        }
    }
}
