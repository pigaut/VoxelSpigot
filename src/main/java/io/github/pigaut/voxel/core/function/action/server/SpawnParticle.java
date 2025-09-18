package io.github.pigaut.voxel.core.function.action.server;

import io.github.pigaut.voxel.core.function.action.*;
import io.github.pigaut.voxel.core.particle.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

public class SpawnParticle implements Action {

    private final ParticleEffect particle;
    private final Location location;

    public SpawnParticle(ParticleEffect particle, World world, double x, double y, double z) {
        this.particle = particle;
        this.location = new Location(world, x, y, z);
    }

    @Override
    public void execute(@Nullable PlayerState player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        particle.spawn(player != null ? player.asPlayer() : null, location);
    }

}
