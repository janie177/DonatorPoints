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

import java.util.List;
import java.util.UUID;

public class LevelCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (s instanceof ConsoleCommandSender) return true;
        if (cmd.getName().equalsIgnoreCase("level")) {
            Player p = (Player) s;
            UUID uuid = p.getUniqueId();

            List<String> helpList = Lists.newArrayList("/Level Info" + ChatColor.YELLOW + " - Show your account's details.", "/Level Armour" + ChatColor.YELLOW + " - Armour Information.", "/Horse" + ChatColor.YELLOW + " - Horse Help.");
            List<String> info = Lists.newArrayList("Race: " + ChatColor.YELLOW + Data.getRace(uuid), "Level: " + ChatColor.YELLOW + Data.getLevel(uuid), "Kills: " + ChatColor.YELLOW + Data.getMobsKilled(uuid), "Deaths: " + ChatColor.YELLOW + Data.getDeaths(uuid), "Quests Completed: " + ChatColor.YELLOW + Data.getQuestsDone(uuid), "Experience Points: " + ChatColor.YELLOW + Data.getExperience(uuid), "Experience Till Next Level: " + ChatColor.YELLOW + LevelManager.getExpLeftTillNextLevel(uuid));
            List<String> armourHelp = Lists.newArrayList(ChatColor.YELLOW + "Your level determines what you can wear.", ChatColor.YELLOW + "Each armour piece has a required amount of levels.", ChatColor.YELLOW + "Your level is the total amount all your armour can have.", ChatColor.GOLD + "- - - - - - -", "Leather: " + ChatColor.AQUA + "1 level.", "Gold: " + ChatColor.AQUA + "5 levels.", "ChainMail: " + ChatColor.AQUA + "10 levels.", "Iron: " + ChatColor.AQUA + "15 levels.", "Diamond: " + ChatColor.AQUA + "20 levels.", "Example:", ChatColor.YELLOW + "Wearing an iron helmet and leather boots would require level 16 (15 + 1).");
            if (args.length == 0) sendText(p, helpList);
            else if (args[0].equalsIgnoreCase("info")) {
                sendText(p, info);
            } else if (args[0].equalsIgnoreCase("armour") || args[0].equalsIgnoreCase("armor")) {
                sendText(p, armourHelp);
            } else {
                sendText(p, helpList);
            }

        }
        return true;
    }


    public void sendText(Player p, List<String> text) {
        p.sendMessage(ChatColor.DARK_RED + "---------------" + ChatColor.GOLD + " RPG Help " + ChatColor.DARK_RED + "---------------");
        for (String s : text) {
            p.sendMessage(ChatColor.RED + s);
        }
        p.sendMessage(ChatColor.DARK_RED + "------------------------------------------");
    }
}
