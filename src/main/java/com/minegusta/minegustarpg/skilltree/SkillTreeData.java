package com.minegusta.minegustarpg.skilltree;

import com.google.common.collect.Maps;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.concurrent.ConcurrentMap;

public class SkillTreeData {

    private static FileConfiguration conf = SkillTreeFileManager.getConf();
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
        if (conf.isSet(uuid + ".warrior") && conf.getInt(uuid + ".warrior") != 0) {
            warrior.put(uuid, conf.getInt(uuid + warrior));
        }
        if (conf.isSet(uuid + ".athlete") && conf.getInt(uuid + ".athlete") != 0) {
            athlete.put(uuid, conf.getInt(uuid + athlete));
        }
        if (conf.isSet(uuid + ".archer") && conf.getInt(uuid + ".archer") != 0) {
            archer.put(uuid, conf.getInt(uuid + archer));
        }
        if (conf.isSet(uuid + ".assassin") && conf.getInt(uuid + ".assassin") != 0) {
            assassin.put(uuid, conf.getInt(uuid + assassin));
        }
        if (conf.isSet(uuid + ".tank") && conf.getInt(uuid + ".tank") != 0) {
            tank.put(uuid, conf.getInt(uuid + tank));
        }
        if (conf.isSet(uuid + ".stunner") && conf.getInt(uuid + ".stunner") != 0) {
            stunner.put(uuid, conf.getInt(uuid + stunner));
        }
        if (conf.isSet(uuid + ".healer") && conf.getInt(uuid + ".healer") != 0) {
            healer.put(uuid, conf.getInt(uuid + healer));
        }
        if (conf.isSet(uuid + ".scout") && conf.getInt(uuid + ".scout") != 0) {
            scout.put(uuid, conf.getInt(uuid + scout));
        }
        if (conf.isSet(uuid + ".alchemist") && conf.getInt(uuid + ".alchemist") != 0) {
            alchemist.put(uuid, conf.getInt(uuid + alchemist));
        }
        if (conf.isSet(uuid + ".luck") && conf.getInt(uuid + ".luck") != 0) {
            luck.put(uuid, conf.getInt(uuid + luck));
        }
        if (conf.isSet(uuid + ".bloodbath") && conf.getInt(uuid + ".bloodbath") != 0) {
            bloodbath.put(uuid, conf.getInt(uuid + bloodbath));
        }
        if (conf.isSet(uuid + ".bowman") && conf.getInt(uuid + ".bowman") != 0) {
            bowman.put(uuid, conf.getInt(uuid + bowman));
        }
        if (conf.isSet(uuid + ".arrowefficiency") && conf.getInt(uuid + ".arrowefficiency") != 0) {
            arrowefficiency.put(uuid, conf.getInt(uuid + arrowefficiency));
        }
        if (conf.isSet(uuid + ".power") && conf.getInt(uuid + ".power") != 0) {
            power.put(uuid, conf.getInt(uuid + power));
        }
        if (conf.isSet(uuid + ".runner") && conf.getInt(uuid + ".runner") != 0) {
            runner.put(uuid, conf.getInt(uuid + runner));
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
        if (!conf.isSet(uuid + ".warrior")) return 0;
        else {
            return conf.getInt(uuid + ".warrior");
        }
    }

    public static int getAthleteFromFile(String uuid) {
        if (!conf.isSet(uuid + ".athlete")) return 0;
        else {
            return conf.getInt(uuid + ".athlete");
        }
    }

    public static int getArcherFromFile(String uuid) {
        if (!conf.isSet(uuid + ".archer")) return 0;
        else {
            return conf.getInt(uuid + ".archer");
        }
    }

    public static int getAssassinFromFile(String uuid) {
        if (!conf.isSet(uuid + ".assassin")) return 0;
        else {
            return conf.getInt(uuid + ".assassin");
        }
    }

    public static int getTankFromFile(String uuid) {
        if (!conf.isSet(uuid + ".tank")) return 0;
        else {
            return conf.getInt(uuid + ".tank");
        }
    }

    public static int getStunnerFromFile(String uuid) {
        if (!conf.isSet(uuid + ".stunner")) return 0;
        else {
            return conf.getInt(uuid + ".stunner");
        }
    }

