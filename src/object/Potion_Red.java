package object;

import entity.Entity;
import main.GamePanel;

public class Potion_Red extends Entity {
    GamePanel gp;
    public static final String objName = "Red Potion";

    public Potion_Red(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = objName;
        down1 = setup("/objects/potion_red");
        type = TYPE_CONSUMABLE;
        value = 4;
        description = "[" + name + "]\nDrink this potion to recover\nyour health.\n+" + value + " HP";
        price = 16;
        stackable = true;
        setDialogue();
    }

    public void setDialogue() {
        dialogues[0][0] = "You drink the " + name + "!\n You have recovered " + value + " HP!" + "\ns\n[press enter]";
    }

    public boolean useItem(Entity entity) {
        gp.soundEffect(11);
        entity.life += value;
        startDialogue(this, 0);
        return true;
    }
}
