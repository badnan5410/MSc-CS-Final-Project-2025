package object;

import javax.imageio.ImageIO;

public class Key extends ParentObject {

    public Key() {
        name = "Key";
        try {
            bufferedImage = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
