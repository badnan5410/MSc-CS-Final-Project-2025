package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Icon_Thief extends ParentObject {
    GamePanel gp;

    public Icon_Thief(GamePanel gp) {
        this.gp = gp;
        name = "Thief-Icon";
        try {
            image1 = ImageIO.read(getClass().getResourceAsStream("/objects/thief_icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
