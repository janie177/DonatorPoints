package com.minegusta.donatorpoints.commands;


import com.google.common.collect.Lists;
import com.minegusta.donatorpoints.LevelManager;
import com.minegusta.donatorpoints.playerdata.Data;
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

            List<String> itemsKept = getArmourNames(p);


            List<String> helpList = Lists.newArrayList("/Level Info" + ChatColor.GRAY + " - Show your account's details.", "/Level Armour" + ChatColor.GRAY + " - Armour Information.", "/Level Death" + ChatColor.GRAY + " - Help on dieing. (No, not what it sounds like).", "/Horse" + ChatColor.GRAY + " - Horse Help.");
            List<String> info = Lists.newArrayList("Race: " + ChatColor.GRAY + Data.getRace(uuid), "Level: " + ChatColor.GRAY + Data.getLevel(uuid), "Kills: " + ChatColor.GRAY + Data.getMobsKilled(uuid), "Deaths: " + ChatColor.GRAY + Data.getDeaths(uuid), "Quests Completed: " + ChatColor.GRAY + Data.getQuestsDone(uuid), "Experience Points: " + ChatColor.GRAY + Data.getExperience(uuid), "Experience Till Next Level: " + ChatColor.GRAY + LevelManager.getExpLeftTillNextLevel(uuid));
            List<String> armourHelp = Lists.newArrayList(ChatColor.GRAY + "Your level determines what you can wear.", ChatColor.GRAY + "Each armour piece has a required amount of levels.", ChatColor.GRAY + "Your level is the total amount all your armour can have.", ChatColor.GOLD + "- - - - - - -", "Leather: " + ChatColor.AQUA + "1 level.", "Gold: " + ChatColor.AQUA + "5 levels.", "ChainMail: " + ChatColor.AQUA + "10 levels.", "Iron: " + ChatColor.AQUA + "15 levels.", "Diamond: " + ChatColor.AQUA + "20 levels.", "Example:", ChatColor.GRAY + "Wearing an iron helmet and leather boots would require level 16 (15 + 1).");
            List<String> deathHelp = Lists.newArrayList("When you die, you keep a few items:", " - " + ChatColor.GRAY + "All your armour.", " - " + ChatColor.GRAY + "The itemstack in your first hotbar slot.", "For you, those are the following items:", itemsKept.get(0), itemsKept.get(1), itemsKept.get(2), itemsKept.get(3), itemsKept.get(4));


            if (args.length == 0) sendText(p, helpList);
            else if (args[0].equalsIgnoreCase("info")) {
                sendText(p, info);
            } else if (args[0].equalsIgnoreCase("armour") || args[0].equalsIgnoreCase("armor")) {
                sendText(p, armourHelp);
            } else if (args[0].equalsIgnoreCase("death")) {
                sendText(p, deathHelp);
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
            body = ChatColor.YELLOW + " - " + ChatColor.AQUA + inv.getChestplate().toString();
        }
        if (p.getInventory().getBoots() != null) {
            boots = ChatColor.YELLOW + " - " + ChatColor.AQUA + inv.getBoots().toString();

        }
        if (p.getInventory().getHelmet() != null) {
            helmet = ChatColor.YELLOW + " - " + ChatColor.AQUA + inv.getHelmet().toString();

        }
        if (p.getInventory().getLeggings() != null) {
            pants = ChatColor.YELLOW + " - " + ChatColor.AQUA + inv.getLeggings().toString();

        }
        if (p.getInventory().getItem(0) != null) {
            slot = ChatColor.YELLOW + " - " + ChatColor.AQUA + p.getInventory().getItem(0).toString();
        }
        list.add(body);
        list.add(boots);
        list.add(helmet);
        list.add(pants);
        list.add(slot);

        return list;
    }
}
