package main;

import entity.NPC_Wizard;

public class ObjectHandler {
    GamePanel gp;

    public ObjectHandler(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {}

    public void setNPC() {
        gp.npc[0] = new NPC_Wizard(gp);
        gp.npc[0].worldX = gp.TILE_SIZE*21;
        gp.npc[0].worldY = gp.TILE_SIZE*21;
    }
}

