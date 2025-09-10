package monster;

import data.Progress;
import entity.Entity;
import main.GamePanel;
import object.*;

import java.util.Random;

/**
 * Boss monster: Umberos, the Skeleton King.
 *
 * Behavior:
 * - Starts asleep until the cutscene triggers the fight.
 * - Enrages at 50% HP: swaps to alternate sprites, moves faster, and hits harder.
 * - Uses large hitbox and extended attack area suitable for a multi-tile boss.
 * - Shows a dedicated boss HP bar via the UI.
 *
 * Notes:
 * - This class intentionally does not apply day/night scaling; it manages its
 *   own phase-based difficulty through the enraged state.
 */
public class MON_Boss extends Entity {
    GamePanel gp;
    public static final String monName = "Umberos, Skeleton King";

    /**
     * Initializes the boss with base stats, large collision box,
     * and attack frames. Starts in a sleeping state.
     *
     * @param gp game context
     */
    public MON_Boss(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = monName;
        type = TYPE_MONSTER;
        isBossMonster = true;
        defaultSpeed = 2;
        speed = defaultSpeed;
        maxLife = 80;
        life = maxLife;
        defaultAttack = 10;
        defaultDefense = 6;
        attack = defaultAttack;
        defense = defaultDefense;
        exp = 200;
        knockBackPower = 4;
        isSleeping = true;
        int size = gp.TILE_SIZE*5;
        rect.x = gp.TILE_SIZE;
        rect.y = gp.TILE_SIZE;
        rect.width = size - (gp.TILE_SIZE*2);
        rect.height = size - gp.TILE_SIZE;
        default_rectX = rect.x;
        default_rectY = rect.y;
        attackArea.width = 170;
        attackArea.height = 170;
        motion1_duration = 30;
        motion2_duration = 45;
        getImage();
        getAttackImage();
        setDialogue();
    }

    /**
     * Loads walking sprites. Uses an alternate set when enraged.
     */
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

    /**
     * Loads attack sprites. Uses an alternate set when enraged.
     */
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

    /**
     * Initializes the boss dialogue lines shown during the encounter.
     */
    public void setDialogue() {
        dialogues[0][0] = "No one can steal my treasure!" + "\n\n\n[press enter]";
        dialogues[0][1] = "You will die here, just like all the heroes who have\ncome before you!" + "\n\n[press enter]";
        dialogues[0][2] = "WELCOME TO YOUR DOOM!" + "\n\n\n[press enter]";
    }

    /**
     * Boss AI:
     * - Enrage trigger at half HP: swaps sprites, buffs stats, shortens attack windup.
     * - If the player is within 10 tiles, pursues; otherwise wanders.
     * - Attack rate and range increase while enraged.
     */
    public void setAction() {

        if (!enraged && life < maxLife/2) {
            enraged = true;
            getImage();
            getAttackImage();
            defaultSpeed++;
            speed = defaultSpeed;
            attack += 4;
            defense += 2;
            motion1_duration = 20;
            motion2_duration = 35;
            gp.ui.addMessage(name + " becomes enraged!");
        }

        if (getTileDistance(gp.player) < 10) {
            int interval = enraged ? 30 : 60;
            moveTowardsThePlayer(interval);
        }
        else {
            getRandomDirection(100);
        }

        // check if it attacks
        if (!attacking) {
            int attackRate = enraged ? 20 : 40;
            int verRange = enraged ? gp.TILE_SIZE * 9 : gp.TILE_SIZE * 7; // larger vertical reach
            int horRange = enraged ? gp.TILE_SIZE * 7 : gp.TILE_SIZE * 5;
            checkIfMonsterAttack(attackRate, verRange, horRange);
        }
    }

    /**
     * On taking damage, cancel any wander delay so the boss can react instantly.
     */
    public void damageReaction() {
        movementCounter = 0;
    }

    /**
     * Drop table:
     * - Guaranteed: 1 gold coin, 1 red potion, 1 blue potion.
     * - Bonus roll (1–1000) for extra rewards such as more gold, tent, lantern,
     *   and iron-tier equipment, with very rare outcomes near the top end.
     */
    public void checkDrop() {
        int i = new Random().nextInt(1000) + 1; // 1–1000 pool

        // Guaranteed drops (boss always gives big loot)
        dropItem(new Coin_Gold(gp));            // Always at least 1 gold
        dropItem(new Potion_Red(gp));           // Always 1 healing potion
        dropItem(new Potion_Blue(gp));          // Always 1 mana potion

        // Bonus RNG drops
        if (i <= 200) {dropItem(new Coin_Gold(gp));}          // 20% extra gold
        else if (i <= 400) {dropItem(new Tent(gp));}          // 20%
        else if (i <= 550) {dropItem(new Lantern(gp));}       // 15%
        else if (i <= 700) {dropItem(new Iron_Shield(gp));}   // 15%
        else if (i <= 850) {dropItem(new Iron_Sword(gp));}    // 15%
        else if (i <= 950) {dropItem(new Iron_Axe(gp));}      // 10%
        else if (i <= 990) {dropItem(new Key(gp));}           // 4%
        else if (i <= 999) {dropItem(new Coin_Gold(gp));}     // 0.9% jackpot
        else if (i == 1000) {dropItem(new Lantern(gp));}      // 0.1% ultra-rare duplicate
    }
}
