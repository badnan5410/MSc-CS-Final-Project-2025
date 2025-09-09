package object;

import entity.Entity;
import main.GamePanel;

/**
 * Legendary treasure pickup that triggers the game's ending cutscene.
 *
 * Picking this up immediately switches the game state to a cutscene
 * and instructs the cutscene manager to play the GAME_ENDING scene.
 */
public class Treasure extends Entity {
    GamePanel gp;
    public static final String objName = "Chaos Gem";

    /**
     * Creates the legendary treasure entity and sets its sprite and type.
     *
     * @param gp the game context
     */
    public Treasure(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = TYPE_PICKUP;
        name = objName;
        down1 = setup("/objects/legendary_treasure");
        setDialogue();
    }

    /**
     * Sets the dialogue shown when the treasure is obtained.
     */
    public void setDialogue() {
        dialogues[0][0] = "You find a beautiful blue crystal." + "\n\n\n[press enter]";
        dialogues[0][1] = "It's the legendary Chaos Sapphire!" + "\n\n\n[press enter]";
    }

    /**
     * Triggers the ending cutscene when the treasure is used (picked up).
     *
     * @param entity the entity interacting with the treasure
     * @return true to indicate the pickup was handled
     */
    @Override
    public boolean useItem(Entity entity) {
        gp.gameState = gp.GS_CUTSCENE;
        gp.cManager.sceneNum = gp.cManager.GAME_ENDING;
        return true;
    }
}
