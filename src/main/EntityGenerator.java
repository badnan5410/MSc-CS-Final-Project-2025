package main;

import entity.Entity;
import object.*;

public class EntityGenerator {
    GamePanel gp;

    public EntityGenerator(GamePanel gp) {
        this.gp = gp;
    }

    public Entity getObject(String itemName) {
        Entity obj = null;

        switch (itemName) {
            case Wood_Sword.objName: obj = new Wood_Sword(gp); break;
            case Wood_Shield.objName: obj = new Wood_Shield(gp); break;
            case Wood_Axe.objName: obj = new Wood_Axe(gp); break;
            case Iron_Sword.objName: obj = new Iron_Sword(gp); break;
            case Iron_Shield.objName: obj = new Iron_Shield(gp); break;
            case Iron_Axe.objName: obj = new Iron_Axe(gp); break;
            case Iron_Pickaxe.objName: obj = new Iron_Pickaxe(gp); break;
            case Potion_Red.objName: obj = new Potion_Red(gp); break;
            case Key.objName: obj = new Key(gp); break;
            case Lantern.objName: obj = new Lantern(gp); break;
            case Tent.objName: obj = new Tent(gp); break;
            case Boots.objName: obj = new Boots(gp); break;
            case Door.objName: obj = new Door(gp); break;
            case Iron_Door.objName: obj = new Iron_Door(gp); break;
            case Chest.objName: obj = new Chest(gp); break;
            case Coin.objName: obj = new Coin(gp); break;
            case Fireball.objName: obj = new Fireball(gp); break;
            case Slimeball_Green.objName: obj = new Slimeball_Green(gp); break;
            case Slimeball_Red.objName: obj = new Slimeball_Red(gp); break;
            case Heart.objName: obj = new Heart(gp); break;
            case Mana.objName: obj = new Mana(gp); break;
            case Icon_Fighter.objName: obj = new Icon_Fighter(gp); break;
            case Icon_Magician.objName: obj = new Icon_Magician(gp); break;
            case Icon_Thief.objName: obj = new Icon_Thief(gp); break;
        }

        return obj;
    }
}
