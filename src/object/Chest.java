package object;

import javax.imageio.ImageIO;

public class Chest extends ParentObject {

    public Chest() {
        name = "Chest";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        collision = true;
    }
}
