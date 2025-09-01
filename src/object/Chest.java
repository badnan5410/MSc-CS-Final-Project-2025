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
    }

    public void interact() {
        gp.gameState = gp.GS_DIALOGUE;

        if (!opened) {
            gp.soundEffect(3);
            StringBuilder sb = new StringBuilder();
            sb.append("You open the chest and find a " + loot.name + "!");

            if (!gp.player.isItemObtainable(loot)) {
                sb.append("\n... but you cannot carry any more!");
            }
            else {
                sb.append("\nYou obtain the " + loot.name + "!");
                down1 = image2;
                opened = true;
            }

            gp.ui.currentDialogue = sb.toString();
        }
        else {
            gp.ui.currentDialogue = "It's empty.";
        }
    }
}
