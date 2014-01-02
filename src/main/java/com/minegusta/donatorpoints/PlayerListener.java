package com.minegusta.donatorpoints;


import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerListener implements Listener {

    //Stop players from killing villagers.
    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerButcherVillies(PlayerCommandPreprocessEvent e) {
        if (e.getPlayer().getWorld().getName().equals(DonatorPointsPlugin.world)) {
            if (e.getMessage().toLowerCase().contains("butcher") && e.getMessage().toLowerCase().contains("-n")) {
                e.setCancelled(true);
            }
        }
    }
}
