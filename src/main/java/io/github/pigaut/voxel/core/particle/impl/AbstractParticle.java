package io.github.pigaut.voxel.core.particle.impl;

import io.github.pigaut.voxel.core.particle.*;
import io.github.pigaut.voxel.server.*;
import io.github.pigaut.voxel.util.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.amount.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

public abstract class AbstractParticle implements ParticleEffect {

    private final String name;
    private final String group;
    private final Particle particle;
    private final Amount amount;
    private final BlockRange range;
    private final boolean playerOnly;

    protected boolean force = false;

    public AbstractParticle(String name, String group, Particle particle,
                           Amount amount, BlockRange range, boolean playerOnly) {
        this.name = name;
        this.group = group;
        this.particle = particle;
        this.amount = amount;
        this.range = range;
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

    public abstract int getParticleCount();

    public abstract double getExtra();

    public abstract @Nullable Object getData();

    @Override
    public void spawn(@Nullable Player player, @NotNull Location location) {
        final int spawnAmount = amount.getInteger();

        if (playerOnly) {
            if (player == null) {
                return;
            }
            for (int i = 0; i < spawnAmount; i++) {
                player.spawnParticle(particle, location, getParticleCount(), range.getX(), range.getY(), range.getZ(),
                        getExtra(), getData());
            }
        }
        else {
            World world = location.getWorld();
            if (world == null) {
                world = SpigotServer.getDefaultWorld();
            }

            for (int i = 0; i < spawnAmount; i++) {
                world.spawnParticle(particle, location, getParticleCount(), range.getX(), range.getY(), range.getZ(),
                        getExtra(), getData(), force);
            }
        }
    }

}
