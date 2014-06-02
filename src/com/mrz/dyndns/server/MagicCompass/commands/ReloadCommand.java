package com.mrz.dyndns.server.MagicCompass.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mrz.dyndns.server.MagicCompass.Permissions;
import com.mrz.dyndns.server.MagicCompass.utils.ConfigAccessor;
import com.mrz.dyndns.server.MagicCompass.zorascommandsystem.bukkitcompat.CSBukkitCommand;

public class ReloadCommand implements CSBukkitCommand
{
	private final ConfigAccessor config;
	
	public ReloadCommand(ConfigAccessor config)
	{
		this.config = config;
	}
	
	@Override
	public boolean execute(CommandSender sender, Player player, String cmdName, String[] preArgs, String[] args)
	{
		if(Permissions.CAN_RELOAD.verify(sender) == false)
		{
			sender.sendMessage(ChatColor.RED + "You are not allowed to reload the compass points config!");
			return true;
		}
		
		config.reloadConfig();
		sender.sendMessage(ChatColor.GREEN + "points.yml reloaded.");
		return true;
	}
}
