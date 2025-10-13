package io.github.pigaut.voxel.core.particle;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class MultiParticle implements ParticleEffect {

    private final String name;
    private final String group;
    private final List<ParticleEffect> particles;

    public MultiParticle(@NotNull String name, @Nullable String group,
                         @NotNull List<@NotNull ParticleEffect> particles) {
        this.name = name;
        this.group = group;
        this.particles = particles;
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
    public void spawn(@Nullable Player player, @NotNull Location location) {
        for (ParticleEffect particle : particles) {
            particle.spawn(player, location);
        }
    }

}
