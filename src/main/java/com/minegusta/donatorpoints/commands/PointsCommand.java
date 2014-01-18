package com.minegusta.donatorpoints.commands;

import com.google.common.collect.Lists;
import com.minegusta.donatorpoints.data.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
            int points = Integer.parseInt(args[2]);
            int senderPoints = DataManager.getPointsFromPlayer(p);
            Player receiver = Bukkit.getOfflinePlayer(args[1]).getPlayer();
            int receiverPoints = DataManager.getPointsFromPlayer(receiver);


            List<String> help = Lists.newArrayList("Your currently have " + ChatColor.AQUA + DataManager.getPointsFromPlayer(p) + ChatColor.YELLOW + "Points.", ChatColor.GOLD + " - - - - - - - - - - - - - ", "/Points" + ChatColor.GRAY + " - Shows this menu", "/Points Pay <Name> <Amount>" + ChatColor.GRAY + " - Pay a player points.", "/Points <Name>" + ChatColor.GRAY + " - See someone's points.", "/Points Set <Name> <Amount>" + ChatColor.GRAY + " - Set someone's points.", "/Points Add <Name> <Amount>" + ChatColor.GRAY + " - Add points to a player.");
            List<String> insufficientFunds = Lists.newArrayList(ChatColor.RED + "You do not have that many points!");
            List<String> wrongPlayer = Lists.newArrayList(ChatColor.RED + "Player is not online or does not exist!");
            List<String> success = Lists.newArrayList("Sucessfully added " + ChatColor.AQUA + points + ChatColor.YELLOW + " points to " + receiver.getName());
            List<String> pointsFromPlayer = Lists.newArrayList(receiver.getName() + " has " + ChatColor.AQUA + receiverPoints + ChatColor.YELLOW + " points.");


            if (args.length == 0) sendText(p, help);
            else if (args[0].equalsIgnoreCase("Pay") || args[0].equalsIgnoreCase("give")) {
                if (args.length < 3) sendText(p, help);
                else {


                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (player.equals(receiver)) {
                            if (points > senderPoints) sendText(p, insufficientFunds);
                            else {

                                DataManager.setPointsFromPlayer(receiver, receiverPoints + points);
                                DataManager.setPointsFromPlayer(p, senderPoints - points);
                                sendText(p, success);
                                return true;
                            }
                        }
                    }
                    sendText(p, wrongPlayer);

                }

            } else if (args[0].equalsIgnoreCase("add")) {
                if (args.length < 3) sendText(p, help);
                else {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (player.equals(receiver)) {
                            DataManager.setPointsFromPlayer(receiver, receiverPoints + points);
                            sendText(p, success);
                            return true;
                        }
                    }
                }
                sendText(p, wrongPlayer);
            } else if (args[0].equalsIgnoreCase("set")) {
                if (args.length < 3) sendText(p, help);
                else {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (player.equals(receiver)) {
                            DataManager.setPointsFromPlayer(receiver, points);
                            sendText(p, success);
                            return true;
                        }
                    }
                }
                sendText(p, wrongPlayer);
            } else if (args.length == 1) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.equals(receiver)) {
                        sendText(p, pointsFromPlayer);
                    }
                }
                sendText(p, wrongPlayer);

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
