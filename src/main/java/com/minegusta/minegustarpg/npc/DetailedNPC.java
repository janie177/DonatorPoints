package com.minegusta.minegustarpg.npc;

import com.censoredsoftware.censoredlib.util.Randoms;
import com.google.common.collect.Maps;
import com.minegusta.minegustarpg.MinegustaRPGPlugin;
import com.minegusta.minegustarpg.data.DataManager;
import com.minegusta.minegustarpg.managers.LevelManager;
import com.minegusta.minegustarpg.playerdata.Data;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DetailedNPC implements NPC {
    private Type type;
    private Boolean trader;
    private String name;
    private List<String> dialog;
    private Integer rewardPoints;
    private Integer item;
    private String rewardMessage;
    private String meta;
    private int exp;
    private boolean hasExp;

    public enum Type {
        RANDOM, NORMAL
    }

    public DetailedNPC(String name, ConfigurationSection conf) {
        type = Type.valueOf(conf.getString("type"));
        this.name = name;
        dialog = conf.getStringList("dialog");
        trader = conf.getBoolean("isTrader");
        item = conf.getInt("item");
        rewardPoints = conf.getInt("rewardPoints");
        meta = conf.getString("meta");
        rewardMessage = conf.getString("rewardMessage");
        exp = conf.getInt("exp");
        hasExp = conf.getBoolean("hasExp");
    }

    public DetailedNPC(Type type, String name, List<String> dialog, Boolean trader, Integer item, Integer rewardPoints, String meta, String rewardMessage, Boolean hasExp, int exp) {
        this.type = type;
        this.name = name;
        this.dialog = dialog;
        this.trader = trader;
        this.item = item;
        this.rewardPoints = rewardPoints;
        this.meta = meta;
        this.rewardMessage = rewardMessage;
        this.exp = exp;
        this.hasExp = hasExp;

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<String> getDialog() {
        return dialog;
    }


    public void sendDialog(Collection<Player> players) {
        for (final Player listening : players) {
            if (type.equals(Type.RANDOM))
                listening.sendMessage(ChatColor.DARK_PURPLE + "[" + name + "] " + ChatColor.YELLOW + dialog.get(Randoms.generateIntRange(0, dialog.size() - 1)));
            else {
                int count = 0;
                for (final String line : dialog) {
                    if (!line.equals("%pause%") && !line.equals("%beat%"))
                        Bukkit.getScheduler().scheduleSyncDelayedTask(MinegustaRPGPlugin.PLUGIN, new Runnable() {
                            @Override
                            public void run() {
                                listening.sendMessage(ChatColor.DARK_PURPLE + "[" + name + "] " + ChatColor.YELLOW + ChatColor.ITALIC + line);
                            }
                        }, count * 30);
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
    public boolean isTrader() {
        return trader;
    }

    @Override
    public boolean hasExp() {
        return hasExp;
    }

    @Override
    public int getExp() {
        return exp;
    }

    @Override
    public String getRewardMeta() {
        return meta;
    }

    @Override
    public String getRewardMessage() {
        return rewardMessage;
    }

    @Override
    public int getRewardPoints() {
        return rewardPoints;
    }

    @Override
    public int getRewardItem() {
        return item;
    }

    @Override
    public boolean awardItem(Player player, LivingEntity villager) {
        ItemStack leItem = player.getItemInHand();
        if (trader) {
            Material material = Material.getMaterial(item);
            if (leItem == null || leItem.getType().equals(Material.AIR) || !leItem.getItemMeta().hasLore())
                return false;
            else if (leItem.getType().equals(material) && leItem.getItemMeta().getLore().toString().contains(meta)) {
                final int oldAmount = leItem.getAmount();
                int newAmount = oldAmount - 1;
                if (newAmount == 0) {
                    player.getInventory().setItemInHand(new ItemStack(Material.AIR));
                } else {
                    leItem.setAmount(newAmount);
                }
                if (hasExp) {
                    LevelManager.addExpAmount(player.getUniqueId(), exp);
                }
                player.sendMessage(ChatColor.DARK_PURPLE + "[" + name + "] " + ChatColor.YELLOW + ChatColor.ITALIC + getRewardMessage());
                player.sendMessage(ChatColor.DARK_RED + "[Trade] " + ChatColor.YELLOW + "Traded 1 " + meta + " for " + rewardPoints + " points.");
                int oldPoints = DataManager.getPointsFromPlayer(player);
                int newPoints = oldPoints + getRewardPoints();
                DataManager.setPointsFromPlayer(player, newPoints);
                Data.addQuestsDone(player.getUniqueId(), 1);
                return true;

            }
        }
        return false;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("type", type.name());
        map.put("dialog", dialog);
        map.put("isTrader", trader);
        map.put("item", item);
        map.put("rewardPoints", rewardPoints);
        map.put("meta", meta);
        map.put("rewardMessage", rewardMessage);
        map.put("hasExp", hasExp);
        map.put("exp", exp);
        return map;
    }
}
