package object;

import entity.Entity;
import main.GamePanel;

public class Lantern extends Entity {
    public static final String objName = "Lantern";

    public Lantern(GamePanel gp) {
        super(gp);
        type = TYPE_LIGHT;
        name = objName;
        down1 = setup("/objects/lantern");
        description = "[Lantern]\nIlluminate your surroundings.";
        price = 100;
        lightRadius = 600;
    }
}
