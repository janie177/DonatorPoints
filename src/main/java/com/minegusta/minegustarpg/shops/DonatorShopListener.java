package com.minegusta.minegustarpg.shops;

import com.censoredsoftware.censoredlib.util.Items;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.minegusta.minegustarpg.MinegustaRPGPlugin;
import com.minegusta.minegustarpg.data.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DonatorShopListener implements Listener {


    //The Trader's inventory

    public void openInventoryOfTrader(Player user) {
        String title = "ShopKeeper's inventory";
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
        if (!(e.getRightClicked() instanceof LivingEntity)) return;
        LivingEntity villager = (LivingEntity) e.getRightClicked();
        if (!(villager instanceof Villager)) return;
        String name = villager.getCustomName();
        if (name != null) {
            e.setCancelled(true);
            Player p = e.getPlayer();

            if (name.contains("Donator Trader") && p.hasPermission("minegusta.donator")) {
                if (!e.getPlayer().getItemInHand().getType().equals(Material.AIR)) {
                    e.getPlayer().sendMessage(ChatColor.RED + "You can only trade with an empty hand!");
                } else {
                    if (DataManager.getPointsFromPlayer(p) == null) DataManager.setPointsFromPlayer(p, 0);
                    p.sendMessage(ChatColor.AQUA + "[Trader]" + ChatColor.GRAY + " You begin trading with the ShopKeeper.");
                    p.sendMessage(ChatColor.AQUA + "[Trader]" + ChatColor.GRAY + " You have " + ChatColor.LIGHT_PURPLE + DataManager.getPointsFromPlayer(p) + ChatColor.GRAY + " points.");
                    openInventoryOfTrader(e.getPlayer());
                }
            }
        }
    }

    //Prevent traders from getting hurt by other non-op entities.  (Also all other trader classes!!).
    @EventHandler
    public void onEntityDamageByNonOp(EntityDamageByEntityEvent e) {
        Entity entity = e.getEntity();
        if (entity.getType().equals(EntityType.VILLAGER)) {

            if (e.getDamager() instanceof Player) {
                Player player = (Player) e.getDamager();
                if (player.isOp()) return;

            }

            LivingEntity villager = (LivingEntity) e.getEntity();
            if (DataManager.isNPC(villager) || villager.getCustomName().contains("Trader") || villager.getCustomName().contains("Trainer")) {
                if (!e.getEntity().getWorld().getName().equalsIgnoreCase(MinegustaRPGPlugin.world)) return;
                e.setCancelled(true);
            } else if (villager.getCustomName().contains("Donator Trader")) {
                e.setCancelled(true);
            }
        }
    }


    //Inventory manager


    @EventHandler
    public void onBuyItem(InventoryClickEvent e) {
        try {
            String invName = e.getClickedInventory().getName();
            if (invName != null && invName.equals("ShopKeeper's inventory") && e.getWhoClicked().hasPermission("minegusta.donator")) {
                if (e.getCursor() != null && e.getCurrentItem().getType().equals(Material.AIR)) e.setCancelled(true);
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
                    entityPlayer.sendMessage(ChatColor.RED + "You do not have enough points to buy that item. Use " + ChatColor.AQUA + "/points" + ChatColor.RED + ".");
                    player.closeInventory();
                    return;
                }

                if (player.getInventory().firstEmpty() == -1) {
                    entityPlayer.sendMessage(ChatColor.RED + "You do not have enough space in your inventory.");
                    player.closeInventory();
                    return;
                }
                DataManager.setPointsFromPlayer(entityPlayer, pointsPresent - points);
                player.getInventory().addItem(theItemToAdd);
                entityPlayer.sendMessage(ChatColor.AQUA + "You have successfully bought an item!");
                player.closeInventory();
            }

        } catch (Exception ignored) {

        }
    }


    //ItemStore's reward items the player actually gets.

    public enum RewardItem {

        APPLE(new ItemStack(Material.GOLDEN_APPLE, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> appleLore = Lists.newArrayList();
                appleLore.add(ChatColor.GOLD + "God Apple");
                meta.setLore(appleLore);
                meta.setDisplayName(ChatColor.GOLD + "God Apple");
                setItemMeta(meta);
            }
        }, 80, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "35 points.", ChatColor.GOLD + "God Apple")),
        HELMET(new ItemStack(Material.DIAMOND_HELMET, 1) {
            {
                ArrayList<String> helmetOfTheFatMan = new ArrayList<String>();
                helmetOfTheFatMan.add(ChatColor.GRAY + "Helmet Of The Fat Man");
                helmetOfTheFatMan.add(ChatColor.GRAY + "Press crouch to feed yourself.");
                addEnchantment(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 4);
                addEnchantment(org.bukkit.enchantments.Enchantment.DURABILITY, 3);
                addEnchantment(org.bukkit.enchantments.Enchantment.OXYGEN, 1);
                ItemMeta meta = getItemMeta();
                meta.setLore(helmetOfTheFatMan);
                meta.setDisplayName(ChatColor.GRAY + "Helmet Of The Fat Man");
                setItemMeta(meta);
            }
        }, 1600, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "1200 points.", ChatColor.GRAY + "Helmet Of The Fat Man")),
        PANTS(new ItemStack(Material.DIAMOND_LEGGINGS, 1) {
            {
                ArrayList<String> pantsOfLife = new ArrayList<String>();
                pantsOfLife.add(ChatColor.GREEN + "Pants Of Life");
                pantsOfLife.add(ChatColor.GREEN + "Crouch to turn dirt to grass.");
                addEnchantment(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 4);
                addEnchantment(org.bukkit.enchantments.Enchantment.DURABILITY, 3);
                addEnchantment(org.bukkit.enchantments.Enchantment.THORNS, 2);
                ItemMeta meta = getItemMeta();
                meta.setLore(pantsOfLife);
                meta.setDisplayName(ChatColor.GREEN + "Pants Of Life");
                setItemMeta(meta);
            }
        }, 1600, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "1200 points.", ChatColor.GREEN + "Pants Of Life")),
        BODY(new ItemStack(Material.DIAMOND_CHESTPLATE, 1) {
            {
                ArrayList<String> plateOfDarkness = new ArrayList<String>();
                plateOfDarkness.add(ChatColor.DARK_GRAY + "Platebody Of Darkness");
                plateOfDarkness.add(ChatColor.DARK_GRAY + "A dark power emerges from this item..");
                addEnchantment(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 4);
                addEnchantment(org.bukkit.enchantments.Enchantment.DURABILITY, 3);
                addEnchantment(org.bukkit.enchantments.Enchantment.THORNS, 3);
                ItemMeta meta = getItemMeta();
                meta.setLore(plateOfDarkness);
                meta.setDisplayName(ChatColor.DARK_GRAY + "Platebody Of Darkness");
                setItemMeta(meta);
            }
        }, 1600, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "1200 points.", ChatColor.DARK_GRAY + "Platebody Of Darkness")),
        BOOTS(new ItemStack(Material.DIAMOND_BOOTS, 1) {
            {
                ArrayList<String> swiftBoots = new ArrayList<String>();
                swiftBoots.add(ChatColor.DARK_AQUA + "Boots Of The Swift");
                swiftBoots.add(ChatColor.DARK_AQUA + "Crouch to get Speed II for 5 minutes.");
                ItemMeta meta = getItemMeta();
                meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
                meta.setDisplayName(ChatColor.DARK_AQUA + "Boots Of The Swift");
                meta.setLore(swiftBoots);
                setItemMeta(meta);
            }
        }, 1600, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "1200 points.", ChatColor.DARK_AQUA + "Boots Of The Swift")),
        BLADE(new ItemStack(Material.DIAMOND_SWORD, 1) {
            {
                ArrayList<String> swordLore = new ArrayList<String>();
                swordLore.add(ChatColor.DARK_RED + "Blade Of The Slayer");
                addEnchantment(org.bukkit.enchantments.Enchantment.DAMAGE_ALL, 5);
                addEnchantment(org.bukkit.enchantments.Enchantment.DAMAGE_ARTHROPODS, 5);
                addEnchantment(org.bukkit.enchantments.Enchantment.DAMAGE_UNDEAD, 5);
                addEnchantment(org.bukkit.enchantments.Enchantment.KNOCKBACK, 2);
                addEnchantment(org.bukkit.enchantments.Enchantment.FIRE_ASPECT, 2);
                addEnchantment(org.bukkit.enchantments.Enchantment.LOOT_BONUS_MOBS, 3);
                ItemMeta meta = getItemMeta();
                meta.setLore(swordLore);
                meta.setDisplayName(ChatColor.DARK_RED + "Blade Of The Slayer");
                setItemMeta(meta);

            }
        }, 600, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "600 points.", ChatColor.DARK_RED + "Blade Of The Slayer"));


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
