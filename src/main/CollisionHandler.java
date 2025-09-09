package main;

import entity.Entity;

/**
 * Centralised collision checks for tiles, objects, entities, and the player.
 * Keeps all prediction.overlap logic here so Entity.update() can remain simple.
 */
public class CollisionHandler {
    GamePanel gp;

    /**
     * Hold a reference to the game so it can read tiles, entities, and sizes.
     *
     * @param gp
     */
    public CollisionHandler(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Predictive tile collision (AABB vs. solid tiles).
     * It projects the entity's hitbox one "speed" step in its movement direction, sample the two tiles it would touch, and set {@code entity.checkCollision} if bloacked.
     *
     * @param entity
     */
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

        // use knockback direction if active so reactions still collide properly
        String direction = entity.direction;

        if (entity.knockback) {
            direction = entity.knockBackDirection;
        }

        switch (direction) {
            case "up":
                topRow = (top_worldY - entity.speed)/ gp.TILE_SIZE;
                tileNum1 = gp.tManager.mapArray[gp.currentMap][leftCol][topRow];
                tileNum2 = gp.tManager.mapArray[gp.currentMap][rightCol][topRow];

                if (gp.tManager.tile[tileNum1].collision || gp.tManager.tile[tileNum2].collision) {
                    entity.checkCollision = true;
                }

                break;
            case "down":
                bottomRow = (bottom_worldY + entity.speed)/ gp.TILE_SIZE;
                tileNum1 = gp.tManager.mapArray[gp.currentMap][leftCol][bottomRow];
                tileNum2 = gp.tManager.mapArray[gp.currentMap][rightCol][bottomRow];

                if (gp.tManager.tile[tileNum1].collision || gp.tManager.tile[tileNum2].collision) {
                    entity.checkCollision = true;
                }

                break;
            case "right":
                rightCol = (right_worldX + entity.speed)/ gp.TILE_SIZE;
                tileNum1 = gp.tManager.mapArray[gp.currentMap][rightCol][topRow];
                tileNum2 = gp.tManager.mapArray[gp.currentMap][rightCol][bottomRow];

                if (gp.tManager.tile[tileNum1].collision || gp.tManager.tile[tileNum2].collision) {
                    entity.checkCollision = true;
                }

                break;
            case "left":
                leftCol = (left_worldX - entity.speed)/ gp.TILE_SIZE;
                tileNum1 = gp.tManager.mapArray[gp.currentMap][leftCol][topRow];
                tileNum2 = gp.tManager.mapArray[gp.currentMap][leftCol][bottomRow];

                if (gp.tManager.tile[tileNum1].collision || gp.tManager.tile[tileNum2].collision) {
                    entity.checkCollision = true;
                }
                
                break;
        }
    }

    /**
     * Predictive collision against map objects (chests, doors, pickups).
     * I return teh collided object's index for interaction when the mover is the player.
     *
     * @param entity the mover being projected.
     * @param isPlayer true if I should return the index for interactions.
     * @return object index or -1 if none.
     */
    public int checkObject(Entity entity, boolean isPlayer) {
        int index = -1;

        // iterate all object slots on the current map
        for (int i = 0; i < gp.obj[1].length; i++) {

            if (gp.obj[gp.currentMap][i] != null) {
                entity.rect.x = entity.worldX + entity.rect.x;
                entity.rect.y = entity.worldY + entity.rect.y;
                gp.obj[gp.currentMap][i].rect.x = gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].rect.x;
                gp.obj[gp.currentMap][i].rect.y = gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].rect.y;

                switch (entity.direction) {
                    case "up": entity.rect.y -= entity.speed; break;
                    case "down": entity.rect.y += entity.speed; break;
                    case "right": entity.rect.x += entity.speed; break;
                    case "left": entity.rect.x -= entity.speed; break;
                }

                if (entity.rect.intersects(gp.obj[gp.currentMap][i].rect)) {

                    if (gp.obj[gp.currentMap][i].collision) {
                        entity.checkCollision = true;
                    }

                    if (isPlayer) {
                        index = i;
                    }
                }

                entity.rect.x = entity.default_rectX;
                entity.rect.y = entity.default_rectY;
                gp.obj[gp.currentMap][i].rect.x = gp.obj[gp.currentMap][i].default_rectX;
                gp.obj[gp.currentMap][i].rect.y = gp.obj[gp.currentMap][i].default_rectY;
            }
        }

        return index;
    }

    /**
     * Predictive collision against another entity type (NPCs, monsters, projectiles).
     * It cans the target array, project the mover by speed, and return the first hit index.
     *
     * @param entity
     * @param target
     * @return
     */
    public int checkEntity(Entity entity, Entity[][] target) {
        int index = -1;
        String direction = entity.direction;

        if (entity.knockback) {
            direction = entity.knockBackDirection;
        }

        // iterate all target slots on the current map
        for (int i = 0; i < target[1].length; i++) {

            if (target[gp.currentMap][i] != null) {

                entity.rect.x = entity.worldX + entity.rect.x;
                entity.rect.y = entity.worldY + entity.rect.y;
                target[gp.currentMap][i].rect.x = target[gp.currentMap][i].worldX + target[gp.currentMap][i].rect.x;
                target[gp.currentMap][i].rect.y = target[gp.currentMap][i].worldY + target[gp.currentMap][i].rect.y;

                switch (direction) {
                    case "up": entity.rect.y -= entity.speed; break;
                    case "down": entity.rect.y += entity.speed; break;
                    case "right": entity.rect.x += entity.speed; break;
                    case "left": entity.rect.x -= entity.speed; break;
                }

                if (entity.rect.intersects(target[gp.currentMap][i].rect)) {

                    if (target[gp.currentMap][i] != entity) {
                        entity.checkCollision = true;
                        index = i;
                    }
                }

                entity.rect.x = entity.default_rectX;
                entity.rect.y = entity.default_rectY;
                target[gp.currentMap][i].rect.x = target[gp.currentMap][i].default_rectX;
                target[gp.currentMap][i].rect.y = target[gp.currentMap][i].default_rectY;
            }
        }

        return index;
    }

    /**
     * Predictive test for "did this entity touch the player".
     * I project the entity toward its direction and flag both a collision and a boolean result for callers that need immediate player contact.
     *
     * @param entity
     * @return
     */
    public boolean checkPlayer(Entity entity) {
        boolean touchPlayer = false;

        // world-space, project, test
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

        // restore local-space hitboxes
        entity.rect.x = entity.default_rectX;
        entity.rect.y = entity.default_rectY;
        gp.player.rect.x = gp.player.default_rectX;
        gp.player.rect.y = gp.player.default_rectY;
        return touchPlayer;
    }
}
