package object;

import entity.Entity;
import main.GamePanel;

public class Heart extends Entity {
    GamePanel gp;

    public Heart(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = TYPE_PICKUP;
        name = "Heart";
        value = 2;
        down1 = setup("/objects/heart_pickup");
        image1 = setup("/objects/heart_full");
        image2 = setup("/objects/heart_half");
        image3 = setup("/objects/heart_empty");
    }

    public void useItem(Entity entity) {
        gp.soundEffect(2);
        entity.life += value;
        gp.ui.addMessage("You have recovered " + value + " HP!");
    }
}
