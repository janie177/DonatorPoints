package com.minegusta.donatorpoints;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class MobHealthManager implements Listener{


    @EventHandler
    public void onEntityDamageByNonOp(EntityDamageByEntityEvent e) {
        Entity entity = e.getEntity();
        if (!DonatorPointsPlugin.world.equals(entity.getWorld().getName()))return;
        if(entity instanceof LivingEntity){
            LivingEntity mob = (LivingEntity) entity;
            String name = mob.getCustomName();

            if (name == null) return;

            double mobHitPoints = mob.getHealth();
            double maxHealth = mob.getMaxHealth();

            if (mobHitPoints <= (maxHealth / 2) && MobSpawnManager.monsterHealth.containsKey(mob.getUniqueId())) {
                int refillLife = MobSpawnManager.monsterHealth.get(mob.getUniqueId());
                if (refillLife == 1) {
                    MobSpawnManager.monsterHealth.remove(mob.getUniqueId());
                } else {
                    MobSpawnManager.monsterHealth.put(mob.getUniqueId(), refillLife - 1);
                    mob.setHealth(maxHealth);
                }
            }
        }
    }
}
