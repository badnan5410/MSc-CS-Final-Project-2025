package main;

import java.awt.*;

public class EventHandler {
    GamePanel gp;
    EventRect eventRect[][];

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventRect = new EventRect[gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];

        int col = 0;
        int row = 0;

        while (col < gp.MAX_WORLD_COL && row < gp.MAX_WORLD_ROW) {
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;
            if (col == gp.MAX_WORLD_COL) {
                col = 0;
                row++;
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
            if (hit(27, 16, "right")) {teleport(gp.GS_DIALOGUE);}
            if (hit(23, 19, "any")) {damagePit(gp.GS_DIALOGUE);}
            if (hit(23, 12, "up")) {healingPool(gp.GS_DIALOGUE);}
        }

    }

    public boolean hit(int col, int row, String reqDirection) {
        boolean hit = false;
        gp.player.rect.x = gp.player.worldX + gp.player.rect.x;
        gp.player.rect.y = gp.player.worldY + gp.player.rect.y;
        eventRect[col][row].x = col*gp.TILE_SIZE + eventRect[col][row].x;
        eventRect[col][row].y = row*gp.TILE_SIZE + eventRect[col][row].y;

        if (gp.player.rect.intersects(eventRect[col][row]) && !eventRect[col][row].eventDone) {
            if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                hit = true;
                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
        }
        gp.player.rect.x = gp.player.default_rectX;
        gp.player.rect.y = gp.player.default_rectY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        return hit;
    }

    public void teleport(int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You have teleported!";
        gp.player.worldX = gp.TILE_SIZE*37;
        gp.player.worldY = gp.TILE_SIZE*10;
    }

    public void damagePit(int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You fall into a pit. \n You take damage.";
        gp.player.life --;

        canTouchEvent = false;
    }

    public void healingPool(int gameState) {
        if (gp.kHandler.enterPressed) {
            gp.gameState = gameState;
            gp.ui.currentDialogue = "You drink the water. \nYour health has recovered.";
            gp.player.life = gp.player.maxLife;
        }
    }

}
