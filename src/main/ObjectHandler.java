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
        int mapNum = 0;

        gp.obj[mapNum][i] = new Wood_Axe(gp);
        gp.obj[mapNum][i].worldX = gp.TILE_SIZE*33;
        gp.obj[mapNum][i].worldY = gp.TILE_SIZE*7;
        i++;
    }

    public void setNPC() {
        int mapNum = 0;

        gp.npc[mapNum][0] = new NPC_Wizard(gp);
        gp.npc[mapNum][0].worldX = gp.TILE_SIZE*21;
        gp.npc[mapNum][0].worldY = gp.TILE_SIZE*21;
    }

    public void setMonster() {
        int i = 0;
        int mapNum = 0;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.TILE_SIZE*21;
        gp.monster[mapNum][i].worldY = gp.TILE_SIZE*38;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.TILE_SIZE*23;
        gp.monster[mapNum][i].worldY = gp.TILE_SIZE*42;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.TILE_SIZE*24;
        gp.monster[mapNum][i].worldY = gp.TILE_SIZE*37;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.TILE_SIZE*34;
        gp.monster[mapNum][i].worldY = gp.TILE_SIZE*42;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.TILE_SIZE*38;
        gp.monster[mapNum][i].worldY = gp.TILE_SIZE*42;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.TILE_SIZE*36;
        gp.monster[mapNum][i].worldY = gp.TILE_SIZE*10;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.TILE_SIZE*40;
        gp.monster[mapNum][i].worldY = gp.TILE_SIZE*8;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.TILE_SIZE*35;
        gp.monster[mapNum][i].worldY = gp.TILE_SIZE*12;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.TILE_SIZE*41;
        gp.monster[mapNum][i].worldY = gp.TILE_SIZE*12;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.TILE_SIZE*20;
        gp.monster[mapNum][i].worldY = gp.TILE_SIZE*12;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.TILE_SIZE*26;
        gp.monster[mapNum][i].worldY = gp.TILE_SIZE*12;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.TILE_SIZE*26;
        gp.monster[mapNum][i].worldY = gp.TILE_SIZE*7;
        i++;
    }

    public void setInteractiveTile() {
        int i = 0;
        int mapNum = 0;

        gp.iTile[mapNum][i] = new IT_Drytree(gp, 27, 12); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 28, 12); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 29, 12); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 30, 12); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 31, 12); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 32, 12); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 33, 12); i++;

        gp.iTile[mapNum][i] = new IT_Drytree(gp, 23, 13); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 12, 23); i++;

        gp.iTile[mapNum][i] = new IT_Drytree(gp, 10, 38); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 10, 39); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 10, 40); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 10, 41); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 11, 41); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 12, 41); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 13, 41); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 13, 40); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 13, 39); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 14, 39); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 15, 39); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 16, 39); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 17, 39); i++;

    }
}

