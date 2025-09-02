package object;

import entity.Entity;
import main.GamePanel;

public class Boots extends Entity {
    public static final String objName = "Boots";

    public Boots(GamePanel gp) {
        super(gp);
        name = objName;
        down1 = setup("objects/boots");
    }
}
