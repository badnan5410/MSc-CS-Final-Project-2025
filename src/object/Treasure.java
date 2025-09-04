package object;

import entity.Entity;
import main.GamePanel;

public class Treasure extends Entity {
    GamePanel gp;
    public static final String objName = "Blue Heart";

    public Treasure(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = TYPE_PICKUP;
        name = objName;
        down1 = setup("/objects/legendary_treasure");
        setDialogue();
    }

    public void setDialogue() {
        dialogues[0][0] = "You find a beautiful blue crystal." + "\n\n\n[press enter]";
        dialogues[0][1] = "It's the legendary Chaos Sapphire!" + "\n\n\n[press enter]";
    }

    @Override
    public boolean useItem(Entity entity) {
        gp.gameState = gp.GS_CUTSCENE;
        gp.cManager.sceneNum = gp.cManager.GAME_ENDING;
        return true;
    }
}
