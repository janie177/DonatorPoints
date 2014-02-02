package com.minegusta.minegustarpg.commands;


import com.google.common.collect.Lists;
import com.minegusta.minegustarpg.managers.LevelManager;
import com.minegusta.minegustarpg.playerdata.Data;
import com.minegusta.minegustarpg.skilltree.SkillTreeData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;
import java.util.UUID;

public class LevelCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (s instanceof ConsoleCommandSender) return true;
        if (cmd.getName().equalsIgnoreCase("level")) {
            Player p = (Player) s;
            UUID uuid = p.getUniqueId();
            String id = uuid.toString();

            List<String> itemsKept = getArmourNames(p);


            List<String> helpList = Lists.newArrayList("/Level Info" + ChatColor.GRAY + " - Show your account's details.", "/Level Armour" + ChatColor.GRAY + " - Armour Information.", "/Level Death" + ChatColor.GRAY + " - Help on dieing. (No, not what it sounds like).", "/Horse" + ChatColor.GRAY + " - Horse Help.", "/Points" + ChatColor.GRAY + " - Points help.", "/Level Skills" + ChatColor.GRAY + " - Show your skills.");
            List<String> info = Lists.newArrayList("Race: " + ChatColor.GRAY + Data.getRace(uuid), "Level: " + ChatColor.GRAY + Data.getLevel(uuid), "Kills: " + ChatColor.GRAY + Data.getMobsKilled(uuid), "Deaths: " + ChatColor.GRAY + Data.getDeaths(uuid), "Quests Completed: " + ChatColor.GRAY + Data.getQuestsDone(uuid), "Experience Points: " + ChatColor.GRAY + Data.getExperience(uuid), "Experience Till Next Level: " + ChatColor.GRAY + LevelManager.getExpLeftTillNextLevel(uuid));
            List<String> armourHelp = Lists.newArrayList(ChatColor.GRAY + "Your level determines what you can wear.", ChatColor.GRAY + "Each armour piece has a required amount of levels.", ChatColor.GRAY + "Your level is the total amount all your armour can have.", ChatColor.GOLD + "- - - - - - -", "Leather: " + ChatColor.AQUA + "1 level.", "Gold: " + ChatColor.AQUA + "5 levels.", "ChainMail: " + ChatColor.AQUA + "10 levels.", "Iron: " + ChatColor.AQUA + "15 levels.", "Diamond: " + ChatColor.AQUA + "20 levels.", "Example:", ChatColor.GRAY + "Wearing an iron helmet and leather boots would require level 16 (15 + 1).");
            List<String> deathHelp = Lists.newArrayList("When you die, you keep a few items:", " - " + ChatColor.GRAY + "All your armour.", " - " + ChatColor.GRAY + "The itemstack in your first hotbar slot.", "For you, those are the following items:", itemsKept.get(0), itemsKept.get(1), itemsKept.get(2), itemsKept.get(3), itemsKept.get(4));
            List<String> skillHelp = Lists.newArrayList();


            if (args.length == 0) sendText(p, helpList);
            else if (args[0].equalsIgnoreCase("info")) {
                sendText(p, info);
            } else if (args[0].equalsIgnoreCase("armour") || args[0].equalsIgnoreCase("armor")) {
                sendText(p, armourHelp);
            } else if (args[0].equalsIgnoreCase("death")) {
                sendText(p, deathHelp);
            } else if (args[0].equalsIgnoreCase("skills")) {

                if (SkillTreeData.archer.containsKey(id)) {
                    skillHelp.add("Archer: " + ChatColor.GRAY + SkillTreeData.archer.get(id));
                }
                if (SkillTreeData.warrior.containsKey(id)) {
                    skillHelp.add("Warrior: " + ChatColor.GRAY + SkillTreeData.warrior.get(id));
                }
                if (SkillTreeData.assassin.containsKey(id)) {
                    skillHelp.add("Assassin: " + ChatColor.GRAY + SkillTreeData.assassin.get(id));
                }
                if (SkillTreeData.tank.containsKey(id)) {
                    skillHelp.add("Tank: " + ChatColor.GRAY + SkillTreeData.tank.get(id));
                }
                if (SkillTreeData.stunner.containsKey(id)) {
                    skillHelp.add("Stunner: " + ChatColor.GRAY + SkillTreeData.stunner.get(id));
                }
                if (SkillTreeData.healer.containsKey(id)) {
                    skillHelp.add("Healer: " + ChatColor.GRAY + SkillTreeData.healer.get(id));
                }
                if (SkillTreeData.scout.containsKey(id)) {
                    skillHelp.add("Scout: " + ChatColor.GRAY + SkillTreeData.scout.get(id));
                }
                if (SkillTreeData.alchemist.containsKey(id)) {
                    skillHelp.add("Alchemist: " + ChatColor.GRAY + SkillTreeData.alchemist.get(id));
                }
                if (SkillTreeData.luck.containsKey(id)) {
                    skillHelp.add("Luck: " + ChatColor.GRAY + SkillTreeData.luck.get(id));
                }
                if (SkillTreeData.bloodbath.containsKey(id)) {
                    skillHelp.add("Bloodbath: " + ChatColor.GRAY + SkillTreeData.bloodbath.get(id));
                }
                if (SkillTreeData.bowman.containsKey(id)) {
                    skillHelp.add("Bowman: " + ChatColor.GRAY + SkillTreeData.bowman.get(id));
                }
                if (SkillTreeData.power.containsKey(id)) {
                    skillHelp.add("Power: " + ChatColor.GRAY + SkillTreeData.power.get(id));
                }
                if (SkillTreeData.arrowefficiency.containsKey(id)) {
                    skillHelp.add("ArrowEfficiency: " + ChatColor.GRAY + SkillTreeData.arrowefficiency.get(id));
                }
                if (SkillTreeData.runner.containsKey(id)) {
                    skillHelp.add("Runner: " + ChatColor.GRAY + SkillTreeData.runner.get(id));
                }
                if (SkillTreeData.athlete.containsKey(id)) {
                    skillHelp.add("Athlete: " + ChatColor.GRAY + SkillTreeData.athlete.get(id));
                }

                if (skillHelp.isEmpty()) {
                    skillHelp.add("You have no skills! See a" + ChatColor.DARK_RED + " Skill Trainer " + ChatColor.YELLOW + "to get some!");
                    skillHelp.add("You get a skillpoint each 3 levels!");
                }
                sendText(p, skillHelp);
            } else {
                sendText(p, helpList);
            }

        }
        return true;
    }


    public void sendText(Player p, List<String> text) {
        p.sendMessage(ChatColor.DARK_RED + "---------------" + ChatColor.GOLD + " RPG Help " + ChatColor.DARK_RED + "---------------");
        for (String s : text) {
            p.sendMessage(ChatColor.YELLOW + s);
        }
        p.sendMessage(ChatColor.DARK_RED + "------------------------------------------");
    }

    private List<String> getArmourNames(Player p) {
        PlayerInventory inv = p.getInventory();
        String body = ChatColor.YELLOW + " - " + ChatColor.RED + "No chestplate worn.";
        String boots = ChatColor.YELLOW + " - " + ChatColor.RED + "No boots worn.";
        String helmet = ChatColor.YELLOW + " - " + ChatColor.RED + "No helmet worn.";
        String pants = ChatColor.YELLOW + " - " + ChatColor.RED + "No pants worn.";
        String slot = ChatColor.YELLOW + " - " + ChatColor.RED + "No item in first hotbar slot.";

        List<String> list = Lists.newArrayList();


        if (p.getInventory().getChestplate() != null) {
            body = ChatColor.YELLOW + " - " + ChatColor.AQUA + inv.getChestplate().getType().toString();
        }
        if (p.getInventory().getBoots() != null) {
            boots = ChatColor.YELLOW + " - " + ChatColor.AQUA + inv.getBoots().getType().toString();

        }
        if (p.getInventory().getHelmet() != null) {
            helmet = ChatColor.YELLOW + " - " + ChatColor.AQUA + inv.getHelmet().getType().toString();

        }
        if (p.getInventory().getLeggings() != null) {
            pants = ChatColor.YELLOW + " - " + ChatColor.AQUA + inv.getLeggings().getType().toString();

        }
        if (p.getInventory().getItem(0) != null) {
            slot = ChatColor.YELLOW + " - " + ChatColor.AQUA + p.getInventory().getItem(0).getType().toString();
        }
        list.add(body);
        list.add(boots);
        list.add(helmet);
        list.add(pants);
        list.add(slot);

        return list;
    }
}
