package object;

import entity.Entity;
import main.GamePanel;

public class Key extends Entity {
    GamePanel gp;
    public static final String objName = "Key";

    public Key(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = TYPE_CONSUMABLE;
        name = objName;
        down1 = setup("/objects/key");
        description = "[" + name + "]\nA mysterious key. I wonder\nwhat it unlocks...";
        price = 50;
        stackable = true;
        setDialogue();
    }

    public void setDialogue() {
        dialogues[0][0] = "You unlocked the door using the key!" + "\n\n\n[press enter]";

        dialogues[1][0] = "What are you doing?" + "\n\n\n[press enter]";
    }

    public boolean useItem(Entity entity) {
        int objIndex = getDetected(entity, gp.obj, "Door");

        if (objIndex != -1) {
            startDialogue(this, 0);
            gp.soundEffect(3);
            gp.obj[gp.currentMap][objIndex] = null;
            return true;
        }
        else {
            startDialogue(this, 1);
            return false;
        }
    }
}
