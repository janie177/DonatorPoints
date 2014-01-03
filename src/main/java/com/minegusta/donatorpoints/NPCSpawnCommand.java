package com.minegusta.donatorpoints;


import com.google.common.base.Joiner;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

public class NPCSpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (s instanceof ConsoleCommandSender) return false;
        else if (cmd.getName().equalsIgnoreCase("npc") && s.isOp()) {
            if (args.length != 0) {
                Joiner joiner = Joiner.on(" ").skipNulls();
                String name = joiner.join(args);
                ChatColor.translateAlternateColorCodes('&', name);
                Player p = (Player) s;
                Entity entity = p.getWorld().spawnEntity(p.getLocation(), EntityType.VILLAGER);
                Villager villager = (Villager) entity;
                villager.setCustomName(ChatColor.translateAlternateColorCodes('&', name));
                villager.setCustomNameVisible(true);
                villager.setAdult();
                villager.setBreed(false);

            }
        }
        return true;
    }
}
