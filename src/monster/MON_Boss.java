package monster;

import entity.Entity;
import main.GamePanel;
import object.Coin;
import object.Heart;
import object.Mana;

import java.util.Random;

public class MON_Boss extends Entity {
    GamePanel gp;
    public static final String monName = "Umberos, Skeleton King";

    public MON_Boss(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = monName;
        type = TYPE_MONSTER;
        isBossMonster = true;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 50;
        life = maxLife;
        defaultAttack = 12;
        defaultDefense = 2;
        attack = defaultAttack;
        defense = defaultDefense;
        exp = 500;
        knockBackPower = 5;

        int size = gp.TILE_SIZE*5;
        rect.x = gp.TILE_SIZE;
        rect.y = gp.TILE_SIZE;
        rect.width = size - (gp.TILE_SIZE*2);
        rect.height = size - gp.TILE_SIZE;
        default_rectX = rect.x;
        default_rectY = rect.y;
        attackArea.width = 170;
        attackArea.height = 170;
        motion1_duration = 25;
        motion2_duration = 50;

        getImage();
        getAttackImage();
    }

    public void getImage() {
        int i = 5; // scale

        if (!enraged) {
            up1 = setup("/monster/boss/walking/up_1", gp.TILE_SIZE*i, gp.TILE_SIZE*i);
            up2 = setup("/monster/boss/walking/up_2", gp.TILE_SIZE*i, gp.TILE_SIZE*i);
            down1 = setup("/monster/boss/walking/down_1", gp.TILE_SIZE*i, gp.TILE_SIZE*i);
            down2 = setup("/monster/boss/walking/down_2", gp.TILE_SIZE*i, gp.TILE_SIZE*i);
            right1 = setup("/monster/boss/walking/right_1", gp.TILE_SIZE*i, gp.TILE_SIZE*i);
            right2 = setup("/monster/boss/walking/right_2", gp.TILE_SIZE*i, gp.TILE_SIZE*i);
            left1 = setup("/monster/boss/walking/left_1", gp.TILE_SIZE*i, gp.TILE_SIZE*i);
            left2 = setup("/monster/boss/walking/left_2", gp.TILE_SIZE*i, gp.TILE_SIZE*i);
        }
        else {
            up1 = setup("/monster/boss/walking2/up_1", gp.TILE_SIZE*i, gp.TILE_SIZE*i);
            up2 = setup("/monster/boss/walking2/up_2", gp.TILE_SIZE*i, gp.TILE_SIZE*i);
            down1 = setup("/monster/boss/walking2/down_1", gp.TILE_SIZE*i, gp.TILE_SIZE*i);
            down2 = setup("/monster/boss/walking2/down_2", gp.TILE_SIZE*i, gp.TILE_SIZE*i);
            right1 = setup("/monster/boss/walking2/right_1", gp.TILE_SIZE*i, gp.TILE_SIZE*i);
            right2 = setup("/monster/boss/walking2/right_2", gp.TILE_SIZE*i, gp.TILE_SIZE*i);
            left1 = setup("/monster/boss/walking2/left_1", gp.TILE_SIZE*i, gp.TILE_SIZE*i);
            left2 = setup("/monster/boss/walking2/left_2", gp.TILE_SIZE*i, gp.TILE_SIZE*i);
        }

    }

    public void getAttackImage() {
        int i = 5; // scale

        if (!enraged) {
            atk_up1 = setup("/monster/boss/attacking/up_1", gp.TILE_SIZE*i, gp.TILE_SIZE*2*i);
            atk_up2 = setup("/monster/boss/attacking/up_2", gp.TILE_SIZE*i, gp.TILE_SIZE*2*i);
            atk_down1 = setup("/monster/boss/attacking/down_1", gp.TILE_SIZE*i, gp.TILE_SIZE*2*i);
            atk_down2 = setup("/monster/boss/attacking/down_2", gp.TILE_SIZE*i, gp.TILE_SIZE*2*i);
            atk_right1 = setup("/monster/boss/attacking/right_1", gp.TILE_SIZE*2*i, gp.TILE_SIZE*i);
            atk_right2 = setup("/monster/boss/attacking/right_2", gp.TILE_SIZE*2*i, gp.TILE_SIZE*i);
            atk_left1 = setup("/monster/boss/attacking/left_1", gp.TILE_SIZE*2*i, gp.TILE_SIZE*i);
            atk_left2 = setup("/monster/boss/attacking/left_2", gp.TILE_SIZE*2*i, gp.TILE_SIZE*i);
        }
        else {
            atk_up1 = setup("/monster/boss/attacking2/up_1", gp.TILE_SIZE*i, gp.TILE_SIZE*2*i);
            atk_up2 = setup("/monster/boss/attacking2/up_2", gp.TILE_SIZE*i, gp.TILE_SIZE*2*i);
            atk_down1 = setup("/monster/boss/attacking2/down_1", gp.TILE_SIZE*i, gp.TILE_SIZE*2*i);
            atk_down2 = setup("/monster/boss/attacking2/down_2", gp.TILE_SIZE*i, gp.TILE_SIZE*2*i);
            atk_right1 = setup("/monster/boss/attacking2/right_1", gp.TILE_SIZE*2*i, gp.TILE_SIZE*i);
            atk_right2 = setup("/monster/boss/attacking2/right_2", gp.TILE_SIZE*2*i, gp.TILE_SIZE*i);
            atk_left1 = setup("/monster/boss/attacking2/left_1", gp.TILE_SIZE*2*i, gp.TILE_SIZE*i);
            atk_left2 = setup("/monster/boss/attacking2/left_2", gp.TILE_SIZE*2*i, gp.TILE_SIZE*i);
        }

    }

    public void setAction() {

        if (!enraged && life < maxLife/2) {
            enraged = true;
            getImage();
            getAttackImage();
            defaultSpeed++;
            speed = defaultSpeed;
            attack += 2;
            defense += 2;
        }

        if (getTileDistance(gp.player) < 10) {
            moveTowardsThePlayer(60);
        }
        else {
            getRandomDirection(100);
        }

        // check if it attacks
        if (!attacking) {
            checkIfMonsterAttack(60, gp.TILE_SIZE*7, gp.TILE_SIZE*5);
        }
    }

    public void damageReaction() {
        movementCounter = 0;
    }

    public void checkDrop() {
        int i = new Random().nextInt(100) + 1; // 1–100

        if (i <= 80) {dropItem(new Coin(gp));}
        else if (i <= 90) {dropItem(new Mana(gp));}
        else if (i < 100) {dropItem(new Heart(gp));}
    }
}
