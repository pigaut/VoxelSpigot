package io.github.pigaut.voxel.core.particle;

import io.github.pigaut.yaml.amount.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

public class OffsetParticle implements ParticleEffect {

    private final ParticleEffect particle;
    private final Amount offsetX;
    private final Amount offsetY;
    private final Amount offsetZ;

    public OffsetParticle(ParticleEffect particle, Amount offsetX, Amount offsetY, Amount offsetZ) {
        this.particle = particle;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
    }

    @Override
    public @NotNull String getName() {
        return particle.getName();
    }

    @Override
    public @Nullable String getGroup() {
        return particle.getGroup();
    }

    @Override
    public void spawn(@Nullable Player player, @NotNull Location location) {
        particle.spawn(player, location.clone().add(offsetX.getDouble(), offsetY.getDouble(), offsetZ.getDouble()));
    }

}
