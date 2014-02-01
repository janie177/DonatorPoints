package com.minegusta.minegustarpg.managers;

import com.minegusta.minegustarpg.MinegustaRPGPlugin;
import com.minegusta.minegustarpg.playerdata.Data;
import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.UUID;

public class LevelManager {

    public enum LevelExp {
        L1(0),
        L2(165),
        L3(385),
        L4(660),
        L5(990),
        L6(1375),
        L7(1815),
        L8(2310),
        L9(2860),
        L10(3465),
        L11(4125),
        L12(4840),
        L13(5610),
        L14(6435),
        L15(7315),
        L16(8250),
        L17(9240),
        L18(10285),
        L19(11385),
        L20(12540),
        L21(13750),
        L22(15015),
        L23(16335),
        L24(17710),
        L25(19140),
        L26(20625),
        L27(22165),
        L28(23760),
        L29(25410),
        L30(27115),
        L31(28875),
        L32(30690),
        L33(32560),
        L34(34485),
        L35(36465),
        L36(38500),
        L37(40590),
        L38(42735),
        L39(44935),
        L40(47190),
        L41(49500),
        L42(51865),
        L43(54285),
        L44(56760),
        L45(59290),
        L46(61875),
        L47(64515),
        L48(67210),
        L49(69960),
        L50(72765),
        L51(75625),
        L52(78540),
        L53(81510),
        L54(84535),
        L55(87615),
        L56(90750),
        L57(93940),
        L58(97185),
        L59(100485),
        L60(103840),
        L61(107250),
        L62(110715),
        L63(114235),
        L64(117810),
        L65(121440),
        L66(125125),
        L67(128865),
        L68(132660),
        L69(136510),
        L70(140415),
        L71(144375),
        L72(148390),
        L73(152460),
        L74(156585),
        L75(160765),
        L76(165000),
        L77(169290),
        L78(173635),
        L79(178035),
        L80(182490),
        L81(187000),
        L82(191565),
        L83(196185),
        L84(200860),
        L85(205590),
        L86(210375),
        L87(215215),
        L88(220110),
        L89(225060),
        L90(230065),
        L91(235125),
        L92(240240),
        L93(245410),
        L94(250635),
        L95(255915),
        L96(261250),
        L97(266640),
        L98(272085),
        L99(277585),
        L100(283140);

        private int i;

        private LevelExp(Integer i) {
            this.i = i;
        }

        public int getExpAtLevel() {
            return i;
        }
    }

    public static int getExpForLevel(Integer level) {
        return LevelExp.valueOf("L" + level.toString()).getExpAtLevel();
    }

    public static void levelUp(Player player) {
        UUID playerUUID = player.getUniqueId();
        int exp = Data.getExperience(playerUUID);
        if (Data.getLevel(playerUUID) == 100) return;
        int nextLevel = Data.getLevel(playerUUID) + 1;

        if (getExpForLevel(nextLevel) <= exp) {

            Data.setLevel(playerUUID, nextLevel);
            Data.addLevelPoints(playerUUID, 1);
            player.sendMessage(ChatColor.DARK_AQUA + "[" + ChatColor.DARK_GREEN + "Level Up" + ChatColor.DARK_AQUA + "] " + ChatColor.YELLOW + "Congratulations! You are now level " + ChatColor.AQUA + nextLevel + ChatColor.YELLOW + ".");
            sendFireWork(player);


        }
    }

    public static void addExp(UUID playerUUID, int monsterLevel) {
        int expToAdd = (monsterLevel * 2) + 4;
        Data.addExperience(playerUUID, expToAdd);
    }

    public static void addExpAmount(UUID playerUUID, int amount) {
        Data.addExperience(playerUUID, amount);
    }

    public static int getExpLeftTillNextLevel(UUID playerUUID) {
        int nextLevel = Data.getLevel(playerUUID) + 1;
        int exp = Data.getExperience(playerUUID);
        return getExpForLevel(nextLevel) - exp;
    }

    private static void sendFireWork(final Player player) {
        for (int i = 0; i < 5; i++) {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(MinegustaRPGPlugin.PLUGIN, new Runnable() {
                @Override
                public void run() {
                    Location location = player.getLocation();
                    Firework firework = player.getWorld().spawn(location, Firework.class);
                    FireworkMeta data = firework.getFireworkMeta();
                    data.addEffects(FireworkEffect.builder().withColor(Color.RED).with(FireworkEffect.Type.BALL_LARGE).build());
                    data.setPower(1);
                    firework.setFireworkMeta(data);
                }
            }, i * 20);
        }
    }


}
