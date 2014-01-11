package com.minegusta.donatorpoints.commands;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.minegusta.donatorpoints.DonatorPointsPlugin;
import com.minegusta.donatorpoints.playerdata.Data;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class HorseCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("horse")) {
            if (s instanceof ConsoleCommandSender) return true;
            Player player = (Player) s;
            UUID uuid = player.getUniqueId();

            String style = Data.getHorseStyle(uuid);
            String color = Data.getHorseColor(uuid);
            double jump = Data.getHorseJump(uuid);
            String name = Data.getHorseName(uuid);
            double speed = Data.getHorseSpeed(uuid);
            String armour = Data.getHorseArmour(uuid);
            Location location = player.getLocation();

            if (!player.getWorld().getName().toLowerCase().equals(DonatorPointsPlugin.world)) return true;
            //With args

            if (args.length == 0) {
                List<String> helpMenu = Lists.newArrayList("/Horse " + ChatColor.GRAY + "- Shows this menu.", "/Horse Summon " + ChatColor.GRAY + "- Summon your horse. Has 15 minute cooldown.", "/Horse Cooldown " + ChatColor.GRAY + "- Shows cooldown time remaining.", "/Horse Info " + ChatColor.GRAY + "- Shows your horse's current look.", "/Horse Customize " + ChatColor.GRAY + "- Customize your horse's look.");
                sendText(player, helpMenu);
            } else if (args[0].equalsIgnoreCase("Summon")) {

                boolean hasHorse = Data.getHasHorse(uuid);
                if (!hasHorse) {
                    player.sendMessage(ChatColor.RED + "You do not have a horse to summon!");
                    return true;
                }

                long lastCall = Data.getLastCallHorse(uuid);
                long time = System.currentTimeMillis();
                long difference = time - lastCall;
                long coolDownTime = TimeUnit.MINUTES.toMillis(15);
                long remainingCoolDown = coolDownTime - difference;

                //Did they wait 15 minutes?
                if (difference > coolDownTime) {
                    Entity entityHorse = player.getWorld().spawnEntity(location, EntityType.HORSE);
                    Horse horse = (Horse) entityHorse;

                    horse.setVariant(Horse.Variant.HORSE);
                    horse.setAdult();
                    horse.setCustomName(name);
                    horse.setCustomNameVisible(true);
                    horse.setJumpStrength(jump);
                    horse.setStyle(getStyle(style));
                    horse.setColor(getHorseColor(color));
                    horse.getInventory().setArmor(getArmour(armour));
                    player.sendMessage(ChatColor.AQUA + "You summoned your steed!");
                } else {
                    List<String> horseTimer = Lists.newArrayList("You have to wait another " + getRemainingCooldown(remainingCoolDown) + " before you can summon again.");
                    sendText(player, horseTimer);
                }

            } else if (args[0].equalsIgnoreCase("Cooldown")) {
                long lastCall = Data.getLastCallHorse(uuid);
                long time = System.currentTimeMillis();
                long difference = time - lastCall;
                long coolDownTime = TimeUnit.MINUTES.toMillis(15);
                long remainingCoolDown = coolDownTime - difference;

                //Did they wait 15 minutes?
                if (difference > coolDownTime) {
                    List<String> canSummon = Lists.newArrayList("You can summon your horse.");
                } else {
                    List<String> horseTimer = Lists.newArrayList("You have to wait another " + getRemainingCooldown(remainingCoolDown) + " before you can summon again.");
                    sendText(player, horseTimer);
                }

            } else if (args[0].equalsIgnoreCase("Info")) {

                List<String> horseInfo = Lists.newArrayList("Name: " + ChatColor.AQUA + name, "Speed: " + ChatColor.AQUA + speed, "Jump power: " + ChatColor.AQUA + jump, "Armour: " + ChatColor.AQUA + armour, "Style: " + ChatColor.AQUA + style, "Color: " + ChatColor.AQUA + color);
                sendText(player, horseInfo);

            } else if (args[0].equalsIgnoreCase("Customize")) {

                if (args.length == 1) {
                    List<String> customizehelp = Lists.newArrayList("Name: " + ChatColor.GRAY + "- /Horse Customize Name <Name>", "Style: " + ChatColor.GRAY + "- /Horse Customize Style <Style>", "Color: " + ChatColor.GRAY + "- /Horse Customize Color <Color>");

                    sendText(player, customizehelp);

                } else if (args[1].equalsIgnoreCase("style")) {
                    if (args.length > 2) {
                        String newStyle = args[2];
                        Data.setHorseStyle(uuid, args[2].toLowerCase());
                        player.sendMessage(ChatColor.YELLOW + "Style set!");
                    } else {
                        List<String> horseStyleHelp = Lists.newArrayList("None", "BlackDots", "WhiteDots", "WhiteField", "White");
                        sendText(player, horseStyleHelp);
                    }
                } else if (args[1].equalsIgnoreCase("color")) {
                    if (args.length > 2) {
                        Data.setHorseColor(uuid, args[2].toLowerCase());
                        player.sendMessage(ChatColor.YELLOW + "Color set!");
                    } else {
                        List<String> horseColorHelp = Lists.newArrayList("Brown", "Black", "Gray", "White", "ChestNut", "DarkBrown", "Creamy", "None");
                        sendText(player, horseColorHelp);
                    }
                } else if (args[1].equalsIgnoreCase("name")) {
                    if (args.length > 2) {
                        List<String> newArgs = Lists.newArrayList(args);
                        newArgs.remove(args[0]);
                        newArgs.remove(args[1]);
                        Joiner joiner = Joiner.on(" ").skipNulls();
                        String horseNewName = joiner.join(newArgs);
                        player.sendMessage(ChatColor.YELLOW + "Name set to " + ChatColor.GOLD + horseNewName + ChatColor.YELLOW + ".");
                        if (horseNewName.length() > 27)
                            player.sendMessage(ChatColor.DARK_RED + "You cannot set your horses name to be longer than 27 characters.");
                        Data.setHorseName(uuid, horseNewName);
                    } else {
                        List<String> horseNameHelp = Lists.newArrayList("Name: " + ChatColor.GRAY + "- /Horse Customize Name <Name>");
                        sendText(player, horseNameHelp);
                    }
                } else {
                    List<String> customizehelp = Lists.newArrayList("Name: " + ChatColor.GRAY + "- /Horse Customize Name <Name>", "Style: " + ChatColor.GRAY + "- /Horse Customize Style <Style>", "Color: " + ChatColor.GRAY + "- /Horse Customize Color <Color>");

                    sendText(player, customizehelp);

                }
            }
        }
        return true;
    }

    public void sendText(Player p, List<String> text) {
        p.sendMessage(ChatColor.DARK_RED + "---------------" + ChatColor.GOLD + " Horse Help " + ChatColor.DARK_RED + "---------------");
        for (String s : text) {
            p.sendMessage(ChatColor.YELLOW + s);
        }
        p.sendMessage(ChatColor.DARK_RED + "------------------------------------------");
    }

    //Code to get the remaining cooldown.

    public static String getRemainingCooldown(long millis) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        StringBuilder sb = new StringBuilder();
        if (minutes != 0L) {
            sb.append(minutes);
            sb.append(" minutes ");
        }

        if (seconds != 0L) {
            sb.append(seconds);
            sb.append(" seconds.");
        }

        return sb.toString();
    }

    public Horse.Color getHorseColor(String color) {
        if (color.equalsIgnoreCase("brown")) {
            return Horse.Color.BROWN;
        } else if (color.equalsIgnoreCase("black")) {
            return Horse.Color.BLACK;
        } else if (color.equalsIgnoreCase("white")) {
            return Horse.Color.WHITE;
        } else if (color.equalsIgnoreCase("gray")) {
            return Horse.Color.GRAY;
        } else if (color.equalsIgnoreCase("chestnut")) {
            return Horse.Color.CHESTNUT;
        } else if (color.equalsIgnoreCase("darkbrown")) {
            return Horse.Color.DARK_BROWN;
        } else if (color.equalsIgnoreCase("creamy")) {
            return Horse.Color.CREAMY;
        } else {
            return Horse.Color.BLACK;
        }
    }

    public ItemStack getArmour(String armour) {
        if (armour.equalsIgnoreCase("none")) {
            return new ItemStack(Material.AIR, 1);
        } else if (armour.equalsIgnoreCase("gold")) {
            return new ItemStack(Material.GOLD_BARDING, 1);
        } else if (armour.equalsIgnoreCase("iron")) {
            return new ItemStack(Material.IRON_BARDING, 1);
        } else if (armour.equalsIgnoreCase("diamond")) {
            return new ItemStack(Material.DIAMOND_BARDING, 1);
        } else {
            return new ItemStack(Material.AIR, 1);
        }
    }

    public Horse.Style getStyle(String style) {
        if (style.equalsIgnoreCase("none")) {
            return Horse.Style.NONE;
        } else if (style.equalsIgnoreCase("blackdots")) {
            return Horse.Style.BLACK_DOTS;
        } else if (style.equalsIgnoreCase("white")) {
            return Horse.Style.WHITE;
        } else if (style.equalsIgnoreCase("whitedots")) {
            return Horse.Style.WHITE_DOTS;
        } else if (style.equalsIgnoreCase("whitefield")) {
            return Horse.Style.WHITEFIELD;
        } else {
            return Horse.Style.NONE;
        }
    }
}
