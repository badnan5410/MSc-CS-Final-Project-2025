package entity;

import main.GamePanel;
import main.KeyHandler;
import object.*;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The controllable player entity: holds stats, equipment, sprites, and input-driven behavior.
 */
public class Player extends Entity {
    KeyHandler kHandler;
    public final int screenX;
    public final int screenY;
    public String playerClass;
    public boolean attackCancelled = false;
    public boolean lightUpdated = false;

    /**
     * Creates a player bound to the game panel and key handler, and initializes defaults.
     *
     * @param gp game context
     * @param kHandler input source
     */
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

    /**
     * Resets core state: position, base stats, equipment, sprites, and inventory.
     * Also prepares dialogue lines.
     */
    public void setDefaultValues() {
        worldX = gp.TILE_SIZE * 23; // starting pos
        worldY = gp.TILE_SIZE * 21;

//        worldX = gp.TILE_SIZE * 11; // dungeon 1 entrance
//        worldY = gp.TILE_SIZE * 8;

//        worldX = gp.TILE_SIZE * 12; // inside shop
//        worldY = gp.TILE_SIZE * 12;
//        gp.currentMap = 1;

        worldX = gp.TILE_SIZE * 9; // dungeon 2 entrance
        worldY = gp.TILE_SIZE * 7;
        gp.currentMap = 2;

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

    /**
     * Applies class-specific bonuses after class selection.
     * Fighter: HP/STR; Magician: MP/projectile; Thief: speed/DEX/coins.
     */
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

    /**
     * Sends the player to the default spawn on the main map facing down.
     */
    public void setDefaultPosition() {
        gp.currentMap = 0;
        worldX = gp.TILE_SIZE * 23;
        worldY = gp.TILE_SIZE * 21;
        direction = "down";
    }

    /**
     * Prepares player dialogue strings (e.g., level-up message).
     */
    public void setDialogue() {
        dialogues[0][0] = "You are level " + level + " now!\n" + "You feel stronger!" + "\n\n[press enter]";
    }

    /**
     * Restores transient status to healthy defaults and flags light to refresh.
     * Resets invincibility, attack/guard, knockback, and speed.
     */
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

    /**
     * Rebuilds inventory with the currently equipped weapon and shield.
     */
    public void setInventory() {
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new Key(gp));
        inventory.add(new Key(gp));
        inventory.add(new Iron_Sword(gp));
        inventory.add(new Iron_Shield(gp));
        inventory.add(new Lantern(gp));
        inventory.add(new Iron_Pickaxe(gp));
    }

    /**
     * Recomputes attack value and attack animation timings from the equipped weapon.
     *
     * @return computed attack value
     */
    public int getAttackValue() {
        attackArea = currentWeapon.attackArea;
        motion1_duration = currentWeapon.motion1_duration;
        motion2_duration = currentWeapon.motion2_duration;
        return attack = strength + currentWeapon.attackValue;
    }

    /**
     * Recomputes defense value from the equipped shield.
     *
     * @return computed defense value
     */
    public int getDefenseValue() {
        return defense = dexterity + currentShield.defenseValue;
    }

    /**
     * Finds the inventory slot index of the equipped weapon.
     *
     * @return slot index (0-based)
     */
    public int getCurrentWeaponSlot() {
        int currentWeaponSlot = 0;

        for (int i = 0; i < inventory.size(); i++) {

            if (inventory.get(i) == currentWeapon) {
                currentWeaponSlot = i;
            }
        }

        return currentWeaponSlot;
    }

