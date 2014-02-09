package com.minegusta.minegustarpg.listeners;

import com.google.common.collect.Maps;
import com.minegusta.minegustarpg.MinegustaRPGPlugin;
import com.minegusta.minegustarpg.skilltree.SkillTreeData;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Random;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class BoostListener implements Listener {

    public static ConcurrentMap<String, Long> healerCooldown = Maps.newConcurrentMap();

    //Listening for events that alter gameplay.

    //Entity damage by entity: Warrior, Power, Runner, Archer, ArrowEfficieny, Assassin, Tank, Stunner, Scout, BloodBath.

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (!e.getEntity().getWorld().getName().toLowerCase().equalsIgnoreCase(MinegustaRPGPlugin.world)) return;
        if (e.isCancelled()) return;
        Player player;
        LivingEntity enemy;

        if ((e.getDamager() instanceof Player || e.getDamager() instanceof Arrow) && e.getEntity() instanceof LivingEntity) {


            enemy = (LivingEntity) e.getEntity();
            int level;
            double damage = e.getDamage();
            Random rand = new Random();


            if (e.getDamager() instanceof Arrow) {
                Arrow arrow = (Arrow) e.getDamager();
                if (arrow.getShooter() instanceof Player) {
                    player = (Player) arrow.getShooter();
                    String uuid = player.getUniqueId().toString();

                    //Bowman
                    if (SkillTreeData.bowman.containsKey(uuid)) {
                        if (rand.nextInt(100) < SkillTreeData.bowman.get(uuid) * 8) {
                            for (int i = 0; i < 8; i++) {
                                enemy.getWorld().spawnEntity(enemy.getLocation().add(rand.nextDouble(), 8, rand.nextDouble()), EntityType.ARROW);
                            }
                        }
                    }

                    //Archer
                    if (SkillTreeData.archer.containsKey(uuid)) {
                        level = SkillTreeData.archer.get(uuid);
                        e.setDamage(e.getDamage() + level);
                    }


                    //ArrowEfficiency
                    if (SkillTreeData.arrowefficiency.containsKey(uuid)) {
                        int chance = rand.nextInt(100);
                        if (chance < (SkillTreeData.arrowefficiency.get(uuid) * 40)) {
                            enemy.getWorld().dropItemNaturally(enemy.getLocation(), new ItemStack(Material.ARROW, 1));
                        }
                    }
                }
            } else {
                player = (Player) e.getDamager();
                String uuid = player.getUniqueId().toString();

                //Warrior
                if (SkillTreeData.warrior.containsKey(uuid)) {

                    level = SkillTreeData.warrior.get(uuid);
                    e.setDamage(damage + level);
                }
                //Power
                if (SkillTreeData.power.containsKey(uuid)) {
                    int random = rand.nextInt(10);
                    if (random < SkillTreeData.power.get(uuid) + 1) {
                        Vector direction = player.getLocation().getDirection();
                        enemy.setVelocity(direction.multiply(1.0));
                    }
                }


                //Bloodbath
                if (SkillTreeData.bloodbath.containsKey(uuid)) {
                    int random = rand.nextInt(100);
                    if (random < (SkillTreeData.bloodbath.get(uuid) * 5) + 1) {
                        bleedEntity(enemy);
                    }
                }

                //Stunner
                if (SkillTreeData.stunner.containsKey(uuid)) {
                    int random = rand.nextInt(100);
                    if (random < (SkillTreeData.stunner.get(uuid) * 5) + 1) {
                        enemy.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 0));
                    }
                }
                //Assassin
                if (SkillTreeData.assassin.containsKey(uuid)) {
                    int random = rand.nextInt(100);
                    if (random < (SkillTreeData.assassin.get(uuid) * 5) + 1) {
                        e.setDamage(e.getDamage() * 2);
                    }
                }
            }
        }
        if (e.getEntity() instanceof Player && (e.getDamager() instanceof LivingEntity || e.getDamager() instanceof Arrow)) {
            player = (Player) e.getEntity();
            String uuid = player.getUniqueId().toString();

            //Runner
            if (SkillTreeData.runner.containsKey(uuid)) {
                int amount = SkillTreeData.runner.get(uuid);
                if (player.getHealth() < 3) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 3 * amount, 1));
                }
            }
            //Tank
            if (SkillTreeData.tank.containsKey(uuid)) {
                int amount = SkillTreeData.tank.get(uuid);
                Random rand = new Random();
                int i = rand.nextInt(100);
                if (i < amount * 10) {
                    e.setDamage(0);
                }
            }
        }
    }

    //Potion splash: Alchemist.

    @EventHandler
    public void onPotionSplash(PotionSplashEvent e) {
        if (!e.getEntity().getWorld().getName().toLowerCase().equalsIgnoreCase(MinegustaRPGPlugin.world)) return;
        if (e.getEntity().getShooter() instanceof Player) {
            Player p = (Player) e.getEntity().getShooter();
            Random rand = new Random();
            int i = rand.nextInt(100);
            double randomDouble = rand.nextDouble();
            if (SkillTreeData.alchemist.containsKey(p.getUniqueId().toString())) {
                if ((SkillTreeData.alchemist.get(p.getUniqueId().toString()) * 10) + 1 < i) {
                    ThrownPotion potion = e.getPotion();
                    World world = e.getEntity().getWorld();
                    for (int le = 0; le < 4; le++) {
                        ThrownPotion clone = (ThrownPotion) world.spawnEntity(e.getEntity().getLocation(), EntityType.SPLASH_POTION);
                        Vector v = new Vector(rand.nextDouble() / 10, 0.5, rand.nextDouble() / 10);
                        clone.setVelocity(v);
                        clone.setItem(potion.getItem());
                    }
                }
            }

        }

    }

    //Entity Damage: Athlete, Runner.

    @EventHandler
    public void onEnvironmentalDamage(EntityDamageEvent e) {
        if (!e.getEntity().getWorld().getName().toLowerCase().equalsIgnoreCase(MinegustaRPGPlugin.world)) return;
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            String uuid = player.getUniqueId().toString();
            //Runner
            if (SkillTreeData.runner.containsKey(uuid)) {
                int amount = SkillTreeData.runner.get(uuid);
                if (player.getHealth() < 3) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 3 * amount, 1));
                }
            }
            //Athlete
            if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                if (SkillTreeData.athlete.containsKey(uuid)) {
                    double damage = e.getDamage();
                    double damageToSet = damage - (SkillTreeData.athlete.get(uuid) * 2);
                    if (damageToSet < 0) damageToSet = 1;
                    e.setDamage(damageToSet);
                }
            }
        }
    }

    //InteractEvent InteractEntityEvent: healing

    @EventHandler
    public void interactAirOrBlock(PlayerInteractEvent e) {
        if (!e.getPlayer().getWorld().getName().toLowerCase().equalsIgnoreCase(MinegustaRPGPlugin.world)) return;
        Player p = e.getPlayer();
        if (!p.isSneaking() || ((!e.getAction().equals(Action.RIGHT_CLICK_AIR)) && (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK))))
            return;
        if (SkillTreeData.healer.containsKey(p.getUniqueId().toString())) {
            long coolDownTime = TimeUnit.SECONDS.toMillis(90);


            if (healerCooldown.containsKey(p.getUniqueId().toString())) {
                long remainingTime = System.currentTimeMillis() - healerCooldown.get(p.getUniqueId().toString());
                if (!(remainingTime >= coolDownTime)) {
                    p.sendMessage(ChatColor.YELLOW + "You have to wait another " + ChatColor.RED + getRemainingCooldown(coolDownTime - remainingTime) + ChatColor.YELLOW + " before you can heal again.");
                    return;
                }
            }
            int amount = SkillTreeData.healer.get(p.getUniqueId().toString());
            healEntities(p, amount);
            healerCooldown.put(p.getUniqueId().toString(), System.currentTimeMillis());
        }

    }

    @EventHandler
    public void interactEntity(PlayerInteractEntityEvent e) {
        if (!e.getPlayer().getWorld().getName().toLowerCase().equalsIgnoreCase(MinegustaRPGPlugin.world)) return;
        Player p = e.getPlayer();
        if (!p.isSneaking()) return;
        if (SkillTreeData.healer.containsKey(p.getUniqueId().toString())) {
            long coolDownTime = TimeUnit.SECONDS.toMillis(90);

            if (healerCooldown.containsKey(p.getUniqueId().toString())) {
                long remainingTime = System.currentTimeMillis() - healerCooldown.get(p.getUniqueId().toString());
                if (!(remainingTime >= coolDownTime)) {
                    p.sendMessage(ChatColor.YELLOW + "You have to wait another " + ChatColor.RED + getRemainingCooldown(coolDownTime - remainingTime) + ChatColor.YELLOW + " before you can heal again.");
                    return;
                }
            }
            int amount = SkillTreeData.healer.get(p.getUniqueId().toString());
            healEntities(p, amount);
            healerCooldown.put(p.getUniqueId().toString(), System.currentTimeMillis());
        }
    }

    //EntityDeath: Scout.
    @EventHandler
    public void entityDeath(EntityDeathEvent e) {
        if (!e.getEntity().getWorld().getName().toLowerCase().equalsIgnoreCase(MinegustaRPGPlugin.world)) return;
        if (e.getEntity().getKiller() != null) {
            Player p = e.getEntity().getKiller();
            if (SkillTreeData.scout.containsKey(p.getUniqueId().toString())) {
                int level = SkillTreeData.scout.get(p.getUniqueId().toString());
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * level, 0));
            }
        }

    }

    //TODO add race boosts here.

    //Points Listener death event for luck.

    private void bleedEntity(LivingEntity entity) {
        final LivingEntity e = entity;
        for (int i = 0; i < 60; i++) {
            final int k = i;
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(MinegustaRPGPlugin.PLUGIN, new Runnable() {
                @Override
                public void run() {
                    if (e instanceof Player) {
                        Player p = (Player) e;
                        if (k == 1) p.sendMessage(ChatColor.RED + "You are bleeding!");
                    }
                    e.getWorld().spigot().playEffect(e.getLocation(), Effect.CRIT);
                    if (k == 20) e.damage(1.0);
                    if (k == 40) e.damage(1.0);
                    if (k == 59) e.damage(1.0);


                }
            }, i);
        }
    }

    private void healEntities(Player p, int amount) {
        double pMaxAdded = 20 - p.getHealth();
        double pHealthToAdd = 5 * amount;
        if (pMaxAdded < pHealthToAdd) pHealthToAdd = pMaxAdded;
        p.setHealth(p.getHealth() + pHealthToAdd);
        playHearts(p);
        for (Entity e : p.getNearbyEntities(3.0, 4.0, 3.0)) {
            if (e instanceof Player || e instanceof Horse) {
                LivingEntity le = (LivingEntity) e;
                double maxAdded = le.getMaxHealth() - le.getHealth();
                double healthToAdd = 5 * amount;
                if (maxAdded < healthToAdd) healthToAdd = maxAdded;
                le.setHealth(le.getHealth() + healthToAdd);
                playHearts(le);
            }
        }
    }

    private void playHearts(LivingEntity e) {
        e.getWorld().spigot().playEffect(e.getLocation(), Effect.HEART, 0, 0, 0, 0, 0, 1F, 20, 25);
        if (e instanceof Player) {
            Player p = (Player) e;
            p.sendMessage(ChatColor.DARK_RED + "You have been healed!");
        }
    }

    public static String getRemainingCooldown(long millis) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        StringBuilder sb = new StringBuilder();
        if (minutes != 0L) {
            sb.append(minutes);
            sb.append(" minutes ");
        }

        if (seconds != 0L) {
            sb.append(seconds);
            sb.append(" seconds.");
        }

        return sb.toString();
    }
}
