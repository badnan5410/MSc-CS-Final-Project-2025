package monster;

import entity.Entity;
import main.GamePanel;
import object.*;

import java.util.Random;

public class MON_Orc extends Entity {
    GamePanel gp;

    public MON_Orc(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "Orc";
        type = TYPE_MONSTER;
        defaultSpeed = 2;
        speed = defaultSpeed;
        maxLife = 40;
        life = maxLife;
        defaultAttack = 6;
        defaultDefense = 3;
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
        motion1_duration = 20;
        motion2_duration = 30;
        getImage();
        getAttackImage();
    }

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

    public void setAction() {
        monsterBoost(4);

        if (onPath) {

            // check if it stops chasing
            checkIfPlayerOutOfAggro(gp.player, 8, 4);

            // search the direction to go
            searchPath(getEndCol(gp.player), getEndRow(gp.player));
        }
        else {

            // check if it starts chasing
            checkIfPlayerInAggro(gp.player, 4, 80);

            // get a random direction if it's not onPath
            getRandomDirection(120);
        }

        // check if it attacks
        if (!attacking) {
            checkIfMonsterAttack(25, gp.TILE_SIZE*3, gp.TILE_SIZE);
        }
    }

    public void damageReaction() {
        movementCounter = 0;
        onPath = true;
    }

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

