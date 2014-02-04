package com.minegusta.minegustarpg.scoreboard;

import com.minegusta.minegustarpg.MinegustaRPGPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class ScoreBoardListener implements Listener {

    //On world switch.

    @EventHandler
    public void onGoToDonatorWorld(PlayerChangedWorldEvent e) {
        Player p = e.getPlayer();
        if (e.getPlayer().getWorld().getName().toLowerCase().equals(MinegustaRPGPlugin.world)) {
            ScoreBoardManager.setScoreboard(p);

        } else if (e.getFrom().getName().toLowerCase().equalsIgnoreCase(MinegustaRPGPlugin.world)) {
            ScoreBoardManager.clearScoreboard(p);
        }
    }
}
