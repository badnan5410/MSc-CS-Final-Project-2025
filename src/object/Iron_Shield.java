package object;

import entity.Entity;
import main.GamePanel;

public class Iron_Shield extends Entity {

    public Iron_Shield(GamePanel gp) {
        super(gp);
        name = "Warrior's Shield";
        down1 = setup("/objects/shield_iron");
        type = TYPE_SHIELD;
        defenseValue = 2;
        description = "[" + name + "]\nA tough shield, perfect for a\nwarrior.\n+" + defenseValue + " DEF";
        price = 15;
    }
}

