package object;

import main.GamePanel;

import javax.imageio.ImageIO;

public class Boots extends ParentObject {
    GamePanel gp;

    public Boots(GamePanel gp) {
        this.gp = gp;
        name = "Boots";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/boots.png"));
            uTool.scaleImage(image, gp.TILE_SIZE, gp.TILE_SIZE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
