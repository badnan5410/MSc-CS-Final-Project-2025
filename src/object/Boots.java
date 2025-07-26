package object;

import javax.imageio.ImageIO;

public class Boots extends ParentObject {

    public Boots() {
        name = "Boots";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/boots.png"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
