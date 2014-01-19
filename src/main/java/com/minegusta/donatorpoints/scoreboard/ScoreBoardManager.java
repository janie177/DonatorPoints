package com.minegusta.donatorpoints.scoreboard;

import com.minegusta.donatorpoints.DonatorPointsPlugin;
import com.minegusta.donatorpoints.LevelManager;
import com.minegusta.donatorpoints.playerdata.Data;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.UUID;

public class ScoreBoardManager {

    static ScoreboardManager manager = Bukkit.getScoreboardManager();
    static Scoreboard sb = manager.getMainScoreboard();

    static Objective data = sb.registerNewObjective("data", "dummy");
    static Score level = data.getScore(Bukkit.getOfflinePlayer(ChatColor.YELLOW + "Level: "));
    static Score kills = data.getScore(Bukkit.getOfflinePlayer(ChatColor.YELLOW + "Kills: "));
    static Score deaths = data.getScore(Bukkit.getOfflinePlayer(ChatColor.YELLOW + "Deaths: "));
    static Score expLeft = data.getScore(Bukkit.getOfflinePlayer(ChatColor.YELLOW + "Exp.Left: "));

    public static String getWorldName() {
        String worldName = "noWorldGiven.";

        for (World w : Bukkit.getWorlds()) {
            if (w.getName().equalsIgnoreCase("donator")) {
                worldName = w.getName();
            }
        }
        return worldName;
    }

    public static void setScoreBoard() {
        data.setDisplaySlot(DisplaySlot.SIDEBAR);
        data.setDisplayName(ChatColor.GOLD + "Data:");

        String worldName = getWorldName();

        for (Player p : Bukkit.getWorld(worldName).getPlayers()) {
            p.setScoreboard(sb);
        }
    }

    public static void setScoreboardForPlayer(Player p) {
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
        return Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(DonatorPointsPlugin.PLUGIN, new Runnable() {
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
