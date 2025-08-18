package main;

import entity.NPC_Wizard;
import monster.MON_GreenSlime;
import object.*;
import tile_interactive.IT_Drytree;

public class ObjectHandler {
    GamePanel gp;

    public ObjectHandler(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        int i = 0;

        gp.obj[i] = new Potion_Red(gp);
        gp.obj[i].worldX = gp.TILE_SIZE*21;
        gp.obj[i].worldY = gp.TILE_SIZE*23;
        i++;
    }

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

    public void setInteractiveTile() {
        int i = 0;

        gp.iTile[i] = new IT_Drytree(gp, 27, 12); i++;
        gp.iTile[i] = new IT_Drytree(gp, 28, 12); i++;
        gp.iTile[i] = new IT_Drytree(gp, 29, 12); i++;
        gp.iTile[i] = new IT_Drytree(gp, 30, 12); i++;
        gp.iTile[i] = new IT_Drytree(gp, 31, 12); i++;
        gp.iTile[i] = new IT_Drytree(gp, 32, 12); i++;
        gp.iTile[i] = new IT_Drytree(gp, 33, 12); i++;

        gp.iTile[i] = new IT_Drytree(gp, 30, 20); i++;
        gp.iTile[i] = new IT_Drytree(gp, 30, 21); i++;
        gp.iTile[i] = new IT_Drytree(gp, 30, 22); i++;
        gp.iTile[i] = new IT_Drytree(gp, 20, 20); i++;
        gp.iTile[i] = new IT_Drytree(gp, 20, 21); i++;
        gp.iTile[i] = new IT_Drytree(gp, 20, 22); i++;
        gp.iTile[i] = new IT_Drytree(gp, 22, 24); i++;
        gp.iTile[i] = new IT_Drytree(gp, 23, 24); i++;
        gp.iTile[i] = new IT_Drytree(gp, 24, 24); i++;
    }
}

