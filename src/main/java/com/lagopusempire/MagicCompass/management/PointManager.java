package com.mrz.dyndns.server.MagicCompass.management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import com.mrz.dyndns.server.MagicCompass.evilmidget38.UUIDFetcher;
import com.mrz.dyndns.server.MagicCompass.utils.ConfigAccessor;

import static com.mrz.dyndns.server.MagicCompass.management.PointReadResultType.*;

public class PointManager
{
	private final ConfigAccessor config;
	private final Logger logger;
	
	public PointManager(ConfigAccessor config, Logger logger)
	{
		this.config = config;
		this.logger = logger;
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
		FileConfiguration config = this.config.getConfig();
		
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		
		config.set(uuidString + "." + pointName + ".X", x);
		config.set(uuidString + "." + pointName + ".Y", y);
		config.set(uuidString + "." + pointName + ".Z", z);
		config.set(uuidString + "." + pointName + ".World", loc.getWorld().getName());
		
		this.config.saveConfig();
	}
	
	/**
	 * Delets a pont for a player under the given name from the config file
	 * @param uuid The uuid of the palyer who owns the point
	 * @param pointName The name of the point
	 */
	public void deletePoint(UUID uuid, String pointName)
	{
		String uuidString = uuid.toString();
		FileConfiguration config = this.config.getConfig();
		
		config.set(uuidString + "." + pointName, null);
		
		this.config.saveConfig();
	}
	
	/**
	 * Reads a point from the file
	 * @param uuid The uuid of the owner of the point
	 * @param pointName The name of the point
	 * @return A PointReadResult, which contains the type of failure 
	 *             (if any) that occured when trying to load the pont, 
	 *             and the location itself (if there was no failure)
	 */
	public PointReadResult readPoint(UUID uuid, String pointName)
	{
		String uuidString = uuid.toString();
		FileConfiguration config = this.config.getConfig();
		
		if(!pointExists(uuid, pointName))
		{
			return new PointReadResult(NO_POINT, null);
		}
		
		String worldName = config.getString(uuidString + "." + pointName + ".World");
		World world = Bukkit.getWorld(worldName);
		
		if(world == null)
		{
			return new PointReadResult(NO_WORLD, null);
		}
		
		double x = config.getDouble(uuidString + "." + pointName + ".X");
		double y = config.getDouble(uuidString + "." + pointName + ".Y");
		double z = config.getDouble(uuidString + "." + pointName + ".Z");
		
		Location loc = new Location(world, x, y, z);
		return new PointReadResult(SUCCESS, loc);
	}
	
	/**
	 * Checks if a point exists or not
	 * @param uuid The uuid of the owner of the point
	 * @param pointName The name of the point to check
	 * @return True if the point exists, False if the point does not exist.
	 */
	public boolean pointExists(UUID uuid, String pointName)
	{
		return this.config.getConfig().contains(uuid.toString() + "." + pointName);
	}
	
	/**
	 * Gets a set of the points a player owns
	 * @param uuid The uuid of the player who owns the points that are to be returned
	 * @return A set of point names
	 */
	public Set<String> getPointList(UUID uuid)
	{
		return this.config.getConfig().getConfigurationSection(uuid.toString()).getKeys(false);
	}
	
	/**
	 * Converts the config file to a uuid based system
	 */
	public void convertToUuids()
	{
		FileConfiguration config = this.config.getConfig();
		
		class Point
		{
			String name, world;
			double x, y, z;
		}
		
		Map<String, Set<Point>> tempPoints = new HashMap<String, Set<Point>>();
		
		Set<String> keys = config.getConfigurationSection("").getKeys(false);
		for(String key : keys)
		{
			if(!isUuid(key))
			{
				//it be time to convert!
				
				Set<Point> points = new HashSet<Point>();
				Set<String> pointNames = config.getConfigurationSection(key).getKeys(false);
				for(String pointName : pointNames)
				{
					Point point = new Point();
					point.name = pointName;
					point.x = config.getDouble(key + "." + pointName + ".X");
					point.y = config.getDouble(key + "." + pointName + ".Y");
					point.z = config.getDouble(key + "." + pointName + ".Z");
					point.world = config.getString(key + "." + pointName + ".World");
					
					points.add(point);
				}
				
				tempPoints.put(key, points);
			}
		}
		
		if(!tempPoints.isEmpty())
		{
			logger.info("Converting to uuid system...");
			UUIDFetcher fetcher = new UUIDFetcher(new ArrayList<String>(tempPoints.keySet()));
			Map<String, UUID> result = null;
			try
			{
				result = fetcher.call();
			}
			catch (Exception e)
			{
				logger.severe("Failed to convert to uuid system!");
				e.printStackTrace();
				return;
			}
			
			for(Map.Entry<String, UUID> items : result.entrySet())
			{
				String uuidString = items.getValue().toString();
				String name = items.getKey();
				
				Set<Point> points = tempPoints.get(name);
				
				for(Point point : points)
				{
					config.set(uuidString + "." + point.name + ".X", point.x);
					config.set(uuidString + "." + point.name + ".Y", point.y);
					config.set(uuidString + "." + point.name + ".Z", point.z);
					config.set(uuidString + "." + point.name + ".World", point.world);
					
					config.set(name + "." + point.name, null);
				}
				
				config.set(name, null);
				
				this.config.saveConfig();
			}
		}
	}
	
	private static boolean isUuid(String uuidString)
	{
		try
		{
			UUID uuid = UUID.fromString(uuidString);
			
			return uuid.toString().equals(uuidString);
		}
		catch (Exception e)
		{
			return false;
		}
	}
}
