package io.github.pigaut.voxel.bukkit;

import io.github.pigaut.voxel.server.*;
import io.github.pigaut.voxel.util.*;
import org.bukkit.*;
import org.bukkit.entity.*;

public class ExpOrb {

    private ExpOrb() {}

    public static void spawn(Location location, int exp) {
        World world = location.getWorld();
        if (world == null) {
            world = SpigotServer.getDefaultWorld();
        }

        final ExperienceOrb expOrb = (ExperienceOrb) world.spawnEntity(location, EntityType.EXPERIENCE_ORB);
        expOrb.setExperience(exp);
    }

    public static void spawn(Location location, int exp, int count) {
        World world = location.getWorld();
        if (world == null) {
            world = SpigotServer.getDefaultWorld();
        }

        for (int i = 0; i < count; i++) {
            final ExperienceOrb expOrb = (ExperienceOrb) world.spawnEntity(location, EntityType.EXPERIENCE_ORB);
            expOrb.setExperience(exp);
        }
    }

    public static void spawn(Location location, Amount expAmount, int count) {
        World world = location.getWorld();
        if (world == null) {
            world = SpigotServer.getDefaultWorld();
        }

        for (int i = 0; i < count; i++) {
            final ExperienceOrb expOrb = (ExperienceOrb) world.spawnEntity(location, EntityType.EXPERIENCE_ORB);
            expOrb.setExperience(expAmount.getInt());
        }
    }

}
