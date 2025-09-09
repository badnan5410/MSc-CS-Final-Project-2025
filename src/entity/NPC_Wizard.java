package entity;

import main.GamePanel;

import java.util.Random;

/**
 * Simple wandering NPC wizard.
 *
 * Loads two-frame walk sprites per direction, holds multi-page dialogue, and either wanders randomly or follows a path when {@code onPath} is true.
 * Collision box is 40×40 and default facing is down.
 */
public class NPC_Wizard extends Entity {

    /**
     * Initializes type, speed, collision bounds, sprites, and dialogue.
     * Starts with {@code dialogueSet = -1} so the first speak() shows set 0.
     */
    public NPC_Wizard(GamePanel gp) {
        super(gp);
        direction = "down";
        speed = 1;
        type = TYPE_NPC;
        rect.x = 0;
        rect.y = 0;
        rect.width = 40;
        rect.height = 40;
        default_rectX = rect.x;
        default_rectY = rect.y;
        dialogueSet = -1;
        getImage();
        setDialogue();
    }

    /**
     * Loads directional walking frames from resources.
     */
    public void getImage() {
        up1 = setup("/npc/wizard/wizard_up_1");
        up2 = setup("/npc/wizard/wizard_up_2");
        down1 = setup("/npc/wizard/wizard_down_1");
        down2 = setup("/npc/wizard/wizard_down_2");
        right1 = setup("/npc/wizard/wizard_right_1");
        right2 = setup("/npc/wizard/wizard_right_2");
        left1 = setup("/npc/wizard/wizard_left_1");
        left2 = setup("/npc/wizard/wizard_left_2");
    }

    /**
     * Defines the wizard’s dialogue pages, grouped by {@code dialogueSet}.s
     */
    public void setDialogue() {
        dialogues[0][0] = "Greetings, young adventurer!" + "\n\n\n[press enter]";
        dialogues[0][1] = "So you've come to this island to find \ntreasure?" + "\n\n[press enter]";
        dialogues[0][2] = "I used to be a great wizard but now... \nI'm a bit too old for an adventure." + "\n\n[press enter]";
        dialogues[0][3] = "Well, good luck to you!" + "\n\n\n[press enter]";

        dialogues[1][0] = "If you're low on health or mana, recover it at the\npool." + "\n\n[press enter]";
        dialogues[1][1] = "Be careful, the monster's will respawn if you\nrecover from the pool." + "\n\n[press enter]";
        dialogues[1][2] = "In any case, try not to push yourself too hard." + "\n\n\n[press enter]";

        dialogues[2][0] = "I wonder how you unlock that door..." + "\n\n\n[press enter]";
    }

    /**
     * AI tick: if {@code onPath}, uses A* to move toward a fixed target tile; otherwise, picks a random direction at intervals to wander.
     */
    @Override
    public void setAction() {

        if (onPath) {
            int endCol, endRow;

            // wizard's house position
            endCol = 10;
            endRow = 10;

            // player's position
            /*endCol = (gp.player.worldX + gp.player.rect.x)/gp.TILE_SIZE;
            endRow = (gp.player.worldY + gp.player.rect.y)/gp.TILE_SIZE;*/

            searchPath(endCol, endRow);
        }

        else {
            movementCounter++;

            if (movementCounter == 120) {
                Random rand = new Random();
                int i = rand.nextInt(100)+1;

                if (i <= 25) {
                    direction = "up";
                }

                if (i > 25 && i <= 50) {
                    direction = "down";
                }

                if (i > 50 && i <= 75) {
                    direction = "right";
                }

                if (i > 75 && i <= 100) {
                    direction = "left";
                }

                movementCounter = 0;
            }
        }
    }

    /**
     * Faces the player and advances the dialogue set, clamping at the last page.
     */
    @Override
    public void speak() {
        faceThePlayer();
        startDialogue(this, dialogueSet);
        dialogueSet++;

        if (dialogues[dialogueSet][0] == null) {
            dialogueSet--;
        }
    }
}
