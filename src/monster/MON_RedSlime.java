package monster;

import entity.Entity;
import main.GamePanel;
import object.*;

import java.util.Random;

/**
 * Red Slime enemy.
 *
 * Stronger and more aggressive than the Green Slime:
 * - Higher speed, life, and knockback.
 * - Tends to aggro from farther away and wander more nervously.
 * - Frequently fires red slime projectiles while chasing.
 * - Attack and defense scale with time-of-day via monsterBoost(int).
 */
public class MON_RedSlime extends Entity {
    GamePanel gp;

    /**
     * Constructs a Red Slime with default stats, hit box, and projectile.
     *
     * @param gp game context
     */
    public MON_RedSlime(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "Red Slime";
        type = TYPE_MONSTER;
        defaultSpeed = 2;
        speed = defaultSpeed;
        maxLife = 12;
        life = maxLife;
        defaultAttack = 4;
        defaultDefense = 3;
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

    /**
     * Loads walk-cycle frames.
     * Reuses the "down" frames for all directions.
     */
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

    /**
     * AI tick.
     *
     * When on a path:
     * - Less likely to give up the chase.
     * - Pathfinds toward the player.
     * - Frequently shoots with a short cooldown.
     *
     * When wandering:
     * - 25% chance to aggro if the player is within 8 tiles.
     * - Changes direction more often than the green slime.
     */
    public void setAction() {
        monsterBoost(3); // Stronger scaling than green slime

        if (onPath) {

            // More likely to keep chasing
            checkIfPlayerOutOfAggro(gp.player, 6, 2);

            // Uses A* pathfinding to track the player
            searchPath(getEndCol(gp.player), getEndRow(gp.player));

            // Shoots more frequently (25% chance per frame, 30-frame cooldown)
            checkIfMonsterShoot(70, 30);
        } else {

            // 25% chance to enter aggro if player is nearby
            checkIfPlayerInAggro(gp.player, 3, 1);

            // Twitchier when wandering (changes direction every 35 frames)
            getRandomDirection(35);
        }
    }

    /**
     * On damage, immediately switches to pathing toward the player.
     */
    public void damageReaction() {
        movementCounter = 0;
        onPath = true;
    }

    /**
     * Randomized drop table based on a 1–1000 roll.
     *
     * Rates:
     * - 1–300   : Copper Coin (30.0%)
     * - 301–550 : Silver Coin (25.0%)
     * - 551–650 : Gold Coin (10.0%)
     * - 651–750 : Heart (10.0%)
     * - 751–830 : Mana (8.0%)
     * - 831–900 : Red Potion (7.0%)
     * - 901–950 : Blue Potion (5.0%)
     * - 951–970 : Tent (2.0%)
     * - 971–972 : Lantern (0.2%)
     * - 973     : Iron Shield (0.1%)
     * - 974     : Iron Sword (0.1%)
     * - 975     : Iron Axe (0.1%)
     * - 976–1000: Key (2.5%)
     */
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