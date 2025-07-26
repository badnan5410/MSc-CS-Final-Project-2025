package object;

import main.GamePanel;
import main.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ParentObject {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle rect = new Rectangle(0,0,48,48);
    public int default_rectX = rect.x;
    public int default_rectY = rect.y;
    UtilityTool uTool = new UtilityTool();

    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.screenX && worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.screenX && worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.screenY && worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.screenY) {

            g2.drawImage(image, screenX, screenY, gp.TILE_SIZE, gp.TILE_SIZE, null);
        }
    }
}
