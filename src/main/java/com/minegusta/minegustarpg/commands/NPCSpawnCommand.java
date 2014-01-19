package com.minegusta.minegustarpg.commands;


import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import java.util.List;

public class NPCSpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (s instanceof ConsoleCommandSender) return false;
        else if (cmd.getName().equalsIgnoreCase("npc") && s.isOp()) {
            if (args.length > 1) {

                List<String> newName = Lists.newArrayList();
                for (String string : args) {
                    newName.add(string);
                }
                newName.remove(args[0]);
                Joiner joiner = Joiner.on(" ").skipNulls();
                String name = joiner.join(newName);
                ChatColor.translateAlternateColorCodes('&', name);
                Player p = (Player) s;

                String t = args[0];
                Villager.Profession w;
                if (t.equalsIgnoreCase("smith")) {
                    w = Villager.Profession.BLACKSMITH;
                } else if (t.equalsIgnoreCase("butcher")) {
                    w = Villager.Profession.BUTCHER;
                } else if (t.equalsIgnoreCase("farmer")) {
                    w = Villager.Profession.FARMER;
                } else if (t.equalsIgnoreCase("librarian")) {
                    w = Villager.Profession.LIBRARIAN;
                } else if (t.equalsIgnoreCase("priest")) {
                    w = Villager.Profession.PRIEST;
                } else {
                    w = Villager.Profession.FARMER;
                }


                Entity entity = p.getWorld().spawnEntity(p.getLocation(), EntityType.VILLAGER);
                Villager villager = (Villager) entity;
                villager.setCustomName(ChatColor.translateAlternateColorCodes('&', name));
                villager.setCustomNameVisible(true);
                villager.setAdult();
                villager.setProfession(w);
                villager.setBreed(false);

            } else {
                s.sendMessage(ChatColor.DARK_RED + "/NPC <Smith/Butcher/Farmer/Librarian/Priest> <name here>");
            }
        }
        return true;
    }
}
