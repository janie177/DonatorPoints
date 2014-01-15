package com.minegusta.donatorpoints.listeners.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class MysteryBoxListener implements Listener {


    //Cancel placing Mystery Boxes

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBoxPlace(BlockPlaceEvent e) {
        ItemStack item = e.getItemInHand();
        if (item.getType().equals(Material.CHEST) && item.getItemMeta().hasLore() && item.getItemMeta().getLore().contains("Rightclick the air to open!")) {
            e.setCancelled(true);
        }

    }

    //Open Mystery Box
    @EventHandler
    public void onMysteryBoxOpen(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) && player.getItemInHand().getType() != Material.AIR) {
            if (!player.getItemInHand().getItemMeta().hasLore()) return;
            if (player.getItemInHand().getType().equals(Material.CHEST) && player.getItemInHand().getItemMeta().getLore().toString().contains("Rightclick the air to open!")) {
                player.sendMessage(ChatColor.DARK_PURPLE + "[Mystery Box]" + ChatColor.AQUA + " You start opening the box....");
                int oldAmount = player.getItemInHand().getAmount();
                int newAmount = oldAmount - 1;
                if (player.getInventory().firstEmpty() == -1 && newAmount != 0) {
                    player.sendMessage(ChatColor.DARK_PURPLE + "[Mystery Box]" + ChatColor.RED + "You do not have enough space in your inventory.");
                    return;
                }
                if (newAmount == 0) {
                    player.getInventory().remove(player.getItemInHand());
                } else {
                    player.getItemInHand().setAmount(newAmount);
                }
                Random rand = new Random();
                int number = rand.nextInt(35);
                ItemStack itemReward = new ItemStack(Material.ARROW, 1);

                switch (number) {

                    case 1:
                        itemReward = new ItemStack(Material.DIAMOND, 3);
                        break;
                    case 2:
                        itemReward = new ItemStack(Material.DIRT, 3);
                        break;
                    case 3:
                        itemReward = new ItemStack(Material.EMERALD, 3);
                        break;
                    case 4:
                        itemReward = new ItemStack(Material.IRON_ORE, 3);
                        break;
                    case 5:
                        itemReward = new ItemStack(Material.GOLD_INGOT, 25);
                        break;
                    case 6:
                        itemReward = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
                        break;
                    case 7:
                        itemReward = new ItemStack(Material.DIAMOND_SWORD, 1);
                        break;
                    case 8:
                        itemReward = new ItemStack(Material.DIAMOND_BOOTS, 1);
                        break;
                    case 9:
                        itemReward = new ItemStack(Material.DIAMOND_HELMET, 1);
                        break;
                    case 10:
                        itemReward = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
                        break;
                    case 11:
                        itemReward = new ItemStack(Material.DIAMOND_AXE, 1);
                        break;
                    case 12:
                        itemReward = new ItemStack(Material.DIAMOND_PICKAXE, 1);
                        break;
                    case 13:
                        itemReward = new ItemStack(Material.WOOD_BUTTON, 1);
                        break;
                    case 14:
                        itemReward = new ItemStack(Material.MOB_SPAWNER, 1);
                        break;
                    case 15:
                        itemReward = new ItemStack(Material.STONE, 25);
                        break;
                    case 16:
                        itemReward = new ItemStack(Material.COOKIE, 64);
                        break;
                    case 17:
                        itemReward = new ItemStack(Material.REDSTONE, 64);
                        break;
                    case 18:
                        itemReward = new ItemStack(Material.STICK, 20);
                        break;
                    case 19:
                        itemReward = new ItemStack(Material.IRON_SPADE, 1);
                        break;
                    case 20:
                        itemReward = new ItemStack(Material.DIAMOND_SPADE, 1);
                        break;
                    case 21:
                        itemReward = new ItemStack(Material.GREEN_RECORD, 1);
                        break;
                    case 22:
                        itemReward = new ItemStack(Material.POISONOUS_POTATO, 3);
                        break;
                    case 23:
                        itemReward = new ItemStack(Material.DIAMOND_BLOCK, 1);
                        break;
                    case 24:
                        itemReward = new ItemStack(Material.BLAZE_POWDER, 5);
                        break;
                    case 25:
                        itemReward = new ItemStack(Material.EGG, 3);
                        break;
                    case 26:
                        itemReward = new ItemStack(Material.EXP_BOTTLE, 15);
                        break;
                    case 27:
                        itemReward = new ItemStack(Material.BEACON, 1);
                        break;
                    case 28:
                        itemReward = new ItemStack(Material.SKULL, 1);
                        break;
                    case 29:
                        itemReward = new ItemStack(Material.SADDLE, 1);
                        break;
                    case 30:
                        itemReward = new ItemStack(Material.WHEAT, 15);
                        break;
                    case 31:
                        itemReward = new ItemStack(Material.COAL, 9);
                        break;
                    case 32:
                        itemReward = new ItemStack(Material.MELON, 3);
                        break;
                    case 33:
                        itemReward = new ItemStack(Material.CAKE, 1);
                        break;
                    case 34:
                        itemReward = new ItemStack(Material.ARROW, 25);
                        break;
                }
                player.getInventory().addItem(itemReward);
                player.updateInventory();
                player.sendMessage(ChatColor.DARK_PURPLE + "[Mystery Box]" + ChatColor.AQUA + " And receive a random item!");


            }
        }
    }
}
