package com.minegusta.donatorpoints.npc;

import com.censoredsoftware.censoredlib.util.Randoms;
import com.google.common.collect.Maps;
import com.minegusta.donatorpoints.DonatorPointsPlugin;
import com.minegusta.donatorpoints.data.DataManager;
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
    }

    public DetailedNPC(Type type, String name, List<String> dialog, Boolean trader, Integer item, Integer rewardPoints, String meta, String rewardMessage) {
        this.type = type;
        this.name = name;
        this.dialog = dialog;
        this.trader = trader;
        this.item = item;
        this.rewardPoints = rewardPoints;
        this.meta = meta;
        this.rewardMessage = rewardMessage;

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
    public boolean isTrader(String name) {
        return trader;
    }

    @Override
    public String getRewardMeta(String name) {
        return meta;
    }

    @Override
    public String getRewardMessage(String name) {
        return rewardMessage;
    }

    @Override
    public int getRewardPoints(String name) {
        return rewardPoints;
    }

    @Override
    public int getRewardItem(String name) {
        return item;
    }

    @Override
    public boolean awardItem(Player player, LivingEntity villager) {

        String name = villager.getCustomName();
        ItemStack item = player.getItemInHand();
        if (is(name) && isTrader(name)) {
            if (getRewardItem(name) == item.getTypeId() && getRewardMeta(name).contains(item.getItemMeta().getLore().toString())) {
                if (player.getItemInHand().getAmount() > 1) {
                    final int oldAmount = item.getAmount();
                    int newAmount = oldAmount - 1;
                    item.setAmount(newAmount);
                } else {
                    player.getItemInHand().setType(Material.AIR);
                }
                player.sendMessage(ChatColor.DARK_RED + "[Trade] " + ChatColor.AQUA + getRewardMessage(name));
                player.sendMessage(ChatColor.DARK_RED + "[Trade] " + ChatColor.AQUA + "Traded 1 " + getRewardMeta(name) + " for " + getRewardPoints(name) + " points.");
                int oldPoints = DataManager.getPointsFromPlayer(player);
                int newPoints = oldPoints + getRewardPoints(name);
                DataManager.setPointsFromPlayer(player, newPoints);
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
        return map;
    }
}
