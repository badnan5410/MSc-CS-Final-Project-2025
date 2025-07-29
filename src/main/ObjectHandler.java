package main;

import object.*;

public class ObjectHandler {
    GamePanel gp;

    public ObjectHandler(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.obj[0] = new Key(gp);
        gp.obj[0].worldX = 20 * gp.TILE_SIZE;
        gp.obj[0].worldY = 7 * gp.TILE_SIZE;

        gp.obj[1] = new Key(gp);
        gp.obj[1].worldX = 8 * gp.TILE_SIZE;
        gp.obj[1].worldY = 36 * gp.TILE_SIZE;

        gp.obj[2] = new Key(gp);
        gp.obj[2].worldX = 37 * gp.TILE_SIZE;
        gp.obj[2].worldY = 9 * gp.TILE_SIZE;

        gp.obj[3] = new Door(gp);
        gp.obj[3].worldX = 10 * gp.TILE_SIZE;
        gp.obj[3].worldY = 12 * gp.TILE_SIZE;

        gp.obj[4] = new Door(gp);
        gp.obj[4].worldX = 8 * gp.TILE_SIZE;
        gp.obj[4].worldY = 28 * gp.TILE_SIZE;

        gp.obj[5] = new Door(gp);
        gp.obj[5].worldX = 12 * gp.TILE_SIZE;
        gp.obj[5].worldY = 23 * gp.TILE_SIZE;

        gp.obj[6] = new Chest(gp);
        gp.obj[6].worldX = 10 * gp.TILE_SIZE;
        gp.obj[6].worldY = 8 * gp.TILE_SIZE;

        gp.obj[7] = new Boots(gp);
        gp.obj[7].worldX = 35 * gp.TILE_SIZE;
        gp.obj[7].worldY = 41 * gp.TILE_SIZE;
    }
}

