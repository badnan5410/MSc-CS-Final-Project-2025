package entity;

import main.GamePanel;
import object.*;

public class NPC_Merchant extends Entity {

    public NPC_Merchant(GamePanel gp) {
        super(gp);
        direction = "down";
        speed = 1;
        type = TYPE_NPC;

        getImage();
        setDialogue();
        setItems();

        rect.x = 0;
        rect.y = 16;
        rect.width = 48;
        rect.height = 32;
        default_rectX = rect.x;
        default_rectY = rect.y;
    }

    public void getImage() {
        up1 = setup("/npc/merchant/down_1");
        up2 = setup("/npc/merchant/down_2");
        down1 = setup("/npc/merchant/down_1");
        down2 = setup("/npc/merchant/down_2");
        right1 = setup("/npc/merchant/down_1");
        right2 = setup("/npc/merchant/down_2");
        left1 = setup("/npc/merchant/down_1");
        left2 = setup("/npc/merchant/down_2");
    }

    public void setDialogue() {
        dialogues[0] = "He he, so you found me? I have some good stuff.\nAre interested in trading with me?";
    }

    public void setItems() {
        inventory.add(new Iron_Shield(gp));
        inventory.add(new Iron_Sword(gp));
        inventory.add(new Iron_Axe(gp));
        inventory.add(new Potion_Red(gp));
        inventory.add(new Key(gp));
        inventory.add(new Tent(gp));
        inventory.add(new Lantern(gp));
    }

    @Override
    public void speak() {
        super.speak();
        gp.gameState = gp.GS_TRADE;
        gp.ui.merchant = this;
    }
}