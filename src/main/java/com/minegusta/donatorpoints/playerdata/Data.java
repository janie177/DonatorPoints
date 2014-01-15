package com.minegusta.donatorpoints.playerdata;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Data {

    //Getting the config

    public static FileConfiguration getStatusConfig() {
        return PlayerData.statusData;
    }

    public static FileConfiguration getHorseConfig() {
        return PlayerData.horseData;
    }

    public static FileConfiguration getLevelConfig() {
        return PlayerData.levelData;
    }

    //All getters and setters for the playerData config file.

    //PLAYERNAME IN STRING

    public static void setPlayerName(UUID mojangID, String name) {
        getStatusConfig().set(mojangID.toString() + ".playername", name);
    }

    //HORSES


    public static void setHorseName(UUID mojangID, String horseName) {
        getHorseConfig().set(mojangID + ".horsename", horseName);
    }

    public static void setHorseColor(UUID mojangID, String horseColor) {
        getHorseConfig().set(mojangID + ".horsecolor", horseColor);
    }

    public static void setLastCallHorse(UUID mojangID) {
        Long currentTime = System.currentTimeMillis();
        getHorseConfig().set(mojangID + ".lastcallhorse", currentTime);
    }

    public static void setHorseSpeed(UUID mojangID, Double horseSpeed) {
        getHorseConfig().set(mojangID + ".horsespeed", horseSpeed);
    }

    public static void setHorseJump(UUID mojangID, Double horseJump) {
        getHorseConfig().set(mojangID + ".horsejump", horseJump);
    }

    public static void setHorseStyle(UUID mojangID, String horseStyle) {
        getHorseConfig().set(mojangID + ".horsestyle", horseStyle);
    }

    public static void setHorseArmour(UUID mojangID, String armourType) {
        getHorseConfig().set(mojangID + ".horsearmor", armourType);
    }

    public static void setHasHorse(UUID mojangID, Boolean hasHorse) {
        getHorseConfig().set(mojangID + ".hashorse", hasHorse);
    }


    public static String getHorseName(UUID mojangID) {
        if (!getHorseConfig().isSet(mojangID + ".horsename")) return "Error MissingNo.";
        String path = mojangID + ".horsename";
        return getHorseConfig().getString(path);
    }

    public static String getHorseColor(UUID mojangID) {
        if (!getHorseConfig().isSet(mojangID + ".horsecolor")) return "none";
        String path = mojangID + ".horsecolor";
        return getHorseConfig().getString(path);
    }

    public static long getLastCallHorse(UUID mojangID) {
        long timeNow = System.currentTimeMillis();
        long coolDownTime = TimeUnit.MINUTES.toMillis(15);
        long resetCooldown = timeNow - coolDownTime;

        if (!getHorseConfig().isSet(mojangID + ".lastcallhorse")) return resetCooldown;
        return getHorseConfig().getLong(mojangID + ".lastcallhorse");
    }

    public static double getHorseSpeed(UUID mojangID) {
        if (!getHorseConfig().isSet(mojangID + ".horsespeed")) return 0.0;
        return getHorseConfig().getDouble(mojangID + ".horsespeed");
    }

    public static double getHorseJump(UUID mojangID) {
        if (!getHorseConfig().isSet(mojangID + ".horsejump")) return 0.0;
        return getHorseConfig().getDouble(mojangID + ".horsejump");
    }

    public static String getHorseStyle(UUID mojangID) {
        if (!getHorseConfig().isSet(mojangID + ".horsestyle")) return "None";
        return getHorseConfig().getString(mojangID + ".horsestyle");
    }

    public static String getHorseArmour(UUID mojangID) {
        if (!getHorseConfig().isSet(mojangID + ".horsearmor")) return "None";
        return getHorseConfig().getString(mojangID + ".horsearmor");
    }

    public static boolean getHasHorse(UUID mojangID) {
        if (!getHorseConfig().isSet(mojangID + ".horse.hashorse")) return false;
        return getHorseConfig().getBoolean(mojangID + ".horse.hashorse");
    }


    //LEVELS

    public static void setLevel(UUID mojangID, Integer level) {
        getLevelConfig().set(mojangID + ".level", level);
    }

    public static void setExperience(UUID mojangID, Integer experience) {
        getLevelConfig().set(mojangID + ".experience", experience);
    }

    public static int getLevel(UUID mojangID) {
        if (!getLevelConfig().isSet(mojangID + ".level")) return 0;
        return getLevelConfig().getInt(mojangID + ".level");
    }

    public static int getExperience(UUID mojangID) {
        if (!getLevelConfig().isSet(mojangID + ".experience")) return 0;
        return getLevelConfig().getInt(mojangID + ".experience");
    }

    //RACE

    public static void setRace(UUID mojangID, String race) {
        getStatusConfig().set(mojangID + ".race.race", race);
    }

    public static String getRace(UUID mojangID) {
        if (!getStatusConfig().isSet(mojangID + ".race.race")) return "none";
        return getStatusConfig().getString(mojangID + ".race.race");
    }

    //DATA

    public static void setDeaths(UUID mojangID, Integer amount) {
        getStatusConfig().set(mojangID + ".data.deaths", amount);
    }

    public static void addDeaths(UUID mojangID, Integer amount) {
        getStatusConfig().set(mojangID + ".data.deaths", getDeaths(mojangID) + amount);
    }

    public static void addQuestsDone(UUID mojangID, Integer amount) {
        getStatusConfig().set(mojangID + ".data.questsdone", getQuestsDone(mojangID) + amount);
    }

    public static void setQuestsDone(UUID mojangID, Integer amount) {
        getStatusConfig().set(mojangID + ".data.questsdone", amount);
    }

    public static void addMobsKilled(UUID mojangID, Integer amount) {
        getStatusConfig().set(mojangID + ".data.mobskilled", getMobsKilled(mojangID) + amount);
    }

    public static void setMobsKilled(UUID mojangID, Integer amount) {
        getStatusConfig().set(mojangID + ".data.mobskilled", amount);
    }

    public static int getDeaths(UUID mojangID) {
        if (!getStatusConfig().isSet(mojangID + ".data.deaths")) return 0;
        return getStatusConfig().getInt(mojangID + ".data.deaths");

    }

    public static int getMobsKilled(UUID mojangID) {
        if (!getStatusConfig().isSet(mojangID + ".data.mobskilled")) return 0;
        return getStatusConfig().getInt(mojangID + ".data.mobskilled");

    }

    public static int getQuestsDone(UUID mojangID) {
        if (!getStatusConfig().isSet(mojangID + ".data.questsdone")) return 0;
        return getStatusConfig().getInt(mojangID + ".data.questsdone");

    }
}
