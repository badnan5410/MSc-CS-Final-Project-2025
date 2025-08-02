package icons;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Icon_Magician extends Icon {
    GamePanel gp;

    public Icon_Magician(GamePanel gp) {
        this.gp = gp;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/icon/magician_icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
