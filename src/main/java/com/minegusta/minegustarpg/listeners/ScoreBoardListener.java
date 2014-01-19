package com.minegusta.minegustarpg.listeners;

import com.minegusta.minegustarpg.MinegustaRPGPlugin;
import com.minegusta.minegustarpg.scoreboard.ScoreBoardManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class ScoreBoardListener implements Listener {

    @EventHandler
    public void onGoToDonatorWorld(PlayerChangedWorldEvent e) {
        Player p = e.getPlayer();
        if (e.getPlayer().getWorld().getName().toLowerCase().equals(MinegustaRPGPlugin.world)) {
            ScoreBoardManager.setScoreboardForPlayer(p);

        } else if (e.getFrom().getName().toLowerCase().equalsIgnoreCase(MinegustaRPGPlugin.world)) {
            ScoreBoardManager.clearScoreBoardForPlayer(p);
        }
    }
}
