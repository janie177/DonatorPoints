package com.minegusta.minegustarpg.skilltree;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentMap;

public class SkillTreeData {

    public static FileConfiguration getConf() {
        return SkillTreeFileManager.conf;
    }

    public static ConcurrentMap<String, Integer> warrior = Maps.newConcurrentMap();
    public static ConcurrentMap<String, Integer> athlete = Maps.newConcurrentMap();
    public static ConcurrentMap<String, Integer> archer = Maps.newConcurrentMap();
    public static ConcurrentMap<String, Integer> assassin = Maps.newConcurrentMap();
    public static ConcurrentMap<String, Integer> tank = Maps.newConcurrentMap();
    public static ConcurrentMap<String, Integer> stunner = Maps.newConcurrentMap();
    public static ConcurrentMap<String, Integer> healer = Maps.newConcurrentMap();
    public static ConcurrentMap<String, Integer> scout = Maps.newConcurrentMap();
    public static ConcurrentMap<String, Integer> alchemist = Maps.newConcurrentMap();
    public static ConcurrentMap<String, Integer> luck = Maps.newConcurrentMap();
    public static ConcurrentMap<String, Integer> bloodbath = Maps.newConcurrentMap();
    public static ConcurrentMap<String, Integer> power = Maps.newConcurrentMap();
    public static ConcurrentMap<String, Integer> runner = Maps.newConcurrentMap();
    public static ConcurrentMap<String, Integer> arrowefficiency = Maps.newConcurrentMap();
    public static ConcurrentMap<String, Integer> bowman = Maps.newConcurrentMap();

    public static void loadPlayerToMaps(String uuid) {
        if (getConf().isSet(uuid + ".warrior") && getConf().getInt(uuid + ".warrior") != 0) {
            warrior.put(uuid, getConf().getInt(uuid + ".warrior"));
        }
        if (getConf().isSet(uuid + ".athlete") && getConf().getInt(uuid + ".athlete") != 0) {
            athlete.put(uuid, getConf().getInt(uuid + ".athlete"));
        }
        if (getConf().isSet(uuid + ".archer") && getConf().getInt(uuid + ".archer") != 0) {
            archer.put(uuid, getConf().getInt(uuid + ".archer"));
        }
        if (getConf().isSet(uuid + ".assassin") && getConf().getInt(uuid + ".assassin") != 0) {
            assassin.put(uuid, getConf().getInt(uuid + ".assassin"));
        }
        if (getConf().isSet(uuid + ".tank") && getConf().getInt(uuid + ".tank") != 0) {
            tank.put(uuid, getConf().getInt(uuid + ".tank"));
        }
        if (getConf().isSet(uuid + ".stunner") && getConf().getInt(uuid + ".stunner") != 0) {
            stunner.put(uuid, getConf().getInt(uuid + ".stunner"));
        }
        if (getConf().isSet(uuid + ".healer") && getConf().getInt(uuid + ".healer") != 0) {
            healer.put(uuid, getConf().getInt(uuid + ".healer"));
        }
        if (getConf().isSet(uuid + ".scout") && getConf().getInt(uuid + ".scout") != 0) {
            scout.put(uuid, getConf().getInt(uuid + ".scout"));
        }
        if (getConf().isSet(uuid + ".alchemist") && getConf().getInt(uuid + ".alchemist") != 0) {
            alchemist.put(uuid, getConf().getInt(uuid + ".alchemist"));
        }
        if (getConf().isSet(uuid + ".luck") && getConf().getInt(uuid + ".luck") != 0) {
            luck.put(uuid, getConf().getInt(uuid + ".luck"));
        }
        if (getConf().isSet(uuid + ".bloodbath") && getConf().getInt(uuid + ".bloodbath") != 0) {
            bloodbath.put(uuid, getConf().getInt(uuid + ".bloodbath"));
        }
        if (getConf().isSet(uuid + ".bowman") && getConf().getInt(uuid + ".bowman") != 0) {
            bowman.put(uuid, getConf().getInt(uuid + ".bowman"));
        }
        if (getConf().isSet(uuid + ".arrowefficiency") && getConf().getInt(uuid + ".arrowefficiency") != 0) {
            arrowefficiency.put(uuid, getConf().getInt(uuid + ".arrowefficiency"));
        }
        if (getConf().isSet(uuid + ".power") && getConf().getInt(uuid + ".power") != 0) {
            power.put(uuid, getConf().getInt(uuid + ".power"));
        }
        if (getConf().isSet(uuid + ".runner") && getConf().getInt(uuid + ".runner") != 0) {
            runner.put(uuid, getConf().getInt(uuid + ".runner"));
        }
    }

    public static void unloadPlayerFromMaps(String uuid) {
        if (warrior.containsKey(uuid)) {
            warrior.remove(uuid);
        }
        if (athlete.containsKey(uuid)) {
            athlete.remove(uuid);
        }
        if (archer.containsKey(uuid)) {
            archer.remove(uuid);
        }
        if (assassin.containsKey(uuid)) {
            assassin.remove(uuid);
        }
        if (tank.containsKey(uuid)) {
            tank.remove(uuid);
        }
        if (stunner.containsKey(uuid)) {
            stunner.remove(uuid);
        }
        if (healer.containsKey(uuid)) {
            healer.remove(uuid);
        }
        if (scout.containsKey(uuid)) {
            scout.remove(uuid);
        }
        if (alchemist.containsKey(uuid)) {
            alchemist.remove(uuid);
        }
        if (luck.containsKey(uuid)) {
            luck.remove(uuid);
        }
        if (bloodbath.containsKey(uuid)) {
            bloodbath.remove(uuid);
        }
        if (runner.containsKey(uuid)) {
            runner.remove(uuid);
        }
        if (power.containsKey(uuid)) {
            power.remove(uuid);
        }
        if (arrowefficiency.containsKey(uuid)) {
            arrowefficiency.remove(uuid);
        }
        if (bowman.containsKey(uuid)) {
            bowman.remove(uuid);
        }
    }


