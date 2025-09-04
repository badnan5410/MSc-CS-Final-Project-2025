package object;

import entity.Entity;
import main.GamePanel;

public class Wood_Shield extends Entity {
    public static final String objName = "Wooden Shield";

    public Wood_Shield(GamePanel gp) {
        super(gp);
        name = objName;
        down1 = setup("/objects/shield_wooden");
        type = TYPE_SHIELD;
        defenseValue = 1;
        description = "[" + name + "]\nJust a wooden shield.\n+" + defenseValue + " DEF";
        price = 10;
    }
}