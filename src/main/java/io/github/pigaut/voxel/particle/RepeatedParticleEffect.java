package io.github.pigaut.voxel.particle;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

public class RepeatedParticleEffect implements ParticleEffect {

    private final ParticleEffect particle;
    private final int repetitions;

    public RepeatedParticleEffect(ParticleEffect particle, int repetitions) {
        this.particle = particle;
        this.repetitions = repetitions;
    }

    @Override
    public void spawn(@Nullable Player player, @NotNull Location location) {
        for (int i = 0; i < repetitions; i++) {
            particle.spawn(player, location);
        }
    }

}
