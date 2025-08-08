package entity;

import main.GamePanel;
import main.KeyHandler;
import object.Shield_Wood;
import object.Sword_Wood;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    KeyHandler kHandler;

    public final int screenX;
    public final int screenY;

    public String playerClass;
    public boolean attackCancelled = false;

    public Player(GamePanel gp, KeyHandler kHandler) {
        super(gp);
        this.kHandler = kHandler;

        screenX = (gp.SCREEN_WIDTH/2) - (gp.TILE_SIZE /2);
        screenY = (gp.SCREEN_HEIGHT/2) - (gp.TILE_SIZE /2);

        rect = new Rectangle(16, 19, 22, 25);
        default_rectX = rect.x;
        default_rectY = rect.y;

        attackArea.width = 36;
        attackArea.height = 36;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }

    public void setDefaultValues() {
        worldX = gp.TILE_SIZE * 23;
        worldY = gp.TILE_SIZE * 21;
        speed = 3;
        direction = "down";

        // Player Status
        level = 1;
        maxLife = 6;
        life = maxLife;
        strength = 1; // More strength = more damage dealt
        dexterity = 1; // More dexterity =  less damage received
        exp = 0;
        nextLevelExp = 5;
        gold = 0;
        currentWeapon = new Sword_Wood(gp);
        currentShield = new Shield_Wood(gp);
        attack = getAttackValue(); // Strength * weapon
        defense = getDefenseValue(); // Dexterity * shield
    }

    public int getAttackValue() {
        return attack = strength * currentWeapon.attackValue;
    }
    public int getDefenseValue() {
        return defense = dexterity * currentShield.defenseValue;
    }

    public void getPlayerImage() {
        up1 = setup("/player/walking/up_1");
        up2 = setup("/player/walking/up_2");
        down1 = setup("/player/walking/down_1");
        down2 = setup("/player/walking/down_2");
        right1 = setup("/player/walking/right_1");
        right2 = setup("/player/walking/right_2");
        left1 = setup("/player/walking/left_1");
        left2 = setup("/player/walking/left_2");
    }

    public void getPlayerAttackImage() {
        atk_up1 = setup("/player/attacking/up_1", gp.TILE_SIZE, gp.TILE_SIZE*2);
        atk_up2 = setup("/player/attacking/up_2", gp.TILE_SIZE, gp.TILE_SIZE*2);
        atk_down1 = setup("/player/attacking/down_1", gp.TILE_SIZE, gp.TILE_SIZE*2);
        atk_down2 = setup("/player/attacking/down_2", gp.TILE_SIZE, gp.TILE_SIZE*2);
        atk_right1 = setup("/player/attacking/right_1", gp.TILE_SIZE*2, gp.TILE_SIZE);
        atk_right2 = setup("/player/attacking/right_2", gp.TILE_SIZE*2, gp.TILE_SIZE);
        atk_left1 = setup("/player/attacking/left_1", gp.TILE_SIZE*2, gp.TILE_SIZE);
        atk_left2 = setup("/player/attacking/left_2", gp.TILE_SIZE*2, gp.TILE_SIZE);
    }

    public void update() {

        if (attacking) {
            playerAttack();
        }
        else if (kHandler.upKey || kHandler.downKey || kHandler.rightKey || kHandler.leftKey || kHandler.enterPressed) {
            if (kHandler.upKey) {
                direction = "up";
            }
            else if (kHandler.downKey) {
                direction = "down";
            }
            else if (kHandler.rightKey) {
                direction = "right";
            }
            else if (kHandler.leftKey) {
                direction = "left";
            }

            // Check Tile Collision
            checkCollision = false;
            gp.cHandler.checkTile(this);

            // Check Object Collision
            int objectIndex = gp.cHandler.checkObject(this, true);
            objectPickup(objectIndex);

            // Check NPC Collision
            int npcIndex = gp.cHandler.checkEntity(this, gp.npc);
            npcInteraction(npcIndex);

            // Check Monster Collision
            int monsterIndex = gp.cHandler.checkEntity(this, gp.monster);
            monsterInteraction(monsterIndex);

            // Check Events
            gp.eHandler.checkEvent();

            // Player Moves When No Collision
            if (!checkCollision && !kHandler.enterPressed) {
                switch(direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "right": worldX += speed; break;
                    case "left": worldX -= speed; break;
                }
            }

            if (kHandler.enterPressed && !attackCancelled) {
                gp.soundEffect(7);
                attacking = true;
                spriteCounter = 0;
            }
            attackCancelled = false;

            gp.kHandler.enterPressed = false;

            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                }
                else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

        // Invincible Counter
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void playerAttack() {
        spriteCounter++;
        if (spriteCounter <= 5) {spriteNum = 1;}
        if (spriteCounter > 5 && spriteCounter <= 25) {
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

            // check monster collision with updated worldX/worldY and rect
            int monsterIndex = gp.cHandler.checkEntity(this, gp.monster);
            damageMonster(monsterIndex);

            // restore original data
            worldX = currentWorldX;
            worldY = currentWorldY;
            rect.width = currentWidth;
            rect.height = currentHeight;
        }
        if (spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void objectPickup(int i) {
        if (i != -1) {}
    }

    public void npcInteraction(int i) {

        if (gp.kHandler.enterPressed) {
            if (i != -1) {
                attackCancelled = true;
                gp.gameState = gp.GS_DIALOGUE;
                gp.npc[i].speak();
            }
        }
    }

    public void monsterInteraction(int i) {
        if (i != -1) {
            if (!invincible) {
                gp.soundEffect(6);
                int damage = gp.monster[i].attack - defense;
                if (damage < 0) {
                    damage = 0;
                }
                life -= damage;
                invincible = true;
            }
        }
    }

    public void damageMonster(int i) {
        if (i != -1) {
            if (!gp.monster[i].invincible) {
                gp.soundEffect(5);
                int damage = attack - gp.monster[i].defense;
                if (damage < 0) {
                    damage = 0;
                }
                gp.monster[i].life -= damage;
                gp.ui.addMessage(damage + " damage!");

                gp.monster[i].invincible = true;
                gp.monster[i].damageReaction();

                if (gp.monster[i].life <= 0) {
                    gp.monster[i].dying = true;
                    gp.ui.addMessage("You killed the " + gp.monster[i].name + "!");
                    gp.ui.addMessage("Exp " + gp.monster[i].exp);
                    exp += gp.monster[i].exp;
                    checkLevelUp();
                }
            }
        }
    }

    public void checkLevelUp() {
        if (exp >= nextLevelExp) {
            level++;
            nextLevelExp = nextLevelExp*2;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttackValue();
            defense = getDefenseValue();

            gp.soundEffect(8);
            gp.gameState = gp.GS_DIALOGUE;
            gp.ui.currentDialogue = "You are level " + level + "now!\n" + "You feel stronger!";
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
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

        if (invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.drawImage(image, tempScreenX, tempScreenY, null);

        // Reset alpha transparency
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // Debug
        /*g2.setFont(new Font("Arial", Font.PLAIN, 26));
        g2.setColor(Color.white);
        g2.drawString("Invincible:"+invincibleCounter, 10, 400);*/
    }
}
