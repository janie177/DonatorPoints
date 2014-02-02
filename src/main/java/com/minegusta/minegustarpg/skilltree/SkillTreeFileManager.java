package com.minegusta.minegustarpg.skilltree;

import com.minegusta.minegustarpg.MinegustaRPGPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class SkillTreeFileManager {
    private static File file;
    private static FileConfiguration conf;
    private static Plugin p = MinegustaRPGPlugin.PLUGIN;

    public static void loadSkillTreeFile() {

        file = new File(p.getDataFolder() + "/skilltree/" + "skilltree.yml");
        File mapDir = new File(p.getDataFolder() + "/skilltree/");

        if (!mapDir.exists()) {
            try {
                mapDir.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
            conf = YamlConfiguration.loadConfiguration(file);
        }
    }

    private static File getFile() {
        return new File(p.getDataFolder() + "/skilltree/" + "skilltree.yml");
    }

    public static void saveSkillTreeFile() {
        try {
            conf.save(getFile());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static FileConfiguration getConf() {
        return conf;
    }
}
