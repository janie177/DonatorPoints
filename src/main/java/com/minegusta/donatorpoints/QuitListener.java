package com.minegusta.donatorpoints;

import com.censoredsoftware.censoredlib.helper.QuitReasonHandler;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event) {
        String message = event.getQuitMessage();
        switch (QuitReasonHandler.latestQuit) {
            case QUITTING:
                break;
            case GENERIC_REASON:
                message = ChatColor.YELLOW + event.getPlayer().getName() + " has either quit or crashed.";
                break;
            case TIMEOUT:
                message = ChatColor.YELLOW + event.getPlayer().getName() + " has timed out.";
                break;
            case END_OF_STREAM:
                message = ChatColor.YELLOW + event.getPlayer().getName() + " has crashed.";
                break;
            case SPAM:
                message = ChatColor.YELLOW + event.getPlayer().getName() + " was kicked for spam.";
                break;
            case OVERFLOW:
                message = ChatColor.YELLOW + event.getPlayer().getName() + " has crashed.";
                break;
        }
        event.setQuitMessage(message);
    }
}
