package main;

import java.awt.*;

public class EventHandler {
    GamePanel gp;
    Rectangle eventRect;
    int eventRectDefaultX, eventRectDefaultY;

    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventRect = new Rectangle(23, 23, 2, 2);
        eventRectDefaultX = eventRect.x;
        eventRectDefaultY = eventRect.y;
    }

    public void checkEvent() {
        //if (hit(27, 16, "right")) {damagePit(gp.GS_DIALOGUE);}
        if (hit(27, 16, "right")) {teleport(gp.GS_DIALOGUE);}
        if (hit(23, 12, "up")) {healingPool(gp.GS_DIALOGUE);}
    }

    public boolean hit(int eventCol, int eventRow, String reqDirection) {
        boolean hit = false;
        gp.player.rect.x = gp.player.worldX + gp.player.rect.x;
        gp.player.rect.y = gp.player.worldY + gp.player.rect.y;
        eventRect.x = eventCol*gp.TILE_SIZE + eventRect.x;
        eventRect.y = eventRow*gp.TILE_SIZE + eventRect.y;

        if (gp.player.rect.intersects(eventRect)) {
            if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                hit = true;
            }
        }
        gp.player.rect.x = gp.player.default_rectX;
        gp.player.rect.y = gp.player.default_rectY;
        eventRect.x = eventRectDefaultX;
        eventRect.y = eventRectDefaultY;

        return hit;
    }

    public void teleport(int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You have teleported!";
        gp.player.worldX = gp.TILE_SIZE*37;
        gp.player.worldY = gp.TILE_SIZE*10;
        gp.player.direction = "down";
    }

    public void damagePit(int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You fall into a pit. \n You take damage.";
        gp.player.life --;
    }

    public void healingPool(int gameState) {
        if (gp.kHandler.enterPressed) {
            gp.gameState = gameState;
            gp.ui.currentDialogue = "You drink the water. \nYour health has recovered.";
            gp.player.life = gp.player.maxLife;
        }
    }

}
