package icons;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Icon_Thief extends Icon {
    GamePanel gp;

    public Icon_Thief(GamePanel gp) {
        this.gp = gp;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/icon/thief_icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
