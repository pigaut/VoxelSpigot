package io.github.pigaut.voxel.core.particle.impl;

import io.github.pigaut.voxel.core.particle.*;
import io.github.pigaut.voxel.server.*;
import io.github.pigaut.yaml.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

public class SimpleParticle implements ParticleEffect {

    private final String name;
    private final String group;
    private final ConfigSection section;
    private final Particle particle;
    private final int count;
    private final double rangeX, rangeY, rangeZ;
    private final boolean force;
    private final boolean playerOnly;

    public SimpleParticle(String name, @Nullable String group, ConfigSection section, Particle particle, int count,
                          double rangeX, double rangeY, double rangeZ,
                          boolean force, boolean playerOnly) {
        this.name = name;
        this.group = group;
        this.section = section;
        this.particle = particle;
        this.count = count;
        this.rangeX = rangeX;
        this.rangeY = rangeY;
        this.rangeZ = rangeZ;
        this.force = force;
        this.playerOnly = playerOnly;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public @Nullable String getGroup() {
        return group;
    }

    @Override
    public @NotNull ConfigField getField() {
        return section;
    }

    @Override
    public void spawn(@Nullable Player player, @NotNull Location location) {
        if (playerOnly) {
            if (player != null) {
                player.spawnParticle(particle, location, count, rangeX, rangeY, rangeZ);
            }
            return;
        }

        World world = location.getWorld();
        if (world == null) {
            world = SpigotServer.getDefaultWorld();
        }
        world.spawnParticle(particle, location, count, rangeX, rangeY, rangeZ, 0, null, force);
    }

}
