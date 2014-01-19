package com.minegusta.minegustarpg.listeners.items;


import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ItemListener implements Listener {

    // Give people Speed Grass and fewd when they got tha st00f.

    @EventHandler
    public void applySpeedBoost(PlayerToggleSneakEvent e) {
        Player player = e.getPlayer();
        ItemStack boots = player.getInventory().getBoots();
        if (boots != null && boots.getItemMeta().hasLore() && boots.getItemMeta().getLore().toString().contains("Boots Of The Swift")) {
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000, 1));
            player.getWorld().spigot().playEffect(player.getLocation(), Effect.FLAME, 0, 0, 1F, 0.1F, 1F, 0.5F, 3, 30);
        }
        ItemStack helmet = player.getInventory().getHelmet();
        if (helmet != null && helmet.getItemMeta().hasLore() && helmet.getItemMeta().getLore().toString().contains("Helmet Of The Fat Man")) {
            player.setFoodLevel(player.getFoodLevel() + 1);
            player.getWorld().spigot().playEffect(player.getLocation(), Effect.CRIT, 0, 0, 1F, 0.1F, 1F, 0.5F, 3, 30);
        }
        ItemStack pants = player.getInventory().getLeggings();
        if (pants != null && pants.getItemMeta().hasLore() && pants.getItemMeta().getLore().toString().contains("Pants Of Life")) {
            Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
            if (block.getType().equals(Material.DIRT)) {
                block.setType(Material.GRASS);
                player.getWorld().spigot().playEffect(player.getLocation(), Effect.HAPPY_VILLAGER, 0, 0, 1F, 0.1F, 1F, 0.5F, 3, 30);
            }
        }

    }

    //Give people with darkness plate cewl effewcts.

    @EventHandler
    public void darkParticleEmit(PlayerMoveEvent e) {
        if (e.getPlayer().getInventory().getChestplate() != null) {
            ItemStack chest = e.getPlayer().getInventory().getChestplate();
            if (chest.getItemMeta().hasLore() && chest.getItemMeta().getLore().toString().contains("Platebody Of Darkness")) {
                Player player = e.getPlayer();
                e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000, 1));
                player.getWorld().spigot().playEffect(player.getLocation(), Effect.POTION_SWIRL, 0, 0, 1F, 0.1F, 1F, 0.5F, 3, 30);
            }
        }
    }

}
