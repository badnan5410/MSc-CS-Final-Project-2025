package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Key extends ParentObject {
    GamePanel gp;

    public Key(GamePanel gp) {
        this.gp = gp;
        name = "Key";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
            uTool.scaleImage(image, gp.TILE_SIZE, gp.TILE_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
