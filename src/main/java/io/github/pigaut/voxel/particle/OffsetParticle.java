package io.github.pigaut.voxel.particle;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

public class OffsetParticle implements ParticleEffect {

    private final ParticleEffect particle;
    private final double offsetX;
    private final double offsetY;
    private final double offsetZ;

    public OffsetParticle(ParticleEffect particle, double offsetX, double offsetY, double offsetZ) {
        this.particle = particle;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
    }

    @Override
    public void spawn(@Nullable Player player, @NotNull Location location) {
        particle.spawn(player, location.clone().add(offsetX, offsetY, offsetZ));
    }

}
