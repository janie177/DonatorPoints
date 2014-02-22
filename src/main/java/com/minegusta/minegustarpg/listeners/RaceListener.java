package com.minegusta.minegustarpg.listeners;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.minegusta.minegustarpg.MinegustaRPGPlugin;
import com.minegusta.minegustarpg.data.DataManager;
import com.minegusta.minegustarpg.playerdata.Data;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

public class RaceListener implements Listener {

    //Altar that sets your race.
    @EventHandler
    public void onBeaconClick(PlayerInteractEvent e) {
        if (e.getPlayer().getWorld().getName().toLowerCase().equalsIgnoreCase(MinegustaRPGPlugin.world)) {
            e.setCancelled(true);
            openRaceMenu(e.getPlayer());
        }

    }

    @EventHandler
    public void onBuyRace(InventoryClickEvent e) {
        try {
            String invName = e.getClickedInventory().getName();
            if (invName != null && invName.equals("Race Crystal")) {
                if (e.getCursor() != null && e.getCurrentItem().getType().equals(Material.AIR)) e.setCancelled(true);
                if (e.getCurrentItem().getType() == Material.AIR) return;
                if (e.getCurrentItem() == null || e.getCurrentItem().getItemMeta().getDisplayName() == null) return;

                e.setCancelled(true);
                HumanEntity player = e.getWhoClicked();

                ItemStack theItemToAdd = null;
                int points = 0;
                for (Races item : Races.values()) {
                    if (!item.getItem().getType().equals(e.getCurrentItem().getType())) continue;
                    points = item.getPoints();
                    theItemToAdd = item.getItem();
                    break;
                }

                if (theItemToAdd == null) return;

                String playerString = e.getWhoClicked().getName();
                Player entityPlayer = Bukkit.getOfflinePlayer(playerString).getPlayer();

                int pointsPresent = DataManager.getPointsFromPlayer(entityPlayer);

                if (points > pointsPresent) {
                    entityPlayer.sendMessage(ChatColor.RED + "You do not have enough points to buy that race. Use" + ChatColor.AQUA + "/points" + ChatColor.RED + ".");
                    player.closeInventory();
                    return;
                }
                DataManager.setPointsFromPlayer(entityPlayer, pointsPresent - points);
                UUID uuid = entityPlayer.getUniqueId();
                if (theItemToAdd.getType().equals(Material.DIAMOND_SWORD)) {
                    Data.setRace(uuid, "human");
                } else if (theItemToAdd.getType().equals(Material.BOW)) {
                    Data.setRace(uuid, "elf");
                } else if (theItemToAdd.getType().equals(Material.DIAMOND_AXE)) {
                    Data.setRace(uuid, "dwarf");
                } else if (theItemToAdd.getType().equals(Material.POISONOUS_POTATO)) {
                    Data.setRace(uuid, "snakeman");
                }
                putPlayerRaceInMap(uuid);
                entityPlayer.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "Trade" + ChatColor.DARK_RED + "]" + ChatColor.YELLOW + "You have successfully bought a new you!");
                entityPlayer.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "Trade" + ChatColor.DARK_RED + "]" + ChatColor.YELLOW + "Use " + ChatColor.GOLD + "/RPG info" + ChatColor.YELLOW + " for help!");
                player.closeInventory();
            }

        } catch (Exception ignored) {

        }
    }

    private void openRaceMenu(Player p) {
        Inventory inv = Bukkit.createInventory(null, 9, ChatColor.RED + "Race Crystal");
        p.openInventory(inv);
        int count = 0;
        for (Races shopItem : Races.values()) {
            inv.setItem(count, shopItem.getItem());
            count++;
        }
    }

    public enum Races {
        ELF(new ItemStack(Material.BOW) {
            {
                ItemMeta meta = getItemMeta();
                meta.setDisplayName(ChatColor.GREEN + "Elf");
                List<String> lore = Lists.newArrayList(ChatColor.GRAY + "Elves are the people of the forst.", ChatColor.GRAY + "They use bows and are rather agile.", ChatColor.DARK_PURPLE + "/RPG Races Elf" + ChatColor.GRAY + " - More info about this race.");
                meta.setLore(lore);
                setItemMeta(meta);

            }
        }, 900),
        DWARF(new ItemStack(Material.DIAMOND_AXE) {
            {
                ItemMeta meta = getItemMeta();
                meta.setDisplayName(ChatColor.GREEN + "Dwarf");
                List<String> lore = Lists.newArrayList(ChatColor.GRAY + "Lords of the mountain halls.", ChatColor.GRAY + "They have strength and use axes.", ChatColor.DARK_PURPLE + "/RPG Races Dwarf" + ChatColor.GRAY + " - More info about this race.");
                meta.setLore(lore);
                setItemMeta(meta);

            }
        }, 900),
        HUMAN(new ItemStack(Material.DIAMOND_SWORD) {
            {
                ItemMeta meta = getItemMeta();
                meta.setDisplayName(ChatColor.GREEN + "Human");
                List<String> lore = Lists.newArrayList(ChatColor.GRAY + "Greedy bastards.", ChatColor.GRAY + "They use swords.", ChatColor.DARK_PURPLE + "/RPG Races Human" + ChatColor.GRAY + " - More info about this race.");
                meta.setLore(lore);
                setItemMeta(meta);

            }
        }, 900),
        SNAKEMAN(new ItemStack(Material.POISONOUS_POTATO) {
            {
                ItemMeta meta = getItemMeta();
                meta.setDisplayName(ChatColor.GREEN + "SnakeMan");
                List<String> lore = Lists.newArrayList(ChatColor.GRAY + "Stealth assassins from the east.", ChatColor.GRAY + "They move in the shadows and are resistant to poison.", ChatColor.DARK_PURPLE + "/RPG Races SnakeMan" + ChatColor.GRAY + " - More info about this race.");
                meta.setLore(lore);
                setItemMeta(meta);

            }
        }, 900);


        private ItemStack itemStack;
        private int points;

        private Races(ItemStack itemStack, int points) {
            this.itemStack = itemStack;
            this.points = points;
        }

        public ItemStack getItem() {
            return itemStack;
        }

        public int getPoints() {
            return points;
        }
    }

    //Race Data.

    public static ConcurrentMap<UUID, String> raceMap = Maps.newConcurrentMap();

    public static String getRace(UUID mojangID) {
        if (raceMap.containsKey(mojangID)) {
            return raceMap.get(mojangID);
        }
        return null;
    }

    public static void putPlayerRaceInMap(UUID mojangID) {
        if (getRace(mojangID).equals("none")) return;
        else {
            raceMap.put(mojangID, Data.getRace(mojangID));
        }
    }

    public static void removePlayerRaceFromMap(UUID mojangID) {
        if (raceMap.containsKey(mojangID)) {
            raceMap.remove(mojangID);
        }
    }

    public static void addPlayersToRaceMapAtStart() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            putPlayerRaceInMap(p.getUniqueId());
        }
    }


}
