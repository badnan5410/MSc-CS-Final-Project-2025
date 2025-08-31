package monster;

import entity.Entity;
import main.GamePanel;
import object.Coin;
import object.Heart;
import object.Mana;

import java.util.Random;

public class MON_Orc extends Entity {
    GamePanel gp;

    public MON_Orc(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "Orc";
        type = TYPE_MONSTER;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 20;
        life = maxLife;
        defaultAttack = 8;
        defaultDefense = 10;
        attack = defaultAttack;
        defense = defaultDefense;
        resistance = 5;
        exp = 10;
        rect.x = 4;
        rect.y = 4;
        rect.width = 40;
        rect.height = 44;
        default_rectX = rect.x;
        default_rectY = rect.y;
        attackArea.width = gp.TILE_SIZE;
        attackArea.height = gp.TILE_SIZE;
        motion1_duration = 40;
        motion2_duration = 85;
        getImage();
        getAttackImage();
    }

    public void getImage() {
        up1 = setup("/monster/orc/walking/up_1");
        up2 = setup("/monster/orc/walking/up_2");
        down1 = setup("/monster/orc/walking/down_1");
        down2 = setup("/monster/orc/walking/down_2");
        right1 = setup("/monster/orc/walking/right_1");
        right2 = setup("/monster/orc/walking/right_2");
        left1 = setup("/monster/orc/walking/left_1");
        left2 = setup("/monster/orc/walking/left_2");
    }

    public void getAttackImage() {
        atk_up1 = setup("/monster/orc/attacking/up_1", gp.TILE_SIZE, gp.TILE_SIZE*2);
        atk_up2 = setup("/monster/orc/attacking/up_2", gp.TILE_SIZE, gp.TILE_SIZE*2);
        atk_down1 = setup("/monster/orc/attacking/down_1", gp.TILE_SIZE, gp.TILE_SIZE*2);
        atk_down2 = setup("/monster/orc/attacking/down_2", gp.TILE_SIZE, gp.TILE_SIZE*2);
        atk_right1 = setup("/monster/orc/attacking/right_1", gp.TILE_SIZE*2, gp.TILE_SIZE);
        atk_right2 = setup("/monster/orc/attacking/right_2", gp.TILE_SIZE*2, gp.TILE_SIZE);
        atk_left1 = setup("/monster/orc/attacking/left_1", gp.TILE_SIZE*2, gp.TILE_SIZE);
        atk_left2 = setup("/monster/orc/attacking/left_2", gp.TILE_SIZE*2, gp.TILE_SIZE);
    }

    public void setAction() {
        monsterBoost(4);

        if (onPath) {

            // check if it stops chasing
            checkIfPlayerOutOfAggro(gp.player, 10, 100);

            // search the direction to go
            searchPath(getEndCol(gp.player), getEndRow(gp.player));
        }
        else {

            // check if it starts chasing
            checkIfPlayerInAggro(gp.player, 5, 100);

            // get a random direction if it's not onPath
            getRandomDirection();
        }

        // check if it attacks
        if (!attacking) {
            checkIfMonsterAttack(30, gp.TILE_SIZE*4, gp.TILE_SIZE);
        }
    }

    public void damageReaction() {
        movementCounter = 0;
        onPath = true;
    }

    public void checkDrop() {
        int i = new Random().nextInt(100) + 1; // 1–100

        if (i <= 80) {dropItem(new Coin(gp));}
        else if (i <= 90) {dropItem(new Mana(gp));}
        else if (i < 100) {dropItem(new Heart(gp));}
    }
}

