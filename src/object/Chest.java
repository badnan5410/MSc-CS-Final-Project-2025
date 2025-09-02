package object;

import entity.Entity;
import main.GamePanel;

public class Chest extends Entity {
    GamePanel gp;

    public Chest(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = TYPE_OBSTACLE;
        name = "Chest";
        image1 = setup("/objects/chest_closed");
        image2 = setup("/objects/chest_opened");
        down1 = image1;
        collision = true;
        rect.x = 4;
        rect.y = 16;
        rect.width = 40;
        rect.height = 32;
        default_rectX = rect.x;
        default_rectY = rect.y;
    }

    public void setLoot(Entity loot) {
        this.loot = loot;
        setDialogue();
    }

    public void setDialogue() {
        dialogues[0][0] = "You open the chest and find a " + loot.name + "!\n... but you cannot carry any more!" + "\n\n[press enter]";

        dialogues[1][0] = "You open the chest and find a " + loot.name + "!\nYou obtain the " + loot.name + "!" + "\n\n[press enter]";

        dialogues[2][0] = "It's empty." + "\n\n\n[press enter]";
    }

    public void interact() {

        if (!opened) {
            gp.soundEffect(3);

            if (!gp.player.isItemObtainable(loot)) {
                startDialogue(this, 0);
            }
            else {
                startDialogue(this, 1);
                down1 = image2;
                opened = true;
            }
        }
        else {
            startDialogue(this, 2);
        }
    }
}
