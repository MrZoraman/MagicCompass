package com.mrz.dyndns.server.MagicCompass;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class PointManager
{
	private final JavaPlugin plugin;
	
	public PointManager(JavaPlugin plugin)
	{
		this.plugin = plugin;
	}
	
	/**
	 * Saves a location to the config file under the given name for a certain player
	 * @param uuid The uuid of the player who owns the points
	 * @param loc The location of the point
	 * @param pointName The name of the point
	 */
	public void savePoint(UUID uuid, Location loc, String pointName)
	{
		String uuidString = uuid.toString();
		FileConfiguration config = plugin.getConfig();
		
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		
		config.set(uuidString + "." + pointName + ".X", x);
		config.set(uuidString + "." + pointName + ".Y", y);
		config.set(uuidString + "." + pointName + ".Z", z);
		
		plugin.saveConfig();
	}
	
	/**
	 * Delets a pont for a player under the given name from the config file
	 * @param uuid The uuid of the palyer who owns the point
	 * @param pointName The name of the point
	 */
	public void deletePoint(UUID uuid, String pointName)
	{
		String uuidString = uuid.toString();
		FileConfiguration config = plugin.getConfig();
		
		config.set(uuidString + "." + pointName + ".X", null);
		config.set(uuidString + "." + pointName + ".Y", null);
		config.set(uuidString + "." + pointName + ".Z", null);
		
		plugin.saveConfig();
	}
	
	/**
	 * Converts the config file to a uuid based system
	 */
	public void convertToUuids()
	{
		
	}
}
