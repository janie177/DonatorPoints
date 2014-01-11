package com.minegusta.donatorpoints.listeners;

import com.google.common.collect.Maps;
import com.minegusta.donatorpoints.DonatorPointsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

public class HorseListener implements Listener {

    public static ConcurrentMap<UUID, UUID> horseMap = Maps.newConcurrentMap();
    public static ConcurrentMap<UUID, UUID> playerMap = Maps.newConcurrentMap();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onHorseDeath(EntityDeathEvent e) {
        if (!e.getEntity().getWorld().getName().toLowerCase().equals(DonatorPointsPlugin.world)) return;
        LivingEntity entity = e.getEntity();
        if (entity instanceof Horse) {
            e.setDroppedExp(0);
            e.getDrops().set(1, new ItemStack(Material.AIR, 1));
            killHorse(entity.getUniqueId(), entity.getWorld());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent e) {
        if (!e.getPlayer().getWorld().getName().toLowerCase().equals(DonatorPointsPlugin.world)) return;
        Player player = e.getPlayer();
        if (playerMap.containsKey(player.getUniqueId())) {
            UUID playerUUID = player.getUniqueId();
            UUID horseUUID = playerMap.get(playerUUID);
            killHorse(horseUUID, player.getWorld());
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (!e.getEntity().getWorld().getName().toLowerCase().equals(DonatorPointsPlugin.world)) return;
        Player player = e.getEntity();
        if (playerMap.containsKey(player.getUniqueId())) {
            UUID horseUUID = playerMap.get(player.getUniqueId());
            killHorse(horseUUID, player.getWorld());
        }

    }

    public static void killHorse(UUID uuid, World world) {
        for (Entity entity : Bukkit.getServer().getWorld(world.getName()).getEntities()) {
            if (entity.getUniqueId().equals(uuid)) {
                LivingEntity horse = (LivingEntity) entity;
                UUID playerUUID = horseMap.get(horse.getUniqueId());
                playerMap.remove(playerUUID);
                horse.remove();
                horseMap.remove(entity.getUniqueId());
            }
        }
    }

    public static void killAllHorses() {
        for (World world : Bukkit.getServer().getWorlds()) {
            for (Entity mob : world.getLivingEntities()) {
                UUID id = mob.getUniqueId();
                if (horseMap.containsKey(id)) {
                    LivingEntity horse = (LivingEntity) mob;
                    horseMap.remove(id);
                    horse.remove();
                }
            }
        }
    }

}
