package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {
    GamePanel gp;

    public int worldX, worldY;
    public int speed;

    public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
    public String direction = "down";

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle rect = new Rectangle(0, 0, 48, 48);
    public int default_rectX, default_rectY;
    public boolean checkCollision = false;

    public int movementCounter = 0;

    public boolean invincible = false;
    public int invincibleCounter = 0;

    String[] dialogues = new String[20];
    int dialogueIndex = 0;

    public BufferedImage image1, image2, image3;
    public String name;
    public boolean collision = false;

    public int type;

    // Character Status
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
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.screenX && worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.screenX && worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.screenY && worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.screenY) {

            switch (direction) {
                case "up":
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                    break;
                case "down":
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                    break;
                case "right":
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                    break;
                case "left":
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                    break;
            }

            g2.drawImage(image, screenX, screenY, gp.TILE_SIZE, gp.TILE_SIZE, null);
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
}
