package com.minegusta.donatorpoints;

import com.censoredsoftware.censoredlib.util.Items;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.minegusta.donatorpoints.data.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class ShopListener implements Listener {


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

    public void openInventoryOfFreeTrader(Player user) {
        String title = "Free ShopKeeper's inventory";
        Inventory inv = Bukkit.getServer().createInventory(null, 18, title);
        user.openInventory(inv);

        int count = 0;
        for (FreeRewardItem shopItem : FreeRewardItem.values()) {
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

                if (name.contains("Donator Trader")) {
                    if (!e.getPlayer().getItemInHand().getType().equals(Material.AIR)) {
                        e.getPlayer().sendMessage(ChatColor.RED + "You can only trade with an empty hand!");
                    } else {
                        if (DataManager.getPointsFromPlayer(p) == null) DataManager.setPointsFromPlayer(p, 0);
                        p.sendMessage(ChatColor.AQUA + "[Trader]" + ChatColor.GRAY + " You begin trading with the ShopKeeper.");
                        p.sendMessage(ChatColor.AQUA + "[Trader]" + ChatColor.GRAY + " You have " + ChatColor.LIGHT_PURPLE + DataManager.getPointsFromPlayer(p) + ChatColor.GRAY + " points.");
                        openInventoryOfTrader(e.getPlayer());
                    }
                } else if (name.contains("Reward Trader")) {
                    if (!e.getPlayer().getItemInHand().getType().equals(Material.AIR)) {
                        e.getPlayer().sendMessage(ChatColor.RED + "You can only trade with an empty hand!");
                    } else {
                        if (DataManager.getPointsFromPlayer(p) == null) DataManager.setPointsFromPlayer(p, 0);
                        p.sendMessage(ChatColor.AQUA + "[Trader]" + ChatColor.GRAY + " You begin trading with the ShopKeeper.");
                        p.sendMessage(ChatColor.AQUA + "[Trader]" + ChatColor.GRAY + " You have " + ChatColor.LIGHT_PURPLE + DataManager.getPointsFromPlayer(p) + ChatColor.GRAY + " points.");
                        openInventoryOfFreeTrader(e.getPlayer());
                    }
                }
            }
        }
    }

    //Cancel placing Mystery Boxes

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBoxPlace(BlockPlaceEvent e) {
        if (Items.areEqual(e.getItemInHand(), RewardItem.BOX.getItem())) {
            e.setCancelled(true);
        }

    }


    //Open Mystery Box
    @EventHandler
    public void onMysteryBoxOpen(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) && player.getItemInHand().getType() != Material.AIR) {
            if (!player.getItemInHand().getItemMeta().hasLore()) return;
            if (player.getItemInHand().getType().equals(Material.CHEST) && player.getItemInHand().getItemMeta().getLore().toString().contains("Mystery Box")) {
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


    // Give people Speed Grass and fewd when they got tha st00f.

    @EventHandler
    public void applySpeedBoost(PlayerToggleSneakEvent e) {
        Player player = e.getPlayer();
        ItemStack boots = player.getInventory().getBoots();
        if (boots != null && Items.areEqual(boots, RewardItem.BOOTS.getItem())) {
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

    //Prevent traders from getting hurt by other non-op entities.
    @EventHandler
    public void onEntityDamageByNonOp(EntityDamageByEntityEvent e) {
        Entity entity = e.getEntity();
        if (entity.getType().equals(EntityType.VILLAGER)) {

            if (e.getDamager() instanceof Player) {
                Player player = (Player) e.getDamager();
                if (player.isOp()) return;

            }

            LivingEntity villager = (LivingEntity) e.getEntity();
            if (DataManager.isNPC(villager) || villager.getCustomName().contains("Reward Trader")) {
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
            } else if (invName != null && invName.equals("Free ShopKeeper's inventory")) {
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
        }, 35, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "35 points.", ChatColor.GOLD + "God Apple")),
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
        }, 600, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "600 points.", ChatColor.GRAY + "Helmet Of The Fat Man")),
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
        }, 600, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "600 points.", ChatColor.GREEN + "Pants Of Life")),
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
        }, 600, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "600 points.", ChatColor.DARK_GRAY + "Platebody Of Darkness")),
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
        }, 600, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "600 points.", ChatColor.DARK_AQUA + "Boots Of The Swift")),
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
        }, 280, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "280 points.", ChatColor.DARK_RED + "Blade Of The Slayer")),
        BOX(new ItemStack(Material.CHEST, 1) {
            {
                ArrayList<String> mysteryBox = new ArrayList<String>();
                mysteryBox.add(ChatColor.GREEN + "Mystery Box");
                mysteryBox.add(ChatColor.GRAY + "Right-click to open for a random item!");
                ItemMeta meta = getItemMeta();
                meta.setLore(mysteryBox);
                meta.setDisplayName(ChatColor.GREEN + "Mystery Box");
                setItemMeta(meta);
            }
        }, 25, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "25 points.", ChatColor.GREEN + "Mystery Box"));


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

    public enum FreeRewardItem {

        APPLE(new ItemStack(Material.GOLDEN_APPLE, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> appleLore = Lists.newArrayList();
                appleLore.add(ChatColor.GOLD + "God Apple");
                meta.setLore(appleLore);
                meta.setDisplayName(ChatColor.GOLD + "God Apple");
                setItemMeta(meta);
            }
        }, 45, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "45 points.", ChatColor.GOLD + "God Apple")),
        PANTS(new ItemStack(Material.DIAMOND_LEGGINGS, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.AQUA + "Normal Diamond Pants");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.AQUA + "Normal Diamond Pants");
                setItemMeta(meta);
            }
        }, 200, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "200 points.", ChatColor.AQUA + "Normal Diamond Pants")),
        PLATE(new ItemStack(Material.DIAMOND_CHESTPLATE, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.AQUA + "Normal Diamond ChestPlate");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.AQUA + "Normal Diamond ChestPlate");
                setItemMeta(meta);
            }
        }, 200, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "200 points.", ChatColor.AQUA + "Normal Diamond ChestPlate")),
        BOX(new ItemStack(Material.CHEST, 1) {
            {
                ArrayList<String> mysteryBox = new ArrayList<String>();
                mysteryBox.add(ChatColor.GREEN + "Mystery Box");
                mysteryBox.add(ChatColor.GRAY + "Right-click to open for a random item!");
                ItemMeta meta = getItemMeta();
                meta.setLore(mysteryBox);
                meta.setDisplayName(ChatColor.GREEN + "Mystery Box");
                setItemMeta(meta);
            }
        }, 40, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "40 points.", ChatColor.GREEN + "Mystery Box"));


        private ItemStack itemStack, shopItem;
        private int points;
        private PotionEffect potionEffect;
        private Effect effect;
        private Collection<String> events;

        private FreeRewardItem(ItemStack itemStack, int points, List<String> saleInfo) {
            this.itemStack = itemStack;
            this.points = points;
            shopItem = getItem().clone();
            ItemMeta meta = shopItem.getItemMeta();
            meta.setLore(saleInfo);
            shopItem.setItemMeta(meta);
        }

        private FreeRewardItem(ItemStack itemStack, int points, List<String> saleInfo, PotionEffect potionEffect, Effect effect, Event... events) {
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
