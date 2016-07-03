package com.lagopusempire.MagicCompass.commands;

import com.lagopusempire.MagicCompass.Permissions;
import com.lagopusempire.MagicCompass.management.PointManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ResetCommand extends CommandBase {

    public ResetCommand(PointManager pointManager) {
        super(pointManager);
    }

    @Override
    public boolean execute(CommandSender sender, Player player, String cmdName, String[] preArgs, String[] args) {
        if (player == null) {
            sender.sendMessage(ChatColor.RED + "You must be a player to save a compass point!");
            return true;
        }
        
        if (Permissions.CAN_USE.verify(player) == false) {
            player.sendMessage(ChatColor.RED + "You're not allowed to change the location your compass points to!");
            return true;
        }
        
        player.setCompassTarget(player.getWorld().getSpawnLocation());
        player.sendMessage(ChatColor.GREEN + "Your compass not points to your world's spawn location.");
        return true;
    }
    
}
