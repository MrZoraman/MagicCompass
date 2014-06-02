package com.mrz.dyndns.server.MagicCompass;

import org.bukkit.plugin.java.JavaPlugin;

import com.mrz.dyndns.server.MagicCompass.commands.HelpCommand;
import com.mrz.dyndns.server.MagicCompass.commands.ListPointsCommand;
import com.mrz.dyndns.server.MagicCompass.commands.ReloadCommand;
import com.mrz.dyndns.server.MagicCompass.commands.RemovePointCommand;
import com.mrz.dyndns.server.MagicCompass.commands.SavePointCommand;
import com.mrz.dyndns.server.MagicCompass.commands.SetPointCommand;
import com.mrz.dyndns.server.MagicCompass.management.PointManager;
import com.mrz.dyndns.server.MagicCompass.utils.ConfigAccessor;
import com.mrz.dyndns.server.MagicCompass.zorascommandsystem.bukkitcompat.BukkitCommandSystem;

public class MagicCompass extends JavaPlugin
{
	@Override
	public void onEnable()
	{
		ConfigAccessor pointConfig = new ConfigAccessor(this, "points.yml");
		
		PointManager pointManager = new PointManager(pointConfig, getLogger());
		pointManager.convertToUuids();
		
		BukkitCommandSystem cs = new BukkitCommandSystem(this);
		
		HelpCommand helpCommand = new HelpCommand();
		
		cs.registerCommand("point", helpCommand);
		cs.registerCommand("point help", helpCommand);
		cs.registerCommand("point {put|add|save}", new SavePointCommand(pointManager));
		cs.registerCommand("point {del|rm|remove|delete|clear}", new RemovePointCommand(pointManager));
		cs.registerCommand("point set", new SetPointCommand(pointManager));
		cs.registerCommand("point list", new ListPointsCommand(pointManager));
		cs.registerCommand("point reload", new ReloadCommand(pointConfig));
	}
}
