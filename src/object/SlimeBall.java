package object;

import entity.Projectile;
import main.GamePanel;

public class SlimeBall extends Projectile {
    GamePanel gp;

    public SlimeBall(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Slime Ball";
        speed = 8;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage() {
        up1 = setup("/projectiles/slime_ball");
        up2 = setup("/projectiles/slime_ball");
        down1 = setup("/projectiles/slime_ball");
        down2 = setup("/projectiles/slime_ball");
        right1 = setup("/projectiles/slime_ball");
        right2 = setup("/projectiles/slime_ball");
        left1 = setup("/projectiles/slime_ball");
        left2 = setup("/projectiles/slime_ball");
    }
}
