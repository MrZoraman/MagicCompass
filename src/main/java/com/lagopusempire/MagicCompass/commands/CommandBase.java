package com.lagopusempire.MagicCompass.commands;

import com.lagopusempire.MagicCompass.management.PointManager;
import com.lagopusempire.MagicCompass.zorascommandsystem.bukkitcompat.CSBukkitCommand;

public abstract class CommandBase implements CSBukkitCommand {

    private final PointManager pointManager;

    public CommandBase(PointManager pointManager) {
        this.pointManager = pointManager;
    }

    protected PointManager getPointManager() {
        return pointManager;
    }
}
