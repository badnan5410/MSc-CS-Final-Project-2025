package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Base type for all in-game actors (player, NPCs, monsters, projectiles, items, interactives).
 * Stores world/screen position, hitbox, sprites, stats, and state flags.
 * Exposes shared behavior: movement/AI hooks, combat, collision, rendering, and particles.
 */
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
    public String[][] dialogues = new String[20][20];
    Color brightRed = new Color(255, 0, 30);
    Color darkGrey = new Color(35, 35, 35);
    public Entity attacker;
    public Entity linkedEntity;
    public boolean temp = false;

    // State
    public int worldX, worldY;
    public String direction = "down";
    public int spriteNum = 1;
    public int dialogueSet = 0;
    public int dialogueIndex = 0;
    public boolean checkCollision = false;
    public boolean invincible = false;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    public boolean hpBarOn = false;
    public boolean destructible = false;
    public boolean onPath = false;
    public boolean knockback = false;
    public String knockBackDirection;
    public boolean guarding = false;
    public boolean transparent = false;
    public boolean offBalance = false;
    public Entity loot;
    public boolean opened = false;
    public boolean enraged = false;
    public boolean isSleeping = false;
    public boolean isDrawing = true;

    // Counters
    public int spriteCounter = 0;
    public int movementCounter = 0;
    public int invincibleCounter = 0;
    public int dyingCounter = 0;
    public int hpBarCounter = 0;
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
    public boolean isBossMonster = false;

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
    public final int TYPE_PICKAXE = 10;

    /**
     * Constructs a base entity with a game context.
     *
     * @param gp game panel reference
     */
    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * @return screen X based on world position relative to the player camera.
     */
    public int getScreenX() {
        return worldX - gp.player.worldX + gp.player.screenX;
    }

    /**
     * @return screen Y based on world position relative to the player camera.
     */
    public int getScreenY() {
        return worldY - gp.player.worldY + gp.player.screenY;
    }

    /**
     * @return world-space left edge of the hitbox.
     */
    public int getLeftX() {
        return worldX + rect.x;
    }

    /**
     * @return world-space right edge of the hitbox.
     */
    public int getRightX() {
        return worldX + rect.x + rect.width;
    }

    /**
     * @return world-space top edge of the hitbox.
     */
    public int getTopY() {
        return worldY + rect.y;
    }

    /**
     * @return world-space bottom edge of the hitbox.
     */
    public int getBottomY() {
        return worldY + rect.y + rect.height;
    }

    /**
     * @return tile column from entity world position (top-left).
     */
    public int getCol() {
        return worldX/gp.TILE_SIZE;
    }

    /**
     * @return tile row from entity world position (top-left).
     */
    public int getRow() {
        return worldY/gp.TILE_SIZE;
    }

    /**
     * @return world-space X of sprite center (uses up1 width).
     */
    public int getCenterX() {
        return worldX + up1.getWidth()/2;
    }

    /**
     * @return world-space Y of sprite center (uses left1 height).
     */
    public int getCenterY() {
        return worldY + left1.getHeight()/2;
    }

    /**
     * @param target other entity
     * @return absolute X distance between centers in pixels
     */
    public int getDistanceX(Entity target) {
        return Math.abs(getCenterX() - target.getCenterX());
    }

    /**
     * @param target other entity
     * @return absolute Y distance between centers in pixels
     */
    public int getDistanceY(Entity target) {
        return Math.abs(getCenterY() - target.getCenterY());
    }

    /**
     * Manhattan distance in tiles between centers.
     *
     * @param target other entity
     * @return tile distance
     */
    public int getTileDistance(Entity target) {
        return (getDistanceX(target) + getDistanceY(target))/gp.TILE_SIZE;
    }

    /**
     * Tile column of the target's hitbox origin (top-left).
     *
     * @param target other entity
     * @return column index
     */
    public int getEndCol(Entity target) {return (target.worldX + target.rect.x)/gp.TILE_SIZE;}

    /**
     * Tile row of the target's hitbox origin (top-left).
     *
     * @param target other entity
     * @return row index
     */
    public int getEndRow(Entity target) {return (target.worldY + target.rect.y)/gp.TILE_SIZE;}

    /**
     * Resets common counters (animation, timers, status) to zero.
     */
    public void resetCounter() {
        spriteCounter = 0;
        movementCounter = 0;
        invincibleCounter = 0;
        dyingCounter = 0;
        hpBarCounter = 0;
        shotCooldownCounter = 0;
        knockBackCounter = 0;
        guardCounter = 0;
        offBallanceCounter = 0;
    }

    /**
     * assigns loot for containers/enemies (override in subclasses).
     *
     * @param loot
     */
    public void setLoot(Entity loot) {}

    /**
     * per-tick AI/state decision (override in subclasses).
     */
    public void setAction() {}

    /**
     * applies movement in a direction (override in subclasses).
     *
     * @param direction
     */
    public void move(String direction) {}

    /**
     * reaction when taking damage (override in subclasses).
     */
    public void damageReaction() {}

    /**
     * starts speaking/queues dialogue (override in NPCs).
     */
    public void speak() {}

    /**
     * Faces the player by flipping this entity's direction.
     */
    public void faceThePlayer() {

        switch(gp.player.direction) {
            case "up": direction = "down"; break;
            case "down": direction = "up"; break;
            case "right": direction = "left"; break;
            case "left": direction = "right"; break;
        }
    }

    /**
     * Enters dialogue state using an entity and dialogue set index.
     *
     * @param entity the speaker entity
     * @param setNum dialogue set id
     */
    public void startDialogue(Entity entity, int setNum) {
        gp.gameState = gp.GS_DIALOGUE;
        gp.ui.npc = entity;
        dialogueSet = setNum;
    }

    /**
     * interaction when the player presses action near this entity.
     */
    public void interact() {}

    /**
     * uses/consumes this entity as an item.
     *
     * @param entity consumer (usually player)
     * @return true if consumed/used
     */
    public boolean useItem(Entity entity) {return false;}

    /**
     * drops items on death or open (override in subclasses).
     */
    public void checkDrop() {}

    /**
     * Drops an item at this entity's world position into the first free object slot on the current map.
     *
     * @param droppedItem pre-instantiated item to place
     */
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

    /**
     * @return particle color for this entity's effects (override in subclasses).
     */
    public Color getParticleColor() {
        return new Color(0, 0, 0);
    }

    /**
     * @return particle square size in pixels (override in subclasses).
     */
    public int getParticleSize() {
        return -1;
    }

    /**
     * @return particle speed in pixels/tick (override in subclasses).
     */
    public int getParticleSpeed() {
        return -1;
    }

    /**
     * @return particle lifetime in ticks (override in subclasses).
     */
    public int getParticleMaxLife() {
        return -1;
    }

    /**
     * Spawns a 4-way burst of particles around a target using this entity's particle presets.
     *
     * @param generator source entity providing color/size/speed/life
     * @param target entity the particles originate from
     */
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

    /**
     * Runs collision checks against tiles, objects, entities, interactive tiles, and the player.
     * Sets {@code checkCollision} when blocked. If this entity is a monster and touches the player, it applies damage via {@code damagePlayer(attack)}.
     */
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

    /**
     * Advances the entity one tick.
     *
     * Flow:
     * 1) Sleep check – returns early if {@code isSleeping}.
     *
     * 2) Knockback – temporarily sets {@code direction} to {@code knockBackDirection}, probes collisions, moves if free, cancels on hit or after 10 ticks, then restores direction.
     *
     * 3) Attack – if {@code attacking} is true, delegates to {@link #attacking()}.
     *
     * 4) Normal AI/move – calls {@link #setAction()}, then {@link #checkCollision()}. If not blocked, translates by {@code speed} in {@code direction}. Toggles 2-frame walk animation every 24 ticks.
     *
     * 5) Timers – updates status counters:
     * invincibility (40 ticks), shot cooldown (up to 30), off-balance (60 ticks).
     */
    public void update() {

        if (!isSleeping) {

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

            else if (attacking) {
                attacking();
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

                spriteCounter++;

                if (spriteCounter > 24) {

                    if (spriteNum == 1) {
                        spriteNum = 2;
                    }

                    else if (spriteNum == 2) {
                        spriteNum = 1;
                    }

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

            if (shotCooldownCounter < 30) {
                shotCooldownCounter++;
            }

            if (offBalance) {
                offBallanceCounter++;

                if (offBallanceCounter > 60) {
                    offBalance = false;
                    offBallanceCounter = 0;
                }
            }
        }
    }

    /**
     * Scales monster attack/defense by day phase.
     *
     * @param rate boost step applied at evening/morning; doubled at night
     */
    public void monsterBoost(int rate) {
        int dayState = gp.eManager.lighting.dayState;

        switch (dayState) {
            case 0: attack = defaultAttack; defense = defaultDefense; break;
            case 1: attack = defaultAttack+rate; defense = defaultDefense+rate; break;
            case 2: attack = defaultAttack+(rate*2); defense = defaultDefense+(rate*2); break;
            case 3: attack = defaultAttack+rate; defense = defaultDefense+rate; break;
        }
    }

    /**
     * Decides whether the monster starts a melee attack when the player is in front within given vertical/horizontal ranges. Uses a 1/rate chance.
     *
     * @param rate random trigger denominator (lower = more likely)
     * @param verticalRange max vertical distance (pixels) along facing axis
     * @param horizontalRange max horizontal distance (pixels) perpendicular axis
     */
    public void checkIfMonsterAttack(int rate, int verticalRange, int horizontalRange) {
        boolean isTargetInRange = false;
        int dx = getDistanceX(gp.player);
        int dy = getDistanceY(gp.player);

        switch (direction) {
            case "up":
                isTargetInRange = gp.player.getCenterY() < getCenterY()
                        && dy < verticalRange && dx < horizontalRange;
                break;
            case "down":
                isTargetInRange = gp.player.getCenterY() > getCenterY()
                        && dy < verticalRange && dx < horizontalRange;
                break;
            case "left":
                isTargetInRange = gp.player.getCenterX() < getCenterX()
                        && dx < horizontalRange && dy < verticalRange;
                break;
            case "right":
                isTargetInRange = gp.player.getCenterX() > getCenterX()
                        && dx < horizontalRange && dy < verticalRange;
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

    /**
     * Fires the monster's projectile on a 1/rate chance if cooled down.
     *
     * @param rate random trigger denominator (lower = more likely)
     * @param shotInterval required cooldown ticks between shots
     */
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

    /**
     * Randomly clears aggro if the player is farther than {@code distance} tiles.
     *
     * @param target the player (or target entity)
     * @param distance tile distance threshold
     * @param rate 1/rate chance to drop aggro when beyond threshold
     */
    public void checkIfPlayerOutOfAggro(Entity target, int distance, int rate) {

        if (getTileDistance(target) > distance) {
            int i = new Random().nextInt(rate);

            if (i == 0) {
                onPath = false;
            }
        }
    }

    /**
     * Randomly sets aggro if the player is closer than {@code distance} tiles.
     *
     * @param target the player (or target entity)
     * @param distance tile distance threshold
     * @param rate 1/rate chance to gain aggro when within threshold
     */
    public void checkIfPlayerInAggro(Entity target, int distance, int rate) {

        if (getTileDistance(target) < distance) {
            int i = new Random().nextInt(rate);

            if (i == 0) {
                onPath = true;
            }
        }
    }

    /**
     * Picks a new facing direction every {@code interval} ticks with equal chance.
     *
     * @param interval ticks between direction rolls
     */
    public void getRandomDirection(int interval) {
        movementCounter++;

        if (movementCounter > interval) {
            Random rand = new Random();
            int i = rand.nextInt(100)+1;

            if (i <= 25) {direction = "up";}

            if (i > 25 && i <= 50) {direction = "down";}

            if (i > 50 && i <= 75) {direction = "right";}

            if (i > 75 && i <= 100) {direction = "left";}

            movementCounter = 0;
        }
    }

    /**
     * Nudges facing toward the player every {@code interval} ticks, choosing the dominant axis (X vs Y).
     *
     * @param interval ticks between facing updates
     */
    public void moveTowardsThePlayer(int interval) {
        movementCounter++;

        if (movementCounter > interval) {

            if (getDistanceX(gp.player) > getDistanceY(gp.player)) {
                if (gp.player.getCenterX() < getCenterX()) {direction = "left";}
                else {direction = "right";}
            }
            else if (getDistanceX(gp.player) < getDistanceY(gp.player)) {
                if (gp.player.getCenterY() < getCenterY()) {direction = "up";}
                else {direction = "down";}
            }

            movementCounter = 0;
        }
    }

    /**
     * Runs the attack animation and applies hitboxes.
     * Phase 1 shows wind-up; phase 2 expands the hitbox in the facing direction, checks collisions, applies damage/knockback, then restores position/rect.
     */
    public void attacking() {
        spriteCounter++;

        if (spriteCounter <= motion1_duration) {
            spriteNum = 1;
        }

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

    /**
     * Applies damage and combat reactions to the player.
     * Honors guard direction, supports parry (quick guard) vs block, plays SFX/messages, triggers knockback and i-frames.
     *
     * @param attack raw attacker damage before defense and guards
     */
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

            }
            else {
                gp.soundEffect(6);
            }

            if (damage < 1) {damage = 1;}

            gp.player.life -= damage;
            gp.ui.addMessage("You take " + damage + " damage!");

            if (knockBackPower > 0) {
                setKnockBack(gp.player, this, knockBackPower);
            }

            gp.player.invincible = true;
        }
    }

    /**
     * Starts knockback on a target away from an attacker.
     * Sets target direction to attacker’s facing, bumps speed, and flags knockback.
     *
     * @param target entity to push
     * @param attacker source of knockback
     * @param power speed bonus applied during knockback
     */
    public void setKnockBack(Entity target, Entity attacker, int power) {
        this.attacker = attacker;
        target.knockBackDirection = attacker.direction;
        target.speed += power;
        target.knockback = true;
    }

    /**
     * Returns true if this entity is within the camera view (with margin).
     */
    public boolean entityIsInCamera() {
        boolean isInCamera = false;
        int i = 5;

        if (worldX + gp.TILE_SIZE*i > gp.player.worldX - gp.player.screenX && worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.screenX && worldY + gp.TILE_SIZE*i > gp.player.worldY - gp.player.screenY && worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.screenY) {
            isInCamera = true;
        }

        return isInCamera;
    }

    /**
     * Renders this entity if it’s within the camera.
     * Chooses the correct sprite by direction, attack state, and frame, applies invincibility tint and dying animation, and draws at world→screen coords (with offsets for attack frames).
     *
     * @param g2 the game’s Graphics2D context
     */
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        if (entityIsInCamera()) {

            int tempScreenX = getScreenX();
            int tempScreenY = getScreenY();

            switch (direction) {
                case "up":

                    if (!attacking) {
                        if (spriteNum == 1) {image = up1;}
                        if (spriteNum == 2) {image = up2;}
                    }
                    if (attacking) {
                        tempScreenY = getScreenY() - up1.getHeight();
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
                        tempScreenX = getScreenX() - left1.getWidth();
                        if (spriteNum == 1) {image = atk_left1;}
                        if (spriteNum == 2) {image = atk_left2;}
                    }
                    break;
            }

            if (invincible && !destructible) {
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2, 0.4f);
            }

            if (dying) {
                dyingAnimation(g2);
            }

            g2.drawImage(image, tempScreenX, tempScreenY, null);
            changeAlpha(g2, 1f);
        }
    }

    /**
     * Plays a blink/fade-out sequence, then flags the entity as dead.
     * Toggles alpha several times using a fixed step window, and sets {@code alive=false} at the end.
     * Caller should restore alpha after drawing.
     *
     * @param g2 the Graphics2D context used for alpha changes
     */
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

    /**
     * Sets the global alpha on the provided Graphics2D.
     * Note: this does not restore the previous Composite; caller must reset alpha when done.
     *
     * @param g2 the graphics context
     * @param alphaValue opacity in [0.0, 1.0]
     */
    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    /**
     * Loads a PNG from the classpath and scales it to the tile size.
     *
     * @param imagePath path without the .png suffix (e.g. "/player/walking/down_1")
     * @return a scaled BufferedImage, or {@code null} if loading fails
     */
    public BufferedImage setup(String imagePath) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, gp.TILE_SIZE, gp.TILE_SIZE);

        }

        catch(IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    /**
     * Loads a PNG from the classpath and scales it to the given size.
     *
     * @param imagePath path without the .png suffix
     * @param width target width in pixels
     * @param height target height in pixels
     * @return a scaled BufferedImage, or {@code null} if loading fails
     */
    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);

        }

        catch(IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    /**
     * Uses A* to steer the entity one step toward a target tile.
     * Computes the entity’s current tile from its world position and hitbox, queries the global PathFinder, then sets {@code direction} toward the next node.
     * If the next node is the end tile, it disables {@code onPath}.
     *
     * @param endCol destination column (tile coords)
     * @param endRow destination row (tile coords)
     */
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

    /**
     * Looks one tile ahead of a user entity to find a target by name.
     * Computes the next tile in the user's facing direction and scans the target grid on the current map for an entity at that tile with the given name.
     *
     * @param user the searching entity
     * @param target the 2D target array (e.g., npc, monster, iTile)
     * @param targetName exact name to match
     * @return index in the target array if found; -1 otherwise
     */
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

    /**
     * Returns the opposite facing direction string.
     *
     * @param direction one of "up", "down", "left", "right"
     * @return the opposite direction, or empty string if unknown
     */
    public String getOppositeDirection(String direction) {

        switch (direction) {
            case "up": return "down";
            case "down": return "up";
            case "right": return "left";
            case "left": return "right";
            default: return "";
        }
    }

    /**
     * Base coin value for this entity when dropped or counted.
     * Subclasses override to return their specific worth.
     *
     * @return coin value (default 0)
     */
    public int getCoinValue() {
        return 0;
    }

}
