package com.minegusta.minegustarpg.npc;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Collection;

public interface NPC extends ConfigurationSerializable {
    public String getName();

    public Collection<String> getDialog();

    public void sendDialog(Collection<Player> players);

    public boolean is(String name);

    public String getRewardMeta();

    public String getRewardMessage();

    public int getRewardPoints();

    public int getRewardItem();

    public int getExp();

    public boolean isTrader();

    public boolean hasExp();

    public boolean awardItem(Player player, LivingEntity villager);

}
