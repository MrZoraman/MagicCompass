package com.lagopusempire.MagicCompass.commands;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.lagopusempire.MagicCompass.Permissions;
import com.lagopusempire.MagicCompass.management.PointManager;

public class ListPointsCommand extends CommandBase {

    public ListPointsCommand(PointManager pointManager) {
        super(pointManager);
    }

    @Override
    public boolean execute(CommandSender sender, Player player, String cmdName, String[] preArgs, String[] args) {
        if (player == null) {
            sender.sendMessage(ChatColor.RED + "Consoles don't have any points to list!");
            return true;
        }

        if (Permissions.CAN_USE.verify(player) == false) {
            player.sendMessage(ChatColor.RED + "You're not allowed to view your points!");
            return true;
        }

        Set<String> pointNames = getPointManager().getPointList(player.getUniqueId());
        if (pointNames.size() <= 0) {
            player.sendMessage(ChatColor.RED + "You don't have any points!");
        } else {
            StringBuilder builder = new StringBuilder();

            for (String point : pointNames) {
                builder.append(ChatColor.YELLOW).append(point).append(ChatColor.DARK_GRAY).append(", ");
            }

            builder.setLength(builder.length() - 2);
            player.sendMessage(ChatColor.GREEN + "Your Points:");
            player.sendMessage(builder.toString());
        }

        return true;
    }
}
