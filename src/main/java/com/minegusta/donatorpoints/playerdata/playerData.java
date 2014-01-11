package com.minegusta.donatorpoints.playerdata;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class PlayerData {

    public static FileConfiguration playerData;
    public static File playerDataFile;


    public static void loadPlayerData(Plugin p) {
        try {
            playerDataFile = new File(p.getDataFolder(), "playerdata.yml");


            if (!playerDataFile.exists()) {
                p.saveResource("playerdata.yml", false);
                playerData = YamlConfiguration.loadConfiguration(playerDataFile);
                playerData.options().copyDefaults(true);
                playerData.save(playerDataFile);

                Bukkit.getLogger().info("Successfully created playerdata.yml");
            }
            playerData = YamlConfiguration.loadConfiguration(playerDataFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void savePlayerData() {
        try {
            playerData.save(playerDataFile);
        } catch (Exception ignored) {
            Bukkit.getLogger().severe(ChatColor.RED + "Could not save playerData!");
        }
    }
}
