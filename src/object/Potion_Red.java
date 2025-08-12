package object;

import entity.Entity;
import main.GamePanel;

public class Potion_Red extends Entity {
    GamePanel gp;

    public Potion_Red(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "Red Potion";
        down1 = setup("/objects/potion_red");
        type = TYPE_CONSUMABLE;
        life = 4;
        description = "[" + name + "]\nDrink this potion to recover your health.\n+" + life + " HP";
    }

    public void useItem(Entity entity) {
        gp.soundEffect(11);
        gp.gameState = gp.GS_DIALOGUE;
        gp.ui.currentDialogue = "You drink the " + name + "!\n You have recovered " + life + " HP!";
        if (life + entity.life > entity.maxLife) {
            entity.life = entity.maxLife;
        }
        else {
            entity.life += life;
        }
    }
}
