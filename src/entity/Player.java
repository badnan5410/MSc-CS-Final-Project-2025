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
    public boolean lightUpdated = false;

    public Player(GamePanel gp, KeyHandler kHandler) {
        super(gp);
        this.kHandler = kHandler;

        screenX = (gp.SCREEN_WIDTH/2) - (gp.TILE_SIZE /2);
        screenY = (gp.SCREEN_HEIGHT/2) - (gp.TILE_SIZE /2);

        rect = new Rectangle(16, 19, 25, 22);
        default_rectX = rect.x;
        default_rectY = rect.y;

        setDefaultValues();
    }

    public void setDefaultValues() {
        worldX = gp.TILE_SIZE * 23; // starting pos
        worldY = gp.TILE_SIZE * 21;

//        worldX = gp.TILE_SIZE * 11; // dungeon 1 entrance
//        worldY = gp.TILE_SIZE * 8;

//        worldX = gp.TILE_SIZE * 12; // inside shop
//        worldY = gp.TILE_SIZE * 12;
//        gp.currentMap = 1;

//        worldX = gp.TILE_SIZE * 9; // dungeon 2 entrance
//        worldY = gp.TILE_SIZE * 7;
//        gp.currentMap = 2;

        worldX = gp.TILE_SIZE * 25; // starting pos
        worldY = gp.TILE_SIZE * 10;
        gp.currentMap = 3;

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
        currentLight = null;
        projectile = new Fireball(gp);
        attack = getAttackValue(); // Strength * weapon
        defense = getDefenseValue(); // Dexterity * shield

        // call getter methods
        getImage();
        getAttackImage();
        getGuardImage();
        setInventory();
        setDialogue();
    }

    public void playerClassBonus() {

        switch (playerClass) {
            case "Fighter":
                maxLife = 8;
                life = maxLife;
                strength = 2;
                attack = getAttackValue();
                break;
            case "Magician":
                maxMana = 6;
                mana = maxMana;
                projectile.attack = 4;
                projectile.speed = 7;
                projectile.maxLife = 80;
                break;
            case "Thief":
                defaultSpeed = 5;
                speed = defaultSpeed;
                dexterity = 2;
                defense = getDefenseValue();
                coins = 5;
                break;
        }
    }

    public void setDefaultPosition() {
        gp.currentMap = 0;
        worldX = gp.TILE_SIZE * 23;
        worldY = gp.TILE_SIZE * 21;
        direction = "down";
    }

    public void setDialogue() {
        dialogues[0][0] = "You are level " + level + " now!\n" + "You feel stronger!" + "\n\n[press enter]";
    }

    public void restoreStatus() {
        life = maxLife;
        mana = maxMana;
        speed = defaultSpeed;
        invincible = false;
        attacking = false;
        guarding = false;
        knockback = false;
        lightUpdated = true;
    }

    public void setInventory() {
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new Lantern(gp));
        inventory.add(new Iron_Pickaxe(gp));
        inventory.add(new Iron_Sword(gp));
        inventory.add(new Iron_Shield(gp));
        inventory.add(new Potion_Blue(gp));
    }

    public int getAttackValue() {
        attackArea = currentWeapon.attackArea;
        motion1_duration = currentWeapon.motion1_duration;
        motion2_duration = currentWeapon.motion2_duration;
        return attack = strength + currentWeapon.attackValue;
    }

    public int getDefenseValue() {
        return defense = dexterity + currentShield.defenseValue;
    }

    public int getCurrentWeaponSlot() {
        int currentWeaponSlot = 0;

        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i) == currentWeapon) {
                currentWeaponSlot = i;
            }
        }

        return currentWeaponSlot;
    }

    public int getCurrentShieldSlot() {
        int currentShieldSlot = 0;

        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i) == currentShield) {
                currentShieldSlot = i;
            }
        }

        return currentShieldSlot;
    }

    public void getImage() {
        up1 = setup("/player/walking/up_1");
        up2 = setup("/player/walking/up_2");
        down1 = setup("/player/walking/down_1");
        down2 = setup("/player/walking/down_2");
        right1 = setup("/player/walking/right_1");
        right2 = setup("/player/walking/right_2");
        left1 = setup("/player/walking/left_1");
        left2 = setup("/player/walking/left_2");
    }

    public void getSleepingImage(BufferedImage image)  {
        up1 = image;
        up2 = image;
        down1 = image;
        down2 = image;
        right1 = image;
        right2 = image;
        left1 = image;
        left2 = image;
    }

    public void getAttackImage() {

        if (currentWeapon.type == TYPE_SWORD) {
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
        }

        if (currentWeapon.type == TYPE_AXE) {
            if (currentWeapon.name == "Wooden Axe") {
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

        if (currentWeapon.type == TYPE_PICKAXE) {
            atk_up1 = setup("/player/attacking/pickaxe/up_1", gp.TILE_SIZE, gp.TILE_SIZE*2);
            atk_up2 = setup("/player/attacking/pickaxe/up_2", gp.TILE_SIZE, gp.TILE_SIZE*2);
            atk_down1 = setup("/player/attacking/pickaxe/down_1", gp.TILE_SIZE, gp.TILE_SIZE*2);
            atk_down2 = setup("/player/attacking/pickaxe/down_2", gp.TILE_SIZE, gp.TILE_SIZE*2);
            atk_right1 = setup("/player/attacking/pickaxe/right_1", gp.TILE_SIZE*2, gp.TILE_SIZE);
            atk_right2 = setup("/player/attacking/pickaxe/right_2", gp.TILE_SIZE*2, gp.TILE_SIZE);
            atk_left1 = setup("/player/attacking/pickaxe/left_1", gp.TILE_SIZE*2, gp.TILE_SIZE);
            atk_left2 = setup("/player/attacking/pickaxe/left_2", gp.TILE_SIZE*2, gp.TILE_SIZE);
        }
    }

    public void getGuardImage() {

        if (currentShield.name == "Wooden Shield") {
            guardUp = setup("/player/guarding/wooden/up_1");
            guardDown = setup("/player/guarding/wooden/down_1");
            guardRight = setup("/player/guarding/wooden/right_1");
            guardLeft = setup("/player/guarding/wooden/left_1");
        }
        if (currentShield.name == "Hero's Shield") {
            guardUp = setup("/player/guarding/iron/up_1");
            guardDown = setup("/player/guarding/iron/down_1");
            guardRight = setup("/player/guarding/iron/right_1");
            guardLeft = setup("/player/guarding/iron/left_1");
        }
    }

    public void update() {

        if (knockback) {
            String prevDir = direction;
            direction = knockBackDirection;
            checkCollision = false;
            collision = false;
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

        else if (attacking) {
            attacking();
        }
        else if (kHandler.spacePressed) {
            guarding = true;
            guardCounter++;
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
            guarding = false;
            guardCounter = 0;
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
            guarding = false;
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

        if (!kHandler.godMode) {
            if (life <= 0) {
                gp.gameState = gp.GS_GAME_OVER;
                gp.ui.cNum = -1;
                gp.soundEffect(15);
            }
        }

        if (maxLife > 32) {maxLife = 32;}
        if (maxLife > 8) {maxMana = 8;}
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

                if (isItemObtainable(gp.obj[gp.currentMap][i])) {
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

        if (i != -1) {
            if (gp.kHandler.enterPressed) {
                attackCancelled = true;
                gp.npc[gp.currentMap][i].speak();
            }

            gp.npc[gp.currentMap][i].move(direction);
        }
    }

    public void monsterContact(int i) {
        if (i != -1) {
            if (!invincible && !gp.monster[gp.currentMap][i].dying) {
                gp.soundEffect(6);

                int damage = gp.monster[gp.currentMap][i].attack - defense;

                // clamp: anything below 1 is 0
                if (damage < 1) damage = 0;

                if (damage > 0) {
                    life -= damage;
                    gp.ui.addMessage("You take " + damage + " damage!");
                } else {
                    gp.ui.addMessage("You take no damage!");
                }

                invincible = true;
            }
        }
    }

    public void damageMonster(int i, Entity attacker, int attack, int knockBackPower, boolean isProjectile) {

        if (i != -1) {

            if (!gp.monster[gp.currentMap][i].invincible) {
                gp.soundEffect(5);

                if (currentWeapon.knockBackPower > 0 && !isProjectile) {
                    setKnockBack(gp.monster[gp.currentMap][i], attacker , knockBackPower);

                }

                if (gp.monster[gp.currentMap][i].offBalance) {
                    attack *= 3;
                }

                int damage = attack - gp.monster[gp.currentMap][i].defense;

                if (damage > 0) {
                    gp.monster[gp.currentMap][i].life -= damage;
                    gp.ui.addMessage("You deal " + damage + " damage!");
                }
                else {
                    gp.monster[gp.currentMap][i].life--;
                    gp.ui.addMessage("You deal 1 damage!");
                }


                gp.monster[gp.currentMap][i].invincible = true;
                gp.monster[gp.currentMap][i].damageReaction();

                if (gp.monster[gp.currentMap][i].life <= 0) {
                    gp.monster[gp.currentMap][i].dying = true;
                    gp.ui.addMessage("You killed the " + gp.monster[gp.currentMap][i].name + "!");
                    gp.soundEffect(1);
                    gp.ui.addMessage("Exp " + gp.monster[gp.currentMap][i].exp);
                    exp += gp.monster[gp.currentMap][i].exp;
                    checkIfPlayerLevelUp();
                }
            }
        }
    }

    public void damageInteractiveTile(int i) {

        if (i != -1 && gp.iTile[gp.currentMap][i].destructible && gp.iTile[gp.currentMap][i].checkTool(this) && !gp.iTile[gp.currentMap][i].invincible) {
            gp.iTile[gp.currentMap][i].soundEffect();
            gp.iTile[gp.currentMap][i].life -= currentWeapon.value;
            gp.iTile[gp.currentMap][i].invincible = true;
            generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);

            if (gp.iTile[gp.currentMap][i].life <= 0) {
                gp.iTile[gp.currentMap][i].checkDrop();
                gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedImage();
            }
        }
    }

    public void breakProjectile(int i) {

        if (i != -1) {
            Entity projectile = gp.projectile[gp.currentMap][i];
            projectile.alive = false;
            generateParticle(projectile, projectile);
        }
    }

    public void checkIfPlayerLevelUp() {

        if (exp >= nextLevelExp) {
            level++;
            nextLevelExp = (int)(5 * Math.pow(1.3, level));
            exp = 0;
            maxLife += 2;
            life = maxLife;
            maxMana++;
            mana = maxMana;
            strength++;
            dexterity++;
            attack = getAttackValue();
            defense = getDefenseValue();
            gp.soundEffect(8);

            setDialogue();
            startDialogue(this, 0);
        }
    }

    public void selectItemInInventory() {
        int itemIndex = gp.ui.getSlotIndex(gp.ui.playerSlotCol, gp.ui.playerSlotRow);

        if (itemIndex < inventory.size()) {
            Entity selectedItem = inventory.get(itemIndex);

            if (selectedItem.type == TYPE_SWORD || selectedItem.type == TYPE_AXE || selectedItem.type == TYPE_PICKAXE) {
                currentWeapon = selectedItem;
                attack = getAttackValue();
                getAttackImage();
            }

            if (selectedItem.type == TYPE_SHIELD) {
                currentShield = selectedItem;
                defense = getDefenseValue();
                getGuardImage();
            }

            if (selectedItem.type == TYPE_LIGHT) {
                if (currentLight == selectedItem) {currentLight = null;}
                else {currentLight = selectedItem;}
                lightUpdated = true;
            }

            if (selectedItem.type == TYPE_CONSUMABLE) {
                if (selectedItem.useItem(this)) {
                    if (selectedItem.amount > 1) {selectedItem.amount--;}
                    else {inventory.remove(itemIndex);}
                }
            }
        }
    }

    public int searchInventory(String itemName) {
        int itemIndex = -1;

        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).name.equals(itemName)) {
                itemIndex = i;
                break;
            }
        }

        return itemIndex;
    }

    public boolean isItemObtainable(Entity item) {
        boolean itemObtainable = false;
        Entity newItem = gp.eGenerator.getObject(item.name);

        // check if item stackable
        if (newItem.stackable) {
            int index = searchInventory(newItem.name);

            if (index != -1) {
                inventory.get(index).amount++;
                itemObtainable = true;
            }
            else { // this is new item, check vacancy
                if (inventory.size() != INVENTORY_CAPACITY) {
                    inventory.add(newItem);
                    itemObtainable = true;
                }
            }
        }
        else { // item not stackable, check vacancy
            if (inventory.size() != INVENTORY_CAPACITY) {
                inventory.add(newItem);
                itemObtainable = true;
            }
        }

        return itemObtainable;
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
                if (guarding) {image = guardUp;}

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
                if (guarding) {image = guardDown;}

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
                if (guarding) {image = guardRight;}

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
                if (guarding) {image = guardLeft;}

                break;
        }

        if (invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        if (isDrawing) {
            g2.drawImage(image, tempScreenX, tempScreenY, null);
        }

        // Reset alpha transparency
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // Debug
        /*g2.setFont(new Font("Arial", Font.PLAIN, 26));
        g2.setColor(Color.white);
        g2.drawString("Invincible:"+invincibleCounter, 10, 400);*/
    }
}
