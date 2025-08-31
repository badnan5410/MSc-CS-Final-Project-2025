package monster;

import entity.Entity;
import main.GamePanel;
import object.*;

import java.util.Random;

public class MON_GreenSlime extends Entity {
    GamePanel gp;

    public MON_GreenSlime(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Green Slime";
        type = TYPE_MONSTER;
        defaultSpeed = 2;
        speed = defaultSpeed;
        maxLife = 8;
        life = maxLife;
        defaultAttack = 2;
        defaultDefense = 1;
        attack = defaultAttack;
        defense = defaultDefense;
        resistance = 1;
        exp = 2;
        projectile = new SlimeBall(gp);

        rect.x = 3;
        rect.y = 18;
        rect.width = 42;
        rect.height = 30;
        default_rectX = rect.x;
        default_rectY = rect.y;

        getImage();
    }

    public void getImage() {
        up1 = setup("/monster/green_slime/down_1");
        up2 = setup("/monster/green_slime/down_2");
        down1 = setup("/monster/green_slime/down_1");
        down2 = setup("/monster/green_slime/down_2");
        right1 = setup("/monster/green_slime/down_1");
        right2 = setup("/monster/green_slime/down_2");
        left1 = setup("/monster/green_slime/down_1");
        left2 = setup("/monster/green_slime/down_2");
    }

    public void setAction() {
        monsterBoost(2);

        if (onPath) {

            // check if it stops chasing
            checkIfPlayerOutOfAggro(gp.player, 15, 100);

            // search the direction to go
            searchPath(getEndCol(gp.player), getEndRow(gp.player));

            // check if it shoots a projectile
            checkIfMonsterShoot(200, 30);
        }
        else {

            // check if it starts chasing
            checkIfPlayerInAggro(gp.player, 5, 100);

            // get a random direction if its not onPath
            getRandomDirection();
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
        else if (i <= 100) {dropItem(new Heart(gp));}
    }
}
