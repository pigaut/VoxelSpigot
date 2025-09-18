package io.github.pigaut.voxel.core.particle;

import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

public class PeriodicalParticleEffect implements ParticleEffect {

    private final EnhancedPlugin plugin;
    private final ParticleEffect particle;
    private final int interval;
    private final int repetitions;

    public PeriodicalParticleEffect(EnhancedPlugin plugin, ParticleEffect particle, int interval, int repetitions) {
        this.plugin = plugin;
        this.particle = particle;
        this.interval = interval;
        this.repetitions = repetitions;
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
        particle.spawn(player, location);
        for (int i = 1; i < repetitions + 1; i++) {
            final long delay = (long) interval * i;
            plugin.getScheduler().runTaskLater(delay, () -> particle.spawn(player, location));
        }
    }

}
