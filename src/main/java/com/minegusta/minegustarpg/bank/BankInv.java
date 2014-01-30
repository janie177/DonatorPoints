package com.minegusta.minegustarpg.bank;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BankInv {

    public static void saveItems(String mojangID, Inventory i) {
        ItemStack[] itemsToSave = i.getContents();
        FileConfiguration conf = PlayerFiles.createAndLoadBankFile(mojangID);
        conf.set("items", itemsToSave);
        PlayerFiles.saveBankFile(mojangID, conf);
    }

    public static Inventory loadItemsToInv(String mojangID, String playerName) {
        Inventory inv = Bukkit.getServer().createInventory(null, 126, ChatColor.YELLOW + playerName + "'s Bank");
        ItemStack[] itemsInBank = null;

        try {
            itemsInBank = (ItemStack[]) PlayerFiles.createAndLoadBankFile(mojangID).get("items");
        } catch (Exception e) {
        }

        if (itemsInBank == null) return inv;
        else {
            int inventorySpace = 0;
            for (ItemStack itemStack : itemsInBank) {
                inv.setItem(inventorySpace, itemStack);
                inventorySpace++;
            }

            return inv;
        }
    }
}
