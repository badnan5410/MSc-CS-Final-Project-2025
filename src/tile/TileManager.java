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
import java.sql.SQLOutput;
import java.util.ArrayList;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapArray[][][];
    boolean drawPath = true;
    ArrayList<String> fileNames = new ArrayList<>();
    ArrayList<String> collisionStatus = new ArrayList<>();

    public TileManager(GamePanel gp) {
        this.gp = gp;

        // read tile data file
        InputStream is = getClass().getResourceAsStream("/maps/tiledata.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        // read tile names and collision status from file
        String line;

        try {
            while ((line = br.readLine()) != null) {
                fileNames.add(line);
                collisionStatus.add(br.readLine());
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // initialise tile array based on fileNames.size()
        tile = new Tile[fileNames.size()];
        getTileImage();

        // get maxWorldCol/Row
        is = getClass().getResourceAsStream("/maps/world_03.txt");
        br = new BufferedReader(new InputStreamReader(is));

        try {
            String line2 = br.readLine();
            String maxTile[] = line2.split(" ");
            gp.MAX_WORLD_COL = maxTile.length;
            gp.MAX_WORLD_ROW = maxTile.length;
            mapArray = new int[gp.maxMap][gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];
        } catch (IOException e) {
            System.out.println("Exception!");
        }

        mapLoader("/maps/world_03.txt", 0);
        mapLoader("/maps/interior_02.txt", 1);
    }

    public void getTileImage() {

        for (int i = 0; i < fileNames.size(); i++) {

            // get file name
            String fileName = fileNames.get(i);

            // get collision status
            boolean collision;

            if (collisionStatus.get(i).equals("true")) {collision = true;}
            else {collision = false;}

            setup(i, fileName, collision);
        }


    }

    public void setup(int i, String imageName, boolean collision) {
        UtilityTool uTool = new UtilityTool();

        try {
            tile[i] = new Tile();
            tile[i].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName));
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