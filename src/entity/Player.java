package entity;

import main.GamePanel;
import main.KeyHandler;
import object.*;

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

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setInventory();
    }

    public void setDefaultValues() {
        worldX = gp.TILE_SIZE * 23;
        worldY = gp.TILE_SIZE * 21;

        /*worldX = gp.TILE_SIZE * 12;
        worldY = gp.TILE_SIZE * 12;
        gp.currentMap = 1;*/

        defaultSpeed = 4;
        speed = defaultSpeed;
        direction = "down";

        // Player Status
        type = TYPE_PLAYER;
        level = 1;
        maxLife = 6;
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        strength = 1; // More strength = more damage dealt
        dexterity = 1; // More dexterity =  less damage received
        exp = 0;
        nextLevelExp = 4;
        coins = 0;
        currentWeapon = new Wood_Sword(gp);
        currentShield = new Wood_Shield(gp);
        projectile = new Fireball(gp);
        attack = getAttackValue(); // Strength * weapon
        defense = getDefenseValue(); // Dexterity * shield
    }

    public void setDefaultPosition() {
        worldX = gp.TILE_SIZE * 23;
        worldY = gp.TILE_SIZE * 21;
        direction = "down";
    }

    public void restoreLifeAndMana() {
        life = maxLife;
        mana = maxMana;
        invincible = false;
    }

    public void setInventory() {
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new Iron_Axe(gp));
        inventory.add(new Key(gp));
    }

    public int getAttackValue() {
        attackArea = currentWeapon.attackArea;
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

        if (currentWeapon.type == TYPE_SWORD) {
            if (currentWeapon.name == "Hero's Sword") {
                atk_up1 = setup("/player/attacking/sword/iron/up_1", gp.TILE_SIZE, gp.TILE_SIZE*2);
                atk_up2 = setup("/player/attacking/sword/iron/up_2", gp.TILE_SIZE, gp.TILE_SIZE*2);
                atk_down1 = setup("/player/attacking/sword/iron/down_1", gp.TILE_SIZE, gp.TILE_SIZE*2);
                atk_down2 = setup("/player/attacking/sword/iron/down_2", gp.TILE_SIZE, gp.TILE_SIZE*2);
                atk_right1 = setup("/player/attacking/sword/iron/right_1", gp.TILE_SIZE*2, gp.TILE_SIZE);
                atk_right2 = setup("/player/attacking/sword/iron/right_2", gp.TILE_SIZE*2, gp.TILE_SIZE);
                atk_left1 = setup("/player/attacking/sword/iron/left_1", gp.TILE_SIZE*2, gp.TILE_SIZE);
                atk_left2 = setup("/player/attacking/sword/iron/left_2", gp.TILE_SIZE*2, gp.TILE_SIZE);
            }
            if (currentWeapon.name == "Wooden Sword") {
                atk_up1 = setup("/player/attacking/sword/wood/up_1", gp.TILE_SIZE, gp.TILE_SIZE*2);
                atk_up2 = setup("/player/attacking/sword/wood/up_2", gp.TILE_SIZE, gp.TILE_SIZE*2);
                atk_down1 = setup("/player/attacking/sword/wood/down_1", gp.TILE_SIZE, gp.TILE_SIZE*2);
                atk_down2 = setup("/player/attacking/sword/wood/down_2", gp.TILE_SIZE, gp.TILE_SIZE*2);
                atk_right1 = setup("/player/attacking/sword/wood/right_1", gp.TILE_SIZE*2, gp.TILE_SIZE);
                atk_right2 = setup("/player/attacking/sword/wood/right_2", gp.TILE_SIZE*2, gp.TILE_SIZE);
                atk_left1 = setup("/player/attacking/sword/wood/left_1", gp.TILE_SIZE*2, gp.TILE_SIZE);
                atk_left2 = setup("/player/attacking/sword/wood/left_2", gp.TILE_SIZE*2, gp.TILE_SIZE);
            }
        }
        if (currentWeapon.type == TYPE_AXE) {
            if (currentWeapon.name == "Basic Axe") {
                atk_up1 = setup("/player/attacking/axe/wood/up_1", gp.TILE_SIZE, gp.TILE_SIZE*2);
                atk_up2 = setup("/player/attacking/axe/wood/up_2", gp.TILE_SIZE, gp.TILE_SIZE*2);
                atk_down1 = setup("/player/attacking/axe/wood/down_1", gp.TILE_SIZE, gp.TILE_SIZE*2);
                atk_down2 = setup("/player/attacking/axe/wood/down_2", gp.TILE_SIZE, gp.TILE_SIZE*2);
                atk_right1 = setup("/player/attacking/axe/wood/right_1", gp.TILE_SIZE*2, gp.TILE_SIZE);
                atk_right2 = setup("/player/attacking/axe/wood/right_2", gp.TILE_SIZE*2, gp.TILE_SIZE);
                atk_left1 = setup("/player/attacking/axe/wood/left_1", gp.TILE_SIZE*2, gp.TILE_SIZE);
                atk_left2 = setup("/player/attacking/axe/wood/left_2", gp.TILE_SIZE*2, gp.TILE_SIZE);
            }
            if (currentWeapon.name == "Woodcutter's Axe") {
                atk_up1 = setup("/player/attacking/axe/iron/up_1", gp.TILE_SIZE, gp.TILE_SIZE*2);
                atk_up2 = setup("/player/attacking/axe/iron/up_2", gp.TILE_SIZE, gp.TILE_SIZE*2);
                atk_down1 = setup("/player/attacking/axe/iron/down_1", gp.TILE_SIZE, gp.TILE_SIZE*2);
                atk_down2 = setup("/player/attacking/axe/iron/down_2", gp.TILE_SIZE, gp.TILE_SIZE*2);
                atk_right1 = setup("/player/attacking/axe/iron/right_1", gp.TILE_SIZE*2, gp.TILE_SIZE);
                atk_right2 = setup("/player/attacking/axe/iron/right_2", gp.TILE_SIZE*2, gp.TILE_SIZE);
                atk_left1 = setup("/player/attacking/axe/iron/left_1", gp.TILE_SIZE*2, gp.TILE_SIZE);
                atk_left2 = setup("/player/attacking/axe/iron/left_2", gp.TILE_SIZE*2, gp.TILE_SIZE);
            }
        }
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
            monsterContact(monsterIndex);

            // Check Interactive Tile Collision
            int iTileIndex = gp.cHandler.checkEntity(this, gp.iTile);

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
            if (spriteCounter > 10) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                }
                else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

        if (gp.kHandler.shotKeyPressed && !projectile.alive && shotCooldownCounter == 30 && projectile.checkResource(this)) {
            projectile.set(worldX, worldY, direction, true, this);

            // use up mana
            projectile.subtractResource(this);

            // check if there's space
            for (int i = 0; i < gp.projectile[1].length; i++) {
                if (gp.projectile[gp.currentMap][i] == null) {
                    gp.projectile[gp.currentMap][i] = projectile;
                    break;
                }
            }

            shotCooldownCounter = 0;
            gp.soundEffect(12);
        }

        // Invincible Counter
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if (shotCooldownCounter < 30) {shotCooldownCounter++;}

        if (life > maxLife) {life = maxLife;}

        if (mana > maxMana) {mana = maxMana;}

        if (life <= 0) {
            gp.gameState = gp.GS_GAME_OVER;
            gp.soundEffect(15);
        }

        if (maxLife > 12) {maxLife = 12;}
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
            damageMonster(monsterIndex, attack, currentWeapon.knockBackPower);

            int iTileIndex = gp.cHandler.checkEntity(this, gp.iTile);
            damageInteractiveTile(iTileIndex);

            int projectileIndex = gp.cHandler.checkEntity(this, gp.projectile);
            damageProjectile(projectileIndex);

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

        if (i != -1) {

            // pickup objects
            if (gp.obj[gp.currentMap][i].type == TYPE_PICKUP) {
                gp.obj[gp.currentMap][i].useItem(this);
                gp.obj[gp.currentMap][i] = null;
            }

            // obstacle
            else if (gp.obj[gp.currentMap][i].type == TYPE_OBSTACLE) {

                if (gp.kHandler.enterPressed) {
                    attackCancelled = true;
                    gp.obj[gp.currentMap][i].interact();
                }
            }

            // inventory items
            else {
                String text;

                if (inventory.size() != INVENTORY_CAPACITY) {
                    inventory.add(gp.obj[gp.currentMap][i]);
                    gp.soundEffect(1);
                    text = "Got a " + gp.obj[gp.currentMap][i].name + "!";
                    gp.obj[gp.currentMap][i] = null;// this line ruined me
                }
                else {text = "Inventory is full!";}

                gp.ui.addMessage(text);
            }
        }
    }

    public void npcInteraction(int i) {

        if (gp.kHandler.enterPressed) {
            if (i != -1) {
                attackCancelled = true;
                gp.gameState = gp.GS_DIALOGUE;
                gp.npc[gp.currentMap][i].speak();
            }
        }
    }

    public void monsterContact(int i) {
        if (i != -1) {
            if (!invincible && !gp.monster[gp.currentMap][i].dying) {
                gp.soundEffect(6);
                int damage = gp.monster[gp.currentMap][i].attack - defense;
                if (damage > 0) {
                    life -= damage;
                }
                else {
                    life--;
                }
                invincible = true;
            }
        }
    }

    public void damageMonster(int i, int attack, int power) {

        if (i != -1) {

            if (!gp.monster[gp.currentMap][i].invincible) {
                gp.soundEffect(5);

                if (currentWeapon.knockBackPower > 0) {
                    knockBack(gp.monster[gp.currentMap][i], knockBackPower);
                }

                int damage = attack - gp.monster[gp.currentMap][i].defense;
                gp.monster[gp.currentMap][i].life -= damage;
                gp.ui.addMessage(damage + " damage!");

                gp.monster[gp.currentMap][i].invincible = true;
                gp.monster[gp.currentMap][i].damageReaction();

                if (gp.monster[gp.currentMap][i].life <= 0) {
                    gp.monster[gp.currentMap][i].dying = true;
                    gp.ui.addMessage("You killed the " + gp.monster[gp.currentMap][i].name + "!");
                    gp.soundEffect(1);
                    gp.ui.addMessage("Exp " + gp.monster[gp.currentMap][i].exp);
                    exp += gp.monster[gp.currentMap][i].exp;
                    checkLevelUp();
                }
            }
        }
    }

    public void knockBack(Entity entity, int power) {
        entity.direction = direction;
        entity.speed += power;
        entity.knockback = true;
    }

    public void damageInteractiveTile(int i) {
        if (i != -1 && gp.iTile[gp.currentMap][i].destructible && gp.iTile[gp.currentMap][i].checkTool(this) && !gp.iTile[gp.currentMap][i].invincible) {
            gp.iTile[gp.currentMap][i].soundEffect();
            gp.iTile[gp.currentMap][i].life -= currentWeapon.value;
            gp.iTile[gp.currentMap][i].invincible = true;

            generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);

            if (gp.iTile[gp.currentMap][i].life <= 0) {
                gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedImage();
            }
        }
    }

    public void damageProjectile(int i) {

        if (i != -1) {
            Entity projectile = gp.projectile[gp.currentMap][i];
            projectile.alive = false;
            generateParticle(projectile, projectile);
        }
    }

    public void checkLevelUp() {
        if (exp >= nextLevelExp) {
            level++;
            nextLevelExp = (int)(5 * Math.pow(1.3, level));
            exp = 0;
            maxLife += 2;
            life = maxLife;
            mana = maxMana;
            strength++;
            dexterity++;
            attack = getAttackValue();
            defense = getDefenseValue();

            gp.soundEffect(8);
            gp.gameState = gp.GS_DIALOGUE;
            gp.ui.currentDialogue = "You are level " + level + " now!\n" + "You feel stronger!";
        }
    }

    public void selectItem() {
        int itemIndex = gp.ui.getSlotIndex(gp.ui.playerSlotCol, gp.ui.playerSlotRow);

        if (itemIndex < inventory.size()) {
            Entity selectedItem = inventory.get(itemIndex);

            if (selectedItem.type == TYPE_SWORD || selectedItem.type == TYPE_AXE) {
                currentWeapon = selectedItem;
                attack = getAttackValue();
                getPlayerAttackImage();
            }
            if (selectedItem.type == TYPE_SHIELD) {
                currentShield = selectedItem;
                defense = getDefenseValue();
            }
            if (selectedItem.type == TYPE_CONSUMABLE) {
                if (selectedItem.useItem(this)) {inventory.remove(itemIndex);};
            }
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
