package com.lagopusempire.MagicCompass;

import org.bukkit.plugin.java.JavaPlugin;

import com.lagopusempire.MagicCompass.commands.HelpCommand;
import com.lagopusempire.MagicCompass.commands.ListPointsCommand;
import com.lagopusempire.MagicCompass.commands.ReloadCommand;
import com.lagopusempire.MagicCompass.commands.RemovePointCommand;
import com.lagopusempire.MagicCompass.commands.SavePointCommand;
import com.lagopusempire.MagicCompass.commands.SetPointCommand;
import com.lagopusempire.MagicCompass.management.PointManager;
import com.lagopusempire.MagicCompass.utils.ConfigAccessor;
import com.lagopusempire.MagicCompass.zorascommandsystem.bukkitcompat.BukkitCommandSystem;

public class MagicCompass extends JavaPlugin {

    @Override
    public void onEnable() {
        ConfigAccessor pointConfig = new ConfigAccessor(this, "points.yml");

        PointManager pointManager = new PointManager(pointConfig, getLogger());
        pointManager.convertToUuids();

        BukkitCommandSystem cs = new BukkitCommandSystem(this);

        HelpCommand helpCommand = new HelpCommand();

        cs.registerCommand("point", helpCommand);
        cs.registerCommand("point help", helpCommand);
        cs.registerCommand("point {put|add|save}", new SavePointCommand(pointManager));
        cs.registerCommand("point {del|rm|remove|delete|clear}", new RemovePointCommand(pointManager));
        cs.registerCommand("point {to|set}", new SetPointCommand(pointManager));
        cs.registerCommand("point list", new ListPointsCommand(pointManager));
        cs.registerCommand("point reload", new ReloadCommand(pointConfig, getLogger()));
    }
}
