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

public class PotionShopListener implements Listener {

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

                if (name.contains("Potion Trader")) {
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
                    if (!(item.getShopItem().getDurability() == e.getCurrentItem().getDurability())) continue;
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
        regenII(new ItemStack(Material.POTION, 1, (short) 8225) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Smells odd. Oddly good!");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Potion of Regeneration II");
                setItemMeta(meta);
            }
        }, 25, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "25 points.", ChatColor.YELLOW + "Potion of Regeneration II")),
        SpeedII(new ItemStack(Material.POTION, 1, (short) 8226) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Smells like speed.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Potion of Speed II");
                setItemMeta(meta);
            }
        }, 20, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "20 points.", ChatColor.YELLOW + "Potion of Speed II")),
        FireResist(new ItemStack(Material.POTION, 1, (short) 8259) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Smells odd.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Potion of Speed II");
                setItemMeta(meta);
            }
        }, 15, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "15 points.", ChatColor.YELLOW + "Potion of Speed II")),
        Health2(new ItemStack(Material.POTION, 1, (short) 8229) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "An apple a day didn't keeps the doctor away.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Potion of Health II");
                setItemMeta(meta);
            }
        }, 20, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "20 points.", ChatColor.YELLOW + "Potion of Health II")),
        nightvision(new ItemStack(Material.POTION, 1, (short) 8262) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "You are the night.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Potion of Night Vision");
                setItemMeta(meta);
            }
        }, 15, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "15 points.", ChatColor.YELLOW + "Potion of Night Vision")),
        strength(new ItemStack(Material.POTION, 1, (short) 8265) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.DARK_GREEN + "SMAAAAAAAASH");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Potion of Strength");
                setItemMeta(meta);
            }
        }, 20, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "20 points.", ChatColor.YELLOW + "Potion of Strength")),
        invis(new ItemStack(Material.POTION, 1, (short) 8270) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "How come the bottle is not invisible..?");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Potion of Invisibility");
                setItemMeta(meta);
            }
        }, 50, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "50 points.", ChatColor.YELLOW + "Potion of Invisibility")),
        harmingsplash(new ItemStack(Material.POTION, 1, (short) 16396) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Also effective against fleas...");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Splash Potion of Damage");
                setItemMeta(meta);
            }
        }, 25, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "25 points.", ChatColor.YELLOW + "Splash Potion of Damage")),
        healingsplash(new ItemStack(Material.POTION, 1, (short) 16421) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "YOU TAKE IT, AND THROW IT ON THE GROUND!");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Splash Potion of Health");
                setItemMeta(meta);
            }
        }, 30, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "30 points.", ChatColor.YELLOW + "Splash Potion of Health")),
        regensplash(new ItemStack(Material.POTION, 1, (short) 16417) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Heals you over time.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Splash Potion of Regeneration");
                setItemMeta(meta);
            }
        }, 30, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "30 points.", ChatColor.YELLOW + "Splash Potion of Regeneration")),
        weaknesssplash(new ItemStack(Material.POTION, 1, (short) 16392) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "I would not drink this.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Splash Potion of Weakness");
                setItemMeta(meta);
            }
        }, 25, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "25 points.", ChatColor.YELLOW + "Splash Potion of Weakness"));


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
