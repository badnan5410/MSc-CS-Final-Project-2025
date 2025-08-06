package main;

import entity.Entity;

public class CollisionHandler {
    GamePanel gp;

    public CollisionHandler(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int left_worldX = entity.worldX + entity.rect.x;
        int right_worldX = entity.worldX + entity.rect.x + entity.rect.width;
        int top_worldY = entity.worldY + entity.rect.y;
        int bottom_worldY = entity.worldY + entity.rect.y + entity.rect.height;

        int leftCol = left_worldX/gp.TILE_SIZE;
        int rightCol = right_worldX/gp.TILE_SIZE;
        int topRow = top_worldY/gp.TILE_SIZE;
        int bottomRow = bottom_worldY/gp.TILE_SIZE;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                topRow = (top_worldY - entity.speed)/ gp.TILE_SIZE;
                tileNum1 = gp.tm.mapArray[leftCol][topRow];
                tileNum2 = gp.tm.mapArray[rightCol][topRow];

                if (gp.tm.tile[tileNum1].collision || gp.tm.tile[tileNum2].collision) {
                    entity.checkCollision = true;
                }
                break;
            case "down":
                bottomRow = (bottom_worldY + entity.speed)/ gp.TILE_SIZE;
                tileNum1 = gp.tm.mapArray[leftCol][bottomRow];
                tileNum2 = gp.tm.mapArray[rightCol][bottomRow];

                if (gp.tm.tile[tileNum1].collision || gp.tm.tile[tileNum2].collision) {
                    entity.checkCollision = true;
                }
                break;
            case "right":
                rightCol = (right_worldX + entity.speed)/ gp.TILE_SIZE;
                tileNum1 = gp.tm.mapArray[rightCol][topRow];
                tileNum2 = gp.tm.mapArray[rightCol][bottomRow];

                if (gp.tm.tile[tileNum1].collision || gp.tm.tile[tileNum2].collision) {
                    entity.checkCollision = true;
                }
                break;
            case "left":
                leftCol = (left_worldX - entity.speed)/ gp.TILE_SIZE;
                tileNum1 = gp.tm.mapArray[leftCol][topRow];
                tileNum2 = gp.tm.mapArray[leftCol][bottomRow];

                if (gp.tm.tile[tileNum1].collision || gp.tm.tile[tileNum2].collision) {
                    entity.checkCollision = true;
                }
                break;
        }
    }

    public int checkObject(Entity entity, boolean isPlayer) {
        int index = -1;

        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {

                entity.rect.x = entity.worldX + entity.rect.x;
                entity.rect.y = entity.worldY + entity.rect.y;

                gp.obj[i].rect.x = gp.obj[i].worldX + gp.obj[i].rect.x;
                gp.obj[i].rect.y = gp.obj[i].worldY + gp.obj[i].rect.y;

                switch (entity.direction) {
                    case "up": entity.rect.y -= entity.speed; break;
                    case "down": entity.rect.y += entity.speed; break;
                    case "right": entity.rect.x += entity.speed; break;
                    case "left": entity.rect.x -= entity.speed; break;
                }

                if (entity.rect.intersects(gp.obj[i].rect)) {
                    if (gp.obj[i].collision) {
                        entity.checkCollision = true;
                    }

                    if (isPlayer) {
                        index = i;
                    }
                }
                entity.rect.x = entity.default_rectX;
                entity.rect.y = entity.default_rectY;
                gp.obj[i].rect.x = gp.obj[i].default_rectX;
                gp.obj[i].rect.y = gp.obj[i].default_rectY;
            }

        }
        return index;
    }

    // Check NPC or Monster Collision
    public int checkEntity(Entity entity, Entity[] target) {
        int index = -1;

        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {

                entity.rect.x = entity.worldX + entity.rect.x;
                entity.rect.y = entity.worldY + entity.rect.y;

                target[i].rect.x = target[i].worldX + target[i].rect.x;
                target[i].rect.y = target[i].worldY + target[i].rect.y;

                switch (entity.direction) {
                    case "up": entity.rect.y -= entity.speed; break;
                    case "down": entity.rect.y += entity.speed; break;
                    case "right": entity.rect.x += entity.speed; break;
                    case "left": entity.rect.x -= entity.speed; break;
                }

                if (entity.rect.intersects(target[i].rect)) {
                    if (target[i] != entity) {
                        entity.checkCollision = true;
                        index = i;
                    }
                }
                entity.rect.x = entity.default_rectX;
                entity.rect.y = entity.default_rectY;
                target[i].rect.x = target[i].default_rectX;
                target[i].rect.y = target[i].default_rectY;
            }

        }
        return index;
    }

    // Check player to NPC Collision
    public boolean checkPlayer(Entity entity) {
        boolean touchPlayer = false;

        entity.rect.x = entity.worldX + entity.rect.x;
        entity.rect.y = entity.worldY + entity.rect.y;

        gp.player.rect.x = gp.player.worldX + gp.player.rect.x;
        gp.player.rect.y = gp.player.worldY + gp.player.rect.y;

        switch (entity.direction) {
            case "up": entity.rect.y -= entity.speed; break;
            case "down": entity.rect.y += entity.speed; break;
            case "right": entity.rect.x += entity.speed; break;
            case "left": entity.rect.x -= entity.speed; break;
        }

        if (entity.rect.intersects(gp.player.rect)) {
            entity.checkCollision = true;
            touchPlayer = true;
        }

        entity.rect.x = entity.default_rectX;
        entity.rect.y = entity.default_rectY;
        gp.player.rect.x = gp.player.default_rectX;
        gp.player.rect.y = gp.player.default_rectY;

        return touchPlayer;
    }
}
