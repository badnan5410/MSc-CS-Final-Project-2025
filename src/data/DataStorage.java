package data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Serializable snapshot of game state for save/load.
 * Holds player stats, inventory, and map object data.
 */
public class DataStorage implements Serializable {

    // player stats
    int level;
    String playerClass;
    int maxLife;
    int life;
    int maxMana;
    int mana;
    int strength;
    int dexterity;
    int defaultSpeed;
    int exp;
    int nextLevelExp;
    int coins;

    // player inventory
    ArrayList<String> itemNames = new ArrayList<>();
    ArrayList<Integer> itemAmounts = new ArrayList<>();
    int currentWeaponSlot;
    int currentShieldSlot;

    // objects on the map
    String mapObjectNames[][];
    int mapObjectWorldX[][];
    int mapObjectWorldY[][];
    String mapObjectLootNames[][];
    boolean mapObjectOpened[][];
}
