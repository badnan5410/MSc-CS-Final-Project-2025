package main;

import entity.*;
import monster.*;
import object.*;
import tile_interactive.*;

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

        gp.obj[mapNum][i] = new Door(gp);
        gp.obj[mapNum][i].worldX = gp.TILE_SIZE*14;
        gp.obj[mapNum][i].worldY = gp.TILE_SIZE*28;
        i++;

        gp.obj[mapNum][i] = new Door(gp);
        gp.obj[mapNum][i].worldX = gp.TILE_SIZE*12;
        gp.obj[mapNum][i].worldY = gp.TILE_SIZE*12;
        i++;

        gp.obj[mapNum][i] = new Lantern(gp);
        gp.obj[mapNum][i].worldX = gp.TILE_SIZE*18;
        gp.obj[mapNum][i].worldY = gp.TILE_SIZE*20;
        i++;

        gp.obj[mapNum][i] = new Tent(gp);
        gp.obj[mapNum][i].worldX = gp.TILE_SIZE*16;
        gp.obj[mapNum][i].worldY = gp.TILE_SIZE*21;
        i++;

        gp.obj[mapNum][i] = new Chest(gp, new Key(gp));
        gp.obj[mapNum][i].worldX = gp.TILE_SIZE*30;
        gp.obj[mapNum][i].worldY = gp.TILE_SIZE*29;
        i++;
    }

    public void setNPC() {
        int i = 0;
        int mapNum = 0;

        gp.npc[mapNum][i] = new NPC_Wizard(gp);
        gp.npc[mapNum][i].worldX = gp.TILE_SIZE*21;
        gp.npc[mapNum][i].worldY = gp.TILE_SIZE*21;

        mapNum++;

        gp.npc[mapNum][i] = new NPC_Merchant(gp);
        gp.npc[mapNum][i].worldX = gp.TILE_SIZE*12;
        gp.npc[mapNum][i].worldY = gp.TILE_SIZE*7;
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

        gp.monster[mapNum][i] = new MON_Orc(gp);
        gp.monster[mapNum][i].worldX = gp.TILE_SIZE*12;
        gp.monster[mapNum][i].worldY = gp.TILE_SIZE*31;
        i++;
    }

    public void setInteractiveTile() {
        int i = 0;
        int mapNum = 0;

        // trees blocking top area
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 27, 12); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 28, 12); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 29, 12); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 30, 12); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 31, 12); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 32, 12); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 33, 12); i++;

        // tree blocking right side of the map
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 31, 21); i++;

        // trees leading to treasure chest
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 27, 28); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 27, 29); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 27, 30); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 27, 31); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 28, 31); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 29, 31); i++;

        // trees leading to shop
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 11, 41); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 12, 41); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 13, 41); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 13, 40); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 14, 40); i++;
    }
}

