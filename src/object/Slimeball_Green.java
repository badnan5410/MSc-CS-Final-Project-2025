package object;

import entity.Projectile;
import main.GamePanel;

import java.awt.*;

public class Slimeball_Green extends Projectile {
    GamePanel gp;
    public static final String objName = "Green Slime Ball";

    public Slimeball_Green(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = objName;
        speed = 6;
        maxLife = 30;
        life = maxLife;
        defaultAttack = 1;
        attack = defaultAttack;
        knockBackPower = 0;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage() {
        up1 = setup("/projectiles/slimeball_green");
        up2 = setup("/projectiles/slimeball_green");
        down1 = setup("/projectiles/slimeball_green");
        down2 = setup("/projectiles/slimeball_green");
        right1 = setup("/projectiles/slimeball_green");
        right2 = setup("/projectiles/slimeball_green");
        left1 = setup("/projectiles/slimeball_green");
        left2 = setup("/projectiles/slimeball_green");
    }

    public Color getParticleColor() {return new Color(106, 193, 127);}

    public int getParticleSize() {return 10;}

    public int getParticleSpeed() {return 1;}

    public int getParticleMaxLife() {return 20;}
}
