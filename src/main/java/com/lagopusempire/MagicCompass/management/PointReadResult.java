package com.lagopusempire.MagicCompass.management;

import org.bukkit.Location;

public class PointReadResult {

    private final PointReadResultType type;
    private final Location loc;

    PointReadResult(PointReadResultType type, Location loc) {
        this.type = type;
        this.loc = loc;
    }

    public PointReadResultType getReadResultType() {
        return type;
    }

    public Location getLocation() {
        return loc;
    }
}
