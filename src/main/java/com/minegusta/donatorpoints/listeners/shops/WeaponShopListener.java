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

public class WeaponShopListener implements Listener {

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

                if (name.contains("Weapon Trader")) {
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
                    entityPlayer.sendMessage(ChatColor.RED + "You do not have enough points to buy that horse. Use " + ChatColor.AQUA + "/points" + ChatColor.RED + ".");
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

        I1(new ItemStack(Material.WOOD_SWORD, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Not too sharp, but a nice last resort. Or envelope opener.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Wooden Knife");
                setItemMeta(meta);
            }
        }, 15, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "15 points.", ChatColor.YELLOW + "Wooden Knife")),
        I2(new ItemStack(Material.IRON_SWORD, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "A decent sharp sword.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Iron Sword");
                setItemMeta(meta);
            }
        }, 50, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "50 points.", ChatColor.YELLOW + "Iron Sword")),
        I3(new ItemStack(Material.STONE_SWORD, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "We're not in the stone-age anymore... We have steel now...");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Stone 'sword'");
                setItemMeta(meta);
            }
        }, 20, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "20 points.", ChatColor.YELLOW + "Stone 'sword'")),
        I4(new ItemStack(Material.DIAMOND_SWORD, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Aww yiss. Shiny!");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Diamond Sword");
                setItemMeta(meta);
            }
        }, 150, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "150 points.", ChatColor.YELLOW + "Diamond Sword")),
        I5(new ItemStack(Material.WOOD_AXE, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Not too sharp, but a nice last resort. Or envelope opener.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Wooden Knife");
                setItemMeta(meta);
            }
        }, 12, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "12 points.", ChatColor.YELLOW + "Wooden Axe")),
        I6(new ItemStack(Material.IRON_AXE, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Good for cutting stuff. Like trees. Or people...");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Iron Axe");
                setItemMeta(meta);
            }
        }, 40, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "40 points.", ChatColor.YELLOW + "Iron Axe")),
        I7(new ItemStack(Material.STONE_AXE, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "We're not in the stone-age anymore... We have steel now...");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Stone Axe");
                setItemMeta(meta);
            }
        }, 15, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "15 points.", ChatColor.YELLOW + "Stone Axe")),
        I8(new ItemStack(Material.DIAMOND_AXE, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Too bad you don't have a tree-cut permit.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Diamond Axe");
                setItemMeta(meta);
            }
        }, 100, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "100 points.", ChatColor.YELLOW + "Diamond Axe")),
        I9(new ItemStack(Material.BOW, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "For when you prefer watching from a distance.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Wooden Bow");
                setItemMeta(meta);
            }
        }, 20, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "20 points.", ChatColor.YELLOW + "Wooden Bow")),
        I10(new ItemStack(Material.ARROW, 5) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "What's the point of violence? In this case, flint!");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Arrow");
                setItemMeta(meta);
            }
        }, 15, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "15 points.", ChatColor.YELLOW + "Arrow"));


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
