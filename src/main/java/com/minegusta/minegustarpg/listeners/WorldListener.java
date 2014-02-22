package com.minegusta.minegustarpg.listeners;

import com.minegusta.minegustarpg.MinegustaRPGPlugin;
import com.minegusta.minegustarpg.managers.HealthManager;
import com.minegusta.minegustarpg.scoreboard.ScoreBoardManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class WorldListener implements Listener {

    //On world switch.

    @EventHandler
    public void onGoToDonatorWorld(PlayerChangedWorldEvent e) {
        Player p = e.getPlayer();
        if (e.getPlayer().getWorld().getName().toLowerCase().equals(MinegustaRPGPlugin.world)) {
            ScoreBoardManager.createScoreBoard(p);
            ScoreBoardManager.setScoreboard(p);
            HealthManager.setRPGHealth(p);

        } else {
            ScoreBoardManager.clearScoreboard(p);
            HealthManager.setNormalHealth(p);
        }
    }
}
