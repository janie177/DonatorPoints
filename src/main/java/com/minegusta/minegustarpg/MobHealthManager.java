package com.minegusta.minegustarpg;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MobHealthManager implements Listener {


    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        Entity entity = e.getEntity();
        if (!entity.getWorld().getName().toLowerCase().equals(MinegustaRPGPlugin.world)) {
            return;
        } else if (entity instanceof LivingEntity) {
            LivingEntity mob = (LivingEntity) entity;
            String name = mob.getCustomName();

            if (name == null) {
                return;
            }

            double mobHitPoints = mob.getHealth();
            double maxHealth = mob.getMaxHealth();

            if (mobHitPoints <= ((maxHealth / 3) * 2) && MobSpawnManager.monsterHealth.containsKey(mob.getUniqueId())) {
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

    //Stop mobs from burning.
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMobBurn(EntityCombustEvent e) {
        if (e.getEntity().getWorld().getName().toLowerCase().equals(MinegustaRPGPlugin.world)) {
            if (e.getEntity() instanceof Zombie || e.getEntity() instanceof Skeleton) {
                LivingEntity mob = (LivingEntity) e.getEntity();
                mob.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 6000, 1));
            }
        }
    }

    //Stop the map from filling up.
    @EventHandler
    public void onEntityDeathInMap(EntityDeathEvent e) {
        LivingEntity entity = e.getEntity();
        if (entity.getLastDamageCause() instanceof EntityDamageByEntityEvent) return;
        if (MobSpawnManager.monsterHealth.containsKey(entity.getUniqueId())) {
            MobSpawnManager.monsterHealth.remove(entity.getUniqueId());
        }
    }
}
