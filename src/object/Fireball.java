package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

import java.awt.*;

public class Fireball extends Projectile {
    GamePanel gp;
    public static final String objName = "Fireball";

    public Fireball(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = objName;
        speed = 5;
        maxLife = 60;
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

    public boolean checkResource(Entity user) {
        return user.mana >= useCost;
    }

    public void subtractResource(Entity user) {
        user.mana -= useCost;
    }

    public Color getParticleColor() {return new Color(240, 50, 0);}

    public int getParticleSize() {return 10;}

    public int getParticleSpeed() {return 1;}

    public int getParticleMaxLife() {return 20;}
}
