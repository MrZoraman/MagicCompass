package com.lagopusempire.MagicCompass.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.lagopusempire.MagicCompass.Permissions;
import com.lagopusempire.MagicCompass.management.PointManager;
import com.lagopusempire.MagicCompass.management.PointReadResult;

public class SetPointCommand extends CommandBase {

    public SetPointCommand(PointManager pointManager) {
        super(pointManager);
    }

    @Override
    public boolean execute(CommandSender sender, Player player, String cmdName, String[] preArgs, String[] args) {
        if (player == null) {
            sender.sendMessage(ChatColor.RED + "You must be a player to have change where your compass points! You don't even have a compass!");
            return true;
        }

        if (Permissions.CAN_USE.verify(player) == false) {
            player.sendMessage(ChatColor.RED + "You're not allowed to change the location your compass points to!");
            return true;
        }

        if (args.length <= 0) {
            player.sendMessage(ChatColor.RED + "Please specify the point you would like your compass to point to!");
            return false;
            //TODO: command usage feedback
        }

        String pointName = args[0];

        if (pointName.equalsIgnoreCase("spawn")) {
            player.setCompassTarget(player.getWorld().getSpawnLocation());
            player.sendMessage(ChatColor.GREEN + "Your compass is now pointing towards the spawn of your current world.");
            return true;
        }

        PointReadResult result = getPointManager().readPoint(player.getUniqueId(), pointName);

        switch (result.getReadResultType()) {
            case NO_POINT:
                player.sendMessage(ChatColor.RED + "Point " + ChatColor.YELLOW + pointName + ChatColor.RED + " doesn't exist!");
                player.sendMessage(ChatColor.RED + "You can add a point with " + ChatColor.AQUA + "/point add [PointName] " + ChatColor.RED + ".");
                break;
            //TODO: better permissions, permission based feedback message, better help system overall
            case NO_WORLD:
                player.sendMessage(ChatColor.RED + "The world that that point is in no longer exists!");
                break;
            case SUCCESS:
                Location loc = result.getLocation();
                player.setCompassTarget(loc);
                player.sendMessage(ChatColor.GREEN + "Your compass is now pointing towards point " + ChatColor.YELLOW + pointName + ChatColor.GREEN + ".");
                break;
        }

        return true;
    }
}
