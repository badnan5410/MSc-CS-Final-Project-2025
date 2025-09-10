package main;

import data.Progress;
import entity.*;
import monster.*;
import object.*;
import tile_interactive.*;

/**
 * Populates the world with static objects, NPCs, monsters, and interactive tiles.
 * All placements are hard-coded by map and grid position. Coordinates are stored in tile units and converted via {@code gp.TILE_SIZE}.
 */
public class ObjectHandler {
    GamePanel gp;

    /**
     * Creates an object spawner bound to the given {@link GamePanel}.
     *
     * @param gp owning game panel
     */
    public ObjectHandler(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Places chests, doors, tools, consumables, and special objects by map.
     * Notes: Resets local index {@code i} when switching {@code mapNum}
     * All world positions are assigned in tiles: {@code worldX = TILE_SIZE * col} and {@code worldY = TILE_SIZE * row}.
     *Chests may be preloaded with loot via {@code setLoot(...)}
     */
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

        gp.obj[mapNum][i] = new Door(gp);
        gp.obj[mapNum][i].worldX = gp.TILE_SIZE*23;
        gp.obj[mapNum][i].worldY = gp.TILE_SIZE*13;
        i++;

        gp.obj[mapNum][i] = new Chest(gp);
        gp.obj[mapNum][i].setLoot(new Key(gp));
        gp.obj[mapNum][i].worldX = gp.TILE_SIZE*30;
        gp.obj[mapNum][i].worldY = gp.TILE_SIZE*29;
        i++;

        gp.obj[mapNum][i] = new Chest(gp);
        gp.obj[mapNum][i].setLoot(new Potion_Blue(gp));
        gp.obj[mapNum][i].worldX = gp.TILE_SIZE*20;
        gp.obj[mapNum][i].worldY = gp.TILE_SIZE*12;
        i++;

        mapNum = 2; // dungeon floor 1
        i = 0; // index reset to 0

        gp.obj[mapNum][i] = new Chest(gp);
        gp.obj[mapNum][i].setLoot(new Iron_Pickaxe(gp));
        gp.obj[mapNum][i].worldX = gp.TILE_SIZE*40;
        gp.obj[mapNum][i].worldY = gp.TILE_SIZE*41;
        i++;

        gp.obj[mapNum][i] = new Chest(gp);
        gp.obj[mapNum][i].setLoot(new Potion_Red(gp));
        gp.obj[mapNum][i].worldX = gp.TILE_SIZE*13;
        gp.obj[mapNum][i].worldY = gp.TILE_SIZE*16;
        i++;

        gp.obj[mapNum][i] = new Chest(gp);
        gp.obj[mapNum][i].setLoot(new Potion_Blue(gp));
        gp.obj[mapNum][i].worldX = gp.TILE_SIZE*26;
        gp.obj[mapNum][i].worldY = gp.TILE_SIZE*34;
        i++;

        gp.obj[mapNum][i] = new Chest(gp);
        gp.obj[mapNum][i].setLoot(new Tent(gp));
        gp.obj[mapNum][i].worldX = gp.TILE_SIZE*27;
        gp.obj[mapNum][i].worldY = gp.TILE_SIZE*15;
        i++;

        gp.obj[mapNum][i] = new Iron_Door(gp);
        gp.obj[mapNum][i].worldX = gp.TILE_SIZE*18;
        gp.obj[mapNum][i].worldY = gp.TILE_SIZE*23;
        i++;

        gp.obj[mapNum][i] = new Chest(gp);
        gp.obj[mapNum][i].setLoot(new Iron_Pickaxe(gp));
        gp.obj[mapNum][i].worldX = gp.TILE_SIZE*40;
        gp.obj[mapNum][i].worldY = gp.TILE_SIZE*41;
        i++;

        gp.obj[mapNum][i] = new Door(gp);
        gp.obj[mapNum][i].worldX = gp.TILE_SIZE*21;
        gp.obj[mapNum][i].worldY = gp.TILE_SIZE*40;
        i++;

        gp.obj[mapNum][i] = new Door(gp);
        gp.obj[mapNum][i].worldX = gp.TILE_SIZE*30;
        gp.obj[mapNum][i].worldY = gp.TILE_SIZE*19;
        i++;

        gp.obj[mapNum][i] = new Door(gp);
        gp.obj[mapNum][i].worldX = gp.TILE_SIZE*26;
        gp.obj[mapNum][i].worldY = gp.TILE_SIZE*29;
        i++;

        mapNum = 3; // dungeon floor 2
        i = 0; // index reset to 0

        gp.obj[mapNum][i] = new Door(gp);
        gp.obj[mapNum][i].worldX = gp.TILE_SIZE*25;
        gp.obj[mapNum][i].worldY = gp.TILE_SIZE*15;
        i++;

        gp.obj[mapNum][i] = new Treasure(gp);
        gp.obj[mapNum][i].worldX = gp.TILE_SIZE*25;
        gp.obj[mapNum][i].worldY = gp.TILE_SIZE*8;
        i++;
    }

