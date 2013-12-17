package com.minegusta.donatorpoints;

import com.minegusta.donatorpoints.data.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class PointsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("points")) {
            if (s instanceof ConsoleCommandSender) return true;
            Player p = (Player) s;
            if (args.length == 0) {
                if (DataManager.getPointsFromPlayer(p) == null) DataManager.setPointsFromPlayer(p, 0);
                p.sendMessage(ChatColor.AQUA + "You have " + ChatColor.LIGHT_PURPLE + DataManager.getPointsFromPlayer(p) + ChatColor.AQUA + " points.");
                return true;
            } else if (args.length == 1 || args.length == 2) {
                p.sendMessage(ChatColor.AQUA + "[Points]" + ChatColor.GRAY + " Use" + ChatColor.GOLD + " /Points " + ChatColor.GRAY + "To display your points.");
                p.sendMessage(ChatColor.AQUA + "[Points]" + ChatColor.GRAY + " Use" + ChatColor.GOLD + " /Points Set <Name> <Amount> " + ChatColor.GRAY + "To set someone's points.");
            } else if (args.length == 3 && args[0].equalsIgnoreCase("Set") && p.isOp()) {
                try {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                    Player player = (Player) offlinePlayer;
                    int amount = Integer.parseInt(args[2]);
                    DataManager.setPointsFromPlayer(player, amount);
                    p.sendMessage(ChatColor.AQUA + "[Points]" + ChatColor.GRAY + " Points for " + player.getName() + " successfully set to " + amount + ".");
                } catch (Exception e) {
                    p.sendMessage(ChatColor.AQUA + "[Points]" + ChatColor.RED + " Player not found or not a number.");
                    p.sendMessage(ChatColor.AQUA + "[Points]" + ChatColor.GRAY + " Use" + ChatColor.GOLD + " /Points Set <Name> <Amount> " + ChatColor.GRAY + "To set someone's points.");
                    return true;
                }

            }
        }
        return true;
    }
}
