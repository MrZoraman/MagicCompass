package com.lagopusempire.MagicCompass.zorascommandsystem.bukkitcompat;

import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.lagopusempire.MagicCompass.zorascommandsystem.CommandPackage;
import com.lagopusempire.MagicCompass.zorascommandsystem.CommandSystem;
import java.util.logging.Level;

public class BukkitCommandSystem extends CommandSystem<CSBukkitCommand> implements CommandExecutor {

    public BukkitCommandSystem(final JavaPlugin plugin) {
        this.plugin = plugin;
    }

    private final JavaPlugin plugin;

    @Override
    public Set<String> registerCommand(final String cmdString, final CSBukkitCommand cmd) {
        if (cmdString == null || cmdString.length() <= 0) {
            throw new IllegalArgumentException("Command string cannot be null and it must contain something!");
        }

        Set<String> registeredCommandNames = super.registerCommand(cmdString, cmd);

        for (String cmdName : registeredCommandNames) {
            PluginCommand pluginCmd;
            try {
                pluginCmd = plugin.getCommand(cmdName);
                pluginCmd.setExecutor(this);
            } catch (Exception e) {
                plugin.getLogger().log(Level.SEVERE, "Failed to register command ''{0}''! Message to developer: Make sure that you have that command listed under your commands in your plugin.yml!", cmdName);
            }
        }

        return registeredCommandNames;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
        StringBuilder commandString = new StringBuilder();
        commandString.append(cmd.getName()).append(" ");
        for (int ii = 0; ii < args.length; ii++) {
            commandString.append(args[ii]).append(" ");
        }
        commandString.setLength(commandString.length() - 1);

        CommandPackage<CSBukkitCommand> pack = super.makeCommandPackage(commandString.toString());

        if (pack.isNull()) {
            sendUnknownCommandMessage(sender);
            return true;
        } else {
            CSBukkitCommand csCommand = pack.getCommand();

            Player player = null;
            if (sender instanceof Player) {
                player = (Player) sender;
            }

            return csCommand.execute(sender, player, pack.getCmdName(), pack.getPreArgs(), pack.getArgs());
        }
    }

    public void sendUnknownCommandMessage(CommandSender sender) {
        sender.sendMessage("Unknown command. Type \"help\" for help.");
    }
}
