package com.minegusta.donatorpoints.listeners;


import com.minegusta.donatorpoints.DonatorPointsPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerListener implements Listener {

    //Stop players from killing villagers.
    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerButcherVillies(PlayerCommandPreprocessEvent e) {
        if (e.getPlayer().getWorld().getName().toLowerCase().equals(DonatorPointsPlugin.world)) {
            if (e.getMessage().toLowerCase().contains("butcher") && (e.getMessage().toLowerCase().contains("-n") || e.getMessage().contains("-f"))) {
                e.setCancelled(true);
            }
        } else if (e.getMessage().toLowerCase().contains("stoplag")) {
            e.setCancelled(true);
        }
    }
}
