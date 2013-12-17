package com.minegusta.donatorpoints.npc;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.Collection;

public interface NPC extends ConfigurationSerializable {
    public String getName();

    public Collection<String> getDialog();

    public void sendDialog(Collection<Player> players);

    public boolean is(String name);
}
