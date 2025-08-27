package object;

import entity.Entity;
import main.GamePanel;

public class Mana extends Entity {
    GamePanel gp;

    public Mana(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = TYPE_PICKUP;
        name = "Mana";
        value = 1;
        down1 = setup("/objects/mana_pickup");
        image1 = setup("/objects/mana_full");
        image2 = setup("/objects/mana_empty");
    }

    public boolean useItem(Entity entity) {
        gp.soundEffect(2);
        entity.mana += value;
        gp.ui.addMessage("You have recovered " + value + " MP!");
        return true;
    }
}
