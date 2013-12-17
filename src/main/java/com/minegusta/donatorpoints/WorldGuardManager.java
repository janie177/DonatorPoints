package com.minegusta.donatorpoints;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.sk89q.worldguard.bukkit.WorldGuardPlayerListener;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardWorldListener;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;
import org.bukkit.World;

public class WorldGuardManager {
    public static boolean isInRegion(Location location, final String regionName) {
        return Iterables.any(WorldGuardPlugin.inst().getRegionManager(location.getWorld()).getApplicableRegions(location), new Predicate<ProtectedRegion>() {
            @Override
            public boolean apply(ProtectedRegion protectedRegion) {
                return protectedRegion.getId().toLowerCase().contains(regionName);
            }
        });
    }
}
