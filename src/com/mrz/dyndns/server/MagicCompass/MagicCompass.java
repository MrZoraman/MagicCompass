package com.mrz.dyndns.server.MagicCompass;

import org.bukkit.plugin.java.JavaPlugin;

import com.mrz.dyndns.server.MagicCompass.zorascommandsystem.bukkitcompat.BukkitCommandSystem;

public class MagicCompass extends JavaPlugin
{
	private PointManager pointManager;
	
	@Override
	public void onEnable()
	{
		pointManager = new PointManager(this);
		pointManager.convertToUuids();
		
		BukkitCommandSystem cs = new BukkitCommandSystem(this);
		
		//point
		//point set
		//point clear
		//point reload
		//point help
		//point list
		
		
	}
}
