package com.minegusta.minegustarpg.managers;

import com.minegusta.minegustarpg.MinegustaRPGPlugin;
import com.minegusta.minegustarpg.listeners.LevelListener;
import com.minegusta.minegustarpg.scoreboard.ScoreBoardManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SaveManager {


    //Scheduled task.

    public static int enableTimedTask() {
        return Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(MinegustaRPGPlugin.PLUGIN, new Runnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.getWorld().getName().toLowerCase().equalsIgnoreCase(MinegustaRPGPlugin.world)) {
                        ScoreBoardManager.updateScoreboard(p);
                        ScoreBoardManager.setScoreboard(p);

                        //Use this task as well for un-equipping armour.

                        LevelListener.unEquipGlitchedArmour(p);

                    }
                }

                //Removing bad health bugs.

                HealthManager.checkForHaxedHealth();

            }
        }, 0, 30 * 20);
    }
}
