package object;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ParentObject {
    public BufferedImage bufferedImage;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle rectangle = new Rectangle(0,0,48,48);
    public int default_rectangleX = rectangle.x;
    public int default_rectangleY = rectangle.y;

    public void draw(Graphics2D g2, GamePanel gamePanel) {
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        if (worldX + gamePanel.TRUE_TILE_SIZE> gamePanel.player.worldX - gamePanel.player.screenX &&                                                        worldX - gamePanel.TRUE_TILE_SIZE< gamePanel.player.worldX + gamePanel.player.screenX &&                                                        worldY + gamePanel.TRUE_TILE_SIZE > gamePanel.player.worldY - gamePanel.player.screenY &&                                                       worldY - gamePanel.TRUE_TILE_SIZE < gamePanel.player.worldY + gamePanel.player.screenY) {

            g2.drawImage(bufferedImage, screenX, screenY, gamePanel.TRUE_TILE_SIZE, gamePanel.TRUE_TILE_SIZE, null);
        }
    }
}
