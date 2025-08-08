package object;

import entity.Entity;
import main.GamePanel;

public class Shield_Wood extends Entity {

    public Shield_Wood(GamePanel gp) {
        super(gp);
        name = "Wooden Shield";
        down1 = setup("/objects/shield_wooden");
        defenseValue = 1;
    }
}