package com.mrz.dyndns.server.MagicCompass.zorascommandsystem.bukkitcompat;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface CSBukkitCommand
{
	public boolean execute(CommandSender sender, Player player, String cmdName, String[] preArgs, String[] args);
}
