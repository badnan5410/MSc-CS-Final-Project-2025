package main;

import entity.NPC_Wizard;
import object.Door;

public class ObjectHandler {
    GamePanel gp;

    public ObjectHandler(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.obj[0] = new Door(gp);
        gp.obj[0].worldX = gp.TILE_SIZE*21;
        gp.obj[0].worldY = gp.TILE_SIZE*22;

        gp.obj[1] = new Door(gp);
        gp.obj[1].worldX = gp.TILE_SIZE*23;
        gp.obj[1].worldY = gp.TILE_SIZE*25;
    }

    public void setNPC() {
        gp.npc[0] = new NPC_Wizard(gp);
        gp.npc[0].worldX = gp.TILE_SIZE*21;
        gp.npc[0].worldY = gp.TILE_SIZE*21;

        gp.npc[1] = new NPC_Wizard(gp);
        gp.npc[1].worldX = gp.TILE_SIZE*11;
        gp.npc[1].worldY = gp.TILE_SIZE*21;

        gp.npc[2] = new NPC_Wizard(gp);
        gp.npc[2].worldX = gp.TILE_SIZE*31;
        gp.npc[2].worldY = gp.TILE_SIZE*21;

        gp.npc[3] = new NPC_Wizard(gp);
        gp.npc[3].worldX = gp.TILE_SIZE*21;
        gp.npc[3].worldY = gp.TILE_SIZE*11;

        gp.npc[4] = new NPC_Wizard(gp);
        gp.npc[4].worldX = gp.TILE_SIZE*21;
        gp.npc[4].worldY = gp.TILE_SIZE*31;
    }
}