    /**
     * Finds the inventory slot index of the equipped shield.
     *
     * @return slot index (0-based)
     */
    public int getCurrentShieldSlot() {
        int currentShieldSlot = 0;

        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i) == currentShield) {

                currentShieldSlot = i;
            }
        }

        return currentShieldSlot;
    }

    /**
     * Loads movement sprites for all four directions at tile size.
     */
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

    /**
     * Overrides all movement sprites with a single image (used for sleeping).
     *
     * @param image sprite to use for every direction
     */
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

    /**
     * Loads attack animation sprites based on the equipped weapon type and name.
     * Swords, axes, and pickaxes use different sprite sheets and sizes.
     */
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

    /**
     * Loads guard sprites based on the equipped shield.
     */
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

    /**
     * Per-frame player update.
     *
     * Processing order:
     * 1) Knockback — temporarily overrides direction, predicts collisions, moves, then cancels after 10 ticks.
     *
     * 2) Attack — runs attack animation and hit logic while {@code attacking} is true.
     *
     * 3) Guard — if SPACE is held and not attacking/knockback, enables guarding and increments {@code guardCounter}.
     *
     * 4) Movement/interaction — on WASD/ENTER:
     *    - Resolve collisions (tiles, objects, NPCs, monsters, iTiles).
     *    - Trigger events.
     *    - Move if clear and ENTER not pressed.
     *    - If ENTER pressed (and not cancelled), start a melee attack and play SFX.
     *
     * 5) Shooting — if shot key is pressed and cooldown/resources allow, spawn the configured {@code projectile}, spend mana, and play SFX.
     *
     * 6) Timers & caps — advance i-frames and shot cooldown, clamp life/mana, handle death (unless in god mode), and enforce UI-driven max HP/MP limits.
     *
     * Side effects: adjusts world position, toggles state flags, plays sounds, and may change {@code gp.gameState} (e.g., on death).
     */
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

        if (shotCooldownCounter < 30) {
            shotCooldownCounter++;
        }

        if (life > maxLife) {
            life = maxLife;
        }

        if (mana > maxMana) {
            mana = maxMana;
        }

        if (!kHandler.godMode) {

            if (life <= 0) {
                gp.gameState = gp.GS_GAME_OVER;
                gp.ui.cNum = -1;
                gp.soundEffect(15);
            }
        }

        if (maxLife > 32) {
            maxLife = 32;
        }

        if (playerClass.equals("Magician")) {

            if (maxMana > 16) {
                maxMana = 16;
            }
        }
        else {

            if (maxMana > 8) {
                maxMana = 8;
            }
        }
    }

    /**
     * Handles picking up or interacting with an object at the given index.
     *
     * Rules:
     * - TYPE_PICKUP: uses the item immediately and removes it.
     * - TYPE_OBSTACLE: requires ENTER; cancels an attack and calls interact().
     * - Else: tries to add to inventory; on success plays SFX and removes it; else shows "full".
     *
     * Side effects: plays sounds, adds UI messages, mutates world object array, inventory, and attack state.
     *
     * @param i index into {@code gp.obj[gp.currentMap]} (-1 means nothing hit)
     */
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

    /**
     * Handles NPC interaction when colliding with an NPC at the given index.
     *
     * - On ENTER: cancels attack and triggers the NPC's dialogue via speak().
     * - Always calls NPC.move(direction) to let the NPC react/follow/face.
     *
     * @param i index into {@code gp.npc[gp.currentMap]} (-1 means none)
     */
    public void npcInteraction(int i) {

        if (i != -1) {

            if (gp.kHandler.enterPressed) {
                attackCancelled = true;
                gp.npc[gp.currentMap][i].speak();
            }

            gp.npc[gp.currentMap][i].move(direction);
        }
    }

    /**
     * Applies contact damage from a monster the player is touching.
     *
     * Preconditions: monster is not dying and player is not currently invincible.
     * Damage: (monster.attack - player.defense) / 3, minimum 1.
     * Sets player invincibility frames and shows a damage message + SFX.
     *
     * @param i index into {@code gp.monster[gp.currentMap]} (-1 means none)
     */
    public void monsterContact(int i) {

        if (i != -1) {

            if (!invincible && !gp.monster[gp.currentMap][i].dying) {
                gp.soundEffect(6);

                int damage = (gp.monster[gp.currentMap][i].attack - defense)/3;

                if (damage < 1) {
                    damage = 1;
                }

                gp.player.life -= damage;
                gp.ui.addMessage("You take " + damage + " damage!");
                invincible = true;
            }
        }
    }

    /**
     * Applies player damage to a monster.
     *
     * Flow:
     * - Skips if target is invincible.
     * - Optional knockback (melee only, not projectiles).
     * - Triples damage if monster is off-balance.
     * - Damage = attack - defense, minimum 1; shows message + SFX.
     * - Marks monster invincible and triggers its damageReaction().
     * - On death: flags dying, grants EXP, shows messages, and checks level-up.
     *
     * @param i                index into {@code gp.monster[gp.currentMap]}
     * @param attacker         the entity dealing damage (usually {@code this})
     * @param attack           raw attack value to apply
     * @param knockBackPower   knockback strength to apply on hit
     * @param isProjectile     true if from a projectile (affects knockback logic)
     */
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

    /**
     * Damages a destructible interactive tile the player is hitting.
     *
     * Requirements: tile is destructible, passes tool check, and not currently invincible.
     * Effects: plays SFX, reduces tile life by weapon value, spawns particles,
     * and replaces the tile with its destroyed form at 0 life.
     *
     * @param i index into {@code gp.iTile[gp.currentMap]} (-1 means none)
     */
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

    /**
     * Breaks a hostile projectile the player has struck.
     *
     * Sets {@code alive=false} and spawns break particles for the projectile.
     *
     * @param i index into {@code gp.projectile[gp.currentMap]} (-1 means none)
     */
    public void breakProjectile(int i) {

        if (i != -1) {
            Entity projectile = gp.projectile[gp.currentMap][i];
            projectile.alive = false;
            generateParticle(projectile, projectile);
        }
    }

    /**
     * Levels the player up when EXP meets the threshold.
     *
     * Effects: increments level, recalculates nextLevelExp, restores HP/MP, increases core stats, refreshes attack/defense, plays SFX, and shows a level-up dialogue.
     */
    public void checkIfPlayerLevelUp() {

        if (exp >= nextLevelExp) {
            level++;
            nextLevelExp = (int)(5 * Math.pow(1.3, level));
            exp = 0;
            maxLife += 2;
            life = maxLife;
            maxMana += 1;
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

    /**
     * Equips/uses the item at the currently selected inventory slot.
     *
     * Behavior:
     * - Weapons/Axes/Pickaxes: equips and refreshes attack sprites.
     * - Shields: equips and refreshes guard sprites.
     * - Lights: toggles as active light and flags lighting update.
     * - Consumables: uses the item; decrements stack or removes it.
     */
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

                if (currentLight == selectedItem) {
                    currentLight = null;
                }

                else {
                    currentLight = selectedItem;
                }

                lightUpdated = true;
            }

            if (selectedItem.type == TYPE_CONSUMABLE) {

                if (selectedItem.useItem(this)) {

                    if (selectedItem.amount > 1) {
                        selectedItem.amount--;
                    }

                    else {
                        inventory.remove(itemIndex);
                    }
                }
            }
        }
    }

    /**
     * Finds the index of the first inventory item with the given name.
     *
     * @param itemName exact item name to search
     * @return index if found, otherwise -1
     */
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

    /**
     * Determines whether the given world item can be added to the inventory, and performs the add/stack if possible.
     *
     * Stackables: increments existing stack or inserts a new copy if space is available.
     * Non-stackables: inserts a new copy if space is available.
     *
     * @param item the world item entity to obtain (its name is used to create a new copy)
     * @return true if obtained/stacked; false if inventory is full
     */
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

    /**
     * Renders the player sprite at the screen center.
     *
     * Chooses the correct frame based on direction and state (idle, walking, attacking with attack-offset, guarding). Applies a fade effect while invincible, respects the isDrawing flag, and restores the Graphics2D composite afterward.
     *
     * @param g2 the graphics context to draw with
     */
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
//        g2.setColor(Color.red);
//        g2.setStroke(new BasicStroke(4));
//        g2.drawRect(screenX + rect.x, screenY + rect.y, rect.width, rect.height);

        /*g2.setFont(new Font("Arial", Font.PLAIN, 26));
        g2.setColor(Color.white);
        g2.drawString("Invincible:"+invincibleCounter, 10, 400);*/
    }
}
