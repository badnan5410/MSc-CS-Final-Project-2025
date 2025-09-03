package monster;

import entity.Entity;
import main.GamePanel;
import object.*;

import java.util.Random;

public class MON_RedSlime extends Entity {
    GamePanel gp;

    public MON_RedSlime(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "Red Slime";
        type = TYPE_MONSTER;
        defaultSpeed = 2;
        speed = defaultSpeed;
        maxLife = 12;
        life = maxLife;
        defaultAttack = 6;
        defaultDefense = 4;
        attack = defaultAttack;
        defense = defaultDefense;
        exp = 5;
        knockBackPower = 4;
        projectile = new Slimeball_Red(gp);
        rect.x = 3;
        rect.y = 18;
        rect.width = 42;
        rect.height = 30;
        default_rectX = rect.x;
        default_rectY = rect.y;
        getImage();
    }

    public void getImage() {
        up1 = setup("/monster/red_slime/down_1");
        up2 = setup("/monster/red_slime/down_2");
        down1 = setup("/monster/red_slime/down_1");
        down2 = setup("/monster/red_slime/down_2");
        right1 = setup("/monster/red_slime/down_1");
        right2 = setup("/monster/red_slime/down_2");
        left1 = setup("/monster/red_slime/down_1");
        left2 = setup("/monster/red_slime/down_2");
    }

    public void setAction() {
        monsterBoost(3);

        if (onPath) {

            // check if it stops chasing
            checkIfPlayerOutOfAggro(gp.player, 20, 100);

            // search the direction to go
            searchPath(getEndCol(gp.player), getEndRow(gp.player));

            // check if it shoots a projectile
            checkIfMonsterShoot(150, 30);
        }
        else {

            // check if it starts chasing
            checkIfPlayerInAggro(gp.player, 10, 100);

            // get a random direction if its not onPath
            getRandomDirection(80);
        }
    }

    public void damageReaction() {
        movementCounter = 0;
        onPath = true;
    }

    public void checkDrop() {
        int i = new Random().nextInt(100) + 1; // 1–100

        if (i <= 60) {dropItem(new Coin(gp));}
        else if (i <= 70) {dropItem(new Mana(gp));}
        else if (i <= 80) {dropItem(new Heart(gp));}
        else if (i <= 90) {dropItem(new Tent(gp));}
        else if (i <= 100) {dropItem(new Potion_Red(gp));}
    }
}