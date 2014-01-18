package com.minegusta.donatorpoints.commands;

import com.google.common.collect.Lists;
import com.minegusta.donatorpoints.data.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class PointsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("points")) {
            if (s instanceof ConsoleCommandSender) return true;

            Player p = (Player) s;
            if (DataManager.getPointsFromPlayer(p) == null) DataManager.setPointsFromPlayer(p, 0);
            List<String> help = Lists.newArrayList(" ", "You currently have " + ChatColor.AQUA + DataManager.getPointsFromPlayer(p) + ChatColor.YELLOW + " Points.", " ", ChatColor.GOLD + " - - - - - - - - - - - - - - - - - - - - - - -", "/Points" + ChatColor.GRAY + " - Shows this menu", "/Points Pay <Name> <Amount>" + ChatColor.GRAY + " - Pay a player points.", "/Points Set <Name> <Amount>" + ChatColor.GRAY + " - Set someone's points.", "/Points Add <Name> <Amount>" + ChatColor.GRAY + " - Add points to a player.");
            List<String> insufficientFunds = Lists.newArrayList(ChatColor.RED + "You do not have that many points!");
            List<String> wrongPlayer = Lists.newArrayList(ChatColor.RED + "Player is not online or does not exist!");


            if (args.length == 0) sendText(p, help);
            else if (args[0].equalsIgnoreCase("Pay") || args[0].equalsIgnoreCase("give")) {
                try {
                    OfflinePlayer person = Bukkit.getOfflinePlayer(args[1]);
                    if (DataManager.getPointsFromPlayer(person) == null) DataManager.setPointsFromPlayer(person, 0);
                    long personPoints = (long) DataManager.getPointsFromPlayer(person);
                    long points = (long) Integer.parseInt(args[2]);
                    List<String> success = Lists.newArrayList("You successfully paid " + person.getName() + ChatColor.AQUA + " " + points + ChatColor.YELLOW + " points.");
                    List<String> self = Lists.newArrayList(ChatColor.RED + "You cannot pay yourself.");
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (player.equals(person)) {
                            if (p.equals(person)) sendText(p, self);
                            else {
                                DataManager.setPointsFromPlayer(p, DataManager.getPointsFromPlayer(p) - (int) points);
                                DataManager.setPointsFromPlayer(person, DataManager.getPointsFromPlayer(person) + (int) points);
                                Player lePlayer = (Player) person;
                                lePlayer.sendMessage(ChatColor.YELLOW + p.getName() + " paid you " + ChatColor.AQUA + points + ChatColor.YELLOW + " points.");
                                sendText(p, success);
                            }
                        }
                    }

                } catch (Exception error) {
                    sendText(p, wrongPlayer);
                }

            } else if (args[0].equalsIgnoreCase("add") && p.isOp()) {
                try {
                    OfflinePlayer person = Bukkit.getOfflinePlayer(args[1]);
                    if (DataManager.getPointsFromPlayer(person) == null) DataManager.setPointsFromPlayer(person, 0);
                    long personPoints = (long) DataManager.getPointsFromPlayer(person);
                    long points = (long) Integer.parseInt(args[2]);
                    List<String> success = Lists.newArrayList("You successfully added " + ChatColor.AQUA + points + ChatColor.YELLOW + " points to " + person.getName());
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (player.equals(person)) {
                            DataManager.setPointsFromPlayer(person, (int) personPoints + (int) points);
                            sendText(p, success);
                        }
                    }
                } catch (Exception error) {
                    sendText(p, wrongPlayer);
                }
            } else if (args[0].equalsIgnoreCase("set") && p.isOp()) {
                try {
                    OfflinePlayer person = Bukkit.getOfflinePlayer(args[1]);
                    if (DataManager.getPointsFromPlayer(person) == null) DataManager.setPointsFromPlayer(person, 0);
                    long personPoints = (long) DataManager.getPointsFromPlayer(person);
                    long points = (long) Integer.parseInt(args[2]);
                    List<String> success = Lists.newArrayList("You successfully set " + person.getName() + "'s points to " + ChatColor.AQUA + points);
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (player.equals(person)) {
                            DataManager.setPointsFromPlayer(person, (int) points);
                            sendText(p, success);
                        }
                    }
                } catch (Exception error) {
                    sendText(p, wrongPlayer);
                }
            } else {
                sendText(p, help);
            }
        }
        return true;
    }

    private void sendText(Player p, List<String> text) {
        p.sendMessage(ChatColor.DARK_RED + "---------------" + ChatColor.GOLD + " Points Help " + ChatColor.DARK_RED + "---------------");
        for (String s : text) {
            p.sendMessage(ChatColor.YELLOW + s);
        }
        p.sendMessage(ChatColor.DARK_RED + "------------------------------------------");
    }
}
