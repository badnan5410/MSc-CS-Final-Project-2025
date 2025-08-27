package entity;

import main.GamePanel;

import java.util.Random;

public class NPC_Wizard extends Entity {

    public NPC_Wizard(GamePanel gp) {
        super(gp);
        direction = "down";
        speed = 1;
        type = TYPE_NPC;

        getImage();
        setDialogue();

        rect.x = 0;
        rect.y = 16;
        rect.width = 32;
        rect.height = 32;
        default_rectX = rect.x;
        default_rectY = rect.y;
    }

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

    public void setDialogue() {
        dialogues[0] = "Hello there, young adventurer! Follow me!";
        dialogues[1] = "So you've come to this island to find \ntreasure?";
        dialogues[2] = "I used to be a great wizard but now... \nI'm a bit too old for an adventure.";
        dialogues[3] = "Well, good luck to you!";
    }

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

    @Override
    public void speak() {
        super.speak();
        onPath = true;
    }
}
