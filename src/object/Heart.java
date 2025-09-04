package object;

import entity.Entity;
import main.GamePanel;

public class Heart extends Entity {
    GamePanel gp;
    public static final String objName = "Heart";

    public Heart(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = TYPE_PICKUP;
        name = objName;
        down1 = setup("/objects/heart_pickup");
        image1 = setup("/objects/heart_full");
        image2 = setup("/objects/heart_half");
        image3 = setup("/objects/heart_empty");
    }

    public boolean useItem(Entity entity) {
        gp.soundEffect(2);
        entity.life = entity.maxLife;
        gp.ui.addMessage("You have regained full health!");
        return true;
    }
}
