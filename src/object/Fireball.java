package object;

import entity.Projectile;
import main.GamePanel;

public class Fireball extends Projectile {
    GamePanel gp;

    public Fireball(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Fireball";
        speed = 5;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage() {
        up1 = setup("/projectiles/fireball_up_1");
        up2 = setup("/projectiles/fireball_up_2");
        down1 = setup("/projectiles/fireball_down_1");
        down2 = setup("/projectiles/fireball_down_2");
        right1 = setup("/projectiles/fireball_right_1");
        right2 = setup("/projectiles/fireball_right_2");
        left1 = setup("/projectiles/fireball_left_1");
        left2 = setup("/projectiles/fireball_left_2");
    }
}
