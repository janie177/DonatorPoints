package com.minegusta.minegustarpg.shops;

import com.google.common.collect.Lists;
import com.minegusta.minegustarpg.MinegustaRPGPlugin;
import com.minegusta.minegustarpg.data.DataManager;
import com.minegusta.minegustarpg.items.ItemListener;
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

public class ShopManager implements Listener {

    public void openInventoryOfTrader(Player user, String traderName) {
        String title = traderName + "Trader";
        Inventory inv = Bukkit.getServer().createInventory(null, 27, title);
        user.openInventory(inv);
        int count = 0;

        for (RewardItem i : RewardItem.values()) {
            if (i.toString().contains(traderName)) {
                inv.setItem(count, i.getShopItem());
                count++;
            }
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

                if (name.contains("Trader")) {
                    if (name.contains("Horse") || name.contains("Donator")) return;
                    else if (!e.getPlayer().getItemInHand().getType().equals(Material.AIR)) {
                        e.getPlayer().sendMessage(ChatColor.RED + "You can only trade with an empty hand!");
                    } else {
                        String shopName = name.replace(" Trader", "");
                        if (DataManager.getPointsFromPlayer(p) == null) DataManager.setPointsFromPlayer(p, 0);
                        p.sendMessage(ChatColor.DARK_RED + "[Trader]" + ChatColor.GRAY + " You begin trading with the ShopKeeper.");
                        p.sendMessage(ChatColor.DARK_RED + "[Trader]" + ChatColor.GRAY + " You have " + ChatColor.LIGHT_PURPLE + DataManager.getPointsFromPlayer(p) + ChatColor.GRAY + " points.");
                        openInventoryOfTrader(e.getPlayer(), shopName);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBuyItem(InventoryClickEvent e) {
        try {
            String invName = e.getClickedInventory().getName();
            if (invName != null && invName.contains("Trader")) {
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

        Armour1(new ItemStack(Material.IRON_CHESTPLATE, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Iron Chestplate");
                setItemMeta(meta);
            }
        }, 200, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "200 points.", ChatColor.YELLOW + "Iron Chestplate")),
        Armour2(new ItemStack(Material.IRON_LEGGINGS, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Iron Leggings");
                setItemMeta(meta);
            }
        }, 200, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "200 points.", ChatColor.YELLOW + "Iron Leggings")),
        Armour3(new ItemStack(Material.IRON_HELMET, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Iron Helmet");
                setItemMeta(meta);
            }
        }, 180, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "180 points.", ChatColor.YELLOW + "Iron Helmet")),
        Armour4(new ItemStack(Material.IRON_BOOTS, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Iron Boots");
                setItemMeta(meta);
            }
        }, 180, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "180 points.", ChatColor.YELLOW + "Iron Boots")),
        Armour5(new ItemStack(Material.LEATHER_CHESTPLATE, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Leather Chestplate");
                setItemMeta(meta);
            }
        }, 15, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "15 points.", ChatColor.YELLOW + "Leather Chestplate")),
        Armour6(new ItemStack(Material.LEATHER_LEGGINGS, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Leather Leggings");
                setItemMeta(meta);
            }
        }, 15, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "15 points.", ChatColor.YELLOW + "Leather Leggings")),
        Armour7(new ItemStack(Material.LEATHER_HELMET, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Leather Helmet");
                setItemMeta(meta);
            }
        }, 8, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "8 points.", ChatColor.YELLOW + "Leather Helmet")),
        Armour8(new ItemStack(Material.LEATHER_BOOTS, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Leather Boots");
                setItemMeta(meta);
            }
        }, 8, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "8 points.", ChatColor.YELLOW + "Leather Boots")),
        Armour9(new ItemStack(Material.GOLD_CHESTPLATE, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Gold Chestplate");
                setItemMeta(meta);
            }
        }, 60, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "60 points.", ChatColor.YELLOW + "Gold Chestplate")),
        Armour10(new ItemStack(Material.GOLD_LEGGINGS, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Gold Leggings");
                setItemMeta(meta);
            }
        }, 60, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "60 points.", ChatColor.YELLOW + "Gold Leggings")),
        Armour11(new ItemStack(Material.GOLD_HELMET, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Gold Helmet");
                setItemMeta(meta);
            }
        }, 50, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "50 points.", ChatColor.YELLOW + "Gold Helmet")),
        Armour12(new ItemStack(Material.GOLD_BOOTS, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Gold Boots");
                setItemMeta(meta);
            }
        }, 50, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "50 points.", ChatColor.YELLOW + "Gold Boots")),
        Armour13(new ItemStack(Material.DIAMOND_CHESTPLATE, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Diamond Chestplate");
                setItemMeta(meta);
            }
        }, 500, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "500 points.", ChatColor.YELLOW + "Diamond Chestplate")),
        Armour14(new ItemStack(Material.DIAMOND_LEGGINGS, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Diamond Leggings");
                setItemMeta(meta);
            }
        }, 500, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "500 points.", ChatColor.YELLOW + "Diamond Leggings")),
        Armour15(new ItemStack(Material.DIAMOND_HELMET, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Diamond Helmet");
                setItemMeta(meta);
            }
        }, 450, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "450 points.", ChatColor.YELLOW + "Diamond Helmet")),
        Armour16(new ItemStack(Material.DIAMOND_BOOTS, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Diamond Boots");
                setItemMeta(meta);
            }
        }, 450, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "450 points.", ChatColor.YELLOW + "Diamond Boots")),
        Armour17(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Chainmail Chestplate");
                setItemMeta(meta);
            }
        }, 150, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "70 points.", ChatColor.YELLOW + "Chainmail Chestplate")),
        Armour18(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Chainmail Leggings");
                setItemMeta(meta);
            }
        }, 150, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "70 points.", ChatColor.YELLOW + "Chainmail Leggings")),
        Armour19(new ItemStack(Material.CHAINMAIL_HELMET, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Chainmail Helmet");
                setItemMeta(meta);
            }
        }, 100, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "60 points.", ChatColor.YELLOW + "Chainmail Helmet")),
        Armour20(new ItemStack(Material.CHAINMAIL_BOOTS, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Protective armour.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Chainmail Boots");
                setItemMeta(meta);
            }
        }, 100, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "60 points.", ChatColor.YELLOW + "Chainmail Boots")),
        Food1(new ItemStack(Material.RAW_BEEF, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Nommable food! I should cook it first though.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Raw Beef");
                setItemMeta(meta);
            }
        }, 5, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "5 points.", ChatColor.YELLOW + "Raw Beef")),
        Food2(new ItemStack(Material.RAW_CHICKEN, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Nommable food! I should cook it first though.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Raw Chicken");
                setItemMeta(meta);
            }
        }, 5, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "5 points.", ChatColor.YELLOW + "Raw Chicken")),
        Food3(new ItemStack(Material.RAW_FISH, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "I would slap people with this if I didn't want to eat it.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Raw Fish");
                setItemMeta(meta);
            }
        }, 5, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "5 points.", ChatColor.YELLOW + "Raw Fish")),
        Food4(new ItemStack(Material.COOKED_BEEF, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Nommable food! Not even raw!");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Cooked Beef");
                setItemMeta(meta);
            }
        }, 8, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "8 points.", ChatColor.YELLOW + "Cooked Beef")),
        Food5(new ItemStack(Material.COOKED_FISH, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Nommable food! Not even raw!");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Cooked Fish");
                setItemMeta(meta);
            }
        }, 8, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "8 points.", ChatColor.YELLOW + "Cooked Fish")),
        Food6(new ItemStack(Material.COOKED_CHICKEN, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Nommable food! Salmonella free!");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Cooked Chicken");
                setItemMeta(meta);
            }
        }, 8, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "8 points.", ChatColor.YELLOW + "Cooked Chicken")),
        Food7(new ItemStack(Material.MELON, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Eat it before you attract any african americans.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Melon Slice");
                setItemMeta(meta);
            }
        }, 3, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "3 points.", ChatColor.YELLOW + "Melon Slice")),
        Food8(new ItemStack(Material.APPLE, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "You're not pretty! Nobody is trying to poison you. Take a bite!");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Red Apple");
                setItemMeta(meta);
            }
        }, 4, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "4 points.", ChatColor.YELLOW + "Red Apple")),
        Food9(new ItemStack(Material.CARROT, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Sup dog?");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Carrot");
                setItemMeta(meta);
            }
        }, 3, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "3 points.", ChatColor.YELLOW + "Carrot")),
        General1(new ItemStack(Material.STICK, 3) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Used for crafting and other purposes.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Wooden Stick");
                setItemMeta(meta);
            }
        }, 10, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "15 points.", ChatColor.YELLOW + "Wooden Stick")),
        General2(new ItemStack(Material.IRON_INGOT, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Used for crafting and other purposes.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Iron Bar");
                setItemMeta(meta);
            }
        }, 40, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "15 points.", ChatColor.YELLOW + "Iron Bar")),
        General3(new ItemStack(Material.LEATHER, 3) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Used for crafting and other purposes.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Refined Leather");
                setItemMeta(meta);
            }
        }, 5, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "25 points.", ChatColor.YELLOW + "Refined Leather")),
        General4(new ItemStack(Material.GOLD_INGOT, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Used for crafting and other purposes.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Gold Stave");
                setItemMeta(meta);
            }
        }, 25, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "30 points.", ChatColor.YELLOW + "Gold Stave")),
        General5(new ItemStack(Material.COAL, 3) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Used for cooking food.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Coal");
                setItemMeta(meta);
            }
        }, 10, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "15 points.", ChatColor.YELLOW + "Coal")),
        General6(ItemListener.getTeleportCrystal(), 15, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "15 points.", ChatColor.DARK_AQUA + "Teleport Crystal")),
        PotionregenII(new ItemStack(Material.POTION, 1, (short) 8225) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Smells odd. Oddly good!");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Potion of Regeneration II");
                setItemMeta(meta);
            }
        }, 25, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "25 points.", ChatColor.YELLOW + "Potion of Regeneration II")),
        PotionSpeedII(new ItemStack(Material.POTION, 1, (short) 8226) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Smells like speed.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Potion of Speed II");
                setItemMeta(meta);
            }
        }, 20, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "20 points.", ChatColor.YELLOW + "Potion of Speed II")),
        PotionFireResist(new ItemStack(Material.POTION, 1, (short) 8259) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Smells odd.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Potion of Speed II");
                setItemMeta(meta);
            }
        }, 15, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "15 points.", ChatColor.YELLOW + "Potion of Speed II")),
        PotionHealth2(new ItemStack(Material.POTION, 1, (short) 8229) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "An apple a day didn't keeps the doctor away.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Potion of Health II");
                setItemMeta(meta);
            }
        }, 20, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "20 points.", ChatColor.YELLOW + "Potion of Health II")),
        Potionnightvision(new ItemStack(Material.POTION, 1, (short) 8262) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "You are the night.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Potion of Night Vision");
                setItemMeta(meta);
            }
        }, 15, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "15 points.", ChatColor.YELLOW + "Potion of Night Vision")),
        Potionstrength(new ItemStack(Material.POTION, 1, (short) 8265) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.DARK_GREEN + "SMAAAAAAAASH");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Potion of Strength");
                setItemMeta(meta);
            }
        }, 20, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "20 points.", ChatColor.YELLOW + "Potion of Strength")),
        Potioninvis(new ItemStack(Material.POTION, 1, (short) 8270) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "How come the bottle is not invisible..?");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Potion of Invisibility");
                setItemMeta(meta);
            }
        }, 50, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "50 points.", ChatColor.YELLOW + "Potion of Invisibility")),
        Potionharmingsplash(new ItemStack(Material.POTION, 1, (short) 16396) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Also effective against fleas...");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Splash Potion of Damage");
                setItemMeta(meta);
            }
        }, 25, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "25 points.", ChatColor.YELLOW + "Splash Potion of Damage")),
        Potionhealingsplash(new ItemStack(Material.POTION, 1, (short) 16421) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "YOU TAKE IT, AND THROW IT ON THE GROUND!");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Splash Potion of Health");
                setItemMeta(meta);
            }
        }, 30, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "30 points.", ChatColor.YELLOW + "Splash Potion of Health")),
        Potionregensplash(new ItemStack(Material.POTION, 1, (short) 16417) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Heals you over time.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Splash Potion of Regeneration");
                setItemMeta(meta);
            }
        }, 30, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "30 points.", ChatColor.YELLOW + "Splash Potion of Regeneration")),
        Potionweaknesssplash(new ItemStack(Material.POTION, 1, (short) 16392) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "I would not drink this.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Splash Potion of Weakness");
                setItemMeta(meta);
            }
        }, 25, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "25 points.", ChatColor.YELLOW + "Splash Potion of Weakness")),
        Weapon1(new ItemStack(Material.WOOD_SWORD, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Not too sharp, but a nice last resort. Or envelope opener.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Wooden Knife");
                setItemMeta(meta);
            }
        }, 15, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "15 points.", ChatColor.YELLOW + "Wooden Knife")),
        Weapon2(new ItemStack(Material.IRON_SWORD, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "A decent sharp sword.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Iron Sword");
                setItemMeta(meta);
            }
        }, 50, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "50 points.", ChatColor.YELLOW + "Iron Sword")),
        Weapon3(new ItemStack(Material.STONE_SWORD, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "We're not in the stone-age anymore... We have steel now...");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Stone 'sword'");
                setItemMeta(meta);
            }
        }, 20, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "20 points.", ChatColor.YELLOW + "Stone 'sword'")),
        Weapon4(new ItemStack(Material.DIAMOND_SWORD, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Aww yiss. Shiny!");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Diamond Sword");
                setItemMeta(meta);
            }
        }, 150, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "150 points.", ChatColor.YELLOW + "Diamond Sword")),
        Weapon5(new ItemStack(Material.WOOD_AXE, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Not too sharp, but a nice last resort. Or envelope opener.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Wooden Knife");
                setItemMeta(meta);
            }
        }, 12, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "12 points.", ChatColor.YELLOW + "Wooden Axe")),
        Weapon6(new ItemStack(Material.IRON_AXE, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Good for cutting stuff. Like trees. Or people...");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Iron Axe");
                setItemMeta(meta);
            }
        }, 40, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "40 points.", ChatColor.YELLOW + "Iron Axe")),
        Weapon7(new ItemStack(Material.STONE_AXE, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "We're not in the stone-age anymore... We have steel now...");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Stone Axe");
                setItemMeta(meta);
            }
        }, 15, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "15 points.", ChatColor.YELLOW + "Stone Axe")),
        Weapon8(new ItemStack(Material.DIAMOND_AXE, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "Too bad you don't have a tree-cut permit.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Diamond Axe");
                setItemMeta(meta);
            }
        }, 100, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "100 points.", ChatColor.YELLOW + "Diamond Axe")),
        Weapon9(new ItemStack(Material.BOW, 1) {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GRAY + "For when you prefer watching from a distance.");
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.YELLOW + "Wooden Bow");
                setItemMeta(meta);
            }
        }, 20, Lists.newArrayList(ChatColor.LIGHT_PURPLE + "Cost: " + ChatColor.AQUA + "20 points.", ChatColor.YELLOW + "Wooden Bow")),
        Weapon10(new ItemStack(Material.ARROW, 5) {
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
