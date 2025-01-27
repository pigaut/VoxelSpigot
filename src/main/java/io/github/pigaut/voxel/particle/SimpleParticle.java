package io.github.pigaut.voxel.particle;

import io.github.pigaut.voxel.server.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

public class SimpleParticle implements ParticleEffect {

    private final Particle particle;
    private final int count;
    private final double rangeX, rangeY, rangeZ;
    private final boolean force;
    private final boolean playerOnly;

    public SimpleParticle(Particle particle, int count, double rangeX, double rangeY, double rangeZ,
                          boolean force, boolean playerOnly) {
        this.particle = particle;
        this.count = count;
        this.rangeX = rangeX;
        this.rangeY = rangeY;
        this.rangeZ = rangeZ;
        this.force = force;
        this.playerOnly = playerOnly;
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
