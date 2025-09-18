package io.github.pigaut.voxel.core.particle;

import io.github.pigaut.yaml.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class MultiParticleEffect implements ParticleEffect {

    private final String name;
    private final String group;
    private final ConfigSequence sequence;
    private final List<ParticleEffect> particles;

    public MultiParticleEffect(@NotNull String name, @Nullable String group, ConfigSequence sequence,
                               @NotNull List<@NotNull ParticleEffect> particles) {
        this.name = name;
        this.group = group;
        this.sequence = sequence;
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
