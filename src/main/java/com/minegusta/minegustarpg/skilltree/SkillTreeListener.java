package com.minegusta.minegustarpg.skilltree;

import com.google.common.collect.Maps;
import com.minegusta.minegustarpg.MinegustaRPGPlugin;
import com.minegusta.minegustarpg.data.DataManager;
import com.minegusta.minegustarpg.playerdata.Data;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Random;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class SkillTreeListener implements Listener {

    public static ConcurrentMap<String, Long> healerCooldown = Maps.newConcurrentMap();

    //Updating skills

    public void openSkillMenu(Player user) {
        String title = ChatColor.DARK_RED + "SkillPoints to spend: " + ChatColor.RED + Data.getLevelPoints(user.getUniqueId()) / 3;
        Inventory inv = Bukkit.getServer().createInventory(null, 18, title);
        user.openInventory(inv);

        int count = 0;
        for (ItemStack i : SkillTreeGui.getmenuItems(user.getUniqueId().toString())) {
            inv.setItem(count, i);
            count++;
        }
    }

    //Open Inventory when clicking the player.
    @EventHandler
    public void villagerRightClickEvent(PlayerInteractEntityEvent e) {
        if (e.getRightClicked().getType() == EntityType.VILLAGER && e.getPlayer().getWorld().getName().toLowerCase().equals(MinegustaRPGPlugin.world)) {
            LivingEntity villager = (LivingEntity) e.getRightClicked();
            String name = villager.getCustomName();
            if (name != null) {
                e.setCancelled(true);
                Player p = e.getPlayer();

                if (name.contains("Skill Trainer")) {
                    if (!e.getPlayer().getItemInHand().getType().equals(Material.AIR)) {
                        e.getPlayer().sendMessage(ChatColor.RED + "You can only trade with an empty hand!");
                    } else {
                        if (DataManager.getPointsFromPlayer(p) == null) DataManager.setPointsFromPlayer(p, 0);
                        p.sendMessage(ChatColor.YELLOW + "[Skill Trainer]" + ChatColor.GRAY + " Click an item to buy that upgrade. Upgrades are permanent!");
                        p.sendMessage(ChatColor.YELLOW + "[Skill Trainer]" + ChatColor.GRAY + " You have " + ChatColor.LIGHT_PURPLE + Data.getLevelPoints(p.getUniqueId()) / 3 + ChatColor.GRAY + " skill points.");
                        p.sendMessage(ChatColor.YELLOW + "[Skill Trainer]" + ChatColor.GRAY + " An upgrade costs 1 skill point.");
                        openSkillMenu(e.getPlayer());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBuyItem(InventoryClickEvent e) {
        if (!e.getWhoClicked().getWorld().getName().toLowerCase().equalsIgnoreCase(MinegustaRPGPlugin.world)) return;
        try {
            String invName = e.getClickedInventory().getName();
            Player user = (Player) e.getWhoClicked();
            if (invName != null && invName.equals(ChatColor.DARK_RED + "SkillPoints to spend: " + ChatColor.RED + Data.getLevelPoints(user.getUniqueId()) / 3)) {
                if (e.getCurrentItem().getType() == Material.AIR) return;
                if (e.getCurrentItem() == null || e.getCurrentItem().getItemMeta().getDisplayName() == null) return;

                e.setCancelled(true);
                HumanEntity player = e.getWhoClicked();

                ItemStack clickedItem = e.getCurrentItem();

                if (clickedItem == null) return;

                String playerName = e.getWhoClicked().getName();
                Player entityPlayer = Bukkit.getOfflinePlayer(playerName).getPlayer();

                int pointsPresent = Data.getLevelPoints(entityPlayer.getUniqueId());
                int points = 3;

                if (points > pointsPresent) {
                    entityPlayer.sendMessage(ChatColor.RED + "You do not have enough skill-points to buy an upgrade.");
                    player.closeInventory();
                    return;
                }
                String uuid = entityPlayer.getUniqueId().toString();

                if (clickedItem.getType().equals(SkillTreeGui.Branches.WARRIOR.getItemStack().getType())) {
                    if (SkillTreeData.getWarriorFromFile(uuid) > 4) {
                        entityPlayer.sendMessage(ChatColor.YELLOW + "[Skill Trainer]" + ChatColor.RED + " You already have the maximum level in that skill.");
                        player.closeInventory();
                        return;
                    }
                    SkillTreeData.addWarrior(entityPlayer.getUniqueId().toString(), 1);
                } else if (clickedItem.getType().equals(SkillTreeGui.Branches.POWER.getItemStack().getType())) {
                    if (SkillTreeData.getPowerFromFile(uuid) > 2) {
                        entityPlayer.sendMessage(ChatColor.YELLOW + "[Skill Trainer]" + ChatColor.RED + " You already have the maximum level in that skill.");
                        player.closeInventory();
                        return;
                    }
                    SkillTreeData.addPower(entityPlayer.getUniqueId().toString(), 1);
                } else if (clickedItem.getType().equals(SkillTreeGui.Branches.ATHLETE.getItemStack().getType())) {
                    if (SkillTreeData.getAthleteFromFile(uuid) > 4) {
                        entityPlayer.sendMessage(ChatColor.YELLOW + "[Skill Trainer]" + ChatColor.RED + " You already have the maximum level in that skill.");
                        player.closeInventory();
                        return;
                    }
                    SkillTreeData.addAthlete(entityPlayer.getUniqueId().toString(), 1);
                } else if (clickedItem.getType().equals(SkillTreeGui.Branches.RUNNER.getItemStack().getType())) {
                    if (SkillTreeData.getRunnerFromFile(uuid) > 2) {
                        entityPlayer.sendMessage(ChatColor.YELLOW + "[Skill Trainer]" + ChatColor.RED + " You already have the maximum level in that skill.");
                        player.closeInventory();
                        return;
                    }
                    SkillTreeData.addRunner(entityPlayer.getUniqueId().toString(), 1);
                } else if (clickedItem.getType().equals(SkillTreeGui.Branches.ARCHER.getItemStack().getType())) {
                    if (SkillTreeData.getArcherFromFile(uuid) > 4) {
                        entityPlayer.sendMessage(ChatColor.YELLOW + "[Skill Trainer]" + ChatColor.RED + " You already have the maximum level in that skill.");
                        player.closeInventory();
                        return;
                    }
                    SkillTreeData.addArcher(entityPlayer.getUniqueId().toString(), 1);
                } else if (clickedItem.getType().equals(SkillTreeGui.Branches.ARROWEFFICIENCY.getItemStack().getType())) {
                    if (SkillTreeData.getArrowEfficiencyFromFile(uuid) > 1) {
                        entityPlayer.sendMessage(ChatColor.YELLOW + "[Skill Trainer]" + ChatColor.RED + " You already have the maximum level in that skill.");
                        player.closeInventory();
                        return;
                    }
                    SkillTreeData.addArrowEfficiency(entityPlayer.getUniqueId().toString(), 1);
                } else if (clickedItem.getType().equals(SkillTreeGui.Branches.BOWMAN.getItemStack().getType())) {
                    if (SkillTreeData.getBowManFromFile(uuid) > 2) {
                        entityPlayer.sendMessage(ChatColor.YELLOW + "[Skill Trainer]" + ChatColor.RED + " You already have the maximum level in that skill.");
                        player.closeInventory();
                        return;
                    }
                    SkillTreeData.addBowMan(entityPlayer.getUniqueId().toString(), 1);
                } else if (clickedItem.getType().equals(SkillTreeGui.Branches.ASSASSIN.getItemStack().getType())) {
                    if (SkillTreeData.getAssassinFromFile(uuid) > 2) {
                        entityPlayer.sendMessage(ChatColor.YELLOW + "[Skill Trainer]" + ChatColor.RED + " You already have the maximum level in that skill.");
                        player.closeInventory();
                        return;
                    }
                    SkillTreeData.addAssassin(entityPlayer.getUniqueId().toString(), 1);
                } else if (clickedItem.getType().equals(SkillTreeGui.Branches.TANK.getItemStack().getType())) {
                    if (SkillTreeData.getTankFromFile(uuid) > 4) {
                        entityPlayer.sendMessage(ChatColor.YELLOW + "[Skill Trainer]" + ChatColor.RED + " You already have the maximum level in that skill.");
                        player.closeInventory();
                        return;
                    }
                    SkillTreeData.addTank(entityPlayer.getUniqueId().toString(), 1);
                } else if (clickedItem.getType().equals(SkillTreeGui.Branches.STUNNER.getItemStack().getType())) {
                    if (SkillTreeData.getStunnerFromFile(uuid) > 2) {
                        entityPlayer.sendMessage(ChatColor.YELLOW + "[Skill Trainer]" + ChatColor.RED + " You already have the maximum level in that skill.");
                        player.closeInventory();
                        return;
                    }
                    SkillTreeData.addStunner(entityPlayer.getUniqueId().toString(), 1);
                } else if (clickedItem.getType().equals(SkillTreeGui.Branches.HEALER.getItemStack().getType())) {
                    if (SkillTreeData.getHealerFromFile(uuid) > 3) {
                        entityPlayer.sendMessage(ChatColor.YELLOW + "[Skill Trainer]" + ChatColor.RED + " You already have the maximum level in that skill.");
                        player.closeInventory();
                        return;
                    }
                    SkillTreeData.addHealer(entityPlayer.getUniqueId().toString(), 1);
                } else if (clickedItem.getType().equals(SkillTreeGui.Branches.SCOUT.getItemStack().getType())) {
                    if (SkillTreeData.getScoutFromFile(uuid) > 3) {
                        entityPlayer.sendMessage(ChatColor.YELLOW + "[Skill Trainer]" + ChatColor.RED + " You already have the maximum level in that skill.");
                        player.closeInventory();
                        return;
                    }
                    SkillTreeData.addScout(entityPlayer.getUniqueId().toString(), 1);
                } else if (clickedItem.getType().equals(SkillTreeGui.Branches.ALCHEMIST.getItemStack().getType())) {
                    if (SkillTreeData.getAlchemistFromFile(uuid) > 3) {
                        entityPlayer.sendMessage(ChatColor.YELLOW + "[Skill Trainer]" + ChatColor.RED + " You already have the maximum level in that skill.");
                        player.closeInventory();
                        return;
                    }
                    SkillTreeData.addAlchemist(entityPlayer.getUniqueId().toString(), 1);
                } else if (clickedItem.getType().equals(SkillTreeGui.Branches.LUCK.getItemStack().getType())) {
                    if (SkillTreeData.getLuckFromFile(uuid) > 2) {
                        entityPlayer.sendMessage(ChatColor.YELLOW + "[Skill Trainer]" + ChatColor.RED + " You already have the maximum level in that skill.");
                        player.closeInventory();
                        return;
                    }
                    SkillTreeData.addLuck(entityPlayer.getUniqueId().toString(), 1);
                } else if (clickedItem.getType().equals(SkillTreeGui.Branches.BLOODBATH.getItemStack().getType())) {
                    if (SkillTreeData.getBloodbathFromFile(uuid) > 2) {
                        entityPlayer.sendMessage(ChatColor.YELLOW + "[Skill Trainer]" + ChatColor.RED + " You already have the maximum level in that skill.");
                        player.closeInventory();
                        return;
                    }
                    SkillTreeData.addBloodbath(entityPlayer.getUniqueId().toString(), 1);
                }
                Data.removeLevelPoints(entityPlayer.getUniqueId(), 3);
                SkillTreeData.loadPlayerToMaps(uuid);
                entityPlayer.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "Trade" + ChatColor.DARK_RED + "]" + ChatColor.YELLOW + "You have successfully bought an upgrade!");
                player.closeInventory();
            }

        } catch (Exception ignored) {

        }
    }


//Listening for events that alter gameplay.

    //Entity damage by entity: Warrior, Power, Runner, Archer, ArrowEfficieny, Assassin, Tank, Stunner, Scout, BloodBath.

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (!e.getEntity().getWorld().getName().toLowerCase().equalsIgnoreCase(MinegustaRPGPlugin.world)) return;
        if (e.isCancelled()) return;
        Player player;
        LivingEntity enemy;

        if (e.getDamager() instanceof Player && e.getEntity() instanceof LivingEntity) {
            player = (Player) e.getDamager();
            enemy = (LivingEntity) e.getEntity();
            int level;
            double damage = e.getDamage();
            String uuid = player.getUniqueId().toString();
            Random rand = new Random();


            if (e.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE)) {

                //Archer
                if (SkillTreeData.archer.containsKey(uuid)) {
                    level = SkillTreeData.archer.get(uuid);
                    e.setDamage(e.getDamage() + level);
                    //TODO REMODE
                    Bukkit.broadcastMessage("Debug: Archer works.");
                }


                //ArrowEfficiency
                if (SkillTreeData.arrowefficiency.containsKey(uuid)) {
                    int chance = rand.nextInt(100);
                    //TODO REMODE
                    Bukkit.broadcastMessage("Debug: arrowefficiency contains you!");
                    if (chance < (SkillTreeData.arrowefficiency.get(uuid) * 40)) {

                        //TODO REMODE
                        Bukkit.broadcastMessage("Debug: Arrowefficiency is ran!");

                        enemy.getWorld().dropItemNaturally(enemy.getLocation(), new ItemStack(Material.ARROW, 1));
                    }
                }
            } else {
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
        if (e.getEntity() instanceof Player && e.getDamager() instanceof LivingEntity) {
            player = (Player) e.getEntity();
            enemy = (LivingEntity) e.getDamager();
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
                double damage = e.getDamage();
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

    //Bow fire: bowMan

    @EventHandler
    public void bowFireEvent(EntityShootBowEvent e) {
        if (!e.getEntity().getWorld().getName().toLowerCase().equalsIgnoreCase(MinegustaRPGPlugin.world)) return;
        if (!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();
        Random rand = new Random();
        int random = rand.nextInt(100);
        if (SkillTreeData.bowman.containsKey(p.getUniqueId().toString())) {
            if ((SkillTreeData.bowman.get(p.getUniqueId().toString()) * 8) + 1 > random) {
                Entity a = e.getProjectile();
                Vector v = a.getLocation().getDirection().multiply(1.5);
                World w = e.getEntity().getWorld();
                Entity b = w.spawnEntity(e.getEntity().getLocation().add(v), EntityType.ARROW);
                b.setVelocity(a.getLocation().getDirection().multiply(1.5));
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
