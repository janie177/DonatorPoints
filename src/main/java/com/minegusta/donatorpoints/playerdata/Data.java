package com.minegusta.donatorpoints.playerdata;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.UUID;

public class Data {

    //Getting the config

    public static FileConfiguration getConfig() {
        return PlayerData.playerData;
    }

    //All getters and setters for the playerData config file.

    //PLAYERNAME IN STRING

    public static void setPlayerName(UUID mojangID, String name) {
        getConfig().set(mojangID.toString() + ".playername", name);
    }

    //HORSES


    public static void setHorseName(UUID mojangID, String horseName) {
        getConfig().set(mojangID + ".horse.horsename", horseName);
    }

    public static void setHorseColor(UUID mojangID, String horseColor) {
        getConfig().set(mojangID + ".horse.horsecolor", horseColor);
    }

    public static void setLastCallHorse(UUID mojangID) {
        Long currentTime = System.currentTimeMillis();
        getConfig().set(mojangID + ".horse.lastcallhorse", currentTime);
    }

    public static void setHorseSpeed(UUID mojangID, Double horseSpeed) {
        getConfig().set(mojangID + ".horse.horsespeed", horseSpeed);
    }

    public static void setHorseJump(UUID mojangID, Double horseJump) {
        getConfig().set(mojangID + ".horse.horsejump", horseJump);
    }

    public static void setHorseStyle(UUID mojangID, String horseStyle) {
        getConfig().set(mojangID + ".horse.horsestyle", horseStyle);
    }

    public static void setHorseArmour(UUID mojangID, String armourType) {
        getConfig().set(mojangID + ".horse.horsearmor", armourType);
    }

    public static void setHasHorse(UUID mojangID, Boolean hasHorse) {
        getConfig().set(mojangID + ".horse.hashorse", hasHorse);
    }


    public static String getHorseName(UUID mojangID) {
        if (!getConfig().isSet(mojangID + ".horse.horsename")) return "Error MissingNo.";
        String path = mojangID + ".horse.horsename";
        return getConfig().getString(path);
    }

    public static String getHorseColor(UUID mojangID) {
        if (!getConfig().isSet(mojangID + ".horse.horsecolor")) return "none";
        String path = mojangID + ".horse.horsecolor";
        return getConfig().getString(path);
    }

    public static long getLastCallHorse(UUID mojangID) {
        if (!getConfig().isSet(mojangID + ".horse.lastcallhorse")) return System.currentTimeMillis();
        return getConfig().getLong(mojangID + ".horse.lastcallhorse");
    }

    public static double getHorseSpeed(UUID mojangID) {
        if (!getConfig().isSet(mojangID + ".horse.horsespeed")) return 0.0;
        return getConfig().getDouble(mojangID + ".horse.horsespeed");
    }

    public static double getHorseJump(UUID mojangID) {
        if (!getConfig().isSet(mojangID + ".horse.horsejump")) return 0.0;
        return getConfig().getDouble(mojangID + ".horse.horsejump");
    }

    public static String getHorseStyle(UUID mojangID) {
        if (!getConfig().isSet(mojangID + ".horse.horsestyle")) return "None";
        return getConfig().getString(mojangID + ".horse.horsestyle");
    }

    public static String getHorseArmour(UUID mojangID) {
        if (!getConfig().isSet(mojangID + ".horse.horsearmor")) return "None";
        return getConfig().getString(mojangID + ".horse.horsearmor");
    }

    public static boolean getHasHorse(UUID mojangID) {
        if (!getConfig().isSet(mojangID + ".horse.hashorse")) return false;
        return getConfig().getBoolean(mojangID + ".horse.hashorse");
    }


    //LEVELS

    public static void setLevel(UUID mojangID, Integer level) {
        getConfig().set(mojangID + ".level.level", level);
    }

    public static void setExperience(UUID mojangID, Integer experience) {
        getConfig().set(mojangID + ".level.experience", experience);
    }

    public static int getLevel(UUID mojangID) {
        if (!getConfig().isSet(mojangID + ".level.level")) return 0;
        return getConfig().getInt(mojangID + ".level.level");
    }

    public static int getExperience(UUID mojangID) {
        if (!getConfig().isSet(mojangID + ".level.experience")) return 0;
        return getConfig().getInt(mojangID + ".level.experience");
    }

    //RACE

    public static void setRace(UUID mojangID, String race) {
        getConfig().set(mojangID + ".race.race", race);
    }

    public static String getRace(UUID mojangID) {
        if (!getConfig().isSet(mojangID + ".race.race")) return "none";
        return getConfig().getString(mojangID + ".race.race");
    }

    //DATA

    public static void setDeaths(UUID mojangID, Integer amount) {
        getConfig().set(mojangID + ".data.deaths", amount);
    }

    public static void setQuestsDone(UUID mojangID, Integer amount) {
        getConfig().set(mojangID + ".data.questsdone", amount);
    }

    public static void setMobsKilled(UUID mojangID, Integer amount) {
        getConfig().set(mojangID + ".data.mobskilled", amount);
    }

    public static int getDeaths(UUID mojangID) {
        if (!getConfig().isSet(mojangID + ".data.deaths")) return 0;
        return getConfig().getInt(mojangID + ".data.deaths");

    }

    public static int getMobsKilled(UUID mojangID) {
        if (!getConfig().isSet(mojangID + ".data.mobskilled")) return 0;
        return getConfig().getInt(mojangID + ".data.mobskilled");

    }

    public static int getQuestsDone(UUID mojangID) {
        if (!getConfig().isSet(mojangID + ".data.questsdone")) return 0;
        return getConfig().getInt(mojangID + ".data.questsdone");

    }
}
