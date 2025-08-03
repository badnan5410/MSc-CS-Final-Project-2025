package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Icon_Magician extends ParentObject {
    GamePanel gp;

    public Icon_Magician(GamePanel gp) {
        this.gp = gp;
        name = "Magician-Icon";
        try {
            image1 = ImageIO.read(getClass().getResourceAsStream("/objects/magician_icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
