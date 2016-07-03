package com.mrz.dyndns.server.MagicCompass;

import org.bukkit.command.CommandSender;

public enum Permissions
{
	CAN_USE     ("compass.use"),
	CAN_RELOAD  ("compass.reload");
	
	private Permissions(String node)
	{
		this.node = node;
	}
	
	private final String node;
	
	public boolean verify(CommandSender sender)
	{
		return sender.hasPermission(node);
	}
}
