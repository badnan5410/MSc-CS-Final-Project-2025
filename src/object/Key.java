package object;

import main.GamePanel;

import javax.imageio.ImageIO;

public class Key extends ParentObject {
    GamePanel gp;

    public Key(GamePanel gp) {
        this.gp = gp;
        name = "Key";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
            uTool.scaleImage(image, gp.TILE_SIZE, gp.TILE_SIZE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
