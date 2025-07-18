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
        mapArray = new int[gamePanel.MAX_COL][gamePanel.MAX_ROW];
        getTileImage();
        mapLoader("/maps/map_01.txt");
    }

    public void getTileImage() {
        try{
            tile[0] = new Tile();
            tile[0].bufferedImage = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));

            tile[1] = new Tile();
            tile[1].bufferedImage = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));

            tile[2] = new Tile();
            tile[2].bufferedImage = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
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

            while (col < gamePanel.MAX_COL && row < gamePanel.MAX_ROW) {
                String line = bufferedReader.readLine();

                while (col < gamePanel.MAX_COL) {
                    String tileNumbers[] = line.split(" ");

                    int currentTile = Integer.parseInt(tileNumbers[col]);
                    mapArray[col][row] = currentTile;
                    col++;
                }
                if (col == gamePanel.MAX_COL) {
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
        int x = 0;
        int y = 0;

        while (col < gamePanel.MAX_COL && row < gamePanel.MAX_ROW) {
            int currentTile = mapArray[col][row];
            g2.drawImage(tile[currentTile].bufferedImage, x, y, gamePanel.TRUE_TILE_SIZE, gamePanel.TRUE_TILE_SIZE, null);
            col++;
            x += gamePanel.TRUE_TILE_SIZE;

            if (col == gamePanel.MAX_COL) {
                col = 0;
                x = 0;
                row++;
                y += gamePanel.TRUE_TILE_SIZE;
            }
        }
    }
}
