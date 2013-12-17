package com.minegusta.donatorpoints;

import com.censoredsoftware.censoredlib.CensoredLibPlugin;
import com.censoredsoftware.censoredlib.helper.CensoredJavaPlugin;
import com.censoredsoftware.censoredlib.helper.QuitReasonHandler;
import com.minegusta.donatorpoints.data.DataManager;
import com.minegusta.donatorpoints.data.TimedDatas;
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
            manager.registerEvents(new PointsListener(), this);
            manager.registerEvents(new ShopListener(), this);
            manager.registerEvents(new MobSpawnManager(), this);
            manager.registerEvents(new NPCManager(), this);

            // command
            getCommand("points").setExecutor(new PointsCommand());

            // data
            DataManager.load();
            SAVE_TASK = DataManager.startSaveTask();
            TIMED_TASK = Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
                @Override
                public void run() {
                    TimedDatas.updateTimedData();
                }
            }, 0, 1);

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

            // undo listener
            HandlerList.unregisterAll(this);
        }

        getLogger().info("Disabled!");
    }


    //Which world is enabled?
    final static String world = "Donator";

}
