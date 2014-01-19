package com.minegusta.donatorpoints.listeners;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.minegusta.donatorpoints.DonatorPointsPlugin;
import com.minegusta.donatorpoints.playerdata.Data;
import com.minegusta.donatorpoints.scoreboard.ScoreBoardManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

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

    ConcurrentMap<String, List<ItemStack>> invMap = Maps.newConcurrentMap();

    //Add deaths to player.
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (e.getEntity().getWorld().getName().toLowerCase().equals(DonatorPointsPlugin.world)) {
            Player player = e.getEntity();
            UUID uuid = player.getUniqueId();

            //add death

            Data.addDeaths(uuid, 1);

            //player inv managing.

            List<ItemStack> inv = Lists.newArrayList();
            List<ItemStack> remainingInv = Lists.newArrayList();

            for (ItemStack i : player.getInventory().getContents()) {
                remainingInv.add(i);
            }

            for (ItemStack i : player.getInventory().getArmorContents()) {
                inv.add(i);
                remainingInv.remove(i);
            }
            if (player.getInventory().getItem(0) != null) {
                inv.add(player.getInventory().getItem(0));
                remainingInv.remove(player.getInventory().getItem(0));
            }

            invMap.put(player.getName(), inv);
            e.getDrops().clear();
            for (ItemStack i : remainingInv) {
                e.getDrops().add(i);
            }


        }
    }

    //Adding armor back after dead.
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        if (!e.getPlayer().getWorld().getName().toLowerCase().equals(DonatorPointsPlugin.world)) return;
        Player player = e.getPlayer();
        List<ItemStack> inv = invMap.get(player.getName());
        if (invMap.containsKey(player.getName())) {
            for (ItemStack i : inv)
                player.getInventory().addItem(i);
            invMap.remove(player.getName());
        }

    }

    //Add scoreboards on login to people in the right world.
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (p.getWorld().getName().toLowerCase().equals(DonatorPointsPlugin.world)) {
            ScoreBoardManager.setScoreboardForPlayer(p);
        }
    }
}
