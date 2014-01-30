package com.minegusta.minegustarpg.listeners;


import com.google.common.collect.Lists;
import com.minegusta.minegustarpg.MinegustaRPGPlugin;
import com.minegusta.minegustarpg.playerdata.Data;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class LevelListener implements Listener {

    //Listen for equiping of items. Check for the level and see if it is possible.
    List<String> help = Lists.newArrayList("You cannot equip this armour piece.", "Your level is not high enough.", "Use " + ChatColor.AQUA + "/level" + ChatColor.YELLOW + " for more help.");

    @EventHandler
    public void onInteractArmour(PlayerInteractEvent e) {
        if (!e.getPlayer().getWorld().getName().toLowerCase().equals(MinegustaRPGPlugin.world)) return;
        else if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {


            Player p = e.getPlayer();
            ItemStack i = p.getItemInHand();
            Material m = i.getType();
            if (m.equals(Material.AIR)) return;

            if (m.equals(Material.LEATHER_CHESTPLATE) || m.equals(Material.LEATHER_BOOTS) || m.equals(Material.LEATHER_HELMET) || m.equals(Material.LEATHER_LEGGINGS)) {
                if (!canEquip(p, i)) {
                    p.updateInventory();
                    sendText(p, help);
                    e.setCancelled(true);
                }
            } else if (m.equals(Material.GOLD_CHESTPLATE) || m.equals(Material.GOLD_BOOTS) || m.equals(Material.GOLD_HELMET) || m.equals(Material.GOLD_LEGGINGS)) {
                if (!canEquip(p, i)) {
                    p.updateInventory();
                    sendText(p, help);
                    e.setCancelled(true);
                }
            } else if (m.equals(Material.CHAINMAIL_CHESTPLATE) || m.equals(Material.CHAINMAIL_BOOTS) || m.equals(Material.CHAINMAIL_HELMET) || m.equals(Material.CHAINMAIL_LEGGINGS)) {
                if (!canEquip(p, i)) {
                    p.updateInventory();
                    sendText(p, help);
                    e.setCancelled(true);
                }
            } else if (m.equals(Material.IRON_CHESTPLATE) || m.equals(Material.IRON_BOOTS) || m.equals(Material.IRON_HELMET) || m.equals(Material.IRON_LEGGINGS)) {
                if (!canEquip(p, i)) {
                    p.updateInventory();
                    sendText(p, help);
                    e.setCancelled(true);
                }
            } else if (m.equals(Material.DIAMOND_CHESTPLATE) || m.equals(Material.DIAMOND_BOOTS) || m.equals(Material.DIAMOND_HELMET) || m.equals(Material.DIAMOND_LEGGINGS)) {
                if (!canEquip(p, i)) {
                    p.updateInventory();
                    sendText(p, help);
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onClickArmour(InventoryClickEvent e) {
        if (!e.getWhoClicked().getWorld().getName().toLowerCase().equals(MinegustaRPGPlugin.world)) return;
        else if (e.getInventory().getName() != null && e.getInventory().getName().contains(e.getWhoClicked().getName() + "'s Bank"))
            return;
        else if (!(e.getClickedInventory().getType() == null) && e.getClickedInventory().getType().equals(InventoryType.PLAYER))
            return;
        ClickType clickType = e.getClick();
        Player p = (Player) e.getWhoClicked();
        ItemStack itemClicked = e.getCurrentItem();
        ItemStack heldItem = e.getCursor();

        if (clickType.equals(ClickType.SHIFT_LEFT) || clickType.equals(ClickType.SHIFT_RIGHT)) {
            if (!canEquip(p, itemClicked)) {
                sendText(p, help);
                e.setCancelled(true);
            }
        } else if ((clickType.equals(ClickType.LEFT) || clickType.equals(ClickType.RIGHT))) {
            InventoryType.SlotType s = e.getSlotType();
            if (s.equals(InventoryType.SlotType.ARMOR)) {
                if (heldItem == null) ;
                else if (!canReplaceEquipment(p, itemClicked, heldItem)) {
                    e.setCancelled(true);
                    sendText(p, help);
                }
            }
        }
    }

    private int getTotalPoints(Player p) {
        int totalPoints = 0;
        for (ItemStack i : p.getInventory().getArmorContents()) {
            totalPoints = totalPoints + getPointsForItemStack(i);
        }
        return totalPoints;

    }

    private boolean canEquip(Player p, ItemStack i) {
        int currentPoints = getTotalPoints(p);
        int addedPoints = getPointsForItemStack(i);
        int maxPoints = Data.getLevel(p.getUniqueId());

        return ((currentPoints + addedPoints) <= maxPoints);
    }

    public boolean canReplaceEquipment(Player p, ItemStack i, ItemStack replacement) {
        int currentlyEquipedPoints = getTotalPoints(p);
        int itemRemoved = getPointsForItemStack(i);
        int itemAdded = getPointsForItemStack(replacement);
        int maxPoints = Data.getLevel(p.getUniqueId());
        return ((currentlyEquipedPoints - itemRemoved) + itemAdded) <= maxPoints;
    }

    public int getPointsForItemStack(ItemStack i) {
        Material m = i.getType();
        int points = 0;

        switch (m) {
            //Leather
            case LEATHER_BOOTS:
                points = 1;
                break;
            case LEATHER_CHESTPLATE:
                points = 1;
                break;
            case LEATHER_LEGGINGS:
                points = 1;
                break;
            case LEATHER_HELMET:
                points = 1;
                break;

            //GOLD
            case GOLD_BOOTS:
                points = 5;
                break;
            case GOLD_CHESTPLATE:
                points = 5;
                break;
            case GOLD_LEGGINGS:
                points = 5;
                break;
            case GOLD_HELMET:
                points = 5;
                break;

            //CHAINMAIL
            case CHAINMAIL_BOOTS:
                points = 10;
                break;
            case CHAINMAIL_CHESTPLATE:
                points = 10;
                break;
            case CHAINMAIL_LEGGINGS:
                points = 10;
                break;
            case CHAINMAIL_HELMET:
                points = 10;
                break;

            //IRON
            case IRON_BOOTS:
                points = 15;
                break;
            case IRON_CHESTPLATE:
                points = 15;
                break;
            case IRON_LEGGINGS:
                points = 15;
                break;
            case IRON_HELMET:
                points = 15;
                break;

            //DIAMOND
            case DIAMOND_BOOTS:
                points = 20;
                break;
            case DIAMOND_CHESTPLATE:
                points = 20;
                break;
            case DIAMOND_LEGGINGS:
                points = 20;
                break;
            case DIAMOND_HELMET:
                points = 20;
                break;
        }

        return points;
    }

    private void sendText(Player p, List<String> l) {
        for (String s : l) {
            p.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.GOLD + "RPG" + ChatColor.DARK_RED + "]" + ChatColor.YELLOW + s);

        }
    }


}
