package object;

import entity.Projectile;
import main.GamePanel;

import java.awt.*;

public class Slimeball_Red extends Projectile {
    GamePanel gp;
    public static final String objName = "Red Slime Ball";

    public Slimeball_Red(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = objName;
        speed = 10;
        maxLife = 90;
        life = maxLife;
        defaultAttack = 4;
        attack = defaultAttack;
        knockBackPower = 0;
        alive = false;
        getImage();
    }

    public void getImage() {
        up1 = setup("/projectiles/slimeball_red");
        up2 = setup("/projectiles/slimeball_red");
        down1 = setup("/projectiles/slimeball_red");
        down2 = setup("/projectiles/slimeball_red");
        right1 = setup("/projectiles/slimeball_red");
        right2 = setup("/projectiles/slimeball_red");
        left1 = setup("/projectiles/slimeball_red");
        left2 = setup("/projectiles/slimeball_red");
    }

    public Color getParticleColor() {return new Color(193, 106, 127);}

    public int getParticleSize() {return 10;}

    public int getParticleSpeed() {return 1;}

    public int getParticleMaxLife() {return 20;}
}
