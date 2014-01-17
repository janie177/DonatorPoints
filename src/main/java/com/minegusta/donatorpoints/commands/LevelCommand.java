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

            List<String> helpList = Lists.newArrayList("Race: " + ChatColor.DARK_AQUA + Data.getRace(uuid), "Level: " + ChatColor.DARK_AQUA + Data.getLevel(uuid), "Kills: " + ChatColor.DARK_AQUA + Data.getMobsKilled(uuid), "Deaths: " + ChatColor.DARK_AQUA + Data.getDeaths(uuid), "Quests Completed: " + ChatColor.DARK_AQUA + Data.getQuestsDone(uuid), "Experience Points: " + ChatColor.DARK_AQUA + Data.getExperience(uuid), "Experience Till Next Level: " + ChatColor.DARK_AQUA + LevelManager.getExpLeftTillNextLevel(uuid));
            sendText(p, helpList);
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
}
