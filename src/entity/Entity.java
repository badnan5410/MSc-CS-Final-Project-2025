package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Entity {
    GamePanel gp;
    public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
    public BufferedImage atk_up1, atk_up2, atk_down1, atk_down2, atk_right1, atk_right2, atk_left1, atk_left2;
    public BufferedImage image1, image2, image3;
    public Rectangle rect = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int default_rectX, default_rectY;
    public boolean collision = false;
    String[] dialogues = new String[20];
    Color brightRed = new Color(255, 0, 30);
    Color darkGrey = new Color(35, 35, 35);

    // State
    public int worldX, worldY;
    public String direction = "down";
    public int spriteNum = 1;
    int dialogueIndex = 0;
    public boolean checkCollision = false;
    public boolean invincible = false;
    boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    boolean hpBarOn = false;
    public boolean destructible = false;
    public boolean onPath = false;
    public boolean knockback = false;

    // Counters
    public int spriteCounter = 0;
    public int movementCounter = 0;
    public int invincibleCounter = 0;
    public int dyingCounter = 0;
    int hpBarCounter = 0;
    public int shotCooldownCounter = 0;
    int knockBackCounter = 0;

    // Character Status
    public String name;
    public int defaultSpeed;
    public int speed;
    public int maxLife;
    public int life;
    public int maxMana;
    public int mana;
    public int level;
    public int strength;
    public int dexterity;
    public int defaultAttack;
    public int attack;
    public int defaultDefense;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public final int baseExp = 5;
    public final double multiplier = 1.3;
    public int coins;
    public Entity currentWeapon;
    public Entity currentShield;
    public Entity currentLight;
    public Projectile projectile;

    // Item Properties
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int INVENTORY_CAPACITY = 20;
    public int attackValue;
    public int defenseValue;
    public int durability;
    public String description = "";
    public int useCost;
    public int value;
    public int price;
    public int knockBackPower = 0;
    public boolean stackable = false;
    public int amount = 1;
    public int lightRadius;

    // Type
    public int type;
    public final int TYPE_PLAYER = 0;
    public final int TYPE_NPC = 1;
    public final int TYPE_MONSTER = 2;
    public final int TYPE_SWORD = 3;
    public final int TYPE_AXE = 4;
    public final int TYPE_SHIELD = 5;
    public final int TYPE_CONSUMABLE = 6;
    public final int TYPE_PICKUP = 7;
    public final int TYPE_OBSTACLE = 8;
    public final int TYPE_LIGHT = 9;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public int getLeftX() {return worldX + rect.x;}

    public int getRightX() {return worldX + rect.x + rect.width;}

    public int getTopY() {return worldY + rect.y;}

    public int getBottomY() {return worldY + rect.y + rect.height;}

    public int getCol() {return (worldX + rect.x)/gp.TILE_SIZE;}

    public int getRow() {return (worldY + rect.y)/gp.TILE_SIZE;}

    public void setAction() {}

    public void damageReaction() {}

    public void speak() {

        if (dialogues[dialogueIndex] == null) {dialogueIndex = 0;}

        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch(gp.player.direction) {
            case "up": direction = "down"; break;
            case "down": direction = "up"; break;
            case "right": direction = "left"; break;
            case "left": direction = "right"; break;
        }
    }

    public void interact() {}

    public boolean useItem(Entity entity) {return false;}

    public void checkDrop() {}

    public void dropItem(Entity droppedItem) {

        for (int i = 0; i < gp.obj[1].length; i++) {

            if (gp.obj[gp.currentMap][i] == null) {
                gp.obj[gp.currentMap][i] = droppedItem;
                gp.obj[gp.currentMap][i].worldX = worldX;
                gp.obj[gp.currentMap][i].worldY = worldY;
                break;
            }
        }
    }

    public Color getParticleColor() {return new Color(0, 0, 0);}

    public int getParticleSize() {return -1;}

    public int getParticleSpeed() {return -1;}

    public int getParticleMaxLife() {return -1;}

    public void generateParticle(Entity generator, Entity target) {
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -2, -1);
        Particle p2 = new Particle(gp, target, color, size, speed, maxLife, 2, -1);
        Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -2, 1);
        Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 2, 1);

        gp.particleList.add(p1);
        gp.particleList.add(p2);
        gp.particleList.add(p3);
        gp.particleList.add(p4);
    }

    public void checkCollision() {
        checkCollision = false;
        gp.cHandler.checkTile(this);
        gp.cHandler.checkObject(this, false);
        gp.cHandler.checkEntity(this, gp.npc);
        gp.cHandler.checkEntity(this, gp.monster);
        gp.cHandler.checkEntity(this, gp.iTile);
        boolean touchPlayer = gp.cHandler.checkPlayer(this);

        if (this.type == TYPE_MONSTER && touchPlayer) {
            damagePlayer(attack);
        }
    }

    public void update() {

        if (knockback) {
            checkCollision();

            if (collision) {
                knockBackCounter = 0; // prevents entity from being pushed into solid object
                knockback = false;
                speed = defaultSpeed;
            }
            else if (!collision) {

                switch(gp.player.direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "right": worldX += speed; break;
                    case "left": worldX -= speed; break;
                }
            }

            knockBackCounter++;

            if (knockBackCounter == 45) {
                knockBackCounter = 0;
                knockback = false;
                speed = defaultSpeed;
            }
        }
        else {
            setAction();
            checkCollision();

            if (!checkCollision) {

                switch(direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "right": worldX += speed; break;
                    case "left": worldX -= speed; break;
                }
            }
        }

        spriteCounter++;

        if (spriteCounter > 24) {

            if (spriteNum == 1) {spriteNum = 2;}
            else if (spriteNum == 2) {spriteNum = 1;}

            spriteCounter = 0;
        }

        if (invincible) {
            invincibleCounter++;

            if (invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if (shotCooldownCounter < 30) {shotCooldownCounter++;}
    }

    public void damagePlayer(int attack) {

        if (!gp.player.invincible) {
            gp.soundEffect(6);
            int damage = attack - gp.player.defense;

            if (damage > 0) {gp.player.life -= damage;}
            else {gp.player.life--;}

            gp.player.invincible = true;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.screenX && worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.screenX && worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.screenY && worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.screenY) {

            switch (direction) {
                case "up":
                    if (spriteNum == 1) {image = up1;}
                    if (spriteNum == 2) {image = up2;}
                    break;
                case "down":
                    if (spriteNum == 1) {image = down1;}
                    if (spriteNum == 2) {image = down2;}
                    break;
                case "right":
                    if (spriteNum == 1) {image = right1;}
                    if (spriteNum == 2) {image = right2;}
                    break;
                case "left":
                    if (spriteNum == 1) {image = left1;}
                    if (spriteNum == 2) {image = left2;}
                    break;
            }

            // monster HP bar
            if (type == TYPE_MONSTER && hpBarOn) {
                double oneScale = (double)gp.TILE_SIZE/maxLife;
                double hpBarValue = oneScale*life;

                g2.setColor(darkGrey);
                g2.fillRect(screenX-1, screenY-16, gp.TILE_SIZE+2, 12);

                g2.setColor(brightRed);
                g2.fillRect(screenX, screenY - 15, (int)hpBarValue, 10);

                hpBarCounter++;

                if (hpBarCounter > 600) {
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }

            if (invincible && !destructible) {
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2, 0.4f);
            }
            if (dying) {
                dyingAnimation(g2);
            }

            g2.drawImage(image, screenX, screenY,null);

            changeAlpha(g2, 1f);
        }
    }

    public void dyingAnimation(Graphics2D g2) {
        dyingCounter++;

        int i = 5;

        if (dyingCounter <= i) {changeAlpha(g2, 0f);}
        if (dyingCounter > i && dyingCounter <= i*2) {changeAlpha(g2, 1f);}
        if (dyingCounter > i*2 && dyingCounter <= i*3) {changeAlpha(g2, 0f);}
        if (dyingCounter > i*3 && dyingCounter <= i*4) {changeAlpha(g2, 1f);}
        if (dyingCounter > i*4 && dyingCounter <= i*5) {changeAlpha(g2, 0f);}
        if (dyingCounter > i*5 && dyingCounter <= i*6) {changeAlpha(g2, 1f);}
        if (dyingCounter > i*6 && dyingCounter <= i*7) {changeAlpha(g2, 0f);}
        if (dyingCounter > i*7 && dyingCounter <= i*8) {changeAlpha(g2, 1f);}
        if (dyingCounter > i*8) {
            alive = false;
        }
    }

    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    public BufferedImage setup(String imagePath) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, gp.TILE_SIZE, gp.TILE_SIZE);

        }catch(IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);

        }catch(IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void searchPath(int endCol, int endRow) {
        int startCol, startRow, nextX, nextY;

        startCol = (worldX + rect.x)/gp.TILE_SIZE;
        startRow = (worldY + rect.y)/gp.TILE_SIZE;


        gp.pFinder.setNodes(startCol, startRow, endCol, endRow);

        if (gp.pFinder.search()) {

            // next worldX and worldY
            nextX = gp.pFinder.pathList.get(0).col * gp.TILE_SIZE;
            nextY = gp.pFinder.pathList.get(0).row * gp.TILE_SIZE;

            // entity's hitbox position
            int leftX = worldX + rect.x;
            int rightX = worldX + rect.x + rect.width;
            int topY = worldY + rect.y;
            int bottomY = worldY + rect.y + rect.height;

            if (topY > nextY && leftX >= nextX && rightX < nextX+gp.TILE_SIZE) {
                direction = "up";
            }
            else if (topY < nextY && leftX >= nextX && rightX < nextX+gp.TILE_SIZE) {
                direction = "down";
            }
            else if (topY >= nextY && bottomY < nextY+gp.TILE_SIZE) {

                // left or right
                if (leftX > nextX) {direction = "left";}
                if (leftX < nextX) {direction = "right";}
            }
            else if (topY > nextY && leftX > nextX) {

                // up or left
                direction = "up";
                checkCollision();
                if (checkCollision) {
                    direction = "left";
                }
            }
            else if (topY > nextY && leftX < nextX) {

                // up or right
                direction = "up";
                checkCollision();
                if (checkCollision) {
                    direction = "right";
                }
            }
            else if (topY < nextY && leftX > nextX) {

                // down or left
                direction = "down";
                checkCollision();
                if (checkCollision) {
                    direction = "left";
                }
            }
            else if (topY < nextY && leftX < nextX) {

                // down or right
                direction = "down";
                checkCollision();
                if (checkCollision) {
                    direction = "right";
                }
            }

            // if the entity reaches the end node, stop this search
            int nextCol = gp.pFinder.pathList.get(0).col;
            int nextRow = gp.pFinder.pathList.get(0).row;
            if (nextCol == endCol && nextRow == endRow) {
                onPath = false;
            }
        }
    }

    public int getDetected(Entity user, Entity target[][], String targetName) {
        int index = -1;

        // check the surrounding objects;
        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();

        switch(user.direction) {
            case "up": nextWorldY = user.getTopY()-user.speed; break;
            case "down": nextWorldY = user.getBottomY()+user.speed; break;
            case "left": nextWorldX = user.getLeftX()-user.speed; break;
            case "right": nextWorldX = user.getRightX()+user.speed; break;
        }

        int col = nextWorldX/gp.TILE_SIZE;
        int row = nextWorldY/gp.TILE_SIZE;

        for (int i = 0; i < target[1].length; i++) {
            if (target[gp.currentMap][i] != null) {

                if (target[gp.currentMap][i].getCol() == col && target[gp.currentMap][i].getRow() == row && target[gp.currentMap][i].name.equals(targetName)) {
                    index = i;
                    break;
                }
            }
        }

        return index;
    }
}
