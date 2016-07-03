package com.mrz.dyndns.server.MagicCompass.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mrz.dyndns.server.MagicCompass.Permissions;
import com.mrz.dyndns.server.MagicCompass.management.PointManager;

public class RemovePointCommand extends CommandBase
{
	public RemovePointCommand(PointManager pointManager)
	{
		super(pointManager);
	}

	@Override
	public boolean execute(CommandSender sender, Player player, String cmdName, String[] preArgs, String[] args)
	{
		if(player == null)
		{
			sender.sendMessage(ChatColor.RED + "You must be a player to remove your points!");
			//TODO: suggest the command for removing other people's points
			return true;
		}
		
		if(Permissions.CAN_USE.verify(player) == false)
		{
			player.sendMessage(ChatColor.RED + "You don't have permission to remove points!");
			return true;
		}
		
		if(args.length <= 0)
		{
			player.sendMessage(ChatColor.RED + "Please specify the name of the point!");
			//TODO: show command help
			return true;
		}
		
		String pointName = args[0];
		
		if(getPointManager().pointExists(player.getUniqueId(), pointName))
		{
			getPointManager().deletePoint(player.getUniqueId(), pointName);
			player.sendMessage(ChatColor.GREEN + "Point " + ChatColor.YELLOW + pointName + ChatColor.GREEN + " removed.");
		}
		else
		{
			player.sendMessage(ChatColor.RED + "Point " + ChatColor.YELLOW + pointName + ChatColor.RED + " does not exist!");
		}
		
		return true;
	}
}
