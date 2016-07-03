package com.mrz.dyndns.server.MagicCompass.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mrz.dyndns.server.MagicCompass.Permissions;
import com.mrz.dyndns.server.MagicCompass.zorascommandsystem.bukkitcompat.CSBukkitCommand;

import static org.bukkit.ChatColor.*;

public class HelpCommand implements CSBukkitCommand
{
	@Override
	public boolean execute(CommandSender sender, Player player, String cmdName, String[] preArgs, String[] args)
	{
		if(Permissions.CAN_USE.verify(sender))
		{
			sender.sendMessage(AQUA + "/point help " + YELLOW + "-" + LIGHT_PURPLE + " Shows this message.");
			sender.sendMessage(AQUA + "/point list " + YELLOW + "-" + LIGHT_PURPLE + " Lists your saved points.");
			sender.sendMessage(AQUA + "/point add [PointName] " + YELLOW + "-" + LIGHT_PURPLE + " Adds and saves a point to your list.");
			sender.sendMessage(AQUA + "/point remove [PointName] " + YELLOW + "-" + LIGHT_PURPLE + " Removes a point from your list.");
			sender.sendMessage(AQUA + "/point set [PointName] " + YELLOW + "-" + LIGHT_PURPLE + " Makes your compass point to a point.");
		}
		
		if(Permissions.CAN_RELOAD.verify(sender))
		{
			sender.sendMessage(AQUA + "/point reload " + YELLOW + "-" + LIGHT_PURPLE + " Reloads the points.yml file.");
		}
		
		if(player == null)
		{
			sender.sendMessage(RED + "NOTE: Because you are the console, the only command you can use is " + AQUA + "/point reload" + RED + "!");
		}
		
		return true;
	}
	
	
}
