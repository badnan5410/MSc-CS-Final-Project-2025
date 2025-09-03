package monster;

import entity.Entity;
import main.GamePanel;
import object.Coin;
import object.Heart;
import object.Mana;
import object.Slimeball_Green;

import java.util.Random;

public class MON_Bat extends Entity {
    GamePanel gp;

    public MON_Bat(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "Bat";
        type = TYPE_MONSTER;
        defaultSpeed = 4;
        speed = defaultSpeed;
        maxLife = 10;
        life = maxLife;
        defaultAttack = 8;
        defaultDefense = 4;
        attack = defaultAttack;
        defense = defaultDefense;
        exp = 6;
        knockBackPower = 6;
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
        getRandomDirection(40);
    }

    public void damageReaction() {
        movementCounter = 0;
    }

    public void checkDrop() {
        int i = new Random().nextInt(100) + 1; // 1–100

        if (i <= 80) {dropItem(new Coin(gp));}
        else if (i <= 90) {dropItem(new Mana(gp));}
        else if (i <= 100) {dropItem(new Heart(gp));}
    }
}
