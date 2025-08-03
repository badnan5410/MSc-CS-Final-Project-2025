package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Heart extends ParentObject {
    GamePanel gp;

    public Heart(GamePanel gp) {
        this.gp = gp;
        name = "Heart";
        try {
            image1 = ImageIO.read(getClass().getResourceAsStream("/objects/heart_full.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/objects/heart_half.png"));
            image3 = ImageIO.read(getClass().getResourceAsStream("/objects/heart_empty.png"));

            image1 = uTool.scaleImage(image1, gp.TILE_SIZE, gp.TILE_SIZE);
            image2 = uTool.scaleImage(image2, gp.TILE_SIZE, gp.TILE_SIZE);
            image3 = uTool.scaleImage(image3, gp.TILE_SIZE, gp.TILE_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
