package io.github.pigaut.voxel.function.action.server;

import io.github.pigaut.voxel.function.action.*;
import io.github.pigaut.voxel.particle.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.jetbrains.annotations.*;

public class SpawnParticle implements Action {

    private final ParticleEffect particle;
    private final Location location;

    public SpawnParticle(ParticleEffect particle, Location location) {
        this.particle = particle;
        this.location = location;
    }

    @Override
    public void execute(@Nullable PluginPlayer player, @Nullable Block block) {
        particle.spawn(player != null ? player.asPlayer() : null, location);
    }

    public static ConfigLoader<SpawnParticle> newConfigLoader() {
        return new BranchLoader<>() {
            @Override
            public @NotNull SpawnParticle loadFromBranch(ConfigBranch branch) throws InvalidConfigurationException {
                final ParticleEffect particle = branch.getField("particle|value", 1).load(ParticleEffect.class);
                final Location location = branch.getField("location", 2).load(Location.class);
                return new SpawnParticle(particle, location);
            }
        };
    }

}
