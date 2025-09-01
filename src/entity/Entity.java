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
    public BufferedImage guardUp, guardDown, guardRight, guardLeft;
    public BufferedImage image1, image2, image3;
    public Rectangle rect = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int default_rectX, default_rectY;
    public boolean collision = false;
    String[] dialogues = new String[20];
    Color brightRed = new Color(255, 0, 30);
    Color darkGrey = new Color(35, 35, 35);
    public Entity attacker;

    // State
    public int worldX, worldY;
    public String direction = "down";
    public int spriteNum = 1;
    int dialogueIndex = 0;
    public boolean checkCollision = false;
    public boolean invincible = false;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    boolean hpBarOn = false;
    public boolean destructible = false;
    public boolean onPath = false;
    public boolean knockback = false;
    public String knockBackDirection;
    public boolean guarding = false;
    public boolean transparent = false;
    public boolean offBalance = false;
    public Entity loot;
    public boolean opened = false;

    // Counters
    public int spriteCounter = 0;
    public int movementCounter = 0;
    public int invincibleCounter = 0;
    public int dyingCounter = 0;
    int hpBarCounter = 0;
    public int shotCooldownCounter = 0;
    int knockBackCounter = 0;
    public int guardCounter = 0;
    int offBallanceCounter = 0;

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
    public int motion1_duration;
    public int motion2_duration;
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

    public int getCol() {return worldX/gp.TILE_SIZE;}

    public int getRow() {return worldY/gp.TILE_SIZE;}

    public int getDistanceX(Entity target) {return Math.abs(worldX - target.worldX);}

    public int getDistanceY(Entity target) {return Math.abs(worldY - target.worldY);}

    public int getTileDistance(Entity target) {
        return (getDistanceX(target) + getDistanceY(target))/gp.TILE_SIZE;
    }

    public int getEndCol(Entity target) {return (target.worldX + target.rect.x)/gp.TILE_SIZE;}

    public int getEndRow(Entity target) {return (target.worldY + target.rect.y)/gp.TILE_SIZE;}

    public void setLoot(Entity loot) {}

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

            // tells the collision entity is about to move
            String prevDir = direction;
            direction = knockBackDirection;

            // clear predictive boolean flags
            checkCollision = false;
            collision = false;

            // Predict collision in the knockback direction
            gp.cHandler.checkTile(this);
            gp.cHandler.checkObject(this, true);
            gp.cHandler.checkEntity(this, gp.npc);
            gp.cHandler.checkEntity(this, gp.monster);
            gp.cHandler.checkEntity(this, gp.iTile);

            if (checkCollision) {
                knockBackCounter = 0; // prevents entity from being pushed into solid object
                knockback = false;
                speed = defaultSpeed;
            }
            else {
                switch(knockBackDirection) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "right": worldX += speed; break;
                    case "left": worldX -= speed; break;
                }
            }

            direction = prevDir;

            if (++knockBackCounter == 10) {
                knockBackCounter = 0;
                knockback = false;
                speed = defaultSpeed;
            }
        }
        else if (attacking) {attacking();}
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

            spriteCounter++;

            if (spriteCounter > 24) {
                if (spriteNum == 1) {spriteNum = 2;}
                else if (spriteNum == 2) {spriteNum = 1;}

                spriteCounter = 0;
            }
        }

        if (invincible) {
            invincibleCounter++;

            if (invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if (shotCooldownCounter < 30) {shotCooldownCounter++;}

        if (offBalance) {
            offBallanceCounter++;

            if (offBallanceCounter > 60) {
                offBalance = false;
                offBallanceCounter = 0;
            }
        }
    }

    public void monsterBoost(int rate) {
        int dayState = gp.eManager.lighting.dayState;

        switch (dayState) {
            case 0: attack = defaultAttack; defense = defaultDefense; break;
            case 1: attack = defaultAttack+rate; defense = defaultDefense+rate; break;
            case 2: attack = defaultAttack+(rate*2); defense = defaultDefense+(rate*2); break;
            case 3: attack = defaultAttack+rate; defense = defaultDefense+rate; break;
        }
    }

    public void checkIfMonsterAttack(int rate, int verticalRange, int horizontalRange) {
        boolean isTargetInRange = false;
        int dx = getDistanceX(gp.player);
        int dy = getDistanceY(gp.player);

        switch (direction) {
            case "up":
                if (gp.player.worldY < worldY && dy < verticalRange && dx < horizontalRange) {
                    isTargetInRange = true;
                }

                break;
            case "down":
                if (gp.player.worldY > worldY && dy < verticalRange && dx < horizontalRange) {
                    isTargetInRange = true;
                }

                break;
            case "left":
                if (gp.player.worldX < worldX && dx < verticalRange && dy < horizontalRange) {
                    isTargetInRange = true;
                }

                break;
            case "right":
                if (gp.player.worldX > worldX && dx < verticalRange && dx < horizontalRange) {
                    isTargetInRange = true;
                }

                break;
        }

        if (isTargetInRange) {

            //check if it initiates an attack
            int i = new Random().nextInt(rate);

            if (i == 0) {
                attacking = true;
                spriteNum = 1;
                spriteCounter = 0;
            }
        }
    }

    public void checkIfMonsterShoot(int rate, int shotInterval) {
        int i = new Random().nextInt(rate);

        if (i == 0 && !projectile.alive && shotCooldownCounter == shotInterval) {
            projectile.set(worldX, worldY, direction, true, this);

            for (int j = 0; j < gp.projectile[1].length; j++) {
                if (gp.projectile[gp.currentMap][j] == null) {
                    gp.projectile[gp.currentMap][j] = projectile;
                    break;
                }
            }

            shotCooldownCounter = 0;
        }
    }

    public void checkIfPlayerOutOfAggro(Entity target, int distance, int rate) {
        if (getTileDistance(target) > distance) {
            int i = new Random().nextInt(rate);

            if (i == 0) {onPath = false;}
        }
    }

    public void checkIfPlayerInAggro(Entity target, int distance, int rate) {

        if (getTileDistance(target) < distance) {
            int i = new Random().nextInt(rate);

            if (i == 0) {onPath = true;}
        }
    }

    public void getRandomDirection() {
        movementCounter++;

        if (movementCounter == 120) {
            Random rand = new Random();
            int i = rand.nextInt(100)+1;

            if (i <= 25) {direction = "up";}

            if (i > 25 && i <= 50) {direction = "down";}

            if (i > 50 && i <= 75) {direction = "right";}

            if (i > 75 && i <= 100) {direction = "left";}

            movementCounter = 0;
        }
    }

    public void attacking() {
        spriteCounter++;

        if (spriteCounter <= motion1_duration) {spriteNum = 1;}
        if (spriteCounter > motion1_duration && spriteCounter <= motion2_duration) {
            spriteNum = 2;

            // Save current worldX, worldY, rect
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int currentWidth = rect.width;
            int currentHeight = rect.height;

            // Adjust player's worldX/worldY for the attackArea
            switch (direction) {
                case "up": worldY -= attackArea.height; break;
                case "down": worldY += attackArea.height; break;
                case "right": worldX += attackArea.width; break;
                case "left": worldX -= attackArea.width; break;
            }

            // attackArea becomes rect
            rect.width = attackArea.width;
            rect.height = attackArea.height;

            if (type == TYPE_MONSTER) {
                if (gp.cHandler.checkPlayer(this)) {
                    damagePlayer(attack);
                }
            }
            else { // player

                // check monster collision with updated worldX/worldY and rect
                int monsterIndex = gp.cHandler.checkEntity(this, gp.monster);
                gp.player.damageMonster(monsterIndex, this, attack, currentWeapon.knockBackPower, false);

                int iTileIndex = gp.cHandler.checkEntity(this, gp.iTile);
                gp.player.damageInteractiveTile(iTileIndex);

                int projectileIndex = gp.cHandler.checkEntity(this, gp.projectile);
                gp.player.breakProjectile(projectileIndex);
            }



            // restore original data
            worldX = currentWorldX;
            worldY = currentWorldY;
            rect.width = currentWidth;
            rect.height = currentHeight;
        }
        if (spriteCounter > motion2_duration) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void damagePlayer(int attack) {

        if (!gp.player.invincible) {
            int damage = attack - gp.player.defense;

            // get opposite direction of attacker
            String guardDirection = getOppositeDirection(direction);

            if (gp.player.guarding && gp.player.direction.equals(guardDirection)) {

                // parry
                if (gp.player.guardCounter < 10) {
                    damage = 0;
                    gp.ui.addMessage("You parried the hit!");
                    gp.soundEffect(20);
                    setKnockBack(this, gp.player, knockBackPower);
                    int parryDamage = attack/3;
                    life -= parryDamage;
                    gp.ui.addMessage("You reflect " + parryDamage + " damage!");
                    offBalance = true;
                    spriteCounter -= 60; // stun monster
                }

                // normal block
                else {
                    damage /= 3;
                    gp.ui.addMessage("You blocked the hit!");
                    gp.soundEffect(19);
                }
            } else {
                gp.soundEffect(6);
            }

            // clamp: anything below 1 is just 0
            if (damage < 1) damage = 0;

            if (damage > 0) {
                gp.player.life -= damage;
                gp.ui.addMessage("You take " + damage + " damage!");
            } else {
                gp.ui.addMessage("You take no damage!");
            }

            if (knockBackPower > 0) {
                setKnockBack(gp.player, this, knockBackPower);
            }

            gp.player.invincible = true;
        }
    }

    public void setKnockBack(Entity target, Entity attacker, int power) {
        this.attacker = attacker;
        target.knockBackDirection = attacker.direction;
        target.speed += power;
        target.knockback = true;
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.screenX && worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.screenX && worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.screenY && worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.screenY) {

            int tempScreenX = screenX;
            int tempScreenY = screenY;

            switch (direction) {
                case "up":
                    if (!attacking) {
                        if (spriteNum == 1) {image = up1;}
                        if (spriteNum == 2) {image = up2;}
                    }
                    if (attacking) {
                        tempScreenY = screenY - gp.TILE_SIZE;
                        if (spriteNum == 1) {image = atk_up1;}
                        if (spriteNum == 2) {image = atk_up2;}
                    }
                    break;
                case "down":
                    if (!attacking) {
                        if (spriteNum == 1) {image = down1;}
                        if (spriteNum == 2) {image = down2;}
                    }
                    if (attacking) {
                        if (spriteNum == 1) {image = atk_down1;}
                        if (spriteNum == 2) {image = atk_down2;}
                    }
                    break;
                case "right":
                    if (!attacking) {
                        if (spriteNum == 1) {image = right1;}
                        if (spriteNum == 2) {image = right2;}
                    }
                    if (attacking) {
                        if (spriteNum == 1) {image = atk_right1;}
                        if (spriteNum == 2) {image = atk_right2;}
                    }
                    break;
                case "left":
                    if (!attacking) {
                        if (spriteNum == 1) {image = left1;}
                        if (spriteNum == 2) {image = left2;}
                    }
                    if (attacking) {
                        tempScreenX = screenX - gp.TILE_SIZE;
                        if (spriteNum == 1) {image = atk_left1;}
                        if (spriteNum == 2) {image = atk_left2;}
                    }
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

            if (dying) {dyingAnimation(g2);}

            g2.drawImage(image, tempScreenX, tempScreenY, null);
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

        if (dyingCounter > i*8) {alive = false;}
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

    public String getOppositeDirection(String direction) {

        switch (direction) {
            case "up": return "down";
            case "down": return "up";
            case "right": return "left";
            case "left": return "right";
            default: return "";
        }
    }

}
