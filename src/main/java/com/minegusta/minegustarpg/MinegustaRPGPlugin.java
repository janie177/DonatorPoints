package com.minegusta.minegustarpg;

import com.censoredsoftware.censoredlib.CensoredLibPlugin;
import com.censoredsoftware.censoredlib.helper.CensoredJavaPlugin;
import com.minegusta.minegustarpg.bank.BankListener;
import com.minegusta.minegustarpg.bank.PlayerFiles;
import com.minegusta.minegustarpg.commands.HorseCommand;
import com.minegusta.minegustarpg.commands.LevelCommand;
import com.minegusta.minegustarpg.commands.NPCSpawnCommand;
import com.minegusta.minegustarpg.commands.PointsCommand;
import com.minegusta.minegustarpg.data.DataManager;
import com.minegusta.minegustarpg.data.TimedDatas;
import com.minegusta.minegustarpg.items.ItemListener;
import com.minegusta.minegustarpg.listeners.*;
import com.minegusta.minegustarpg.managers.MobHealthManager;
import com.minegusta.minegustarpg.managers.MobSpawnManager;
import com.minegusta.minegustarpg.managers.SaveManager;
import com.minegusta.minegustarpg.npc.NPCManager;
import com.minegusta.minegustarpg.playerdata.PlayerData;
import com.minegusta.minegustarpg.scoreboard.ScoreBoardManager;
import com.minegusta.minegustarpg.shops.DonatorShopListener;
import com.minegusta.minegustarpg.shops.HorseShopListener;
import com.minegusta.minegustarpg.shops.ShopManager;
import com.minegusta.minegustarpg.skilltree.SkillTreeData;
import com.minegusta.minegustarpg.skilltree.SkillTreeFileManager;
import com.minegusta.minegustarpg.skilltree.SkillTreeListener;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

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

            //Create bank inventory

            PlayerFiles.createBankDirectory();

            //Races map adding after reloads.

            RaceListener.addPlayersToRaceMapAtStart();

            //Reload for scoreboards.

            ScoreBoardManager.onReloadMakeScoreboards();

            // command
            getCommand("points").setExecutor(new PointsCommand());
            getCommand("npc").setExecutor(new NPCSpawnCommand());
            getCommand("horse").setExecutor(new HorseCommand());
            getCommand("level").setExecutor(new LevelCommand());

            //Run scoreboard updater
            SCOREBOARD_TASK = SaveManager.enableTimedTask();

            //Load skill tree file.

            SkillTreeData.loadOnlineToMap();
            SkillTreeFileManager.loadSkillTreeFile();

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

            getLogger().info("Enabled!");
            return;
        }

        // couldn't enable
        getLogger().severe("MinegustaRPG cannot enable without CensoredLib installed.");
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
        manager.registerEvents(new WorldListener(), this);
        manager.registerEvents(new BankListener(), this);
        manager.registerEvents(new SkillTreeListener(), this);
        manager.registerEvents(new BoostListener(), this);
        manager.registerEvents(new RaceListener(), this);
    }


    //Which world is enabled?
    public final static String world = "donator";

}
