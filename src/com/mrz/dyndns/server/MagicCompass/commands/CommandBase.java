package com.mrz.dyndns.server.MagicCompass.commands;

import com.mrz.dyndns.server.MagicCompass.management.PointManager;
import com.mrz.dyndns.server.MagicCompass.zorascommandsystem.bukkitcompat.CSBukkitCommand;

public abstract class CommandBase implements CSBukkitCommand
{
	private final PointManager pointManager;
	
	public CommandBase(PointManager pointManager)
	{
		this.pointManager = pointManager;
	}
	
	protected PointManager getPointManager()
	{
		return pointManager;
	}
}
