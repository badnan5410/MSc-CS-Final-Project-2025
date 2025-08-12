package object;

import entity.Entity;
import main.GamePanel;

public class Mana extends Entity {
    GamePanel gp;

    public Mana(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Mana";
        image1 = setup("/objects/mana_full");
        image2 = setup("/objects/mana_empty");

    }
}
