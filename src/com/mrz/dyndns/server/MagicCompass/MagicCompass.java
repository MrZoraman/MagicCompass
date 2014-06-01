package com.mrz.dyndns.server.MagicCompass;

import org.bukkit.plugin.java.JavaPlugin;

import com.mrz.dyndns.server.MagicCompass.zorascommandsystem.bukkitcompat.BukkitCommandSystem;

public class MagicCompass extends JavaPlugin
{
	@Override
	public void onEnable()
	{
		BukkitCommandSystem cs = new BukkitCommandSystem(this);
		
		//point
		//point set
		//point clear
		//point reload
		//point help
		//point list
	}
}
