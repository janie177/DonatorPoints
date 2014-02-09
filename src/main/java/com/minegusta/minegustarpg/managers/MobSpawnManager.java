package com.minegusta.minegustarpg.managers;

import com.google.common.collect.Maps;
import com.minegusta.minegustarpg.MinegustaRPGPlugin;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class MobSpawnManager implements Listener {

    public static Map<UUID, Integer> monsterHealth = Maps.newHashMap();

    @EventHandler
    public void onMobSpawn(CreatureSpawnEvent e) {
        if (!e.getEntity().getWorld().getName().toLowerCase().equals(MinegustaRPGPlugin.world)) return;
        if (e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM)) return;
        LivingEntity mob = e.getEntity();
        Location location = mob.getLocation();
        World world = location.getWorld();
        e.setCancelled(true);
        if (WorldGuardManager.isInRegion(mob.getLocation(), "+")) {

            EntityType type;
            String mobType;
            String mobName;

            Random random3 = new Random();
            int i = random3.nextInt(11);

            //Select a random type of mob to spawn
            switch (i) {

                case 1: {
                    type = EntityType.SKELETON;
                    mobName = " Skeletal Warrior";
                    mobType = "s";
                }
                break;
                case 2: {
                    type = EntityType.ZOMBIE;
                    mobName = " Undead Of The Woods";
                    mobType = "z";
                }
                break;
                case 3: {
                    type = EntityType.SPIDER;
                    mobName = " Dark Crawler";
                    mobType = "d";
                }
                break;
                case 4: {
                    type = EntityType.ZOMBIE;
                    mobName = " Fallen Citizen";
                    mobType = "v";
                }
                break;
                case 5: {
                    type = EntityType.SKELETON;
                    mobName = " Skeletal WarLord";
                    mobType = "w";
                }
                break;
                case 6: {
                    type = EntityType.BLAZE;
                    mobName = " DemonSpawn";
                    mobType = "b";
                }
                break;
                case 7: {
                    type = EntityType.GIANT;
                    mobName = " Giant";
                    mobType = "g";
                }
                break;
                case 8: {
                    type = EntityType.GHAST;
                    mobName = " Angry Spirit";
                    mobType = "a";
                }
                break;
                case 9: {
                    type = EntityType.WOLF;
                    mobName = " Wild TimberWolf";
                    mobType = "t";
                }
                break;
                default: {
                    type = EntityType.WITCH;
                    mobName = " Tortured Mage";
                    mobType = "m";
                }
                break;

            }

            //is the mob in a mob-region accepting that type?
            if (!WorldGuardManager.canSpawnMob(location, mobType)) {
                e.setCancelled(true);
                return;
            }

            //Define the max level from that region.
            int levelCap = 0;
            for (String r : WorldGuardManager.getRegionNames(location)) {
                try {
                    if (levelCap < Integer.parseInt(r.replaceAll("[\\D]", ""))) {
                        levelCap = Integer.parseInt(r.replaceAll("[\\D]", ""));
                    }
                } catch (Exception ignored) {
                }
            }

            //Set the mobs level
            Random levelRandom = new Random();
            int monsterLevelMinus = levelRandom.nextInt(12);
            int monsterLevel = levelCap - monsterLevelMinus + 1;
            if (monsterLevel < 1) monsterLevel = levelRandom.nextInt(levelCap);

            //Spawn mobs
            defineMobs(mobType, monsterLevel, world, mobName, type, location);
        }
    }


    //Kill mobs on server restart.

    public static void killRemainingMobs() {
        for (World manyWorld : Bukkit.getServer().getWorlds()) {
            for (Entity suchMob : manyWorld.getLivingEntities()) {
                UUID soId = suchMob.getUniqueId();
                if (monsterHealth.containsKey(soId)) {
                    LivingEntity wow = (LivingEntity) suchMob;
                    monsterHealth.remove(soId);
                    wow.remove(); // setting health won't trigger death
                }
            }
        }
    }

    //Get a random item based on level.

    public ItemStack getRandomItem(Integer level) {

        Random rand = new Random();
        ItemStack item = new ItemStack(Material.WOOD_AXE, 1);

        if (level < 11) {
            int number = rand.nextInt(15);
            switch (number) {
                case 1:
                    item = new ItemStack(Material.WOOD_SWORD, 1);
                    break;
                case 2:
                    item = new ItemStack(Material.WOOD_SPADE, 1);
                    break;
                case 3:
                    item = new ItemStack(Material.WOOD_PICKAXE, 1);
                    break;
                case 4:
                    item = new ItemStack(Material.WOOD_HOE, 1);
                    break;
                case 5:
                    item = new ItemStack(Material.STICK, 1);
                    break;
                case 6:
                    item = new ItemStack(Material.BOW, 1);
                    break;
                case 7:
                    item = new ItemStack(Material.AIR, 1);
                    break;
                case 8:
                    item = new ItemStack(Material.STONE_SWORD, 1);
                    break;
                case 9:
                    item = new ItemStack(Material.STONE_HOE, 1);
                    break;
                case 10:
                    item = new ItemStack(Material.WOOD_SWORD, 1);
                    break;
                case 11:
                    item = new ItemStack(Material.AIR, 1);
                    break;
                case 12:
                    item = new ItemStack(Material.AIR, 1);
                    break;
                case 13:
                    item = new ItemStack(Material.AIR, 1);
                    break;
                case 14:
                    item = new ItemStack(Material.AIR, 1);
                    break;

            }

        } else if (level > 10 && level < 21) {

            int number = rand.nextInt(15);
            switch (number) {
                case 1:
                    item = new ItemStack(Material.IRON_SWORD, 1);
                    break;
                case 2:
                    item = new ItemStack(Material.STONE_AXE, 1);
                    break;
                case 3:
                    item = new ItemStack(Material.STONE_SWORD, 1);
                    break;
                case 4:
                    item = new ItemStack(Material.STONE_HOE, 1);
                    break;
                case 5:
                    item = new ItemStack(Material.STONE_PICKAXE, 1);
                    break;
                case 6:
                    item = new ItemStack(Material.IRON_PICKAXE, 1);
                    break;
                case 7:
                    item = new ItemStack(Material.STONE_SWORD, 1);
                    break;
                case 8:
                    item = new ItemStack(Material.BOW, 1);
                    break;
                case 9:
                    item = new ItemStack(Material.BOW, 1);
                    break;
                case 10:
                    item = new ItemStack(Material.BOW, 1);
                    break;
                case 11:
                    item = new ItemStack(Material.STONE_SWORD, 1);
                    break;
                case 12:
                    item = new ItemStack(Material.BOW, 1);
                    break;
                case 13:
                    item = new ItemStack(Material.AIR, 1);
                    break;
                case 14:
                    item = new ItemStack(Material.AIR, 1);
                    break;

            }
        } else if (level > 20 && level < 26) {

            int number = rand.nextInt(15);
            switch (number) {
                case 1:
                    item = new ItemStack(Material.IRON_SWORD, 1);
                    break;
                case 2:
                    item = new ItemStack(Material.IRON_SPADE, 1);
                    break;
                case 3:
                    item = new ItemStack(Material.IRON_PICKAXE, 1);
                    break;
                case 4:
                    item = new ItemStack(Material.IRON_HOE, 1);
                    break;
                case 5:
                    item = new ItemStack(Material.IRON_SWORD, 1);
                    break;
                case 6:
                    item = new ItemStack(Material.BOW, 1) {
                        @Override
                        public void addEnchantment(Enchantment ench, int level) {
                            super.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
                        }
                    };
                    break;
                case 7:
                    item = new ItemStack(Material.AIR, 1);
                    break;
                case 8:
                    item = new ItemStack(Material.DIAMOND_HOE, 1);
                    break;
                case 9:
                    item = new ItemStack(Material.IRON_SWORD, 1);
                    break;
                case 10:
                    item = new ItemStack(Material.STONE_SWORD, 1);
                    break;
                case 11:
                    item = new ItemStack(Material.BOW, 1);
                    break;
                case 12:
                    item = new ItemStack(Material.BOW, 1);
                    break;
                case 13:
                    item = new ItemStack(Material.BOW, 1);
                    break;
                case 14:
                    item = new ItemStack(Material.BOW, 1);
                    break;

            }

        } else if (level > 25) {

            int number = rand.nextInt(12);
            switch (number) {
                case 1:
                    item = new ItemStack(Material.DIAMOND_SWORD, 1);
                    break;
                case 2:
                    item = new ItemStack(Material.DIAMOND_AXE, 1);
                    break;
                case 3:
                    item = new ItemStack(Material.DIAMOND_HOE, 1);
                    break;
                case 4:
                    item = new ItemStack(Material.DIAMOND_SPADE, 1) {
                        @Override
                        public void addEnchantment(Enchantment ench, int level) {
                            super.addEnchantment(Enchantment.FIRE_ASPECT, 2);
                        }
                    };
                    break;
                case 5:
                    item = new ItemStack(Material.DIAMOND_PICKAXE, 1);
                    break;
                case 6:
                    item = new ItemStack(Material.BOW, 1) {
                        @Override
                        public void addEnchantment(Enchantment ench, int level) {
                            super.addEnchantment(Enchantment.ARROW_DAMAGE, 3);
                        }
                    };
                    break;
                case 7:
                    item = new ItemStack(Material.IRON_SWORD, 1) {
                        @Override
                        public void addEnchantment(Enchantment ench, int level) {
                            super.addEnchantment(Enchantment.KNOCKBACK, 2);
                            super.addEnchantment(Enchantment.DAMAGE_ALL, 4);
                            super.addEnchantment(Enchantment.FIRE_ASPECT, 2);
                        }
                    };
                    break;
                case 8:
                    item = new ItemStack(Material.IRON_AXE, 1);
                    break;
                case 9:
                    item = new ItemStack(Material.IRON_HOE, 1);
                    break;
                case 10:
                    item = new ItemStack(Material.DIAMOND_SWORD, 1) {
                        @Override
                        public void addEnchantment(Enchantment ench, int level) {
                            super.addEnchantment(Enchantment.DAMAGE_ALL, 5);
                        }
                    };
                    break;
                case 11:
                    item = new ItemStack(Material.IRON_SWORD, 1);
                    break;
            }
        }

        return item;
    }

    //Spawn mobs

    public void defineMobs(String mobType, int monsterLevel, World world, String mobName, EntityType type, Location location) {

        Entity spawnedMob = world.spawnEntity(location.add(0, 0.2, 0), type);
        LivingEntity monster = (LivingEntity) spawnedMob;
        monster.setCustomName(ChatColor.RED + "Level: " + monsterLevel + mobName);
        monster.setCustomNameVisible(true);
        monsterHealth.put(spawnedMob.getUniqueId(), monsterLevel);

        if (mobType.equals("z")) {
            monster.getEquipment().setItemInHand(getRandomItem(monsterLevel));
            if (monsterLevel > 15) monster.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000, 0));

        } else if (mobType.equals("v")) {
            Zombie zombie = (Zombie) monster;
            zombie.setVillager(true);

        } else if (mobType.equals("a")) {


        } else if (mobType.equals("d")) {


        } else if (mobType.equals("w")) {
            Skeleton skeleton = (Skeleton) monster;
            skeleton.setSkeletonType(Skeleton.SkeletonType.WITHER);
            monster.getEquipment().setItemInHand(getRandomItem(monsterLevel));

        } else if (mobType.equals("b")) {

            monster.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 6000, 0));

        } else if (mobType.equals("g")) {

            monster.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 6000, 1));

        } else if (mobType.equals("s")) {

            monster.getEquipment().setItemInHand(getRandomItem(monsterLevel));
            if (monsterLevel > 15) monster.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000, 0));

        } else if (mobType.equals("t")) {

            Wolf wolf = (Wolf) monster;
            wolf.setAngry(true);

        } else if (mobType.equals("m")) {

            monster.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000, 0));
        }
        if (monster.getCustomName() == null) monster.remove();
    }
}
