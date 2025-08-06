package main;

import entity.NPC_Wizard;
import monster.MON_GreenSlime;

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

    public void setMonster() {
        gp.monster[0] = new MON_GreenSlime(gp);
        gp.monster[0].worldX = gp.TILE_SIZE*23;
        gp.monster[0].worldY = gp.TILE_SIZE*36;

        gp.monster[1] = new MON_GreenSlime(gp);
        gp.monster[1].worldX = gp.TILE_SIZE*23;
        gp.monster[1].worldY = gp.TILE_SIZE*37;
    }
}

