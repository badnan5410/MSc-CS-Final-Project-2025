package object;

import entity.Projectile;
import main.GamePanel;

import java.awt.*;

public class SlimeBall extends Projectile {
    GamePanel gp;

    public SlimeBall(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Slime Ball";
        speed = 6;
        maxLife = 30;
        life = maxLife;
        defaultAttack = 1;
        attack = defaultAttack;
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

    public Color getParticleColor() {return new Color(106, 193, 127);}

    public int getParticleSize() {return 10;}

    public int getParticleSpeed() {return 1;}

    public int getParticleMaxLife() {return 20;}
}
