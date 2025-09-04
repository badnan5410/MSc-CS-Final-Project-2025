package object;

import entity.Entity;
import main.GamePanel;

public class Potion_Blue extends Entity {
    GamePanel gp;
    public static final String objName = "Mana Potion";

    public Potion_Blue(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = objName;
        down1 = setup("/objects/potion_blue");
        type = TYPE_CONSUMABLE;
        description = "[" + name + "]\nDrink this potion to recover\nyour mana.\n+ 1/2 max MP";
        price = 15;
        stackable = true;
        setDialogue();
    }

    public void setDialogue() {
        dialogues[0][0] = "You drink the " + name + "!\n You have recovered " + value + " MP!" + "\n\n[press enter]";
    }

    public boolean useItem(Entity entity) {
        value = entity.maxMana/2;
        gp.soundEffect(11);
        entity.mana += value;
        startDialogue(this, 0);
        return true;
    }
}
