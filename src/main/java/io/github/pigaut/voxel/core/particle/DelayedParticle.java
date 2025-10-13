package io.github.pigaut.voxel.core.particle;

import io.github.pigaut.voxel.plugin.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

public class DelayedParticle implements ParticleEffect {

    private final EnhancedPlugin plugin;
    private final ParticleEffect particle;
    private final int delay;

    public DelayedParticle(EnhancedPlugin plugin, ParticleEffect particle, int delay) {
        this.plugin = plugin;
        this.particle = particle;
        this.delay = delay;
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
        plugin.getScheduler().runTaskLater(delay, () -> {
            particle.spawn(player, location);
        });
    }

}