    public static int getHealerFromFile(String uuid) {
        if (!conf.isSet(uuid + ".healer")) return 0;
        else {
            return conf.getInt(uuid + ".healer");
        }
    }

    public static int getScoutFromFile(String uuid) {
        if (!conf.isSet(uuid + ".scout")) return 0;
        else {
            return conf.getInt(uuid + ".scout");
        }
    }

    public static int getAlchemistFromFile(String uuid) {
        if (!conf.isSet(uuid + ".alchemist")) return 0;
        else {
            return conf.getInt(uuid + ".alchemist");
        }
    }

    public static int getLuckFromFile(String uuid) {
        if (!conf.isSet(uuid + ".luck")) return 0;
        else {
            return conf.getInt(uuid + ".luck");
        }
    }

    public static int getBloodbathFromFile(String uuid) {
        if (!conf.isSet(uuid + ".bloodbath")) return 0;
        else {
            return conf.getInt(uuid + ".bloodbath");
        }
    }

    public static int getRunnerFromFile(String uuid) {
        if (!conf.isSet(uuid + ".runner")) return 0;
        else {
            return conf.getInt(uuid + ".runner");
        }
    }

    public static int getArrowEfficiencyFromFile(String uuid) {
        if (!conf.isSet(uuid + ".arrowefficiency")) return 0;
        else {
            return conf.getInt(uuid + ".arrowefficiency");
        }
    }

    public static int getPowerFromFile(String uuid) {
        if (!conf.isSet(uuid + ".power")) return 0;
        else {
            return conf.getInt(uuid + ".power");
        }
    }

    public static int getBowManFromFile(String uuid) {
        if (!conf.isSet(uuid + ".bowman")) return 0;
        else {
            return conf.getInt(uuid + ".bowman");
        }
    }

    //Setters
    public static void addWarrior(String uuid, Integer amount) {
        conf.set(uuid + ".warrior", getWarriorFromFile(uuid) + 1);
    }

    public static void addAthlete(String uuid, Integer amount) {
        conf.set(uuid + ".athlete", getAthleteFromFile(uuid) + 1);
    }

    public static void addArcher(String uuid, Integer amount) {
        conf.set(uuid + ".archer", getArcherFromFile(uuid) + 1);
    }

    public static void addAssassin(String uuid, Integer amount) {
        conf.set(uuid + ".assassin", getAssassinFromFile(uuid) + 1);
    }

    public static void addTank(String uuid, Integer amount) {
        conf.set(uuid + ".tank", getTankFromFile(uuid) + 1);
    }

    public static void addStunner(String uuid, Integer amount) {
        conf.set(uuid + ".stunner", getStunnerFromFile(uuid) + 1);
    }

    public static void addHealer(String uuid, Integer amount) {
        conf.set(uuid + ".healer", getHealerFromFile(uuid) + 1);
    }

    public static void addScout(String uuid, Integer amount) {
        conf.set(uuid + ".scout", getScoutFromFile(uuid) + 1);
    }

    public static void addAlchemist(String uuid, Integer amount) {
        conf.set(uuid + ".alchemist", getAlchemistFromFile(uuid) + 1);
    }

    public static void addLuck(String uuid, Integer amount) {
        conf.set(uuid + ".luck", getLuckFromFile(uuid) + 1);
    }

    public static void addBloodbath(String uuid, Integer amount) {
        conf.set(uuid + ".bloodbath", getBloodbathFromFile(uuid) + 1);
    }

    public static void addRunner(String uuid, Integer amount) {
        conf.set(uuid + ".runner", getRunnerFromFile(uuid) + 1);
    }

    public static void addArrowEfficiency(String uuid, Integer amount) {
        conf.set(uuid + ".arrowefficiency", getArrowEfficiencyFromFile(uuid) + 1);
    }

    public static void addBowMan(String uuid, Integer amount) {
        conf.set(uuid + ".bowman", getBowManFromFile(uuid) + 1);
    }

    public static void addPower(String uuid, Integer amount) {
        conf.set(uuid + ".power", getPowerFromFile(uuid) + 1);
    }
}
