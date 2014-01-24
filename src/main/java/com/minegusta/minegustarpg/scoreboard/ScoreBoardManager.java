package com.minegusta.minegustarpg.scoreboard;

import com.minegusta.minegustarpg.LevelManager;
import com.minegusta.minegustarpg.MinegustaRPGPlugin;
import com.minegusta.minegustarpg.playerdata.Data;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.UUID;

public class ScoreBoardManager {

    static ScoreboardManager manager = Bukkit.getScoreboardManager();
    static Scoreboard sb = manager.getMainScoreboard();
    static Objective data = setObjectives();

    public static Objective setObjectives() {
        if (sb.getObjective("playerData") == null) {
            data = sb.registerNewObjective("playerData", "dummy");
        } else {
            data = sb.getObjective("playerData");
        }
        return data;
    }


    static Score level = data.getScore(Bukkit.getOfflinePlayer(ChatColor.YELLOW + "Level: "));
    static Score kills = data.getScore(Bukkit.getOfflinePlayer(ChatColor.YELLOW + "Kills: "));
    static Score deaths = data.getScore(Bukkit.getOfflinePlayer(ChatColor.YELLOW + "Deaths: "));
    static Score expLeft = data.getScore(Bukkit.getOfflinePlayer(ChatColor.YELLOW + "Exp.Left: "));


    public static String getWorldName() {
        String worldName = "noWorldGiven.";

        for (World w : Bukkit.getWorlds()) {
            if (w.getName().equalsIgnoreCase(MinegustaRPGPlugin.world)) {
                worldName = w.getName();
            }
        }
        return worldName;
    }

    public static void setScoreBoard() {
        data.setDisplaySlot(DisplaySlot.SIDEBAR);
        data.setDisplayName(ChatColor.GOLD + "Your Data: ");

        String worldName = getWorldName();

        for (Player p : Bukkit.getWorld(worldName).getPlayers()) {
            p.setScoreboard(sb);
        }
    }

    public static void setScoreboardForPlayer(Player p) {
        if (p.getWorld().getName().equalsIgnoreCase("donator")) return;
        p.setScoreboard(sb);
    }

    public static void clearScoreBoardForPlayer(Player p) {
        p.setScoreboard(manager.getNewScoreboard());
    }


    public static void updateScoreboard(Player p) {


        UUID mojangID = p.getUniqueId();
        int pLevel = Data.getLevel(mojangID);
        int pDeaths = Data.getDeaths(mojangID);
        int pKills = Data.getMobsKilled(mojangID);
        int pExpLeft = LevelManager.getExpLeftTillNextLevel(mojangID);

        level.setScore(pLevel);
        expLeft.setScore(pExpLeft);
        kills.setScore(pKills);
        deaths.setScore(pDeaths);

        p.setScoreboard(sb);
    }

    //Scheduled task.

    public static int enableScoreBoardTimer() {
        return Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(MinegustaRPGPlugin.PLUGIN, new Runnable() {
            @Override
            public void run() {
                String worldName = getWorldName();
                for (Player p : Bukkit.getWorld(worldName).getPlayers()) {
                    updateScoreboard(p);
                }

            }
        }, 0, 60 * 20);
    }

}
