package com.lagopusempire.MagicCompass.listeners;

import com.lagopusempire.MagicCompass.Permissions;
import com.lagopusempire.MagicCompass.management.PointManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class PlayerBedEventListener implements Listener {
    
    private final PointManager pointManager;
    
    public PlayerBedEventListener(PointManager pointManager) {
        this.pointManager = pointManager;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerBed(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();
        if(Permissions.CAN_USE.verify(player)) {
            pointManager.savePoint(player.getUniqueId(), player.getLocation(), "bed");
        }
    }
    
}
