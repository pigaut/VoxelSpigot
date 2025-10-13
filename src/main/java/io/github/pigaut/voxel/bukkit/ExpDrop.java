package io.github.pigaut.voxel.bukkit;

import io.github.pigaut.voxel.server.*;
import io.github.pigaut.yaml.amount.*;
import org.bukkit.*;
import org.bukkit.entity.*;

import java.util.*;

public class ExpDrop {

    private ExpDrop() {}

    public static void spawn(Location location, int totalExp) {
        World world = location.getWorld();
        if (world == null) {
            world = SpigotServer.getDefaultWorld();
        }

        for (Integer orbExp : getExpOrbs(totalExp)) {
            final ExperienceOrb expOrb = (ExperienceOrb) world.spawnEntity(location, EntityType.EXPERIENCE_ORB);
            expOrb.setExperience(orbExp);
        }
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
            expOrb.setExperience(expAmount.getInteger());
        }
    }

    public static List<Integer> getExpOrbs(int totalXP) {
        List<Integer> orbs = new ArrayList<>();

        while (totalXP > 0) {
            int orb = getOrbValue(totalXP);
            orbs.add(orb);
            totalXP -= orb;
        }

        return orbs;
    }

    private static int getOrbValue(int xp) {
        if (xp >= 2477) return 2477;
        if (xp >= 1237) return 1237;
        if (xp >= 617) return 617;
        if (xp >= 307) return 307;
        if (xp >= 149) return 149;
        if (xp >= 73) return 73;
        if (xp >= 37) return 37;
        if (xp >= 17) return 17;
        if (xp >= 7) return 7;
        if (xp >= 3) return 3;
        if (xp >= 1) return 1;
        return 0;
    }

}
