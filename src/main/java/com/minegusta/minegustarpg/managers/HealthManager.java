package com.minegusta.minegustarpg.managers;

import com.minegusta.minegustarpg.MinegustaRPGPlugin;
import com.minegusta.minegustarpg.playerdata.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class HealthManager {

    public static void setNormalHealth(Player p) {
        p.setHealthScale(2.0);
        p.setMaxHealth(20);
    }

    public static void setRPGHealth(Player p) {
        int level = Data.getLevel(p.getUniqueId());
        p.setHealthScaled(true);
        p.setHealthScale(3.0);
        double maxHealth = 10 + level;
        if (maxHealth > 100) maxHealth = 100;
        p.setMaxHealth(maxHealth);
    }


    public static void checkForHaxedHealth() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getWorld().getName().toLowerCase().equalsIgnoreCase(MinegustaRPGPlugin.world)) {
                setNormalHealth(p);
            }
        }
    }
}
