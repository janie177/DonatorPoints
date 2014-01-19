package com.minegusta.minegustarpg;

import com.google.common.collect.Lists;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class DropTable {

    public static ItemStack selectDrop(Integer monsterLevel, EntityType type) {
        Random rand = new Random();
        List<ItemStack> dropList = Lists.newArrayList();
        dropList.clear();
        switch (type) {
            case SKELETON: {

                if (monsterLevel > 0) {

                    dropList.add(new ItemStack(Material.ARROW, 2));
                    dropList.add(new ItemStack(Material.COAL, 1));

                    if (monsterLevel > 4) {

                        dropList.add(new ItemStack(Material.ARROW, 1));
                        dropList.add(new ItemStack(Material.FLINT, 1));
                        dropList.add(new ItemStack(Material.FEATHER, 1));
                        dropList.add(new ItemStack(Material.COAL, 1));
                        dropList.add(new ItemStack(Material.COAL, 2));
                        dropList.add(new ItemStack(Material.STICK, 1));
                        dropList.add(new ItemStack(Material.LEATHER_CHESTPLATE, 1));
                        dropList.add(new ItemStack(Material.LEATHER_LEGGINGS, 1));
                        dropList.add(new ItemStack(Material.LEATHER_BOOTS, 1));
                        dropList.add(new ItemStack(Material.LEATHER_HELMET, 1));
                        dropList.add(new ItemStack(Material.GLASS_BOTTLE, 2));


                        if (monsterLevel > 9) {

                            dropList.add(new ItemStack(Material.ARROW, 2));
                            dropList.add(new ItemStack(Material.BOW, 1));
                            dropList.add(new ItemStack(Material.COAL, 3));
                            dropList.add(new ItemStack(Material.COAL, 1));
                            dropList.add(new ItemStack(Material.WOOD_SWORD, 1));
                            dropList.add(new ItemStack(Material.WOOD_PICKAXE, 1));
                            dropList.add(new ItemStack(Material.WOOD_HOE, 1));


                            if (monsterLevel > 14) {

                                dropList.add(new ItemStack(Material.IRON_BOOTS, 1));
                                dropList.add(new ItemStack(Material.IRON_LEGGINGS, 1));

                                if (monsterLevel > 19) {

                                    dropList.add(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1));
                                    dropList.add(new ItemStack(Material.BONE, 1));

                                    if (monsterLevel > 24) {

                                        dropList.add(new ItemStack(Material.IRON_LEGGINGS, 1));
                                        dropList.add(new ItemStack(Material.IRON_BOOTS, 1));

                                    }
                                }
                            }
                        }
                    }
                }

            }
            break;
            case ZOMBIE: {


                dropList.add(new ItemStack(Material.ROTTEN_FLESH, 1));
                dropList.add(new ItemStack(Material.FEATHER, 2));
                dropList.add(new ItemStack(Material.RAW_BEEF, 1));
                dropList.add(new ItemStack(Material.STICK, 2));


                if (monsterLevel > 4) {

                    dropList.add(new ItemStack(Material.RAW_BEEF, 2));
                    dropList.add(new ItemStack(Material.ROTTEN_FLESH, 2));
                    dropList.add(new ItemStack(Material.LEATHER_CHESTPLATE, 1));
                    dropList.add(new ItemStack(Material.LEATHER_LEGGINGS, 1));
                    dropList.add(new ItemStack(Material.LEATHER_BOOTS, 1));
                    dropList.add(new ItemStack(Material.LEATHER_HELMET, 1));

                    if (monsterLevel > 9) {

                        dropList.add(new ItemStack(Material.WOOD_SWORD, 1));
                        dropList.add(new ItemStack(Material.STONE_AXE, 1));
                        dropList.add(new ItemStack(Material.STONE_SWORD, 1));
                        dropList.add(new ItemStack(Material.FISHING_ROD, 1));
                        dropList.add(new ItemStack(Material.IRON_SWORD, 1));


                        if (monsterLevel > 20) {

                            dropList.add(new ItemStack(Material.COAL, 1));
                            dropList.add(new ItemStack(Material.IRON_CHESTPLATE, 1));
                            dropList.add(new ItemStack(Material.CHAINMAIL_BOOTS, 1));
                            dropList.add(new ItemStack(Material.IRON_HELMET, 1));
                        }
                    }
                }

            }
            break;
            case SPIDER: {


                dropList.add(new ItemStack(Material.STRING, 2));
                dropList.add(new ItemStack(Material.RAW_BEEF, 2));
                dropList.add(new ItemStack(Material.SPIDER_EYE, 2));


                if (monsterLevel > 4) {

                    dropList.add(new ItemStack(Material.GOLD_SWORD, 1));
                    dropList.add(new ItemStack(Material.CARROT, 2));
                    dropList.add(new ItemStack(Material.FERMENTED_SPIDER_EYE, 1));
                    dropList.add(new ItemStack(Material.GLASS_BOTTLE, 2));

                    if (monsterLevel > 9) {

                        dropList.add(new ItemStack(Material.RAW_BEEF, 3));


                        if (monsterLevel > 14) {

                            dropList.add(new ItemStack(Material.LEATHER_HELMET, 1));
                            dropList.add(new ItemStack(Material.LEATHER_CHESTPLATE, 1));

                            if (monsterLevel > 24) {
                                dropList.add(new ItemStack(Material.DIAMOND_AXE, 1));
                            }
                        }
                    }
                }

            }
            break;
            case BLAZE: {


                dropList.add(new ItemStack(Material.BLAZE_POWDER, 2));
                dropList.add(new ItemStack(Material.NETHER_WARTS, 3));
                dropList.add(new ItemStack(Material.MAGMA_CREAM, 2));
                dropList.add(new ItemStack(Material.GLOWSTONE_DUST, 1));
                dropList.add(new ItemStack(Material.MELON, 4));
                dropList.add(new ItemStack(Material.SULPHUR, 2));

                if (monsterLevel > 19) {
                    dropList.add(new ItemStack(Material.IRON_HELMET, 1));
                    dropList.add(new ItemStack(Material.IRON_LEGGINGS, 1));
                    dropList.add(new ItemStack(Material.IRON_CHESTPLATE, 1));
                    dropList.add(new ItemStack(Material.IRON_BOOTS, 1));

                    if (monsterLevel > 24) {
                        dropList.add(new ItemStack(Material.DIAMOND, 1));
                    }
                }

            }
            break;
            case GHAST: {


                dropList.add(new ItemStack(Material.NETHER_WARTS, 2));
                dropList.add(new ItemStack(Material.GLOWSTONE_DUST, 3));
                dropList.add(new ItemStack(Material.GHAST_TEAR, 3));
                dropList.add(new ItemStack(Material.GOLD_INGOT, 3));
                dropList.add(new ItemStack(Material.SULPHUR, 3));


                if (monsterLevel > 19) {

                    dropList.add(new ItemStack(Material.CHAINMAIL_HELMET, 1));
                    dropList.add(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1));
                    dropList.add(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1));
                    dropList.add(new ItemStack(Material.CHAINMAIL_BOOTS, 1));
                    dropList.add(new ItemStack(Material.LEATHER_CHESTPLATE, 1));
                    dropList.add(new ItemStack(Material.LEATHER_LEGGINGS, 1));
                    dropList.add(new ItemStack(Material.LEATHER_HELMET, 1));
                    dropList.add(new ItemStack(Material.LEATHER_BOOTS, 1));
                }

            }
            break;
            case WOLF: {

                dropList.add(new ItemStack(Material.RAW_BEEF, 1));
                dropList.add(new ItemStack(Material.LEATHER, 1));

            }
            break;
            case WITCH: {


                dropList.add(new ItemStack(Material.NETHER_WARTS, 2));
                dropList.add(new ItemStack(Material.BREAD, 1));
                dropList.add(new ItemStack(Material.SULPHUR, 2));
                dropList.add(new ItemStack(Material.SUGAR, 2));
                dropList.add(new ItemStack(Material.GLOWSTONE_DUST, 2));
                dropList.add(new ItemStack(Material.GLASS_BOTTLE, 2));

            }
            break;
            case GIANT: {

                dropList.add(new ItemStack(Material.RAW_BEEF, 8));
                dropList.add(new ItemStack(Material.ARROW, 10));


                if (monsterLevel > 4) {
                    dropList.add(new ItemStack(Material.STONE_AXE, 1));
                    dropList.add(new ItemStack(Material.STONE_SPADE, 1));
                    dropList.add(new ItemStack(Material.STONE_SWORD, 1));
                    dropList.add(new ItemStack(Material.STONE_PICKAXE, 1));
                    dropList.add(new ItemStack(Material.STONE_HOE, 1));

                    if (monsterLevel > 9) {


                        dropList.add(new ItemStack(Material.ARROW, 10));
                        dropList.add(new ItemStack(Material.IRON_INGOT, 1));


                        if (monsterLevel > 14) {

                            dropList.add(new ItemStack(Material.DIAMOND, 1));
                            dropList.add(new ItemStack(Material.LEATHER_CHESTPLATE, 1));
                            dropList.add(new ItemStack(Material.LEATHER_LEGGINGS, 1));
                            dropList.add(new ItemStack(Material.LEATHER_HELMET, 1));
                            dropList.add(new ItemStack(Material.LEATHER_BOOTS, 1));
                            dropList.add(new ItemStack(Material.CHAINMAIL_BOOTS, 1));
                            dropList.add(new ItemStack(Material.IRON_AXE, 1));
                            dropList.add(new ItemStack(Material.IRON_SWORD, 1));


                            if (monsterLevel > 24) {

                                dropList.add(new ItemStack(Material.DIAMOND, 2));
                                dropList.add(new ItemStack(Material.IRON_HELMET, 1));
                                dropList.add(new ItemStack(Material.IRON_LEGGINGS, 1));
                                dropList.add(new ItemStack(Material.IRON_CHESTPLATE, 1));
                                dropList.add(new ItemStack(Material.IRON_BOOTS, 1));
                            }
                        }
                    }
                }

            }
            break;
        }
        int number = rand.nextInt(dropList.size());
        return dropList.get(number);
    }

}
