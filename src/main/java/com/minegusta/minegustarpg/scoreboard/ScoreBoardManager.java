package com.minegusta.minegustarpg.scoreboard;

import com.google.common.collect.Maps;
import com.minegusta.minegustarpg.MinegustaRPGPlugin;
import com.minegusta.minegustarpg.listeners.LevelListener;
import com.minegusta.minegustarpg.managers.LevelManager;
import com.minegusta.minegustarpg.playerdata.Data;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

public class ScoreBoardManager {
    private static ConcurrentMap<UUID, Scoreboard> scoreBoardMap = Maps.newConcurrentMap();
    private static ConcurrentMap<UUID, Score> levelMap = Maps.newConcurrentMap();
    private static ConcurrentMap<UUID, Score> expMap = Maps.newConcurrentMap();

    public static void createScoreBoard(Player p) {
        String scoreboardName = p.getName().substring(0, p.getName().length() - 1);
        UUID uuid = p.getUniqueId();
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard sb = manager.getNewScoreboard();
        Objective data = sb.registerNewObjective(scoreboardName, "dummy");
        Objective levelData = sb.registerNewObjective(scoreboardName + "u", "dummy");
        data.setDisplaySlot(DisplaySlot.SIDEBAR);
        data.setDisplayName(ChatColor.RED + "Your Data:");
        levelData.setDisplaySlot(DisplaySlot.BELOW_NAME);
        levelData.setDisplayName(ChatColor.YELLOW + " " + Data.getRace(uuid));
        Score level = data.getScore(Bukkit.getOfflinePlayer(ChatColor.YELLOW + "Level: "));
        Score expLeft = data.getScore(Bukkit.getOfflinePlayer(ChatColor.YELLOW + "Exp.Left: "));
        Score levelUnderName = levelData.getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD + "Level "));

        level.setScore(1);
        expLeft.setScore(0);
        levelUnderName.setScore(0);

        levelMap.put(uuid, level);
        expMap.put(uuid, expLeft);
        scoreBoardMap.put(uuid, sb);

    }

    public static void updateScoreboard(Player p) {
        UUID uuid = p.getUniqueId();
        Score expLeft = expMap.get(uuid);
        Score level = levelMap.get(uuid);
        Score levelUnderName = levelMap.get(uuid);

        level.setScore(Data.getLevel(uuid));
        levelUnderName.setScore(Data.getLevel(uuid));
        expLeft.setScore(LevelManager.getExpLeftTillNextLevel(uuid));


    }

    public static void setScoreboard(Player p) {
        p.setScoreboard(scoreBoardMap.get(p.getUniqueId()));
    }

    public static void clearScoreboard(Player p) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        p.setScoreboard(manager.getNewScoreboard());
    }

    public static void onReloadMakeScoreboards() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getWorld().getName().toLowerCase().equalsIgnoreCase(MinegustaRPGPlugin.world)) {
                createScoreBoard(p);
                updateScoreboard(p);
                setScoreboard(p);
            } else {
                clearScoreboard(p);
            }
        }
    }


    //Scheduled task.

    public static int enableScoreBoardTimer() {
        return Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(MinegustaRPGPlugin.PLUGIN, new Runnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.getWorld().getName().toLowerCase().equalsIgnoreCase(MinegustaRPGPlugin.world)) {
                        updateScoreboard(p);
                        setScoreboard(p);

                        //Use this task as well for un-equipping armour.

                        LevelListener.unEquipGlitchedArmour(p);
                    }
                }

            }
        }, 0, 30 * 20);
    }

}
