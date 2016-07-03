package com.lagopusempire.MagicCompass.listeners;

import com.lagopusempire.MagicCompass.Permissions;
import com.lagopusempire.MagicCompass.management.PointManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathEventListener implements Listener {
    private final PointManager pointManager;
    
    public PlayerDeathEventListener(PointManager pointManager) {
        this.pointManager = pointManager;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if(Permissions.CAN_USE.verify(player)) {
            pointManager.savePoint(player.getUniqueId(), player.getLocation(), "death");
        }
    }
}
