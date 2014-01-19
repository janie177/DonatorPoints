package com.minegusta.minegustarpg;

import com.google.common.collect.Sets;
import com.minegusta.minegustarpg.data.DataManager;
import com.minegusta.minegustarpg.npc.NPC;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class NPCManager implements Listener {


    @EventHandler
    public void talkToNPCEvent(PlayerInteractEntityEvent e) {
        if (e.getRightClicked().getType() == EntityType.VILLAGER && e.getPlayer().getWorld().getName().toLowerCase().equals(MinegustaRPGPlugin.world)) {


            LivingEntity villager = (LivingEntity) e.getRightClicked();
            if (!DataManager.isNPC(villager)) return;

            //Block people from name tagging mobs with this name.
            if (e.getPlayer().getItemInHand().getType().equals(Material.NAME_TAG) || e.getPlayer().getItemInHand().getType().equals(Material.MONSTER_EGG) && !e.getPlayer().isOp()) {
                e.setCancelled(true);
                e.getPlayer().sendMessage("You cannot make your own NPC's!");
                return;
            }

            // get the NPC
            NPC nPC = DataManager.getNPC(villager.getCustomName());

            // cooldown
            if (DataManager.hasTimed(e.getPlayer().getName(), "talking with " + nPC.getName())) return;

            // send the message/dialog
            if (nPC.awardItem(e.getPlayer(), villager)) return;
            nPC.sendDialog(Sets.newHashSet(e.getPlayer()));
            DataManager.saveTimed(e.getPlayer().getName(), "talking with " + nPC.getName(), true, 12);
        }
    }


    // Stop villagers from spawning.
    @EventHandler
    public void onBabyVillagerSpawn(CreatureSpawnEvent e) {
        if (e.getEntity().getType().equals(EntityType.VILLAGER)) {
            if (DataManager.isNPC(e.getEntity())) {
                e.setCancelled(true);
            }
        }
    }


    //prevent people from making their own villagers.
    @EventHandler
    public void blockFakeVillagers(PlayerInteractEvent e) {
        if (e.getPlayer().getItemInHand().getType().equals(Material.MONSTER_EGG)) {
            String name = e.getPlayer().getItemInHand().getItemMeta().getDisplayName();
            if (DataManager.isNPC(name)) {
                e.setCancelled(true);
            }
        }
    }
}
