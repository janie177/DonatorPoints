package com.minegusta.donatorpoints;

import com.censoredsoftware.censoredlib.CensoredLibPlugin;
import com.censoredsoftware.censoredlib.helper.CensoredJavaPlugin;
import com.censoredsoftware.censoredlib.helper.QuitReasonHandler;
import com.minegusta.donatorpoints.commands.HorseCommand;
import com.minegusta.donatorpoints.commands.LevelCommand;
import com.minegusta.donatorpoints.commands.NPCSpawnCommand;
import com.minegusta.donatorpoints.commands.PointsCommand;
import com.minegusta.donatorpoints.data.DataManager;
import com.minegusta.donatorpoints.data.TimedDatas;
import com.minegusta.donatorpoints.listeners.*;
import com.minegusta.donatorpoints.listeners.items.ItemListener;
import com.minegusta.donatorpoints.listeners.shops.*;
import com.minegusta.donatorpoints.playerdata.PlayerData;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.logging.Handler;

public class DonatorPointsPlugin extends CensoredJavaPlugin {
    private static final String CENSORED_LIBRARY_VERSION = "1.0";
    public static DonatorPointsPlugin PLUGIN;
    public static String SAVE_PATH;
    public static boolean WORLD_GUARD_ENABLED;
    private static int SAVE_TASK, TIMED_TASK;
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

            // command
            getCommand("points").setExecutor(new PointsCommand());
            getCommand("npc").setExecutor(new NPCSpawnCommand());
            getCommand("horse").setExecutor(new HorseCommand());
            getCommand("level").setExecutor(new LevelCommand());

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
        manager.registerEvents(new ShopListener(), this);
        manager.registerEvents(new MobSpawnManager(), this);
        manager.registerEvents(new NPCManager(), this);
        manager.registerEvents(new PlayerListener(), this);
        manager.registerEvents(new HorseShopListener(), this);
        manager.registerEvents(new HorseListener(), this);
        manager.registerEvents(new WeaponShopListener(), this);
        manager.registerEvents(new MiscShopListener(), this);
        manager.registerEvents(new PotionShopListener(), this);
        manager.registerEvents(new FoodShopListener(), this);
        manager.registerEvents(new ArmourShopListener(), this);
        manager.registerEvents(new ItemListener(), this);
        manager.registerEvents(new LevelListener(), this);
    }


    //Which world is enabled?
    public final static String world = "donator";

}
