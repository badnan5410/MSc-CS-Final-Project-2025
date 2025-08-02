package icons;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Icon_Fighter extends Icon {
    GamePanel gp;

    public Icon_Fighter(GamePanel gp) {
        this.gp = gp;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/icon/fighter_icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
