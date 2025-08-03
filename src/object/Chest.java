package object;

import main.GamePanel;

import javax.imageio.ImageIO;

public class Chest extends ParentObject {
    GamePanel gp;

    public Chest(GamePanel gp) {
        this.gp = gp;
        name = "Chest";
        try {
            image1 = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
            uTool.scaleImage(image1, gp.TILE_SIZE, gp.TILE_SIZE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        collision = true;
    }
}
