package com.minegusta.minegustarpg.items;


import com.google.common.collect.Lists;
import com.minegusta.minegustarpg.MinegustaRPGPlugin;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class ItemListener implements Listener {

    //Teleport stones.

    @EventHandler
    public void onTeleportCrystal(PlayerInteractEvent e) {

        Player p = e.getPlayer();
        if (!p.getWorld().getName().toLowerCase().equalsIgnoreCase(MinegustaRPGPlugin.world)) return;
        else if (p.getItemInHand().getType().equals(Material.AIR)) return;
        else if (p.getItemInHand().equals(getTeleportCrystal())) {
            Location l = p.getLocation();
            World w = p.getWorld();
            w.spigot().playEffect(l, Effect.CRIT, 0, 0, 0F, 0F, 0F, 0F, 180, 15);
            w.playSound(l, Sound.CHICKEN_EGG_POP, 1F, 1F);
            if (p.getItemInHand().getAmount() > 1) {
                p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
            } else {
                p.setItemInHand(new ItemStack(Material.AIR, 1));
            }
        }
    }

    // Give people Speed Grass and food when they got OP armour.

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

    public static ItemStack getTeleportCrystal() {
        ItemStack i = new ItemStack(Material.INK_SACK, 1, (short) 4);
        List<String> lore = Lists.newArrayList();
        lore.add(ChatColor.AQUA + "A magic crystal emitting energy.");
        lore.add(ChatColor.AQUA + "Right click to teleport to the spawn.");
        ItemMeta meta = i.getItemMeta();
        meta.setLore(lore);
        meta.setDisplayName(ChatColor.DARK_AQUA + "Teleport Crystal");
        i.setItemMeta(meta);
        return i;
    }

}
