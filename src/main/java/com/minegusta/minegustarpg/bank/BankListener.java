package com.minegusta.minegustarpg.bank;

import com.minegusta.minegustarpg.MinegustaRPGPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

public class BankListener implements Listener {

    @EventHandler
    public void onBankOpen(InventoryOpenEvent e) {
        if (!e.getPlayer().getWorld().getName().toLowerCase().equalsIgnoreCase(MinegustaRPGPlugin.world)) return;
        else if (e.getInventory().getType().equals(InventoryType.ENDER_CHEST)) {
            Player p = (Player) e.getPlayer();
            e.setCancelled(true);
            p.openInventory(BankInv.loadItemsToInv(p.getUniqueId().toString(), p.getName()));
        }
    }

    @EventHandler
    public void onBankClose(InventoryCloseEvent e) {
        if (!e.getPlayer().getWorld().getName().toLowerCase().equalsIgnoreCase(MinegustaRPGPlugin.world)) return;

        //TODO REMOVE
        Bukkit.broadcastMessage("Debug: inv close event!!");
        //TODO REMOVE

        if (e.getInventory().getName().contains(e.getPlayer().getName() + "'s Bank")) {
            //TODO REMOVE
            Bukkit.broadcastMessage("Debug: inv close name is: " + e.getPlayer().getName() + "'s Bank");
            //TODO REMOVE
            BankInv.saveItems(e.getPlayer().getUniqueId().toString(), e.getInventory());
        }
    }
}
