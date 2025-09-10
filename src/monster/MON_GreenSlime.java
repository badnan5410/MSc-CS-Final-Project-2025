package monster;

import entity.Entity;
import main.GamePanel;
import object.*;

import java.util.Random;

/**
 * Green Slime enemy.
 *
 * Behavior:
 * - Wanders randomly until the player enters aggro range, then pathfinds toward the player.
 * - Occasionally fires a green slime projectile while chasing.
 * - Attack/defense scale with day/night via monsterBoost(int).
 *
 * Stats are initialized in the constructor, including hit box, EXP reward, and default projectile.
 */
public class MON_GreenSlime extends Entity {
    GamePanel gp;

    /**
     * Constructs a Green Slime with default stats and sprites.
     *
     * @param gp game context
     */
    public MON_GreenSlime(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "Green Slime";
        type = TYPE_MONSTER;
        defaultSpeed = 1;
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

    /**
     * Loads walk-cycle frames.
     * The slime reuses its "down" frames for all directions.
     */
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

    /**
     * AI tick:
     * - Applies time-of-day combat boost.
     * - If on a path: keeps chasing, may shoot (about 20% chance per tick, cooldown 60).
     * - If not on a path: has about a 100% chance to start chasing when within 1 tiles,
     *   otherwise wanders with frequent direction changes.
     */
    public void setAction() {
        monsterBoost(2);

        if (onPath) {

            // gives up the chase easily
            checkIfPlayerOutOfAggro(gp.player, 4, 2);

            // pathfinding towards player
            searchPath(getEndCol(gp.player), getEndRow(gp.player));

            // shoots often
            checkIfMonsterShoot(100, 30);
        }

        else {

            // 100% chance to aggro if you get too close
            checkIfPlayerInAggro(gp.player, 4, 1);

            // still moves randomly, but changes direction more often
            getRandomDirection(45);
        }
    }

    /**
     * Reaction when taking damage:
     * - Resets movement timer.
     * - Forces aggro/pathing toward the player.
     */
    public void damageReaction() {
        movementCounter = 0;
        onPath = true;
    }

    /**
     * Randomized drop table using a 1–1000 roll.
     * Rates:
     * - 1–500   : Copper Coin (50.0%)
     * - 501–650 : Heart (15.0%)
     * - 651–800 : Mana (15.0%)
     * - 801–900 : Red Potion (10.0%)
     * - 901–950 : Blue Potion (5.0%)
     * - 951–970 : Silver Coin (2.0%)
     * - 971–980 : Key (1.0%)
     * - 981–985 : Lantern (0.5%)
     * - 986–990 : Tent (0.5%)
     * - 991–995 : Gold Coin (0.5%)
     * - 996–998 : Iron Shield (0.3%)
     * - 999     : Iron Sword (0.1%)
     * - 1000    : Iron Axe (0.1%)
     */
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
