package com.minegusta.donatorpoints.listeners.shops;


import com.google.common.collect.Lists;
import com.minegusta.donatorpoints.DonatorPointsPlugin;
import com.minegusta.donatorpoints.data.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class FoodShopListener implements Listener {

    public void openInventoryOfTrader(Player user) {
        String title = "Trader";
        Inventory inv = Bukkit.getServer().createInventory(null, 18, title);
        user.openInventory(inv);

        int count = 0;
        for (RewardItem shopItem : RewardItem.values()) {
            inv.setItem(count, shopItem.getShopItem());
            count++;
        }
    }

    //Open Inventory when clicking the player.
    @EventHandler
    public void villagerRightClickEvent(PlayerInteractEntityEvent e) {
        if (e.getRightClicked().getType() == EntityType.VILLAGER && e.getPlayer().getWorld().getName().toLowerCase().equals(DonatorPointsPlugin.world)) {
            LivingEntity villager = (LivingEntity) e.getRightClicked();
            String name = villager.getCustomName();
            if (name != null) {
                e.setCancelled(true);
                Player p = e.getPlayer();

                if (name.contains("Food Trader")) {
                    if (!e.getPlayer().getItemInHand().getType().equals(Material.AIR)) {
                        e.getPlayer().sendMessage(ChatColor.RED + "You can only trade with an empty hand!");
                    } else {
                        if (DataManager.getPointsFromPlayer(p) == null) DataManager.setPointsFromPlayer(p, 0);
                        p.sendMessage(ChatColor.DARK_RED + "[Trader]" + ChatColor.GRAY + " You begin trading with the ShopKeeper.");
                        p.sendMessage(ChatColor.DARK_RED + "[Trader]" + ChatColor.GRAY + " You have " + ChatColor.LIGHT_PURPLE + DataManager.getPointsFromPlayer(p) + ChatColor.GRAY + " points.");
                        openInventoryOfTrader(e.getPlayer());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBuyItem(InventoryClickEvent e) {
        try {
            String invName = e.getClickedInventory().getName();
            if (invName != null && invName.equals("Trader")) {
                if (e.getCurrentItem().getType() == Material.AIR) return;
                if (e.getCurrentItem() == null || e.getCurrentItem().getItemMeta().getDisplayName() == null) return;

                e.setCancelled(true);
                HumanEntity player = e.getWhoClicked();

                ItemStack theItemToAdd = null;
                int points = 0;
                for (RewardItem item : RewardItem.values()) {
                    if (!item.getShopItem().getType().equals(e.getCurrentItem().getType())) continue;
                    points = item.getPoints();
                    theItemToAdd = item.getItem();
                    break;
                }

                if (theItemToAdd == null) return;

                String playerString = e.getWhoClicked().getName();
                Player entityPlayer = Bukkit.getOfflinePlayer(playerString).getPlayer();

                int pointsPresent = DataManager.getPointsFromPlayer(entityPlayer);

                if (points > pointsPresent) {
                    entityPlayer.sendMessage(ChatColor.RED + "You do not have enough points to buy that. Use " + ChatColor.AQUA + "/points" + ChatColor.RED + ".");
                    player.closeInventory();
                    return;
                }
                DataManager.setPointsFromPlayer(entityPlayer, pointsPresent - points);
                player.getInventory().addItem(theItemToAdd);
                entityPlayer.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "Trade" + ChatColor.DARK_RED + "]" + ChatColor.YELLOW + "You have successfully bought an item!");
                player.closeInventory();
            }

        } catch (Exception ignored) {

        }
    }

    public enum RewardItem {

        I1(new ItemStack(Material.RAW_BEEF, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Nommable food! I should cook it first though.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Raw Beef");
                setItemMeta(meta);
            }
        }, 5, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "5 points.", ChatColor.YELLOW + "Raw Beef")),
        I2(new ItemStack(Material.RAW_CHICKEN, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Nommable food! I should cook it first though.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Raw Chicken");
                setItemMeta(meta);
            }
        }, 5, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "5 points.", ChatColor.YELLOW + "Raw Chicken")),
        I3(new ItemStack(Material.RAW_FISH, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "I would slap people with this if I didn't want to eat it.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Raw Fish");
                setItemMeta(meta);
            }
        }, 5, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "5 points.", ChatColor.YELLOW + "Raw Fish")),
        I4(new ItemStack(Material.COOKED_BEEF, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Nommable food! Not even raw!");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Cooked Beef");
                setItemMeta(meta);
            }
        }, 8, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "8 points.", ChatColor.YELLOW + "Cooked Beef")),
        I5(new ItemStack(Material.COOKED_FISH, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Nommable food! Not even raw!");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Cooked Fish");
                setItemMeta(meta);
            }
        }, 8, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "8 points.", ChatColor.YELLOW + "Cooked Fish")),
        I6(new ItemStack(Material.COOKED_CHICKEN, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Nommable food! Salmonella free!");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Cooked Chicken");
                setItemMeta(meta);
            }
        }, 8, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "8 points.", ChatColor.YELLOW + "Cooked Chicken")),
        I7(new ItemStack(Material.MELON, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Eat it before you attract any african americans.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Melon Slice");
                setItemMeta(meta);
            }
        }, 3, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "3 points.", ChatColor.YELLOW + "Melon Slice")),
        I8(new ItemStack(Material.APPLE, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "You're not pretty! Nobody is trying to poison you. Take a bite!");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Red Apple");
                setItemMeta(meta);
            }
        }, 4, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "4 points.", ChatColor.YELLOW + "Red Apple")),
        I9(new ItemStack(Material.CARROT, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Sup dog?");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Carrot");
                setItemMeta(meta);
            }
        }, 3, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "3 points.", ChatColor.YELLOW + "Carrot"));


        private ItemStack itemStack, shopItem;
        private int points;

        private RewardItem(ItemStack itemStack, int points, List<String> saleInfo) {
            this.itemStack = itemStack;
            this.points = points;
            shopItem = getItem().clone();
            ItemMeta meta = shopItem.getItemMeta();
            meta.setLore(saleInfo);
            shopItem.setItemMeta(meta);
        }

        public ItemStack getItem() {
            return itemStack;
        }

        public int getPoints() {
            return points;
        }

        public ItemStack getShopItem() {
            return shopItem;
        }
    }
}