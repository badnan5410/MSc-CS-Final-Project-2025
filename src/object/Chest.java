package object;

import entity.Entity;
import main.GamePanel;

public class Chest extends Entity {
    GamePanel gp;
    Entity loot;
    boolean opened = false;

    public Chest(GamePanel gp, Entity loot) {
        super(gp);
        this.gp = gp;
        this.loot = loot;
        type = TYPE_OBSTACLE;
        name = "Chest";
        image1 = setup("/objects/chest");
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

    public void interact() {
        gp.gameState = gp.GS_DIALOGUE;

        if (!opened) {
            gp.soundEffect(3);
            StringBuilder sb = new StringBuilder();
            sb.append("You open the chest and find a " + loot.name + "!");

            if (gp.player.inventory.size() == gp.player.INVENTORY_CAPACITY) {
                sb.append("\n... but you cannot carry any more!");
            }
            else {
                sb.append("\nYou obtain the " + loot.name + "!");
                gp.player.inventory.add(loot);
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
