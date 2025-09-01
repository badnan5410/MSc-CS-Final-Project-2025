package main;

import entity.Entity;

public class EventHandler {
    GamePanel gp;
    EventRect eventRect[][][];

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    int tempMap, tempCol, tempRow;

    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventRect = new EventRect[gp.maxMap][gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];

        int map = 0;
        int col = 0;
        int row = 0;

        while (map < gp.maxMap && col < gp.MAX_WORLD_COL && row < gp.MAX_WORLD_ROW) {
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;
            if (col == gp.MAX_WORLD_COL) {
                col = 0;
                row++;

                if (row == gp.MAX_WORLD_ROW) {
                    row = 0;
                    map++;
                }
            }
        }
    }

    public void checkEvent() {

        // Check if player is 1 tile away from previous event
        int distanceX = Math.abs(gp.player.worldX - previousEventX);
        int distanceY = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(distanceX, distanceY);
        if (distance > gp.TILE_SIZE) {
            canTouchEvent = true;
        }

        if (canTouchEvent) {
            if (hit(0, 23, 12, "up")) {healingPool(gp.GS_DIALOGUE);}
            else if (hit(0, 10, 39, "any")) {
                teleport(1, 12, 13);
                gp.soundEffect(16);
            }
            else if (hit(1, 12, 13, "any")) {
                teleport(0, 10, 39);
                gp.soundEffect(16);
            }
            else if (hit(1, 12, 9, "up")) {speak(gp.npc[1][0]);}
        }

    }

    public boolean hit(int map, int col, int row, String reqDirection) {
        boolean hit = false;

        if (map == gp.currentMap) {
            gp.player.rect.x = gp.player.worldX + gp.player.rect.x;
            gp.player.rect.y = gp.player.worldY + gp.player.rect.y;
            eventRect[map][col][row].x = col*gp.TILE_SIZE + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row*gp.TILE_SIZE + eventRect[map][col][row].y;

            if (gp.player.rect.intersects(eventRect[map][col][row]) && !eventRect[map][col][row].eventDone) {
                if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                    hit = true;
                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                }
            }
            gp.player.rect.x = gp.player.default_rectX;
            gp.player.rect.y = gp.player.default_rectY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }

        return hit;
    }

    public void damagePit(int gameState) {
        gp.gameState = gameState;
        gp.soundEffect(6);
        gp.ui.currentDialogue = "You fall into a pit. \n You take damage.";
        gp.player.life--;

        canTouchEvent = false;
    }

    public void healingPool(int gameState) {
        gp.gameState = gameState;
        gp.player.attackCancelled = true;
        gp.soundEffect(11);
        gp.ui.currentDialogue = "You drink the water. \nYour health and mana has recovered.\n" + "(Your progress has been saved!)";
        gp.player.life = gp.player.maxLife;
        gp.player.mana = gp.player.maxMana;
        gp.oHandler.setMonster();
        gp.saveLoad.save();

        canTouchEvent = false;
    }

    public void teleport(int map, int col, int row) {
        gp.gameState = gp.GS_TRANSITION;
        tempMap = map;
        tempCol = col;
        tempRow = row;
        canTouchEvent = false;
    }

    public void speak(Entity entity) {
        if (gp.kHandler.enterPressed) {
            gp.gameState = gp.GS_DIALOGUE;
            gp.player.attackCancelled = true;
            entity.speak();
        }
    }

}