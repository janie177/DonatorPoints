package com.minegusta.donatorpoints;

import com.minegusta.donatorpoints.playerdata.Data;
import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.UUID;

public class LevelManager {

    public enum LevelExp {
        L1(0),
        L2(125),
        L3(225),
        L4(350),
        L5(500),
        L6(675),
        L7(975),
        L8(1100),
        L9(1350),
        L10(1625),
        L11(1925),
        L12(2250),
        L13(2600),
        L14(2965),
        L15(3375),
        L16(3800),
        L17(4250),
        L18(4725),
        L19(5225),
        L20(5750),
        L21(6300),
        L22(6875),
        L23(7475),
        L24(8100),
        L25(8750),
        L26(9425),
        L27(10125),
        L28(10850),
        L29(11600),
        L30(12375),
        L31(13175),
        L32(14000),
        L33(14850),
        L34(15725),
        L35(16625),
        L36(17550),
        L37(18500),
        L38(19475),
        L39(20475),
        L40(21500),
        L41(22550),
        L42(23625),
        L43(24725),
        L44(25850),
        L45(27000),
        L46(28175),
        L47(29375),
        L48(30600),
        L49(31850),
        L50(33125),
        L51(34425),
        L52(35750),
        L53(37100),
        L54(38475),
        L55(39875),
        L56(41300),
        L57(42750),
        L58(44225),
        L59(45725),
        L60(47250),
        L61(48800),
        L62(50375),
        L63(51975),
        L64(53600),
        L65(55250),
        L66(56925),
        L67(58625),
        L68(60350),
        L69(62100),
        L70(63875),
        L71(65675),
        L72(67500),
        L73(69350),
        L74(71225),
        L75(73125),
        L76(75050),
        L77(77000),
        L78(78975),
        L79(80975),
        L80(83000),
        L81(85050),
        L82(87125),
        L83(89225),
        L84(91350),
        L85(93500),
        L86(95675),
        L87(97875),
        L88(100100),
        L89(102350),
        L90(104625),
        L91(106925),
        L92(109250),
        L93(111600),
        L94(113975),
        L95(116375),
        L96(118800),
        L97(121250),
        L98(123725),
        L99(126225),
        L100(128750);

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
        int nextLevel = Data.getLevel(playerUUID) + 1;

        if (getExpForLevel(nextLevel) <= exp) {

            Data.setLevel(playerUUID, nextLevel);
            player.sendMessage(ChatColor.DARK_AQUA + "[" + ChatColor.DARK_GREEN + "Level Up" + ChatColor.DARK_AQUA + "] " + ChatColor.YELLOW + "Congratulations! You are now level " + ChatColor.AQUA + nextLevel + ChatColor.YELLOW + ".");
            sendFireWork(player);


        }
    }

    public static void addExp(UUID playerUUID, int monsterLevel) {
        int expToAdd = monsterLevel * 3 + Data.getLevel(playerUUID);
        Data.addExperience(playerUUID, expToAdd);
    }

    public static int getExpLeftTillNextLevel(UUID playerUUID) {
        int nextLevel = Data.getLevel(playerUUID) + 1;
        int exp = Data.getExperience(playerUUID);
        return getExpForLevel(nextLevel) - exp;
    }

    private static void sendFireWork(final Player player) {
        for (int i = 0; i < 61; i++) {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(DonatorPointsPlugin.PLUGIN, new Runnable() {
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
