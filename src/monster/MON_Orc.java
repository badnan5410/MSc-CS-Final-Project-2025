package monster;

import entity.Entity;
import main.GamePanel;
import object.*;

import java.util.Random;

/**
 * Orc enemy.
 *
 * Heavier melee monster with a large hit box and strong knockback.
 * - Scales attack/defense with time of day via monsterBoost.
 * - Chases the player using pathfinding when aggroed.
 * - Performs short-range melee attacks with a two-phase animation.
 * - Drops mid-tier coins, potions, and occasionally iron gear.
 */
public class MON_Orc extends Entity {
    GamePanel gp;

    /**
     * Builds an Orc with default stats, hit box, attack area, and animations.
     *
     * @param gp game context
     */
    public MON_Orc(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "Orc";
        type = TYPE_MONSTER;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 40;
        life = maxLife;
        defaultAttack = 6;
        defaultDefense = 4;
        attack = defaultAttack;
        defense = defaultDefense;
        exp = 20;
        knockBackPower = 3;
        rect.x = 4;
        rect.y = 4;
        rect.width = 40;
        rect.height = 44;
        default_rectX = rect.x;
        default_rectY = rect.y;
        attackArea.width = gp.TILE_SIZE;
        attackArea.height = gp.TILE_SIZE;
        motion1_duration = 25;
        motion2_duration = 30;
        getImage();
        getAttackImage();
    }

    /**
     * Loads the walking frames for all directions.
     */
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

    /**
     * Loads the attack frames for all directions.
     * Uses tall sprites for vertical swings and wide sprites for horizontal swings.
     */
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

    /**
     * Orc AI:
     * - Boosts stats based on day phase.
     * - If currently pathing, may give up chase and continues A* toward the player.
     * - If not pathing, may aggro if the player is near and otherwise wanders.
     * - Tries a melee attack when in front of the player within a narrow cone.
     */
    public void setAction() {
        monsterBoost(4);

        if (onPath) {

            // check if it stops chasing
            checkIfPlayerOutOfAggro(gp.player, 4, 2);

            // search the direction to go
            searchPath(getEndCol(gp.player), getEndRow(gp.player));
        }

        else {

            // check if it starts chasing
            checkIfPlayerInAggro(gp.player, 2, 2);

            // get a random direction if it's not onPath
            getRandomDirection(120);
        }

        // check if it attacks
        if (!attacking) {
            checkIfMonsterAttack(10, gp.TILE_SIZE*2, gp.TILE_SIZE);
        }
    }

    /**
     * On taking damage, immediately commits to chasing the player.
     */
    public void damageReaction() {
        movementCounter = 0;
        onPath = true;
    }

    /**
     * Randomized drop table using a 1–1000 roll.
     *
     * Rates:
     * - 1–300   : Silver Coin (30.0%)
     * - 301–550 : Gold Coin (25.0%)
     * - 551–700 : Red Potion (15.0%)
     * - 701–800 : Blue Potion (10.0%)
     * - 801–880 : Heart (8.0%)
     * - 881–940 : Mana (6.0%)
     * - 941–970 : Tent (3.0%)
     * - 971–985 : Lantern (1.5%)
     * - 986–995 : Iron Shield (1.0%)
     * - 996–998 : Iron Sword (0.3%)
     * - 999     : Iron Axe (0.1%)
     * - 1000    : Key (0.1%)
     */
    public void checkDrop() {
        int i = new Random().nextInt(1000) + 1; // 1–1000 pool

        if (i <= 300) {dropItem(new Coin_Silver(gp));}         // 30%
        else if (i <= 550) {dropItem(new Coin_Gold(gp));}      // 25%
        else if (i <= 700) {dropItem(new Potion_Red(gp));}     // 15%
        else if (i <= 800) {dropItem(new Potion_Blue(gp));}    // 10%
        else if (i <= 880) {dropItem(new Heart(gp));}          // 8%
        else if (i <= 940) {dropItem(new Mana(gp));}           // 6%
        else if (i <= 970) {dropItem(new Tent(gp));}           // 3%
        else if (i <= 985) {dropItem(new Lantern(gp));}        // 1.5%
        else if (i <= 995) {dropItem(new Iron_Shield(gp));}    // 1%
        else if (i <= 998) {dropItem(new Iron_Sword(gp));}     // 0.3%
        else if (i == 999) {dropItem(new Iron_Axe(gp));}       // 0.1%
        else if (i == 1000) {dropItem(new Key(gp));}           // 0.1% (super rare bonus)
    }
}

