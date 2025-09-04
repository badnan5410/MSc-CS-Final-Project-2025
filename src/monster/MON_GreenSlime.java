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
        exp = 3;
        knockBackPower = 1;
        projectile = new Slimeball_Green(gp);
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

            // much less likely to give up chase (25% chance)
            checkIfPlayerOutOfAggro(gp.player, 12, 4);

            // pathfinding towards player
            searchPath(getEndCol(gp.player), getEndRow(gp.player));

            // shoots often (≈25% chance per frame, cooldown 30)
            checkIfMonsterShoot(4, 30);
        }
        else {

            // much more likely to aggro (≈25% chance per frame)
            checkIfPlayerInAggro(gp.player, 6, 4);

            // still moves randomly, but changes direction more often
            getRandomDirection(45);
        }
    }

    public void damageReaction() {
        movementCounter = 0;
        onPath = true;
    }

    public void checkDrop() {
        int i = new Random().nextInt(1000) + 1; // 1–1000 pool

        if (i <= 500) {dropItem(new Coin_Copper(gp));}          // 50%
        else if (i <= 650) {dropItem(new Heart(gp));}           // 15%
        else if (i <= 800) {dropItem(new Mana(gp));}            // 15%
        else if (i <= 900) {dropItem(new Potion_Red(gp));}      // 10%
        else if (i <= 950) {dropItem(new Potion_Blue(gp));}     // 5%
        else if (i <= 970) {dropItem(new Coin_Silver(gp));}     // 2%
        else if (i <= 980) {dropItem(new Key(gp));}             // 1%
        else if (i <= 985) {dropItem(new Lantern(gp));}         // 0.5%
        else if (i <= 990) {dropItem(new Tent(gp));}            // 0.5%
        else if (i <= 995) {dropItem(new Coin_Gold(gp));}       // 0.5%
        else if (i <= 998) {dropItem(new Iron_Shield(gp));}     // 0.3%
        else if (i <= 999) {dropItem(new Iron_Sword(gp));}      // 0.1%
        else if (i == 1000) {dropItem(new Iron_Axe(gp));}       // 0.1%
    }
}