    /**
     * Spawns NPCs by map (wizard on the main map, merchant in the store,
     * rocks/puzzle elements in dungeon maps).
     * Resets the per-map index when changing {@code mapNum}.
     */
    public void setNPC() {
        int i = 0;
        int mapNum = 0;

        gp.npc[mapNum][i] = new NPC_Wizard(gp);
        gp.npc[mapNum][i].worldX = gp.TILE_SIZE*21;
        gp.npc[mapNum][i].worldY = gp.TILE_SIZE*21;
        i++;

        mapNum = 1; // merchant's store
        i = 0; // index reset to 0

        gp.npc[mapNum][i] = new NPC_Merchant(gp);
        gp.npc[mapNum][i].worldX = gp.TILE_SIZE*12;
        gp.npc[mapNum][i].worldY = gp.TILE_SIZE*7;
        i++;

        mapNum = 2; // dungeon floor 1
        i = 0;

        gp.npc[mapNum][i] = new NPC_Rock(gp);
        gp.npc[mapNum][i].worldX = gp.TILE_SIZE*20;
        gp.npc[mapNum][i].worldY = gp.TILE_SIZE*25;
        i++;

        gp.npc[mapNum][i] = new NPC_Rock(gp);
        gp.npc[mapNum][i].worldX = gp.TILE_SIZE*11;
        gp.npc[mapNum][i].worldY = gp.TILE_SIZE*18;
        i++;

        gp.npc[mapNum][i] = new NPC_Rock(gp);
        gp.npc[mapNum][i].worldX = gp.TILE_SIZE*23;
        gp.npc[mapNum][i].worldY = gp.TILE_SIZE*14;
        i++;
    }

