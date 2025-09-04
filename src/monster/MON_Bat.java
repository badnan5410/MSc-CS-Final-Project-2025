package monster;

import entity.Entity;
import main.GamePanel;
import object.*;

import java.util.Random;

public class MON_Bat extends Entity {
    GamePanel gp;

    public MON_Bat(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "Bat";
        type = TYPE_MONSTER;
        defaultSpeed = 6;
        speed = defaultSpeed;
        maxLife = 6;
        life = maxLife;
        defaultAttack = 3;
        defaultDefense = 0;
        attack = defaultAttack;
        defense = defaultDefense;
        exp = 3;
        knockBackPower = 2;
        rect.x = 3;
        rect.y = 15;
        rect.width = 42;
        rect.height = 21;
        default_rectX = rect.x;
        default_rectY = rect.y;
        getImage();
    }

    public void getImage() {
        up1 = setup("/monster/bat/down_1");
        up2 = setup("/monster/bat/down_2");
        down1 = setup("/monster/bat/down_1");
        down2 = setup("/monster/bat/down_2");
        right1 = setup("/monster/bat/down_1");
        right2 = setup("/monster/bat/down_2");
        left1 = setup("/monster/bat/down_1");
        left2 = setup("/monster/bat/down_2");
    }

    public void setAction() {
        monsterBoost(1);

        if (onPath) {
            checkIfPlayerOutOfAggro(gp.player, 8, 100);
            moveTowardsThePlayer(20);
        }
        else {
            checkIfPlayerInAggro(gp.player, 4, 50);

            getRandomDirection(40);
        }

        if (!attacking) {
            checkIfMonsterAttack(20, gp.TILE_SIZE * 3, gp.TILE_SIZE * 3);
        }
    }

    public void damageReaction() {
        movementCounter = 0;
        onPath = true;
    }

    public void checkDrop() {
        int i = new Random().nextInt(1000) + 1; // 1–1000 pool

        if (i <= 400) {dropItem(new Coin_Copper(gp));}        // 40%
        else if (i <= 600) {dropItem(new Coin_Silver(gp));}   // 20%
        else if (i <= 750) {dropItem(new Heart(gp));}         // 15%
        else if (i <= 850) {dropItem(new Mana(gp));}          // 10%
        else if (i <= 930) {dropItem(new Potion_Red(gp));}    // 8%
        else if (i <= 980) {dropItem(new Potion_Blue(gp));}   // 5%
        else if (i <= 995) {dropItem(new Tent(gp));}          // 1.5%
        else if (i == 996) {dropItem(new Lantern(gp));}       // 0.1%
        else if (i == 997) {dropItem(new Iron_Shield(gp));}   // 0.1%
        else if (i == 998) {dropItem(new Iron_Sword(gp));}    // 0.1%
        else if (i == 999) {dropItem(new Iron_Axe(gp));}      // 0.1%
        else if (i == 1000) {dropItem(new Key(gp));}          // 0.1% jackpot
    }

}
