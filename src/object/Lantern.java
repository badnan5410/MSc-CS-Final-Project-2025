package object;

import entity.Entity;
import main.GamePanel;

public class Lantern extends Entity {

    public Lantern(GamePanel gp) {
        super(gp);
        type = TYPE_LIGHT;
        name = "Lantern";
        down1 = setup("/objects/lantern");
        description = "[Lantern]\nIlluminate your surroundings.";
        price = 6;
        lightRadius = 250;
    }
}
