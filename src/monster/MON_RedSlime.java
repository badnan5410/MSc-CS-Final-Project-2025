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
        defaultSpeed = 3;
        speed = defaultSpeed;
        maxLife = 12;
        life = maxLife;
        defaultAttack = 3;
        defaultDefense = 2;
        attack = defaultAttack;
        defense = defaultDefense;
        exp = 5;
        knockBackPower = 2;
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
        monsterBoost(3); // Stronger scaling than green slime

        if (onPath) {

            // More likely to keep chasing (25% chance to give up)
            checkIfPlayerOutOfAggro(gp.player, 14, 4);

            // Uses A* pathfinding to track the player
            searchPath(getEndCol(gp.player), getEndRow(gp.player));

            // Shoots more frequently (25% chance per frame, 30-frame cooldown)
            checkIfMonsterShoot(4, 30);
        } else {

            // 25% chance to enter aggro if player is nearby
            checkIfPlayerInAggro(gp.player, 8, 4);

            // Twitchier when wandering (changes direction every 35 frames)
            getRandomDirection(35);
        }
    }

    public void damageReaction() {
        movementCounter = 0;
        onPath = true;
    }

    public void checkDrop() {
        int i = new Random().nextInt(1000) + 1; // 1–1000 pool

        if (i <= 300) {dropItem(new Coin_Copper(gp));}        // 30%
        else if (i <= 550) {dropItem(new Coin_Silver(gp));}   // 25%
        else if (i <= 650) {dropItem(new Coin_Gold(gp));}     // 10%
        else if (i <= 750) {dropItem(new Heart(gp));}         // 10%
        else if (i <= 830) {dropItem(new Mana(gp));}          // 8%
        else if (i <= 900) {dropItem(new Potion_Red(gp));}    // 7%
        else if (i <= 950) {dropItem(new Potion_Blue(gp));}   // 5%
        else if (i <= 970) {dropItem(new Tent(gp));}          // 2%
        else if (i == 971 || i == 972) {dropItem(new Lantern(gp));} // 0.2%
        else if (i == 973) {dropItem(new Iron_Shield(gp));}   // 0.1%
        else if (i == 974) {dropItem(new Iron_Sword(gp));}    // 0.1%
        else if (i == 975) {dropItem(new Iron_Axe(gp));}      // 0.1%
        else if (i >= 976 && i <= 1000) {dropItem(new Key(gp));} // 2.5%
    }
}