package com.mrz.dyndns.server.MagicCompass.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mrz.dyndns.server.MagicCompass.Permissions;
import com.mrz.dyndns.server.MagicCompass.management.PointManager;

public class SavePointCommand extends CommandBase
{
	public SavePointCommand(PointManager pointManager)
	{
		super(pointManager);
	}

	@Override
	public boolean execute(CommandSender sender, Player player, String cmdName, String[] preArgs, String[] args)
	{
		if(player == null)
		{
			sender.sendMessage(ChatColor.RED + "You must be a player to save a compass point!");
			return true;
		}
		
		if(Permissions.CAN_USE.verify(player) == false)
		{
			player.sendMessage(ChatColor.RED + "You don't have permission to save compass points!");
			return true;
		}
		
		if(args.length <= 0)
		{
			player.sendMessage(ChatColor.RED + "Please specify a name for your point!");
			//TODO: better feedback
			return false;
		}
		
		String pointName = args[0];
		
		if(pointName.equalsIgnoreCase("spawn"))
		{
			player.sendMessage(ChatColor.YELLOW + pointName + ChatColor.RED + " is the point that points your compass towards spawn, and thus it is reserved.");
			return true;
		}
		
		Location loc = player.getLocation();
		getPointManager().savePoint(player.getUniqueId(), loc, pointName);
		player.sendMessage(ChatColor.GREEN + "Point " + ChatColor.YELLOW + pointName + ChatColor.GREEN + " saved.");
		
		return true;
	}
}
