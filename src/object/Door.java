package object;

import javax.imageio.ImageIO;

public class Door extends ParentObject {

    public Door() {
        name = "Door";
        try {
            bufferedImage = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
