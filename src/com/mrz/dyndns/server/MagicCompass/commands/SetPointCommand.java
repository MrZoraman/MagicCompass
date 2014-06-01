package com.mrz.dyndns.server.MagicCompass.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mrz.dyndns.server.MagicCompass.Permissions;
import com.mrz.dyndns.server.MagicCompass.management.PointManager;

public class SetPointCommand extends CommandBase
{
	public SetPointCommand(PointManager pointManager)
	{
		super(pointManager);
	}

	@Override
	public boolean execute(CommandSender sender, Player player, String cmdName, String[] preArgs, String[] args)
	{
		if(player == null)
		{
			sender.sendMessage(ChatColor.RED + "You must be a player to have change where your compass points! You don't even have a compass!");
			return true;
		}
		
		if(Permissions.CAN_USE.verify(player) == false)
		{
			player.sendMessage(ChatColor.RED + "You're not allowed to change the location your compass points to!");
			return true;
		}
		
		if(args.length <= 0)
		{
			player.sendMessage(ChatColor.RED + "Please specify the point you would like your compass to point to!");
			return false;
			//TODO: command usage feedback
		}
		
		String pointName = args[0];
		
		if(getPointManager().pointExists(player.getUniqueId(), pointName) == false)
		{
			player.sendMessage(ChatColor.RED + "Point " + ChatColor.YELLOW + pointName + ChatColor.RED + " doesn't exist!");
			player.sendMessage(ChatColor.RED + "You can add a point with " + ChatColor.AQUA + "/point add [pointName] " + ChatColor.RED + ".");
			return true;
			//TODO: better permissions, permission based feedback message, better help system overall
		}
		else
		{
			
		}
		
		return false;
	}
}
