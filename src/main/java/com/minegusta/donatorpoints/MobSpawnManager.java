package com.minegusta.donatorpoints;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
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
        if (!DonatorPointsPlugin.world.equals(e.getEntity().getWorld().getName()))return;
        boolean spawn;
        String name;
        LivingEntity mob = e.getEntity();


        switch (mob.getType()) {


            case SKELETON: {
                spawn = true;
                name = " Skeletal Warrior";
            }
            break;
            case ZOMBIE: {
                spawn = true;
                name = " Undead Of The Woods";
            }
            break;
            case SPIDER: {
                spawn = true;
                name = " Dark Crawler";
            }
            break;
            case ENDERMAN: {
                spawn = true;
                name = " Fallen Villager";
            }
            break;
            case CREEPER: {
                spawn = true;
                name = " Skeletal WarLord";
            }
            break;
            case BLAZE: {
                spawn = true;
                name = " DemonSpawn";
            }
            break;
            case GIANT: {
                spawn = true;
                name = " Giant";
            }
            break;
            case GHAST: {
                spawn = true;
                name = " Angry Spirit";
            }
            break;
            case WOLF: {
                spawn = true;
                name = " Wild TimberWolf";
            }
            break;
            default: {
                spawn = false;
                name = "noname";
            }
            break;
        }

        if (!spawn){
            e.setCancelled(true);
            return;
        }

        //generate a random that defines the level. This is only used for default spawning outside of regions containing _

            Random rand = new Random();
            int number = rand.nextInt(51);
            int level = 1;
            if (number < 30) {
                level = 1;
            } else if (29 < number && number < 41) {
                level = 2;
            } else if (40 < number && number < 45) {
                level = 2;
            } else if (number > 44 && number < 44) {
                level = 4;
            } else if (number > 43) {
                level = 5;
            }

        //Set the mobs name and set it visible. The level used for awarding points and also for assigning more HitPoints.

            mob.setCustomName(ChatColor.RED + "Level: " + level + name);
            mob.setCustomNameVisible(true);

        if(!DonatorPointsPlugin.WORLD_GUARD_ENABLED || !WorldGuardManager.isInRegion(mob.getLocation(), "_")){

            //Default spawning in regions that are not mob-defininf. Levels go from 1-5.

            if (e.getEntity() instanceof Spider) {
                monsterHealth.put(mob.getUniqueId(), level);
                } else if (e.getEntity() instanceof Zombie) {
                    Random rand3 = new Random();
                    int le = rand3.nextInt(31);
                    if (le < 15 && le > 9) mob.getEquipment().setItemInHand(new ItemStack(Material.BOW, 1));
                    if (le < 10) mob.getEquipment().setItemInHand(new ItemStack(Material.WOOD_SWORD, 1));
                    if (le == 15) mob.getEquipment().setItemInHand(new ItemStack(Material.WOOD_AXE, 1));
                    if (le > 15) mob.getEquipment().setItemInHand(new ItemStack(Material.WOOD_SPADE, 1));
                    monsterHealth.put(mob.getUniqueId(), level);
                } else if (e.getEntity() instanceof Skeleton) {
                    Random rand2 = new Random();
                    int number2 = rand2.nextInt(21);
                    if (number2 < 11) mob.getEquipment().setItemInHand(new ItemStack(Material.WOOD_PICKAXE, 1));
                    if (number2 == 11) mob.getEquipment().setItemInHand(new ItemStack(Material.STONE_SWORD, 1));
                    if (number2 > 11) mob.getEquipment().setItemInHand(new ItemStack(Material.BOW, 1));
                    mob.getEquipment().setItemInHandDropChance(0.02F);
                    monsterHealth.put(mob.getUniqueId(), level);
                } else if (e.getEntity() instanceof Enderman) {
                    Entity villager = e.getEntity().getWorld().spawnEntity(e.getLocation(), EntityType.ZOMBIE);
                    Zombie fallenVillager = (Zombie) villager;
                    fallenVillager.setVillager(true);
                    fallenVillager.setCustomName(ChatColor.RED + "Level: " + level + name);
                    fallenVillager.setCustomNameVisible(true);
                    e.setCancelled(true);
                    monsterHealth.put(fallenVillager.getUniqueId(), level);
                } else if (e.getEntity() instanceof Creeper) {
                    Entity warLord = e.getEntity().getWorld().spawnEntity(e.getLocation(), EntityType.SKELETON);
                    Skeleton skellyLord = (Skeleton) warLord;
                    skellyLord.setSkeletonType(Skeleton.SkeletonType.WITHER);
                    skellyLord.setCustomName(ChatColor.RED + "Level: " + level + name);
                    skellyLord.setCustomNameVisible(true);
                    skellyLord.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000, 0, false));
                    skellyLord.getEquipment().setItemInHand(new ItemStack(Material.WOOD_SWORD, 1));
                    skellyLord.getEquipment().setItemInHandDropChance(0.02F);
                    e.setCancelled(true);
                    monsterHealth.put(skellyLord.getUniqueId(), level);
                //Normal spawning level 1-5 mobs.

                }
            else if(WorldGuardManager.isInRegion(mob.getLocation(), "_")){

                /*Get world guard region name.
                  If the region name contains _, which is already the case here because of the "else if" above, then do the following.
                  Then check if it contains the letters G Z S W B C F B A D  and the number which is the level cap.
                  If number = 30, make the lowest possible one (number - 10) which is 20 in this case.
                  The highest possible level of mobs to spawn would be 30.
                  This way regions can be high/low level and thus more dangerous.
                  The letters above (G Z S W B C F B A D) stand for mob type.
                  G = Giant.
                  Z = Zombie.
                  Etc.
                  When it contains the letter of a mob, that mob can spawn in that region.
                  Biomes should not matter since I want it to be fully world guard dependent.

                  Mobs should have random items as well. (Zombies and skeletons that is).

                  Level 1-5  has either nothing or wood.
                  Level 6-10 has wood or stone.
                  Level 11-15 has stone/iron.
                  Level 16-20 has Stone/iron and sometimes Diamond.
                  Level 21-25 has Iron/Diamond or Enchanted iron.
                  Level 26-30 has Diamond or Enchanted Diamond or Enchanted iron.

                  Rewarding points is already done.
                */
            }
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
                    wow.damage(wow.getHealth()); // setting health won't trigger death
                }
            }
        }
    }

}
