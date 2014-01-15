package com.minegusta.donatorpoints.playerdata;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class PlayerData {

    public static FileConfiguration horseData;
    public static File horseDataFile;
    public static FileConfiguration statusData;
    public static File statusDataFile;
    public static FileConfiguration levelData;
    public static File levelDataFile;


    public static void loadHorseData(Plugin p) {
        try {

            horseDataFile = new File(p.getDataFolder(), "horsedata.yml");

            if (!horseDataFile.exists()) {
                p.saveResource("horsedata.yml", false);
                Bukkit.getLogger().info("Successfully created " + horseDataFile.getName() + ".");
            }
            horseData = YamlConfiguration.loadConfiguration(horseDataFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadStatusData(Plugin p) {
        try {

            statusDataFile = new File(p.getDataFolder(), "statusdata.yml");

            if (!statusDataFile.exists()) {
                p.saveResource("statusdata.yml", false);
                Bukkit.getLogger().info("Successfully created " + statusDataFile.getName() + ".");
            }
            statusData = YamlConfiguration.loadConfiguration(statusDataFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadLevelData(Plugin p) {
        try {

            levelDataFile = new File(p.getDataFolder(), "leveldata.yml");

            if (!levelDataFile.exists()) {
                p.saveResource("leveldata.yml", false);
                Bukkit.getLogger().info("Successfully created " + levelDataFile.getName() + ".");
            }
            levelData = YamlConfiguration.loadConfiguration(levelDataFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadPlayerData(Plugin p) {
        loadLevelData(p);
        loadHorseData(p);
        loadStatusData(p);
    }


    public static void saveData() {
        try {
            horseData.save(horseDataFile);
            statusData.save(statusDataFile);
            levelData.save(levelDataFile);

        } catch (Exception ignored) {
            Bukkit.getLogger().severe(ChatColor.RED + "Could not save Data!");
        }
    }
}
