package com.minegusta.minegustarpg.listeners;

import com.minegusta.minegustarpg.MinegustaRPGPlugin;
import com.minegusta.minegustarpg.data.DataManager;
import com.minegusta.minegustarpg.managers.LevelManager;
import com.minegusta.minegustarpg.mobs.DropTable;
import com.minegusta.minegustarpg.playerdata.Data;
import com.minegusta.minegustarpg.scoreboard.ScoreBoardManager;
import com.minegusta.minegustarpg.skilltree.SkillTreeData;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Random;
import java.util.UUID;

public class PointsListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDeath(EntityDeathEvent event) {
        // get entity
        LivingEntity entity = event.getEntity();

        // check region
        if (!entity.getWorld().getName().toLowerCase().equals(MinegustaRPGPlugin.world)) {
            return;
        }

        //set exp drops to 0 in the RPG world only
        event.setDroppedExp(0);

        // get cause
        EntityDamageEvent damageEvent = entity.getLastDamageCause();
        if (damageEvent instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent damageByEntityEvent = (EntityDamageByEntityEvent) damageEvent;
            Entity damager = damageByEntityEvent.getDamager();
            if (damageByEntityEvent.getDamager() instanceof Player) {
                Player superMan = (Player) damager;
                int points = 0;
                if (entity.getCustomName() == null) {
                    return;
                }
                String name = event.getEntity().getCustomName();
                // set points based on cause
                String colorlessName = ChatColor.stripColor(name);
                int level = 1;
                try {
                    level = Integer.parseInt(colorlessName.replaceAll("[\\D]", ""));
                } catch (Exception ex) {
                    return;
                }
                switch (entity.getType()) {
                    case ENDERMAN:
                        points = 0;
                        break;
                    case SILVERFISH:
                        points = 0;
                        break;
                    case BAT:
                        points = 0;
                        break;
                    case PIG:
                        points = 0;
                        break;
                    case SHEEP:
                        points = 0;
                        break;
                    case COW:
                        points = 0;
                        break;
                    case CHICKEN:
                        points = 0;
                        break;
                    case SQUID:
                        points = 0;
                        break;
                    case MUSHROOM_COW:
                        points = 0;
                        break;
                    case SNOWMAN:
                        points = 0;
                        break;
                    case OCELOT:
                        points = 0;
                        break;
                    case PLAYER:
                        points = 0;
                        break;
                    case HORSE:
                        points = 0;
                        break;
                    case MAGMA_CUBE:
                        points = 0;
                        break;
                    case ENDER_DRAGON:
                        points = 0;
                        break;
                    case WITHER:
                        points = 0;
                        break;
                    case IRON_GOLEM:
                        points = 0;
                        break;
                    case VILLAGER:
                        points = 0;
                        break;
                    case CREEPER:
                        points = 0;
                        break;
                    default:

                        //Add a kill
                        Data.addMobsKilled(damager.getUniqueId(), 1);

                        //Update scoreboard.
                        ScoreBoardManager.updateScoreboard(superMan);

                        //Awars points
                        if (level < 5) points = 1;
                        else if (level > 4 && level < 10) points = 1;
                        else if (level > 9 && level < 15) points = 2;
                        else if (level > 14 && level < 20) points = 3;
                        else if (level > 19 && level < 25) points = 4;
                        else if (level > 24 && level < 30) points = 5;
                        else if (level == 30) points = 6;
                        event.getDrops().clear();

                        //add experience.

                        UUID uuid = superMan.getUniqueId();

                        LevelManager.addExp(uuid, level);

                        //Check for level up.

                        LevelManager.levelUp(superMan);

                        //Chance for drops

                        Random rand = new Random();
                        int bonus = 0;
                        int chance = rand.nextInt(101);

                        if (SkillTreeData.luck.containsKey(uuid.toString())) {
                            bonus = SkillTreeData.luck.get(uuid.toString()) * 8;

                        }
                        if (chance < 28 + bonus) {
                            Location location = entity.getLocation();
                            World world = entity.getWorld();
                            world.dropItemNaturally(location, DropTable.selectDrop(level, entity.getType()));
                        }
                        //add points.
                        DataManager.setPointsFromPlayer(superMan, DataManager.getPointsFromPlayer(superMan) + points);
                        break;
                }
            }
        }
    }
}
