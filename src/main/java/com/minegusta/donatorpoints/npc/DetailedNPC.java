package com.minegusta.donatorpoints.npc;

import com.censoredsoftware.censoredlib.util.Randoms;
import com.google.common.collect.Maps;
import com.minegusta.donatorpoints.DonatorPointsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DetailedNPC implements NPC {
    private Type type;
    private String name;
    private List<String> dialog;

    public enum Type {
        RANDOM, NORMAL
    }

    public DetailedNPC(String name, ConfigurationSection conf) {
        type = Type.valueOf(conf.getString("type"));
        this.name = name;
        dialog = conf.getStringList("dialog");
    }

    public DetailedNPC(Type type, String name, List<String> dialog) {
        this.type = type;
        this.name = name;
        this.dialog = dialog;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<String> getDialog() {
        return dialog;
    }

    @Override
    public void sendDialog(Collection<Player> players) {
        for (final Player listening : players) {
            if (type.equals(Type.RANDOM))
                listening.sendMessage(ChatColor.DARK_PURPLE + "[" + name + "] " + ChatColor.YELLOW + dialog.get(Randoms.generateIntRange(0, dialog.size() - 1)));
            else {
                int count = 0;
                for (final String line : dialog) {
                    if (!line.equals("%pause%") && !line.equals("%beat%"))
                        Bukkit.getScheduler().scheduleSyncDelayedTask(DonatorPointsPlugin.PLUGIN, new Runnable() {
                            @Override
                            public void run() {
                                listening.sendMessage(ChatColor.DARK_PURPLE + "[" + name + "] " + ChatColor.YELLOW + line);
                            }
                        }, count * 20 + Randoms.generateIntRange(20, 60));
                    count += line.equals("%beat%") ? 2 : 1;
                }
            }
        }
    }

    @Override
    public boolean is(String name) {
        return name != null && ChatColor.stripColor(name).equals(ChatColor.stripColor(this.name));
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("type", type.name());
        map.put("dialog", dialog);
        return map;
    }
}
