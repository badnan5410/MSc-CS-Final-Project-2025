package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Icon_Fighter extends ParentObject {
    GamePanel gp;

    public Icon_Fighter(GamePanel gp) {
        this.gp = gp;
        name = "Fighter-Icon";
        try {
            image1 = ImageIO.read(getClass().getResourceAsStream("/objects/fighter_icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
