package main;

import entity.Entity;
import object.*;

/**
 * Builds Entity instances by name. This is the simple factory for items, projectiles, pickups, and special objects.
 *
 * Depends on each class exposing a unique {@code objName} constant.
 */
public class EntityGenerator {
    GamePanel gp;

    /**
     * Keep a handle to the game so it can pass it into new entities.
     *
     * @param gp
     */
    public EntityGenerator(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Returns a new Entity for the given name, or {@code null} if it doesn't recognise it.
     * The names are the static {@code objName} fields on each class.
     *
     * @param itemName the object key (e.g., {@code Iron_Sword.objName})
     * @return a fresh Entity bound to this GamePanel, or {@code null} if unknown
     */
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
            case Potion_Blue.objName: obj = new Potion_Blue(gp); break;
            case Key.objName: obj = new Key(gp); break;
            case Lantern.objName: obj = new Lantern(gp); break;
            case Tent.objName: obj = new Tent(gp); break;
            case Door.objName: obj = new Door(gp); break;
            case Iron_Door.objName: obj = new Iron_Door(gp); break;
            case Chest.objName: obj = new Chest(gp); break;
            case Coin_Copper.objName: obj = new Coin_Copper(gp); break;
            case Coin_Silver.objName: obj = new Coin_Silver(gp); break;
            case Coin_Gold.objName: obj = new Coin_Gold(gp); break;
            case Fireball.objName: obj = new Fireball(gp); break;
            case Slimeball_Green.objName: obj = new Slimeball_Green(gp); break;
            case Slimeball_Red.objName: obj = new Slimeball_Red(gp); break;
            case Heart.objName: obj = new Heart(gp); break;
            case Mana.objName: obj = new Mana(gp); break;
            case Icon_Fighter.objName: obj = new Icon_Fighter(gp); break;
            case Icon_Magician.objName: obj = new Icon_Magician(gp); break;
            case Icon_Thief.objName: obj = new Icon_Thief(gp); break;
            case Treasure.objName: obj = new Treasure(gp); break;
        }

        return obj;
    }
}
