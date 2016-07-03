package com.lagopusempire.MagicCompass.commands;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.lagopusempire.MagicCompass.Permissions;
import com.lagopusempire.MagicCompass.utils.ConfigAccessor;
import com.lagopusempire.MagicCompass.zorascommandsystem.bukkitcompat.CSBukkitCommand;

public class ReloadCommand implements CSBukkitCommand {

    private final ConfigAccessor config;
    private final Logger logger;

    public ReloadCommand(ConfigAccessor config, Logger logger) {
        this.config = config;
        this.logger = logger;
    }

    @Override
    public boolean execute(CommandSender sender, Player player, String cmdName, String[] preArgs, String[] args) {
        if (Permissions.CAN_RELOAD.verify(sender) == false) {
            sender.sendMessage(ChatColor.RED + "You are not allowed to reload the compass points config!");
            return true;
        }

        config.reloadConfig();
        sender.sendMessage(ChatColor.GREEN + "points.yml reloaded.");
        if (player != null) {
            logger.info("points.yml reloaded.");
        }
        return true;
    }
}
