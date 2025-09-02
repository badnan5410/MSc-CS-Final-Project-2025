package entity;

import main.GamePanel;
import object.Iron_Door;
import tile_interactive.IT_PressurePlate;
import tile_interactive.InteractiveTile;

import java.util.ArrayList;
import java.util.Random;

public class NPC_Rock extends Entity {
    public static final String npcName = "Rock";

    public NPC_Rock(GamePanel gp) {
        super(gp);
        name = npcName;
        direction = "down";
        defaultSpeed = 3;
        speed = defaultSpeed;
        type = TYPE_NPC;
        rect.x = 2;
        rect.y = 6;
        rect.width = 44;
        rect.height = 40;
        default_rectX = rect.x;
        default_rectY = rect.y;

        dialogueSet = -1;
        getImage();
        setDialogue();
    }

    public void getImage() {
        up1 = setup("/npc/rock");
        up2 = setup("/npc/rock");
        down1 = setup("/npc/rock");
        down2 = setup("/npc/rock");
        right1 = setup("/npc/rock");
        right2 = setup("/npc/rock");
        left1 = setup("/npc/rock");
        left2 = setup("/npc/rock");
    }

    public void setDialogue() {
        dialogues[0][0] = "It's just a big rock." + "\n\n\n[press enter]";
    }

    public void setAction() {}

    public void update() {} // stops the rock from moving on its own like other NPCs

    public void speak() {
        faceThePlayer();
        startDialogue(this, dialogueSet);
        dialogueSet++;

        if (dialogues[dialogueSet][0] == null) {
            dialogueSet--;
        }
    }

    public void move(String direction) {
        this.direction = direction;
        checkCollision();

        if (!checkCollision) {

            switch (direction) {
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
            }
        }

        detectPressurePlate();
    }

    public void detectPressurePlate() {
        ArrayList<InteractiveTile> plateList = new ArrayList<>();
        ArrayList<Entity> rockList = new ArrayList<>();

        // scan the map and add all the pressure plates to plateList
        for (int i = 0; i < gp.iTile[1].length; i++) {
            if (gp.iTile[gp.currentMap][i] != null && gp.iTile[gp.currentMap][i].name != null && gp.iTile[gp.currentMap][i].name.equals(IT_PressurePlate.itName)) {
                plateList.add(gp.iTile[gp.currentMap][i]);
            }
        }

        // scan the map and add all the rocks to rockList
        for (int i = 0; i < gp.npc[1].length; i++) {
            if (gp.npc[gp.currentMap][i] != null && gp.npc[gp.currentMap][i].name.equals(NPC_Rock.npcName)) {
                rockList.add(gp.npc[gp.currentMap][i]);
            }
        }

        int count = 0;

        // scan plateList
        for (int i = 0; i < plateList.size(); i++) {
            int dx = Math.abs(worldX - plateList.get(i).worldX);
            int dy = Math.abs(worldY - plateList.get(i).worldY);
            int distance = Math.max(dx, dy);

            if (distance < 8) {
                if (linkedEntity == null) {
                    linkedEntity = plateList.get(i);
                    gp.soundEffect(26);
                }
            }
            else {
                if (linkedEntity == plateList.get(i)) {
                    linkedEntity = null;
                }
            }
        }

        // scan rockList
        for (int i = 0; i < rockList.size(); i++) {

            // count the rocks on the pressure plate
            if (rockList.get(i).linkedEntity != null) {count++;}

            // if all the rocks are on the pressure plates, the door opens
            if (count == rockList.size()) {

                for (int j = 0; j < gp.obj[1].length; j++) {
                    if (gp.obj[gp.currentMap][j] != null && gp.obj[gp.currentMap][j].name.equals(Iron_Door.objName)) {
                        gp.obj[gp.currentMap][j] = null;
                        gp.soundEffect(25);
                    }
                }
            }
        }
    }
}