    /**
     * Spawns monsters by map with fixed positions.
     * Boss appears on dungeon floor 2 only if not already defeated
     * ({@link data.Progress#bossMonsterDefeated}).
     */
    public void setMonster() {
        int i = 0;
        int mapNum = 0;

        // main map
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.TILE_SIZE*20;
        gp.monster[mapNum][i].worldY = gp.TILE_SIZE*36;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.TILE_SIZE*26;
        gp.monster[mapNum][i].worldY = gp.TILE_SIZE*36;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.TILE_SIZE*20;
        gp.monster[mapNum][i].worldY = gp.TILE_SIZE*41;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.TILE_SIZE*26;
        gp.monster[mapNum][i].worldY = gp.TILE_SIZE*41;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.TILE_SIZE*33;
        gp.monster[mapNum][i].worldY = gp.TILE_SIZE*37;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.TILE_SIZE*39;
        gp.monster[mapNum][i].worldY = gp.TILE_SIZE*37;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.TILE_SIZE*39;
        gp.monster[mapNum][i].worldY = gp.TILE_SIZE*42;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.TILE_SIZE*32;
        gp.monster[mapNum][i].worldY = gp.TILE_SIZE*43;
        i++;

        gp.monster[mapNum][i] = new MON_RedSlime(gp);
        gp.monster[mapNum][i].worldX = gp.TILE_SIZE*41;
        gp.monster[mapNum][i].worldY = gp.TILE_SIZE*7;
        i++;

        gp.monster[mapNum][i] = new MON_RedSlime(gp);
        gp.monster[mapNum][i].worldX = gp.TILE_SIZE*41;
        gp.monster[mapNum][i].worldY = gp.TILE_SIZE*12;
        i++;

        gp.monster[mapNum][i] = new MON_RedSlime(gp);
        gp.monster[mapNum][i].worldX = gp.TILE_SIZE*34;
        gp.monster[mapNum][i].worldY = gp.TILE_SIZE*12;
        i++;

        gp.monster[mapNum][i] = new MON_RedSlime(gp);
        gp.monster[mapNum][i].worldX = gp.TILE_SIZE*20;
        gp.monster[mapNum][i].worldY = gp.TILE_SIZE*7;
        i++;

        gp.monster[mapNum][i] = new MON_Orc(gp);
        gp.monster[mapNum][i].worldX = gp.TILE_SIZE*12;
        gp.monster[mapNum][i].worldY = gp.TILE_SIZE*31;
        i++;

        mapNum = 2;
        i = 0;

        // dungeon map 1
        gp.monster[mapNum][i] = new MON_Orc(gp);
        gp.monster[mapNum][i].worldX = gp.TILE_SIZE*15;
        gp.monster[mapNum][i].worldY = gp.TILE_SIZE*40;
        i++;

        gp.monster[mapNum][i] = new MON_Orc(gp);
        gp.monster[mapNum][i].worldX = gp.TILE_SIZE*20;
        gp.monster[mapNum][i].worldY = gp.TILE_SIZE*35;
        i++;

        gp.monster[mapNum][i] = new MON_Orc(gp);
        gp.monster[mapNum][i].worldX = gp.TILE_SIZE*18;
        gp.monster[mapNum][i].worldY = gp.TILE_SIZE*26;
        i++;

        gp.monster[mapNum][i] = new MON_Orc(gp);
        gp.monster[mapNum][i].worldX = gp.TILE_SIZE*15;
        gp.monster[mapNum][i].worldY = gp.TILE_SIZE*8;
        i++;

        gp.monster[mapNum][i] = new MON_RedSlime(gp);
        gp.monster[mapNum][i].worldX = gp.TILE_SIZE*36;
        gp.monster[mapNum][i].worldY = gp.TILE_SIZE*41;
        i++;

        gp.monster[mapNum][i] = new MON_RedSlime(gp);
        gp.monster[mapNum][i].worldX = gp.TILE_SIZE*14;
        gp.monster[mapNum][i].worldY = gp.TILE_SIZE*24;
        i++;

        gp.monster[mapNum][i] = new MON_RedSlime(gp);
        gp.monster[mapNum][i].worldX = gp.TILE_SIZE*14;
        gp.monster[mapNum][i].worldY = gp.TILE_SIZE*28;
        i++;

        gp.monster[mapNum][i] = new MON_RedSlime(gp);
        gp.monster[mapNum][i].worldX = gp.TILE_SIZE*8;
        gp.monster[mapNum][i].worldY = gp.TILE_SIZE*21;
        i++;

        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.TILE_SIZE*34;
        gp.monster[mapNum][i].worldY = gp.TILE_SIZE*39;
        i++;

        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.TILE_SIZE*36;
        gp.monster[mapNum][i].worldY = gp.TILE_SIZE*25;
        i++;

        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.TILE_SIZE*39;
        gp.monster[mapNum][i].worldY = gp.TILE_SIZE*26;
        i++;

        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.TILE_SIZE*28;
        gp.monster[mapNum][i].worldY = gp.TILE_SIZE*11;
        i++;

        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.TILE_SIZE*10;
        gp.monster[mapNum][i].worldY = gp.TILE_SIZE*19;
        i++;

        mapNum = 3; // dungeon map 2
        i = 0;

        if (!Progress.bossMonsterDefeated) {
            gp.monster[mapNum][i] = new MON_Boss(gp);
            gp.monster[mapNum][i].worldX = gp.TILE_SIZE*23;
            gp.monster[mapNum][i].worldY = gp.TILE_SIZE*16;
            i++;
        }
    }

    /**
     * Places interactive/destructible tiles (dry trees, breakable walls, pressure plates) by map and grid location.
     * Each interactive tile is constructed with column/row indices and manages its own behavior (e.g., tool checks, destruction animation).
     */
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

        mapNum = 2; // dungeon floor 1
        i = 0; // index reset to 0

        // trees blocking the chest
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 36, 38); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 37, 38); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 37, 37); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 38, 37); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 39, 37); i++;
        gp.iTile[mapNum][i] = new IT_Drytree(gp, 40, 37); i++;

        // breakable walls all over the map
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 18, 30); i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 17, 31); i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 17, 32); i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 17, 34); i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 18, 34); i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 18, 33); i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 10, 22); i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 10, 24); i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 38, 18); i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 38, 19); i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 38, 20); i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 38, 21); i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 18, 13); i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 18, 14); i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 22, 28); i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 30, 28); i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 32, 28); i++;

        // 3 pressure plates
        gp.iTile[mapNum][i] = new IT_PressurePlate(gp, 20, 22); i++;
        gp.iTile[mapNum][i] = new IT_PressurePlate(gp, 8, 17); i++;
        gp.iTile[mapNum][i] = new IT_PressurePlate(gp, 39, 31); i++;
    }
}

