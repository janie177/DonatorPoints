package com.minegusta.donatorpoints.listeners;


import com.minegusta.donatorpoints.DonatorPointsPlugin;
import com.minegusta.donatorpoints.playerdata.Data;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class LevelListener implements Listener {

    //Listen for equiping of items. Check for the level and see if it is possible.

    public void onInteractArmour(PlayerInteractEvent e) {
        if (!e.getPlayer().getWorld().getName().toLowerCase().equals(DonatorPointsPlugin.world)) return;
        else if (!e.getAction().equals(Action.RIGHT_CLICK_AIR) || !e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
            return;
        else {
            Player p = e.getPlayer();
            ItemStack i = p.getItemInHand();
            Material m = i.getType();
            if (m.equals(Material.AIR)) return;

            if (m.equals(Material.LEATHER_CHESTPLATE) || m.equals(Material.LEATHER_BOOTS) || m.equals(Material.LEATHER_HELMET) || m.equals(Material.LEATHER_LEGGINGS)) {
                if (!canEquip(p, i)) e.setCancelled(true);
            } else if (m.equals(Material.GOLD_CHESTPLATE) || m.equals(Material.GOLD_BOOTS) || m.equals(Material.GOLD_HELMET) || m.equals(Material.GOLD_LEGGINGS)) {
                if (!canEquip(p, i)) e.setCancelled(true);
            } else if (m.equals(Material.CHAINMAIL_CHESTPLATE) || m.equals(Material.CHAINMAIL_BOOTS) || m.equals(Material.CHAINMAIL_HELMET) || m.equals(Material.CHAINMAIL_LEGGINGS)) {
                if (!canEquip(p, i)) e.setCancelled(true);
            } else if (m.equals(Material.IRON_CHESTPLATE) || m.equals(Material.IRON_BOOTS) || m.equals(Material.IRON_HELMET) || m.equals(Material.IRON_LEGGINGS)) {
                if (!canEquip(p, i)) e.setCancelled(true);
            } else if (m.equals(Material.DIAMOND_CHESTPLATE) || m.equals(Material.DIAMOND_BOOTS) || m.equals(Material.DIAMOND_HELMET) || m.equals(Material.DIAMOND_LEGGINGS)) {
                if (!canEquip(p, i)) e.setCancelled(true);
            }
        }
    }

    public void onClickArmour(InventoryClickEvent e) {
        if (!e.getWhoClicked().getWorld().getName().toLowerCase().equals(DonatorPointsPlugin.world)) return;
        else if (!e.getClickedInventory().getType().equals(InventoryType.PLAYER)) return;
        else if (e.getCurrentItem().getType().equals(Material.AIR)) return;

        ClickType clickType = e.getClick();
        Player p = (Player) e.getWhoClicked();
        ItemStack i = e.getCurrentItem();
        ItemStack r = e.getClickedInventory().getItem(e.getSlot());

        if (clickType.equals(ClickType.SHIFT_LEFT) || clickType.equals(ClickType.SHIFT_RIGHT)) {
            if (!canEquip(p, i)) e.setCancelled(true);
        } else if (clickType.equals(ClickType.LEFT)) {
            InventoryType.SlotType s = e.getSlotType();
            if (s.equals(InventoryType.SlotType.ARMOR)) {
                if (!canReplaceEquipment(p, i, r)) e.setCancelled(true);
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


}
