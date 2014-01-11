package com.minegusta.donatorpoints.listeners;

import com.censoredsoftware.censoredlib.util.Items;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.minegusta.donatorpoints.DonatorPointsPlugin;
import com.minegusta.donatorpoints.data.DataManager;
import com.minegusta.donatorpoints.playerdata.Data;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class HorseShopListener implements Listener {

    public void openInventoryOfTrader(Player user) {
        String title = "Horse Trader";
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

                if (name.contains("Horse Trader")) {
                    if (!e.getPlayer().getItemInHand().getType().equals(Material.AIR)) {
                        e.getPlayer().sendMessage(ChatColor.RED + "You can only trade with an empty hand!");
                    } else {
                        if (DataManager.getPointsFromPlayer(p) == null) DataManager.setPointsFromPlayer(p, 0);
                        p.sendMessage(ChatColor.AQUA + "[Horse Trader]" + ChatColor.GRAY + " You begin trading with the ShopKeeper.");
                        p.sendMessage(ChatColor.AQUA + "[Horse Trader]" + ChatColor.GRAY + " You have " + ChatColor.LIGHT_PURPLE + DataManager.getPointsFromPlayer(p) + ChatColor.GRAY + " points.");
                        p.sendMessage(ChatColor.AQUA + "[Horse Trader]" + ChatColor.GRAY + " Use /Horse to customize any purchases.");
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
            if (invName != null && invName.equals("Horse Trader")) {
                if (e.getCurrentItem().getType() == Material.AIR) return;
                if (e.getCurrentItem() == null || e.getCurrentItem().getItemMeta().getDisplayName() == null) return;

                e.setCancelled(true);
                HumanEntity player = e.getWhoClicked();

                ItemStack theItemToAdd = null;
                int points = 0;
                for (RewardItem item : RewardItem.values()) {
                    if (!Items.areEqual(item.getShopItem(), e.getCurrentItem())) continue;
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
                UUID uuid = entityPlayer.getUniqueId();
                if (theItemToAdd.getType().equals(Material.SADDLE)) {
                    Data.setHorseColor(uuid, "black");
                    Data.setHorseName(uuid, "Default Name");
                    Data.setHorseJump(uuid, 1.0);
                    Data.setHorseSpeed(uuid, 2.2);
                    Data.setHorseStyle(uuid, "none");
                    Data.setHorseArmour(uuid, "none");

                } else if (theItemToAdd.getType().equals(Material.GOLD_BARDING)) {
                    Data.setHorseColor(uuid, "black");
                    Data.setHorseName(uuid, "Default Name");
                    Data.setHorseJump(uuid, 1.5);
                    Data.setHorseSpeed(uuid, 2.8);
                    Data.setHorseStyle(uuid, "none");
                    Data.setHorseArmour(uuid, "gold");

                } else if (theItemToAdd.getType().equals(Material.IRON_BARDING)) {
                    Data.setHorseColor(uuid, "black");
                    Data.setHorseName(uuid, "Default Name");
                    Data.setHorseJump(uuid, 2.0);
                    Data.setHorseSpeed(uuid, 3.2);
                    Data.setHorseStyle(uuid, "none");
                    Data.setHorseArmour(uuid, "iron");
                } else if (theItemToAdd.getType().equals(Material.DIAMOND_BARDING)) {
                    Data.setHorseColor(uuid, "black");
                    Data.setHorseName(uuid, "Default Name");
                    Data.setHorseJump(uuid, 3.0);
                    Data.setHorseSpeed(uuid, 4.0);
                    Data.setHorseStyle(uuid, "none");
                    Data.setHorseArmour(uuid, "diamond");
                }
                Data.setHasHorse(uuid, true);
                entityPlayer.sendMessage(ChatColor.AQUA + "You have successfully bought a horse!");
                entityPlayer.sendMessage(ChatColor.AQUA + "Use /Horse for summoning, customization and help!");
                player.closeInventory();
            }

        } catch (Exception ignored) {

        }
    }

    public enum RewardItem {

        Horse1(new ItemStack(Material.SADDLE, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Slow horse.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.GOLD + "FattySteed");
                setItemMeta(meta);
            }
        }, 450, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "450 points.", ChatColor.GOLD + "FattySteed")),
        Horse2(new ItemStack(Material.GOLD_BARDING, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Average lasagna.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.GOLD + "Normal Horse");
                setItemMeta(meta);
            }
        }, 600, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "600 points.", ChatColor.GOLD + "Normal Horse")),
        Horse3(new ItemStack(Material.IRON_BARDING, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "A good horse.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.GOLD + "Good Steed");
                setItemMeta(meta);
            }
        }, 800, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "800 points.", ChatColor.GOLD + "Good Steed")),
        Horse4(new ItemStack(Material.DIAMOND_BARDING, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "The best horse around.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.GOLD + "Mighty Steed");
                setItemMeta(meta);
            }
        }, 1400, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "1400 points.", ChatColor.GOLD + "Mighty Steed"));


        private ItemStack itemStack, shopItem;
        private int points;
        private PotionEffect potionEffect;
        private Effect effect;
        private Collection<String> events;

        private RewardItem(ItemStack itemStack, int points, List<String> saleInfo) {
            this.itemStack = itemStack;
            this.points = points;
            shopItem = getItem().clone();
            ItemMeta meta = shopItem.getItemMeta();
            meta.setLore(saleInfo);
            shopItem.setItemMeta(meta);
        }

        private RewardItem(ItemStack itemStack, int points, List<String> saleInfo, PotionEffect potionEffect, Effect effect, Event... events) {
            this.itemStack = itemStack;
            this.points = points;
            shopItem = getItem().clone();
            ItemMeta meta = shopItem.getItemMeta();
            meta.setLore(saleInfo);
            shopItem.setItemMeta(meta);
            this.potionEffect = potionEffect;
            this.effect = effect;
            this.events = Collections2.transform(Sets.newHashSet(events), new Function<Event, String>() {
                @Override
                public String apply(Event event) {
                    return event.getEventName();
                }
            });
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

        public boolean hasEffects() {
            return potionEffect != null && effect != null;
        }

        public PotionEffect getPotionEffect() {
            return potionEffect;
        }

        public Effect getEffect() {
            return effect;
        }
    }
}
