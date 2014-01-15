package com.minegusta.donatorpoints.data;

import com.censoredsoftware.censoredlib.data.TimedData;
import com.censoredsoftware.censoredlib.helper.ConfigFile;
import com.censoredsoftware.censoredlib.helper.ConfigFile3;
import com.censoredsoftware.censoredlib.helper.MojangIdGrabber;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.minegusta.donatorpoints.DonatorPointsPlugin;
import com.minegusta.donatorpoints.npc.DefaultNPC;
import com.minegusta.donatorpoints.npc.DetailedNPC;
import com.minegusta.donatorpoints.npc.NPC;
import com.minegusta.donatorpoints.playerdata.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

public class DataManager {
    private static ConcurrentMap<String, Integer> points = Maps.newConcurrentMap();
    private static ConcurrentMap<String, NPC> nPCs = Maps.newConcurrentMap();
    static ConcurrentMap<UUID, TimedData> timedData = Maps.newConcurrentMap();
    private static PointsSave pointsSave = new PointsSave();
    private static NPCSave nPCSave = new NPCSave();
    private static TimedDataSave timedDataSave = new TimedDataSave();

    public static void load() {
        // load file
        pointsSave.loadToData();
        nPCSave.loadToData();
        timedDataSave.loadToData();

        // default data
        if (nPCs.isEmpty()) {
            for (DefaultNPC defaultNPC : DefaultNPC.values())
                nPCs.put(defaultNPC.getName(), defaultNPC);
            nPCSave.saveToFile();
        }
    }

    public static void save() {
        pointsSave.saveToFile();
        nPCSave.saveToFile();
        timedDataSave.saveToFile();
        PlayerData.saveData();
    }

    public static int startSaveTask() {
        return Bukkit.getScheduler().scheduleAsyncRepeatingTask(DonatorPointsPlugin.PLUGIN, new Runnable() {
            @Override
            public void run() {
                save();
                TimedDatas.updateTimedData();
            }
        }, 0, 60 * 20);
    }

    // get
    public static Integer getPointsFromMojangId(String mojangId) {
        if (points.containsKey(mojangId)) return points.get(mojangId);
        return null;
    }

    // lazy get
    public static Integer getPointsFromPlayer(OfflinePlayer player) {
        return getPointsFromMojangId(MojangIdGrabber.getUUID(player));
    }

    // set
    public static void setPointsFromMojangId(String mojangId, int points) {
        DataManager.points.put(mojangId, points);
    }

    // lazy set
    public static void setPointsFromPlayer(OfflinePlayer player, int points) {
        String mojangId = MojangIdGrabber.getUUID(player);
        if (mojangId != null) setPointsFromMojangId(mojangId, points);
        else if (player.isOnline())
            player.getPlayer().kickPlayer(ChatColor.RED + "Sorry, but we can't be sure if you exist.");
    }

    public static Collection<NPC> getNPCs() {
        return nPCs.values();
    }

    public static boolean isNPC(LivingEntity entity) {
        return !(entity == null || entity instanceof Player) && isNPC(entity.getCustomName());
    }

    public static boolean isNPC(String name) {
        return name != null && nPCs.containsKey(name) && nPCs.get(name).is(name);
    }

    public static NPC getNPC(final String name) {
        try {
            return Iterables.find(getNPCs(), new Predicate<NPC>() {
                @Override
                public boolean apply(NPC nPC) {
                    return nPC.is(name);
                }
            });
        } catch (Throwable ignored) {
        }
        return null;
    }

    /*
  * Timed data
  */
    public static void saveTimed(String key, String subKey, Object data, Integer seconds) {
        // Remove the data if it exists already
        TimedDatas.remove(key, subKey);

        // Create and save the timed data
        TimedData timedData = new TimedData();
        timedData.generateId();
        timedData.setKey(key);
        timedData.setSubKey(subKey);
        timedData.setData(data.toString());
        timedData.setSeconds(seconds);
        DataManager.timedData.put(timedData.getId(), timedData);
    }

    /*
     * Timed data
     */
    public static void saveTimedWeek(String key, String subKey, Object data) {
        // Remove the data if it exists already
        TimedDatas.remove(key, subKey);

        // Create and save the timed data
        TimedData timedData = new TimedData();
        timedData.generateId();
        timedData.setKey(key);
        timedData.setSubKey(subKey);
        timedData.setData(data.toString());
        timedData.setHours(168);
        DataManager.timedData.put(timedData.getId(), timedData);
    }

    public static void removeTimed(String key, String subKey) {
        TimedDatas.remove(key, subKey);
    }

    public static boolean hasTimed(String key, String subKey) {
        return TimedDatas.find(key, subKey) != null;
    }

    public static Object getTimedValue(String key, String subKey) {
        return TimedDatas.find(key, subKey).getData();
    }

    public static long getTimedExpiration(String key, String subKey) {
        return TimedDatas.find(key, subKey).getExpiration();
    }

    public static class PointsSave extends ConfigFile3<String, Integer> {
        @Override
        public ConcurrentMap<String, Integer> getLoadedData() {
            return points;
        }

        @Override
        public String getSavePath() {
            return DonatorPointsPlugin.SAVE_PATH;
        }

        @Override
        public String getSaveFile() {
            return "points.yml";
        }

        @Override
        public String convertIdFromString(String s) {
            return s;
        }

        @Override
        public Integer convertDataFromObject(Object o) {
            return Integer.valueOf(o.toString());
        }

        @Override
        public void loadToData() {
            points = loadFromFile();
        }
    }

    public static class NPCSave extends ConfigFile<String, NPC> {
        @Override
        public NPC create(String name, ConfigurationSection conf) {
            return new DetailedNPC(name, conf);
        }

        @Override
        public ConcurrentMap<String, NPC> getLoadedData() {
            return nPCs;
        }

        @Override
        public String getSavePath() {
            return DonatorPointsPlugin.SAVE_PATH;
        }

        @Override
        public String getSaveFile() {
            return "npcs.yml";
        }

        @Override
        public Map<String, Object> serialize(String name) {
            return getLoadedData().get(name).serialize();
        }

        @Override
        public String convertFromString(String name) {
            return name;
        }

        @Override
        public void loadToData() {
            nPCs = loadFromFile();
        }
    }

    public static class TimedDataSave extends ConfigFile<UUID, TimedData> {
        @Override
        public TimedData create(UUID id, ConfigurationSection conf) {
            return new TimedData(id, conf);
        }

        @Override
        public ConcurrentMap<UUID, TimedData> getLoadedData() {
            return timedData;
        }

        @Override
        public String getSavePath() {
            return DonatorPointsPlugin.SAVE_PATH;
        }

        @Override
        public String getSaveFile() {
            return "timedData.yml";
        }

        @Override
        public Map<String, Object> serialize(UUID id) {
            return getLoadedData().get(id).serialize();
        }

        @Override
        public UUID convertFromString(String stringId) {
            return UUID.fromString(stringId);
        }

        @Override
        public void loadToData() {
            timedData = loadFromFile();
        }
    }
}
