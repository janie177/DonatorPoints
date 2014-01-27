package com.minegusta.minegustarpg;

import com.censoredsoftware.censoredlib.CensoredLibPlugin;
import com.censoredsoftware.censoredlib.helper.CensoredJavaPlugin;
import com.censoredsoftware.censoredlib.helper.QuitReasonHandler;
import com.minegusta.minegustarpg.commands.HorseCommand;
import com.minegusta.minegustarpg.commands.LevelCommand;
import com.minegusta.minegustarpg.commands.NPCSpawnCommand;
import com.minegusta.minegustarpg.commands.PointsCommand;
import com.minegusta.minegustarpg.data.DataManager;
import com.minegusta.minegustarpg.data.TimedDatas;
import com.minegusta.minegustarpg.listeners.*;
import com.minegusta.minegustarpg.listeners.items.ItemListener;
import com.minegusta.minegustarpg.listeners.shops.DonatorShopListener;
import com.minegusta.minegustarpg.listeners.shops.HorseShopListener;
import com.minegusta.minegustarpg.listeners.shops.ShopManager;
import com.minegusta.minegustarpg.playerdata.PlayerData;
import com.minegusta.minegustarpg.scoreboard.ScoreBoardManager;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.logging.Handler;

public class MinegustaRPGPlugin extends CensoredJavaPlugin {
    private static final String CENSORED_LIBRARY_VERSION = "1.0";
    public static MinegustaRPGPlugin PLUGIN;
    public static String SAVE_PATH;
    public static boolean WORLD_GUARD_ENABLED;
    private static int SAVE_TASK, TIMED_TASK, SCOREBOARD_TASK;
    private static boolean READY = false;

    @Override
    public void onEnable() {
        // static access
        PLUGIN = this;
        SAVE_PATH = getDataFolder() + "/data/";

        // plugin manager
        PluginManager manager = Bukkit.getPluginManager();

        // censored lib
        Plugin cenLib = manager.getPlugin("CensoredLib");
        if (cenLib != null && cenLib instanceof CensoredLibPlugin && cenLib.getDescription().getVersion().startsWith(CENSORED_LIBRARY_VERSION))
            READY = true;

        // can enable
        if (READY) {
            // soft depend
            WORLD_GUARD_ENABLED = manager.isPluginEnabled("WorldGuard") && manager.getPlugin("WorldGuard") instanceof WorldGuardPlugin;

            // listener
            registerListeners();

            //Set scoreboard
            ScoreBoardManager.setScoreBoard();

            // command
            getCommand("points").setExecutor(new PointsCommand());
            getCommand("npc").setExecutor(new NPCSpawnCommand());
            getCommand("horse").setExecutor(new HorseCommand());
            getCommand("level").setExecutor(new LevelCommand());

            //Run scoreboard updater
            SCOREBOARD_TASK = ScoreBoardManager.enableScoreBoardTimer();

            // data
            DataManager.load();
            SAVE_TASK = DataManager.startSaveTask();
            TIMED_TASK = Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
                @Override
                public void run() {
                    TimedDatas.updateTimedData();
                }
            }, 0, 1);

            //playerData

            PlayerData.loadPlayerData(this);

            // quit reason handler
            boolean provideQuitReason = true;
            for (Handler handler : Bukkit.getLogger().getHandlers())
                if (handler instanceof QuitReasonHandler) provideQuitReason = false;
            if (provideQuitReason) {
                Bukkit.getLogger().addHandler(new QuitReasonHandler());
                manager.registerEvents(new QuitListener(), this);
                getLogger().info("Providing and listening for quit reasons.");
            }

            getLogger().info("Enabled!");
            return;
        }

        // couldn't enable
        getLogger().severe("DonatorPoints cannot enable without CensoredLib installed.");
        manager.disablePlugin(this);
    }

    @Override
    public void onDisable() {
        // if enabled
        if (READY) {
            // data
            Bukkit.getScheduler().cancelTask(SAVE_TASK);
            Bukkit.getScheduler().cancelTask(TIMED_TASK);
            Bukkit.getScheduler().cancelTask(SCOREBOARD_TASK);
            DataManager.save();

            //Kill all mobs that still have more HP.
            MobSpawnManager.killRemainingMobs();
            HorseListener.killAllHorses();

            // undo listener
            HandlerList.unregisterAll(this);
        }

        getLogger().info("Disabled!");
    }

    public void registerListeners() {
        PluginManager manager = Bukkit.getPluginManager();

        manager.registerEvents(new PointsListener(), this);
        manager.registerEvents(new MobHealthManager(), this);
        manager.registerEvents(new DonatorShopListener(), this);
        manager.registerEvents(new MobSpawnManager(), this);
        manager.registerEvents(new NPCManager(), this);
        manager.registerEvents(new PlayerListener(), this);
        manager.registerEvents(new HorseShopListener(), this);
        manager.registerEvents(new HorseListener(), this);
        manager.registerEvents(new ShopManager(), this);
        manager.registerEvents(new ItemListener(), this);
        manager.registerEvents(new LevelListener(), this);
        manager.registerEvents(new ScoreBoardListener(), this);
    }


    //Which world is enabled?
    public final static String world = "donator";

}
