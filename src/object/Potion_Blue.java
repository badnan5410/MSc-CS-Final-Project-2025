package object;

import entity.Entity;
import main.GamePanel;

public class Potion_Blue extends Entity {
    GamePanel gp;
    public static final String objName = "Blue Potion";

    public Potion_Blue(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = objName;
        down1 = setup("/objects/potion_blue");
        type = TYPE_CONSUMABLE;
        value = 4;
        description = "[" + name + "]\nDrink this potion to recover\nyour mana.\n+" + value + " MP";
        price = 20;
        stackable = true;
        setDialogue();
    }

    public void setDialogue() {
        dialogues[0][0] = "You drink the " + name + "!\n You have recovered " + value + " MP!" + "\n\n[press enter]";
    }

    public boolean useItem(Entity entity) {
        gp.soundEffect(11);
        entity.mana += value;
        startDialogue(this, 0);
        return true;
    }
}
