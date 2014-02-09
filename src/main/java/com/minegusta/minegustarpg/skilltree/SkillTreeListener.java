package com.minegusta.minegustarpg.skilltree;

import com.minegusta.minegustarpg.MinegustaRPGPlugin;
import com.minegusta.minegustarpg.data.DataManager;
import com.minegusta.minegustarpg.playerdata.Data;
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

public class SkillTreeListener implements Listener {

    //Updating skills

    public void openSkillMenu(Player user) {
        String title = ChatColor.DARK_RED + "SkillPoints to spend: " + ChatColor.RED + Data.getLevelPoints(user.getUniqueId()) / 3;
        Inventory inv = Bukkit.getServer().createInventory(null, 18, title);
        user.openInventory(inv);

        int count = 0;
        for (ItemStack i : SkillTreeGui.getmenuItems(user.getUniqueId().toString())) {
            inv.setItem(count, i);
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

                if (name.contains("Skill Trainer")) {
                    if (!e.getPlayer().getItemInHand().getType().equals(Material.AIR)) {
                        e.getPlayer().sendMessage(ChatColor.RED + "You can only trade with an empty hand!");
                    } else {
                        if (DataManager.getPointsFromPlayer(p) == null) DataManager.setPointsFromPlayer(p, 0);
                        p.sendMessage(ChatColor.YELLOW + "[Skill Trainer]" + ChatColor.GRAY + " Click an item to buy that upgrade. Upgrades are permanent!");
                        p.sendMessage(ChatColor.YELLOW + "[Skill Trainer]" + ChatColor.GRAY + " You have " + ChatColor.LIGHT_PURPLE + Data.getLevelPoints(p.getUniqueId()) / 3 + ChatColor.GRAY + " skill points.");
                        p.sendMessage(ChatColor.YELLOW + "[Skill Trainer]" + ChatColor.GRAY + " An upgrade costs 1 skill point.");
                        openSkillMenu(e.getPlayer());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBuyItem(InventoryClickEvent e) {
        if (!e.getWhoClicked().getWorld().getName().toLowerCase().equalsIgnoreCase(MinegustaRPGPlugin.world)) return;
        try {
            String invName = e.getClickedInventory().getName();
            Player user = (Player) e.getWhoClicked();
            if (invName != null && invName.equals(ChatColor.DARK_RED + "SkillPoints to spend: " + ChatColor.RED + Data.getLevelPoints(user.getUniqueId()) / 3)) {
                if (e.getCursor() != null && e.getCurrentItem().getType().equals(Material.AIR)) e.setCancelled(true);
                if (e.getCurrentItem().getType() == Material.AIR) return;
                if (e.getCurrentItem() == null || e.getCurrentItem().getItemMeta().getDisplayName() == null) return;

                e.setCancelled(true);
                HumanEntity player = e.getWhoClicked();

                ItemStack clickedItem = e.getCurrentItem();

                if (clickedItem == null) return;

                String playerName = e.getWhoClicked().getName();
                Player entityPlayer = Bukkit.getOfflinePlayer(playerName).getPlayer();

                int pointsPresent = Data.getLevelPoints(entityPlayer.getUniqueId());
                int points = 3;

                if (points > pointsPresent) {
                    entityPlayer.sendMessage(ChatColor.RED + "You do not have enough skill-points to buy an upgrade.");
                    player.closeInventory();
                    return;
                }
                String uuid = entityPlayer.getUniqueId().toString();

                if (clickedItem.getType().equals(SkillTreeGui.Branches.WARRIOR.getItemStack().getType())) {
                    if (SkillTreeData.getWarriorFromFile(uuid) > 4) {
                        entityPlayer.sendMessage(ChatColor.YELLOW + "[Skill Trainer]" + ChatColor.RED + " You already have the maximum level in that skill.");
                        player.closeInventory();
                        return;
                    }
                    SkillTreeData.addWarrior(entityPlayer.getUniqueId().toString(), 1);
                } else if (clickedItem.getType().equals(SkillTreeGui.Branches.POWER.getItemStack().getType())) {
                    if (SkillTreeData.getPowerFromFile(uuid) > 2) {
                        entityPlayer.sendMessage(ChatColor.YELLOW + "[Skill Trainer]" + ChatColor.RED + " You already have the maximum level in that skill.");
                        player.closeInventory();
                        return;
                    }
                    SkillTreeData.addPower(entityPlayer.getUniqueId().toString(), 1);
                } else if (clickedItem.getType().equals(SkillTreeGui.Branches.ATHLETE.getItemStack().getType())) {
                    if (SkillTreeData.getAthleteFromFile(uuid) > 4) {
                        entityPlayer.sendMessage(ChatColor.YELLOW + "[Skill Trainer]" + ChatColor.RED + " You already have the maximum level in that skill.");
                        player.closeInventory();
                        return;
                    }
                    SkillTreeData.addAthlete(entityPlayer.getUniqueId().toString(), 1);
                } else if (clickedItem.getType().equals(SkillTreeGui.Branches.RUNNER.getItemStack().getType())) {
                    if (SkillTreeData.getRunnerFromFile(uuid) > 2) {
                        entityPlayer.sendMessage(ChatColor.YELLOW + "[Skill Trainer]" + ChatColor.RED + " You already have the maximum level in that skill.");
                        player.closeInventory();
                        return;
                    }
                    SkillTreeData.addRunner(entityPlayer.getUniqueId().toString(), 1);
                } else if (clickedItem.getType().equals(SkillTreeGui.Branches.ARCHER.getItemStack().getType())) {
                    if (SkillTreeData.getArcherFromFile(uuid) > 4) {
                        entityPlayer.sendMessage(ChatColor.YELLOW + "[Skill Trainer]" + ChatColor.RED + " You already have the maximum level in that skill.");
                        player.closeInventory();
                        return;
                    }
                    SkillTreeData.addArcher(entityPlayer.getUniqueId().toString(), 1);
                } else if (clickedItem.getType().equals(SkillTreeGui.Branches.ARROWEFFICIENCY.getItemStack().getType())) {
                    if (SkillTreeData.getArrowEfficiencyFromFile(uuid) > 1) {
                        entityPlayer.sendMessage(ChatColor.YELLOW + "[Skill Trainer]" + ChatColor.RED + " You already have the maximum level in that skill.");
                        player.closeInventory();
                        return;
                    }
                    SkillTreeData.addArrowEfficiency(entityPlayer.getUniqueId().toString(), 1);
                } else if (clickedItem.getType().equals(SkillTreeGui.Branches.BOWMAN.getItemStack().getType())) {
                    if (SkillTreeData.getBowManFromFile(uuid) > 2) {
                        entityPlayer.sendMessage(ChatColor.YELLOW + "[Skill Trainer]" + ChatColor.RED + " You already have the maximum level in that skill.");
                        player.closeInventory();
                        return;
                    }
                    SkillTreeData.addBowMan(entityPlayer.getUniqueId().toString(), 1);
                } else if (clickedItem.getType().equals(SkillTreeGui.Branches.ASSASSIN.getItemStack().getType())) {
                    if (SkillTreeData.getAssassinFromFile(uuid) > 2) {
                        entityPlayer.sendMessage(ChatColor.YELLOW + "[Skill Trainer]" + ChatColor.RED + " You already have the maximum level in that skill.");
                        player.closeInventory();
                        return;
                    }
                    SkillTreeData.addAssassin(entityPlayer.getUniqueId().toString(), 1);
                } else if (clickedItem.getType().equals(SkillTreeGui.Branches.TANK.getItemStack().getType())) {
                    if (SkillTreeData.getTankFromFile(uuid) > 4) {
                        entityPlayer.sendMessage(ChatColor.YELLOW + "[Skill Trainer]" + ChatColor.RED + " You already have the maximum level in that skill.");
                        player.closeInventory();
                        return;
                    }
                    SkillTreeData.addTank(entityPlayer.getUniqueId().toString(), 1);
                } else if (clickedItem.getType().equals(SkillTreeGui.Branches.STUNNER.getItemStack().getType())) {
                    if (SkillTreeData.getStunnerFromFile(uuid) > 2) {
                        entityPlayer.sendMessage(ChatColor.YELLOW + "[Skill Trainer]" + ChatColor.RED + " You already have the maximum level in that skill.");
                        player.closeInventory();
                        return;
                    }
                    SkillTreeData.addStunner(entityPlayer.getUniqueId().toString(), 1);
                } else if (clickedItem.getType().equals(SkillTreeGui.Branches.HEALER.getItemStack().getType())) {
                    if (SkillTreeData.getHealerFromFile(uuid) > 3) {
                        entityPlayer.sendMessage(ChatColor.YELLOW + "[Skill Trainer]" + ChatColor.RED + " You already have the maximum level in that skill.");
                        player.closeInventory();
                        return;
                    }
                    SkillTreeData.addHealer(entityPlayer.getUniqueId().toString(), 1);
                } else if (clickedItem.getType().equals(SkillTreeGui.Branches.SCOUT.getItemStack().getType())) {
                    if (SkillTreeData.getScoutFromFile(uuid) > 3) {
                        entityPlayer.sendMessage(ChatColor.YELLOW + "[Skill Trainer]" + ChatColor.RED + " You already have the maximum level in that skill.");
                        player.closeInventory();
                        return;
                    }
                    SkillTreeData.addScout(entityPlayer.getUniqueId().toString(), 1);
                } else if (clickedItem.getType().equals(SkillTreeGui.Branches.ALCHEMIST.getItemStack().getType())) {
                    if (SkillTreeData.getAlchemistFromFile(uuid) > 3) {
                        entityPlayer.sendMessage(ChatColor.YELLOW + "[Skill Trainer]" + ChatColor.RED + " You already have the maximum level in that skill.");
                        player.closeInventory();
                        return;
                    }
                    SkillTreeData.addAlchemist(entityPlayer.getUniqueId().toString(), 1);
                } else if (clickedItem.getType().equals(SkillTreeGui.Branches.LUCK.getItemStack().getType())) {
                    if (SkillTreeData.getLuckFromFile(uuid) > 2) {
                        entityPlayer.sendMessage(ChatColor.YELLOW + "[Skill Trainer]" + ChatColor.RED + " You already have the maximum level in that skill.");
                        player.closeInventory();
                        return;
                    }
                    SkillTreeData.addLuck(entityPlayer.getUniqueId().toString(), 1);
                } else if (clickedItem.getType().equals(SkillTreeGui.Branches.BLOODBATH.getItemStack().getType())) {
                    if (SkillTreeData.getBloodbathFromFile(uuid) > 2) {
                        entityPlayer.sendMessage(ChatColor.YELLOW + "[Skill Trainer]" + ChatColor.RED + " You already have the maximum level in that skill.");
                        player.closeInventory();
                        return;
                    }
                    SkillTreeData.addBloodbath(entityPlayer.getUniqueId().toString(), 1);
                }
                Data.removeLevelPoints(entityPlayer.getUniqueId(), 3);
                SkillTreeData.loadPlayerToMaps(uuid);
                entityPlayer.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "Trade" + ChatColor.DARK_RED + "]" + ChatColor.YELLOW + "You have successfully bought an upgrade!");
                player.closeInventory();
            }
        } catch (Exception ignored) {
        }
    }
}
