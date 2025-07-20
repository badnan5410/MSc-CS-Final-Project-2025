package main;

import entity.Entity;

public class CollisionHandler {
    GamePanel gamePanel;

    public CollisionHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkTile(Entity entity) {
        int left_worldX = entity.worldX + entity.rectangle.x;
        int right_worldX = entity.worldX + entity.rectangle.x + entity.rectangle.width;
        int top_worldY = entity.worldY + entity.rectangle.y;
        int bottom_worldY = entity.worldY + entity.rectangle.y + entity.rectangle.height;

        int leftCol = left_worldX/gamePanel.TRUE_TILE_SIZE;
        int rightCol = right_worldX/gamePanel.TRUE_TILE_SIZE;
        int topRow = top_worldY/gamePanel.TRUE_TILE_SIZE;
        int bottomRow = bottom_worldY/gamePanel.TRUE_TILE_SIZE;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                topRow = (top_worldY - entity.speed)/ gamePanel.TRUE_TILE_SIZE;
                tileNum1 = gamePanel.tileManager.mapArray[leftCol][topRow];
                tileNum2 = gamePanel.tileManager.mapArray[rightCol][topRow];

                if (gamePanel.tileManager.tile[tileNum1].collision || gamePanel.tileManager.tile[tileNum2].collision) {
                    entity.checkCollision = true;
                }
                break;
            case "down":
                bottomRow = (bottom_worldY + entity.speed)/ gamePanel.TRUE_TILE_SIZE;
                tileNum1 = gamePanel.tileManager.mapArray[leftCol][bottomRow];
                tileNum2 = gamePanel.tileManager.mapArray[rightCol][bottomRow];

                if (gamePanel.tileManager.tile[tileNum1].collision || gamePanel.tileManager.tile[tileNum2].collision) {
                    entity.checkCollision = true;
                }
                break;
            case "right":
                rightCol = (right_worldX + entity.speed)/ gamePanel.TRUE_TILE_SIZE;
                tileNum1 = gamePanel.tileManager.mapArray[rightCol][topRow];
                tileNum2 = gamePanel.tileManager.mapArray[rightCol][bottomRow];

                if (gamePanel.tileManager.tile[tileNum1].collision || gamePanel.tileManager.tile[tileNum2].collision) {
                    entity.checkCollision = true;
                }
                break;
            case "left":
                leftCol = (left_worldX - entity.speed)/ gamePanel.TRUE_TILE_SIZE;
                tileNum1 = gamePanel.tileManager.mapArray[leftCol][topRow];
                tileNum2 = gamePanel.tileManager.mapArray[leftCol][bottomRow];

                if (gamePanel.tileManager.tile[tileNum1].collision || gamePanel.tileManager.tile[tileNum2].collision) {
                    entity.checkCollision = true;
                }
                break;
        }
    }
}
