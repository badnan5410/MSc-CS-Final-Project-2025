package main;

import object.*;

public class ObjectHandler {
    GamePanel gamePanel;

    public ObjectHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObject() {
        gamePanel.parentObject[0] = new Key();
        gamePanel.parentObject[0].worldX = 20 * gamePanel.TRUE_TILE_SIZE;
        gamePanel.parentObject[0].worldY = 7 * gamePanel.TRUE_TILE_SIZE;

        gamePanel.parentObject[1] = new Key();
        gamePanel.parentObject[1].worldX = 13 * gamePanel.TRUE_TILE_SIZE;
        gamePanel.parentObject[1].worldY = 41 * gamePanel.TRUE_TILE_SIZE;

        gamePanel.parentObject[2] = new Key();
        gamePanel.parentObject[2].worldX = 37 * gamePanel.TRUE_TILE_SIZE;
        gamePanel.parentObject[2].worldY = 9 * gamePanel.TRUE_TILE_SIZE;

        gamePanel.parentObject[3] = new Door();
        gamePanel.parentObject[3].worldX = 10 * gamePanel.TRUE_TILE_SIZE;
        gamePanel.parentObject[3].worldY = 11 * gamePanel.TRUE_TILE_SIZE;

        gamePanel.parentObject[4] = new Door();
        gamePanel.parentObject[4].worldX = 8 * gamePanel.TRUE_TILE_SIZE;
        gamePanel.parentObject[4].worldY = 28 * gamePanel.TRUE_TILE_SIZE;

        gamePanel.parentObject[5] = new Door();
        gamePanel.parentObject[5].worldX = 12 * gamePanel.TRUE_TILE_SIZE;
        gamePanel.parentObject[5].worldY = 22 * gamePanel.TRUE_TILE_SIZE;

        gamePanel.parentObject[6] = new Chest();
        gamePanel.parentObject[6].worldX = 10 * gamePanel.TRUE_TILE_SIZE;
        gamePanel.parentObject[6].worldY = 7 * gamePanel.TRUE_TILE_SIZE;

        gamePanel.parentObject[7] = new Boots();
        gamePanel.parentObject[7].worldX = 35 * gamePanel.TRUE_TILE_SIZE;
        gamePanel.parentObject[7].worldY = 41 * gamePanel.TRUE_TILE_SIZE;

    }
}
