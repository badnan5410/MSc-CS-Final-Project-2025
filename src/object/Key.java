package object;

import entity.Entity;
import main.GamePanel;

public class Key extends Entity {
    GamePanel gp;

    public Key(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = TYPE_CONSUMABLE;
        name = "Key";
        down1 = setup("/objects/key");
        description = "[" + name + "]\nA mysterious key. I wonder\nwhat it unlocks...";
        price = 20;
        stackable = true;
    }

    public boolean useItem(Entity entity) {
        gp.gameState = gp.GS_DIALOGUE;
        int objIndex = getDetected(entity, gp.obj, "Door");

        if (objIndex != -1) {
            gp.ui.currentDialogue = "You unlocked the door using the key!";
            gp.soundEffect(3);
            gp.obj[gp.currentMap][objIndex] = null;
            return true;
        }
        else {
            gp.ui.currentDialogue = "What are you doing?";
            return false;
        }
    }
}
