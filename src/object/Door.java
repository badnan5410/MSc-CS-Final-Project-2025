package object;

import main.GamePanel;

import javax.imageio.ImageIO;

public class Door extends ParentObject {
    GamePanel gp;

    public Door(GamePanel gp) {
        this.gp = gp;
        name = "Door";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));
            uTool.scaleImage(image, gp.TILE_SIZE, gp.TILE_SIZE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        collision = true;
    }
}
