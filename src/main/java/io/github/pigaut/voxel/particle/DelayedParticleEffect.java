package io.github.pigaut.voxel.particle;

import io.github.pigaut.voxel.plugin.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

public class DelayedParticleEffect implements ParticleEffect {

    private final EnhancedPlugin plugin;
    private final ParticleEffect particle;
    private final int delay;

    public DelayedParticleEffect(EnhancedPlugin plugin, ParticleEffect particle, int delay) {
        this.plugin = plugin;
        this.particle = particle;
        this.delay = delay;
    }

    @Override
    public void spawn(@Nullable Player player, @NotNull Location location) {
        plugin.getScheduler().runTaskLater(delay, () -> {
            particle.spawn(player, location);
        });
    }

}
