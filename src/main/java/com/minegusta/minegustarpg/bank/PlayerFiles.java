package com.minegusta.minegustarpg.bank;

import com.minegusta.minegustarpg.MinegustaRPGPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class PlayerFiles {

    private static File file;
    private static FileConfiguration conf;
    private static Plugin p = MinegustaRPGPlugin.PLUGIN;


    public static FileConfiguration createAndLoadBankFile(String mojangID) {
        try {
            file = new File(p.getDataFolder() + "/banks/", mojangID + ".yml");
            if (!file.exists()) {
                file.createNewFile();
            }
            conf = YamlConfiguration.loadConfiguration(file);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return conf;
    }

    private static File getBankFile(String mojangID) {
        return new File(p.getDataFolder() + "/banks/", mojangID + ".yml");
    }

    public static void saveBankFile(String mojangID, FileConfiguration f) {
        try {
            file = getBankFile(mojangID);
            f.save(file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createBankDirectory() {
        File banks = new File(p.getDataFolder() + "/banks/");
        if (!banks.exists()) {
            try {
                banks.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
