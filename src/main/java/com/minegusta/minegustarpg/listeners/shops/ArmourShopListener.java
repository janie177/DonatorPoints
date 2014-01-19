package com.minegusta.minegustarpg.listeners.shops;

import com.google.common.collect.Lists;
import com.minegusta.minegustarpg.MinegustaRPGPlugin;
import com.minegusta.minegustarpg.data.DataManager;
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

public class ArmourShopListener implements Listener {

    public void openInventoryOfTrader(Player user) {
        String title = "Trader";
        Inventory inv = Bukkit.getServer().createInventory(null, 27, title);
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
        if (e.getRightClicked().getType() == EntityType.VILLAGER && e.getPlayer().getWorld().getName().toLowerCase().equals(MinegustaRPGPlugin.world)) {
            LivingEntity villager = (LivingEntity) e.getRightClicked();
            String name = villager.getCustomName();
            if (name != null) {
                e.setCancelled(true);
                Player p = e.getPlayer();

                if (name.contains("Armour Trader")) {
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

        I1(new ItemStack(Material.IRON_CHESTPLATE, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Iron Chestplate");
                setItemMeta(meta);
            }
        }, 200, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "200 points.", ChatColor.YELLOW + "Iron Chestplate")),
        I2(new ItemStack(Material.IRON_LEGGINGS, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Iron Leggings");
                setItemMeta(meta);
            }
        }, 200, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "200 points.", ChatColor.YELLOW + "Iron Leggings")),
        I3(new ItemStack(Material.IRON_HELMET, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Iron Helmet");
                setItemMeta(meta);
            }
        }, 180, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "180 points.", ChatColor.YELLOW + "Iron Helmet")),
        I4(new ItemStack(Material.IRON_BOOTS, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Iron Boots");
                setItemMeta(meta);
            }
        }, 180, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "180 points.", ChatColor.YELLOW + "Iron Boots")),
        I5(new ItemStack(Material.LEATHER_CHESTPLATE, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Leather Chestplate");
                setItemMeta(meta);
            }
        }, 15, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "15 points.", ChatColor.YELLOW + "Leather Chestplate")),
        I6(new ItemStack(Material.LEATHER_LEGGINGS, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Leather Leggings");
                setItemMeta(meta);
            }
        }, 15, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "15 points.", ChatColor.YELLOW + "Leather Leggings")),
        I7(new ItemStack(Material.LEATHER_HELMET, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Leather Helmet");
                setItemMeta(meta);
            }
        }, 8, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "8 points.", ChatColor.YELLOW + "Leather Helmet")),
        I8(new ItemStack(Material.LEATHER_BOOTS, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Leather Boots");
                setItemMeta(meta);
            }
        }, 8, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "8 points.", ChatColor.YELLOW + "Leather Boots")),
        I9(new ItemStack(Material.GOLD_CHESTPLATE, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Gold Chestplate");
                setItemMeta(meta);
            }
        }, 60, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "60 points.", ChatColor.YELLOW + "Gold Chestplate")),
        I10(new ItemStack(Material.GOLD_LEGGINGS, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Gold Leggings");
                setItemMeta(meta);
            }
        }, 60, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "60 points.", ChatColor.YELLOW + "Gold Leggings")),
        I11(new ItemStack(Material.GOLD_HELMET, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Gold Helmet");
                setItemMeta(meta);
            }
        }, 50, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "50 points.", ChatColor.YELLOW + "Gold Helmet")),
        I12(new ItemStack(Material.GOLD_BOOTS, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Gold Boots");
                setItemMeta(meta);
            }
        }, 50, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "50 points.", ChatColor.YELLOW + "Gold Boots")),
        I13(new ItemStack(Material.DIAMOND_CHESTPLATE, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Diamond Chestplate");
                setItemMeta(meta);
            }
        }, 500, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "500 points.", ChatColor.YELLOW + "Diamond Chestplate")),
        I14(new ItemStack(Material.DIAMOND_LEGGINGS, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Diamond Leggings");
                setItemMeta(meta);
            }
        }, 500, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "500 points.", ChatColor.YELLOW + "Diamond Leggings")),
        I15(new ItemStack(Material.DIAMOND_HELMET, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Diamond Helmet");
                setItemMeta(meta);
            }
        }, 450, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "450 points.", ChatColor.YELLOW + "Diamond Helmet")),
        I16(new ItemStack(Material.DIAMOND_BOOTS, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Diamond Boots");
                setItemMeta(meta);
            }
        }, 450, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "450 points.", ChatColor.YELLOW + "Diamond Boots")),
        I17(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Chainmail Chestplate");
                setItemMeta(meta);
            }
        }, 150, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "70 points.", ChatColor.YELLOW + "Chainmail Chestplate")),
        I18(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Chainmail Leggings");
                setItemMeta(meta);
            }
        }, 150, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "70 points.", ChatColor.YELLOW + "Chainmail Leggings")),
        I19(new ItemStack(Material.CHAINMAIL_HELMET, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Chainmail Helmet");
                setItemMeta(meta);
            }
        }, 100, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "60 points.", ChatColor.YELLOW + "Chainmail Helmet")),
        I20(new ItemStack(Material.CHAINMAIL_BOOTS, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Chainmail Boots");
                setItemMeta(meta);
            }
        }, 100, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "60 points.", ChatColor.YELLOW + "Chainmail Boots"));


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