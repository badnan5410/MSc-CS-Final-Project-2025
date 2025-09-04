package object;

import entity.Entity;
import main.GamePanel;

public class Mana extends Entity {
    GamePanel gp;
    public static final String objName = "Mana";

    public Mana(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = TYPE_PICKUP;
        name = objName;
        value = 4;
        down1 = setup("/objects/mana_pickup");
        image1 = setup("/objects/mana_full");
        image2 = setup("/objects/mana_empty");
    }

    public boolean useItem(Entity entity) {
        gp.soundEffect(2);
        entity.mana = entity.maxMana;
        gp.ui.addMessage("You have regained full mana!");
        return true;
    }
}
