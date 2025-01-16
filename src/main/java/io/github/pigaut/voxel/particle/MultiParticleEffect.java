package io.github.pigaut.voxel.particle;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class MultiParticleEffect implements ParticleEffect {

    private final List<ParticleEffect> particles;

    public MultiParticleEffect(@NotNull List<@NotNull ParticleEffect> particles) {
        this.particles = particles;
    }

    @Override
    public void spawn(@Nullable Player player, @NotNull Location location) {
        for (ParticleEffect particle : particles) {
            particle.spawn(player, location);
        }
    }

}
