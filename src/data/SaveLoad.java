package data;

import main.GamePanel;
import java.io.*;

/**
 * Saves and loads a minimal snapshot of game state.
 * Uses {@link DataStorage} as a DTO and Java serialization to "save.dat".
 */
public class SaveLoad {
    GamePanel gp;

    /**
     * Links this saver/loader to the running game.
     *
     * @param gp game context
     */
    public SaveLoad(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Serializes the current game state to disk.
     * Captures player stats, inventory (names + amounts),
     * equipped slots, and per-map placed objects (position, loot, opened).
     * Output file: {@code save.dat} in the working directory.
     */
    public void save() {

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));
            DataStorage ds = new DataStorage();

            // store the players stats
            ds.level = gp.player.level;
            ds.playerClass = gp.player.playerClass;
            ds.maxLife = gp.player.maxLife;
            ds.life = gp.player.life;
            ds.maxMana = gp.player.maxMana;
            ds.mana = gp.player.mana;
            ds.strength = gp.player.strength;
            ds.dexterity = gp.player.dexterity;
            ds.defaultSpeed = gp.player.defaultSpeed;
            ds.exp = gp.player.exp;
            ds.nextLevelExp = gp.player.nextLevelExp;
            ds.coins = gp.player.coins;

            // store the players inventory
            for (int i = 0; i < gp.player.inventory.size(); i++) {
                ds.itemNames.add(gp.player.inventory.get(i).name);
                ds.itemAmounts.add(gp.player.inventory.get(i).amount);
            }

            // player equipped items
            ds.currentWeaponSlot = gp.player.getCurrentWeaponSlot();
            ds.currentShieldSlot = gp.player.getCurrentShieldSlot();

            // objects on map
            ds.mapObjectNames = new String[gp.maxMap][gp.obj[1].length];
            ds.mapObjectWorldX = new int[gp.maxMap][gp.obj[1].length];
            ds.mapObjectWorldY = new int[gp.maxMap][gp.obj[1].length];
            ds.mapObjectLootNames = new String[gp.maxMap][gp.obj[1].length];
            ds.mapObjectOpened = new boolean[gp.maxMap][gp.obj[1].length];
            
            for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {

                for (int i = 0; i < gp.obj[1].length; i++) {

                    if (gp.obj[mapNum][i] == null) {
                        ds.mapObjectNames[mapNum][i] = "N/a";
                    }

                    else {
                        ds.mapObjectNames[mapNum][i] = gp.obj[mapNum][i].name;
                        ds.mapObjectWorldX[mapNum][i] = gp.obj[mapNum][i].worldX;
                        ds.mapObjectWorldY[mapNum][i] = gp.obj[mapNum][i].worldY;

                        if (gp.obj[mapNum][i].loot != null) {
                            ds.mapObjectLootNames[mapNum][i] = gp.obj[mapNum][i].loot.name;
                        }

                        ds.mapObjectOpened[mapNum][i] = gp.obj[mapNum][i].opened;
                    }
                }
            }

            // write the DataStorage object
            oos.writeObject(ds);
        }

        catch (Exception e) {
            System.out.println("Save Exception!");
        }
    }

    /**
     * Deserializes game state from disk and applies it to the current session.
     * Restores player stats, rebuilds inventory via {@code EntityGenerator}, re-equips items, and recreates map objects including opened state.
     */
    public void load() {

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));

            // read the DataStorage object
            DataStorage ds = (DataStorage)ois.readObject();

            // pass the restored data to player's status
            gp.player.level = ds.level;
            gp.player.playerClass = ds.playerClass;
            gp.player.maxLife = ds.maxLife;
            gp.player.life = ds.life;
            gp.player.maxMana = ds.maxMana;
            gp.player.mana = ds.mana;
            gp.player.strength = ds.strength;
            gp.player.dexterity = ds.dexterity;
            gp.player.defaultSpeed = ds.defaultSpeed;
            gp.player.exp = ds.exp;
            gp.player.nextLevelExp = ds.nextLevelExp;
            gp.player.coins = ds.coins;

            // players inventory
            gp.player.inventory.clear();

            for (int i = 0; i < ds.itemNames.size(); i++) {
                gp.player.inventory.add(gp.eGenerator.getObject(ds.itemNames.get(i)));
                gp.player.inventory.get(i).amount = ds.itemAmounts.get(i);
            }

            // player equipped items
            gp.player.currentWeapon = gp.player.inventory.get(ds.currentWeaponSlot);
            gp.player.currentShield = gp.player.inventory.get(ds.currentShieldSlot);
            gp.player.getAttackValue();
            gp.player.getDefenseValue();
            gp.player.getAttackImage();
            gp.player.getGuardImage();

            // objects on map
            for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {

                for (int i = 0; i < gp.obj[1].length; i++) {

                    if (ds.mapObjectNames[mapNum][i].equals("N/a")) {
                        gp.obj[mapNum][i] = null;
                    }

                    else {
                        gp.obj[mapNum][i] = gp.eGenerator.getObject(ds.mapObjectNames[mapNum][i]);
                        gp.obj[mapNum][i].worldX = ds.mapObjectWorldX[mapNum][i];
                        gp.obj[mapNum][i].worldY = ds.mapObjectWorldY[mapNum][i];

                        if (ds.mapObjectLootNames[mapNum][i] != null) {
                            gp.obj[mapNum][i].setLoot(gp.eGenerator.getObject(ds.mapObjectLootNames[mapNum][i]));
                        }

                        gp.obj[mapNum][i].opened = ds.mapObjectOpened[mapNum][i];

                        if (gp.obj[mapNum][i].opened) {
                            gp.obj[mapNum][i].down1 = gp.obj[mapNum][i].image2;
                        }
                    }
                }
            }
        }

        catch (Exception e) {
            System.out.println("Load Exception!");
        }
    }
}
