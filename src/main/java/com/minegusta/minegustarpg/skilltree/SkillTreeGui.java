package com.minegusta.minegustarpg.skilltree;

import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class SkillTreeGui {

    public static List<ItemStack> getmenuItems(String uuid) {
        List<ItemStack> items = Lists.newArrayList();

        items.add(addItemMeta(Branches.WARRIOR.getItemStack(), Lists.newArrayList(ChatColor.GOLD + "Next rank: " + ChatColor.YELLOW + "+1 melee damage.", ChatColor.GOLD + "Current rank: " + ChatColor.YELLOW + "+" + SkillTreeData.getWarriorFromFile(uuid) + " melee damage.", ChatColor.GREEN + "" + SkillTreeData.getWarriorFromFile(uuid) + ChatColor.YELLOW + "/" + ChatColor.DARK_GREEN + "5" + ChatColor.YELLOW + " unlocked.")));
        items.add(addItemMeta(Branches.ARCHER.getItemStack(), Lists.newArrayList(ChatColor.GOLD + "Next rank: " + ChatColor.YELLOW + "+1 bow damage.", ChatColor.GOLD + "Current rank: " + ChatColor.YELLOW + "+" + SkillTreeData.getArcherFromFile(uuid) + " bow damage.", ChatColor.GREEN + "" + SkillTreeData.getArcherFromFile(uuid) + ChatColor.YELLOW + "/" + ChatColor.DARK_GREEN + "5" + ChatColor.YELLOW + " unlocked.")));
        items.add(addItemMeta(Branches.POWER.getItemStack(), Lists.newArrayList(ChatColor.GOLD + "Next rank: " + ChatColor.YELLOW + "+10% chance for melee knockback.", ChatColor.GOLD + "Current rank: " + "+" + ChatColor.YELLOW + SkillTreeData.getPowerFromFile(uuid) * 10 + "% chance for melee knockback.", ChatColor.GREEN + "" + SkillTreeData.getPowerFromFile(uuid) + ChatColor.YELLOW + "/" + ChatColor.DARK_GREEN + "3" + ChatColor.YELLOW + " unlocked.")));
        items.add(addItemMeta(Branches.ATHLETE.getItemStack(), Lists.newArrayList(ChatColor.GOLD + "Next rank: " + ChatColor.YELLOW + "-2 fall damage.", ChatColor.GOLD + "Current rank: " + ChatColor.YELLOW + "-" + SkillTreeData.getAthleteFromFile(uuid) * 2 + " fall damage.", ChatColor.GREEN + "" + SkillTreeData.getAthleteFromFile(uuid) + ChatColor.YELLOW + "/" + ChatColor.DARK_GREEN + "5" + ChatColor.YELLOW + " unlocked.")));

        items.add(addItemMeta(Branches.RUNNER.getItemStack(), Lists.newArrayList(ChatColor.GOLD + "Next rank: " + ChatColor.YELLOW + "+3 seconds of speed II on reaching 3 HP.", ChatColor.GOLD + "Current rank: " + ChatColor.YELLOW + SkillTreeData.getRunnerFromFile(uuid) * 3 + " seconds of speed II on reaching 3HP.", ChatColor.GREEN + "" + SkillTreeData.getRunnerFromFile(uuid) + ChatColor.YELLOW + "/" + ChatColor.DARK_GREEN + "3" + ChatColor.YELLOW + " unlocked.")));

        items.add(addItemMeta(Branches.ARROWEFFICIENCY.getItemStack(), Lists.newArrayList(ChatColor.GOLD + "Next rank: " + ChatColor.YELLOW + "+40% chance to not lose arrows on impact.", ChatColor.GOLD + "Current rank: " + ChatColor.YELLOW + SkillTreeData.getArrowEfficiencyFromFile(uuid) * 40 + "% chance to not lose arrows on impact.", ChatColor.GREEN + "" + SkillTreeData.getArrowEfficiencyFromFile(uuid) + ChatColor.YELLOW + "/" + ChatColor.DARK_GREEN + "2" + ChatColor.YELLOW + " unlocked.")));

        items.add(addItemMeta(Branches.BOWMAN.getItemStack(), Lists.newArrayList(ChatColor.GOLD + "Next rank: " + ChatColor.YELLOW + "+8% chance to rain arrows on your enemy.", ChatColor.GOLD + "Current rank: " + ChatColor.YELLOW + SkillTreeData.getBowManFromFile(uuid) * 8 + "% chance to rain arrows on your enemy.", ChatColor.GREEN + "" + SkillTreeData.getBowManFromFile(uuid) + ChatColor.YELLOW + "/" + ChatColor.DARK_GREEN + "3" + ChatColor.YELLOW + " unlocked.")));

        items.add(addItemMeta(Branches.ASSASSIN.getItemStack(), Lists.newArrayList(ChatColor.GOLD + "Next rank: " + ChatColor.YELLOW + "+5% chance to deal double melee damage.", ChatColor.GOLD + "Current rank: " + ChatColor.YELLOW + SkillTreeData.getAssassinFromFile(uuid) * 5 + "% chance to deal souble melee damage.", ChatColor.GREEN + "" + SkillTreeData.getAssassinFromFile(uuid) + ChatColor.YELLOW + "/" + ChatColor.DARK_GREEN + "3" + ChatColor.YELLOW + " unlocked.")));

        items.add(addItemMeta(Branches.TANK.getItemStack(), Lists.newArrayList(ChatColor.GOLD + "Next rank: " + ChatColor.YELLOW + "+10% chance to get no combat damage.", ChatColor.GOLD + "Current rank: " + ChatColor.YELLOW + SkillTreeData.getTankFromFile(uuid) * 10 + "% chance to get no combat damage.", ChatColor.GREEN + "" + SkillTreeData.getTankFromFile(uuid) + ChatColor.YELLOW + "/" + ChatColor.DARK_GREEN + "5" + ChatColor.YELLOW + " unlocked.")));

        items.add(addItemMeta(Branches.STUNNER.getItemStack(), Lists.newArrayList(ChatColor.GOLD + "Next rank: " + ChatColor.YELLOW + "+5% chance to stun your enemy.", ChatColor.GOLD + "Current rank: " + ChatColor.YELLOW + SkillTreeData.getStunnerFromFile(uuid) * 5 + "% chance to stun your enemy.", ChatColor.GREEN + "" + SkillTreeData.getStunnerFromFile(uuid) + ChatColor.YELLOW + "/" + ChatColor.DARK_GREEN + "3" + ChatColor.YELLOW + " unlocked.")));

        items.add(addItemMeta(Branches.HEALER.getItemStack(), Lists.newArrayList(ChatColor.GOLD + "Next rank: " + ChatColor.YELLOW + "Heal everyone around you +2.5 hearts every 90 seconds. ", ChatColor.GOLD + "Current rank: " + ChatColor.YELLOW + "Heal everyone around you for " + SkillTreeData.getHealerFromFile(uuid) * 2.5 + " hearts every 90 seconds.", ChatColor.GREEN + "" + SkillTreeData.getHealerFromFile(uuid) + ChatColor.YELLOW + "/" + ChatColor.DARK_GREEN + "4" + ChatColor.YELLOW + " unlocked.", ChatColor.YELLOW + "Activate by crouch + right-click.")));

        items.add(addItemMeta(Branches.SCOUT.getItemStack(), Lists.newArrayList(ChatColor.GOLD + "Next rank: " + ChatColor.YELLOW + "+2 seconds of speed I on kills.", ChatColor.GOLD + "Current rank: " + ChatColor.YELLOW + SkillTreeData.getScoutFromFile(uuid) * 2 + " seconds of speed I on kills.", ChatColor.GREEN + "" + SkillTreeData.getScoutFromFile(uuid) + ChatColor.YELLOW + "/" + ChatColor.DARK_GREEN + "4" + ChatColor.YELLOW + " unlocked.")));

        items.add(addItemMeta(Branches.ALCHEMIST.getItemStack(), Lists.newArrayList(ChatColor.GOLD + "Next rank: " + ChatColor.YELLOW + "+10% chance for splash potions to multiply * 4 on throwing.", ChatColor.GOLD + "Current rank: " + ChatColor.YELLOW + SkillTreeData.getAlchemistFromFile(uuid) * 10 + "% chance for splash potions to multiply * 4 on throwing.", ChatColor.GREEN + "" + SkillTreeData.getAlchemistFromFile(uuid) + ChatColor.YELLOW + "/" + ChatColor.DARK_GREEN + "4" + ChatColor.YELLOW + " unlocked.")));

        items.add(addItemMeta(Branches.LUCK.getItemStack(), Lists.newArrayList(ChatColor.GOLD + "Next rank: " + ChatColor.YELLOW + "+6% chance for monster drops.", ChatColor.GOLD + "Current rank: +" + ChatColor.YELLOW + "+" + SkillTreeData.getLuckFromFile(uuid) * 6 + "% chance for monster drops.", ChatColor.GREEN + "" + SkillTreeData.getLuckFromFile(uuid) + ChatColor.YELLOW + "/" + ChatColor.DARK_GREEN + "3" + ChatColor.YELLOW + " unlocked.")));

        items.add(addItemMeta(Branches.BLOODBATH.getItemStack(), Lists.newArrayList(ChatColor.GOLD + "Next rank: " + ChatColor.YELLOW + "+15% chance to make your enemy bleed in melee.", ChatColor.GOLD + "Current rank: " + ChatColor.YELLOW + SkillTreeData.getBloodbathFromFile(uuid) * 15 + "% chance to make your enemy bleed in melee.", ChatColor.GREEN + "" + SkillTreeData.getBloodbathFromFile(uuid) + ChatColor.YELLOW + "/" + ChatColor.DARK_GREEN + "3" + ChatColor.YELLOW + " unlocked.")));
        return items;
    }

    private static ItemStack addItemMeta(ItemStack i, List<String> loreList) {
        ItemMeta meta = i.getItemMeta();
        meta.setLore(loreList);
        i.setItemMeta(meta);
        return i;
    }


    public enum Branches {
        WARRIOR(new ItemStack(Material.DIAMOND_SWORD, 1) {
            {
                ItemMeta meta = getItemMeta();
                meta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[Warrior]");
                setItemMeta(meta);
            }
        }),
        ARCHER(new ItemStack(Material.BOW, 1) {
            {
                ItemMeta meta = getItemMeta();
                meta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[Archer]");
                setItemMeta(meta);
            }
        }),
        ASSASSIN(new ItemStack(Material.ENDER_PEARL, 1) {
            {
                ItemMeta meta = getItemMeta();
                meta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[Assassin]");
                setItemMeta(meta);
            }
        }),
        TANK(new ItemStack(Material.DIAMOND_CHESTPLATE, 1) {
            {
                ItemMeta meta = getItemMeta();
                meta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[Tank]");
                setItemMeta(meta);
            }
        }),
        STUNNER(new ItemStack(Material.SOUL_SAND, 1) {
            {
                ItemMeta meta = getItemMeta();
                meta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[Stunner]");
                setItemMeta(meta);
            }
        }),
        HEALER(new ItemStack(Material.PAPER, 1) {
            {
                ItemMeta meta = getItemMeta();
                meta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[Healer]");
                setItemMeta(meta);
            }
        }),
        SCOUT(new ItemStack(Material.DIAMOND_BOOTS, 1) {
            {
                ItemMeta meta = getItemMeta();
                meta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[Scout]");
                setItemMeta(meta);
            }
        }),
        ALCHEMIST(new ItemStack(Material.POTION, 1) {
            {
                ItemMeta meta = getItemMeta();
                meta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[Alchemist]");
                setItemMeta(meta);
            }
        }),
        LUCK(new ItemStack(Material.GOLD_NUGGET, 1) {
            {
                ItemMeta meta = getItemMeta();
                meta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[Luck]");
                setItemMeta(meta);
            }
        }),
        BLOODBATH(new ItemStack(Material.REDSTONE, 1) {
            {
                ItemMeta meta = getItemMeta();
                meta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[Bloodbath]");
                setItemMeta(meta);
            }
        }),
        BOWMAN(new ItemStack(Material.FLINT, 1) {
            {
                ItemMeta meta = getItemMeta();
                meta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[BowMan]");
                setItemMeta(meta);
            }
        }),
        POWER(new ItemStack(Material.DIAMOND_AXE, 1) {
            {
                ItemMeta meta = getItemMeta();
                meta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[Power]");
                setItemMeta(meta);
            }
        }),
        ARROWEFFICIENCY(new ItemStack(Material.ARROW, 1) {
            {
                ItemMeta meta = getItemMeta();
                meta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[Arrow Efficiency]");
                setItemMeta(meta);
            }
        }),
        RUNNER(new ItemStack(Material.FEATHER, 1) {
            {
                ItemMeta meta = getItemMeta();
                meta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[Runner]");
                setItemMeta(meta);
            }
        }),
        ATHLETE(new ItemStack(Material.IRON_BOOTS, 1) {
            {
                ItemMeta meta = getItemMeta();
                meta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[Athlete]");
                setItemMeta(meta);
            }
        });

        private ItemStack itemStack;


        private Branches(ItemStack itemStack) {
            this.itemStack = itemStack;
        }

        public ItemStack getItemStack() {
            return itemStack;
        }

    }
}