    //Getters


    public static int getWarriorFromFile(String uuid) {
        if (!getConf().isSet(uuid + ".warrior")) return 0;
        else {
            return getConf().getInt(uuid + ".warrior");
        }
    }

    public static int getAthleteFromFile(String uuid) {
        if (!getConf().isSet(uuid + ".athlete")) return 0;
        else {
            return getConf().getInt(uuid + ".athlete");
        }
    }

    public static int getArcherFromFile(String uuid) {
        if (!getConf().isSet(uuid + ".archer")) return 0;
        else {
            return getConf().getInt(uuid + ".archer");
        }
    }

    public static int getAssassinFromFile(String uuid) {
        if (!getConf().isSet(uuid + ".assassin")) return 0;
        else {
            return getConf().getInt(uuid + ".assassin");
        }
    }

    public static int getTankFromFile(String uuid) {
        if (!getConf().isSet(uuid + ".tank")) return 0;
        else {
            return getConf().getInt(uuid + ".tank");
        }
    }

    public static int getStunnerFromFile(String uuid) {
        if (!getConf().isSet(uuid + ".stunner")) return 0;
        else {
            return getConf().getInt(uuid + ".stunner");
        }
    }

    public static int getHealerFromFile(String uuid) {
        if (!getConf().isSet(uuid + ".healer")) return 0;
        else {
            return getConf().getInt(uuid + ".healer");
        }
    }

    public static int getScoutFromFile(String uuid) {
        if (!getConf().isSet(uuid + ".scout")) return 0;
        else {
            return getConf().getInt(uuid + ".scout");
        }
    }

    public static int getAlchemistFromFile(String uuid) {
        if (!getConf().isSet(uuid + ".alchemist")) return 0;
        else {
            return getConf().getInt(uuid + ".alchemist");
        }
    }

    public static int getLuckFromFile(String uuid) {
        if (!getConf().isSet(uuid + ".luck")) return 0;
        else {
            return getConf().getInt(uuid + ".luck");
        }
    }

    public static int getBloodbathFromFile(String uuid) {
        if (!getConf().isSet(uuid + ".bloodbath")) return 0;
        else {
            return getConf().getInt(uuid + ".bloodbath");
        }
    }

    public static int getRunnerFromFile(String uuid) {
        if (!getConf().isSet(uuid + ".runner")) return 0;
        else {
            return getConf().getInt(uuid + ".runner");
        }
    }

    public static int getArrowEfficiencyFromFile(String uuid) {
        if (!getConf().isSet(uuid + ".arrowefficiency")) return 0;
        else {
            return getConf().getInt(uuid + ".arrowefficiency");
        }
    }

    public static int getPowerFromFile(String uuid) {
        if (!getConf().isSet(uuid + ".power")) return 0;
        else {
            return getConf().getInt(uuid + ".power");
        }
    }

    public static int getBowManFromFile(String uuid) {
        if (!getConf().isSet(uuid + ".bowman")) return 0;
        else {
            return getConf().getInt(uuid + ".bowman");
        }
    }

    //Setters
    public static void addWarrior(String uuid, Integer amount) {
        getConf().set(uuid + ".warrior", getWarriorFromFile(uuid) + 1);
    }

    public static void addAthlete(String uuid, Integer amount) {
        getConf().set(uuid + ".athlete", getAthleteFromFile(uuid) + 1);
    }

    public static void addArcher(String uuid, Integer amount) {
        getConf().set(uuid + ".archer", getArcherFromFile(uuid) + 1);
    }

    public static void addAssassin(String uuid, Integer amount) {
        getConf().set(uuid + ".assassin", getAssassinFromFile(uuid) + 1);
    }

    public static void addTank(String uuid, Integer amount) {
        getConf().set(uuid + ".tank", getTankFromFile(uuid) + 1);
    }

    public static void addStunner(String uuid, Integer amount) {
        getConf().set(uuid + ".stunner", getStunnerFromFile(uuid) + 1);
    }

    public static void addHealer(String uuid, Integer amount) {
        getConf().set(uuid + ".healer", getHealerFromFile(uuid) + 1);
    }

    public static void addScout(String uuid, Integer amount) {
        getConf().set(uuid + ".scout", getScoutFromFile(uuid) + 1);
    }

    public static void addAlchemist(String uuid, Integer amount) {
        getConf().set(uuid + ".alchemist", getAlchemistFromFile(uuid) + 1);
    }

    public static void addLuck(String uuid, Integer amount) {
        getConf().set(uuid + ".luck", getLuckFromFile(uuid) + 1);
    }

    public static void addBloodbath(String uuid, Integer amount) {
        getConf().set(uuid + ".bloodbath", getBloodbathFromFile(uuid) + 1);
    }

    public static void addRunner(String uuid, Integer amount) {
        getConf().set(uuid + ".runner", getRunnerFromFile(uuid) + 1);
    }

    public static void addArrowEfficiency(String uuid, Integer amount) {
        getConf().set(uuid + ".arrowefficiency", getArrowEfficiencyFromFile(uuid) + 1);
    }

    public static void addBowMan(String uuid, Integer amount) {
        getConf().set(uuid + ".bowman", getBowManFromFile(uuid) + 1);
    }

    public static void addPower(String uuid, Integer amount) {
        getConf().set(uuid + ".power", getPowerFromFile(uuid) + 1);
    }

    // On reload to be safe.
    public static void loadOnlineToMap() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            loadPlayerToMaps(p.getUniqueId().toString());
        }
    }
}
