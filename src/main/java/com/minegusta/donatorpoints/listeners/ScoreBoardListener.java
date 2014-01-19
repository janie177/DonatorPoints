package com.minegusta.donatorpoints.listeners;

import com.minegusta.donatorpoints.DonatorPointsPlugin;
import com.minegusta.donatorpoints.scoreboard.ScoreBoardManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class ScoreBoardListener implements Listener {

    @EventHandler
    public void onGoToDonatorWorld(PlayerChangedWorldEvent e) {
        Player p = e.getPlayer();
        if (e.getPlayer().getWorld().getName().toLowerCase().equals(DonatorPointsPlugin.world)) {
            ScoreBoardManager.setScoreboardForPlayer(p);

        } else if (e.getFrom().getName().toLowerCase().equalsIgnoreCase(DonatorPointsPlugin.world)) {
            ScoreBoardManager.clearScoreBoardForPlayer(p);
        }
    }
}
