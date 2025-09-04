package object;

import entity.Entity;
import main.GamePanel;

public class Potion_Red extends Entity {
    GamePanel gp;
    public static final String objName = "Health Potion";

    public Potion_Red(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = objName;
        down1 = setup("/objects/potion_red");
        type = TYPE_CONSUMABLE;
        description = "[" + name + "]\nDrink this potion to recover\nyour health.\n+ 1/3 max HP";
        price = 15;
        stackable = true;
        setDialogue();
    }

    public void setDialogue() {
        dialogues[0][0] = "You drink the " + name + "!\n You have recovered " + value + " HP!" + "\n\n[press enter]";
    }

    public boolean useItem(Entity entity) {
        value = entity.maxLife/3;
        gp.soundEffect(11);
        entity.life += value;
        startDialogue(this, 0);
        return true;
    }
}
