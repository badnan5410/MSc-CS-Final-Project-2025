package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

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

    // State
    public int worldX, worldY;
    public String direction = "down";
    public int spriteNum = 1;
    int dialogueIndex = 0;
    public boolean checkCollision = false;
    public boolean invincible = false;
    boolean attacking = false;

    // Counters
    public int spriteCounter = 0;
    public int movementCounter = 0;
    public int invincibleCounter = 0;

    // Character Status
    public int type;
    public String name;
    public int speed;
    public int maxLife;
    public int life;


    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void setAction() {}

    public void speak() {
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch(gp.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "right":
                direction = "left";
                break;
            case "left":
                direction = "right";
                break;
        }
    }

    public void update() {
        setAction();
        checkCollision = false;
        gp.cHandler.checkTile(this);
        gp.cHandler.checkObject(this, false);
        gp.cHandler.checkEntity(this, gp.npc);
        gp.cHandler.checkEntity(this, gp.monster);
        boolean touchPlayer = gp.cHandler.checkPlayer(this);

        if (this.type == 2 && touchPlayer) {
            if (!gp.player.invincible) {
                gp.player.life--;
                gp.player.invincible = true;
            }
        }

        if (!checkCollision) {
            switch(direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
            }
        }

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

        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
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

            if (invincible) {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            }

            g2.drawImage(image, screenX, screenY, gp.TILE_SIZE, gp.TILE_SIZE, null);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
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
}
