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
        int i = 0;

        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.TILE_SIZE*21;
        gp.monster[i].worldY = gp.TILE_SIZE*38;
        i++;

        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.TILE_SIZE*23;
        gp.monster[i].worldY = gp.TILE_SIZE*42;
        i++;

        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.TILE_SIZE*24;
        gp.monster[i].worldY = gp.TILE_SIZE*37;
        i++;

        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.TILE_SIZE*34;
        gp.monster[i].worldY = gp.TILE_SIZE*42;
        i++;

        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.TILE_SIZE*38;
        gp.monster[i].worldY = gp.TILE_SIZE*42;
        i++;
    }
}

