package main;

import entity.NPC_Merchant;
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

        gp.obj[mapNum][i] = new Door(gp);
        gp.obj[mapNum][i].worldX = gp.TILE_SIZE*14;
        gp.obj[mapNum][i].worldY = gp.TILE_SIZE*28;
        i++;

        gp.obj[mapNum][i] = new Door(gp);
        gp.obj[mapNum][i].worldX = gp.TILE_SIZE*10;
        gp.obj[mapNum][i].worldY = gp.TILE_SIZE*12;
        i++;

        gp.obj[mapNum][i] = new Chest(gp, new Key(gp));
        gp.obj[mapNum][i].worldX = gp.TILE_SIZE*30;
        gp.obj[mapNum][i].worldY = gp.TILE_SIZE*26;
        i++;

        gp.obj[mapNum][i] = new Key(gp);
        gp.obj[mapNum][i].worldX = gp.TILE_SIZE*17;
        gp.obj[mapNum][i].worldY = gp.TILE_SIZE*21;
        i++;

        gp.obj[mapNum][i] = new Potion_Red(gp);
        gp.obj[mapNum][i].worldX = gp.TILE_SIZE*16;
        gp.obj[mapNum][i].worldY = gp.TILE_SIZE*21;
        i++;

        gp.obj[mapNum][i] = new Iron_Axe(gp);
        gp.obj[mapNum][i].worldX = gp.TILE_SIZE*15;
        gp.obj[mapNum][i].worldY = gp.TILE_SIZE*21;
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

        // trees blocking top area
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 27, 12); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 28, 12); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 29, 12); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 30, 12); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 31, 12); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 32, 12); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 33, 12); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 23, 13); i++;

        // trees leading to treasure chest
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 25, 24); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 26, 24); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 27, 24); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 27, 25); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 27, 26); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 27, 27); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 27, 28); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 27, 29); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 27, 30); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 28, 30); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 29, 30); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 30, 30); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 30, 29); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 30, 28); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 30, 27); i++;

        // trees leading to shop
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

