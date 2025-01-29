package io.github.pigaut.voxel.particle.config;

import io.github.pigaut.voxel.particle.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public class ParticleEffectLoader implements ConfigLoader<ParticleEffect> {

    private final EnhancedPlugin plugin;

    public ParticleEffectLoader(EnhancedPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getProblemDescription() {
        return "invalid particle effect";
    }

    @Override
    public @NotNull ParticleEffect loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
        final String particleName = scalar.toString();
        final ParticleEffect foundParticle = plugin.getParticle(particleName);
        if (foundParticle == null) {
            throw new InvalidConfigurationException(scalar, "Could not find any particle effect with name: '" + particleName + "'");
        }
        return foundParticle;
    }

    @Override
    public @NotNull ParticleEffect loadFromSection(@NotNull ConfigSection section) throws InvalidConfigurationException {
        final Particle particle = section.get("particle", Particle.class);
        final int count = section.getOptionalInteger("count|amount").orElse(1);
        final double rangeX = section.getOptionalDouble("range.x").orElse(0d);
        final double rangeY = section.getOptionalDouble("range.y").orElse(0d);
        final double rangeZ = section.getOptionalDouble("range.z").orElse(0d);
        final boolean force = section.getOptionalBoolean("force").orElse(false);
        final boolean playerOnly = section.getOptionalBoolean("player-only").orElse(false);

        ParticleEffect particleEffect = new SimpleParticle(particle, count, rangeX, rangeY, rangeZ, force, playerOnly);

        final double offsetX = section.getOptionalDouble("offset.x").orElse(0d);
        final double offsetY = section.getOptionalDouble("offset.y").orElse(0d);
        final double offsetZ = section.getOptionalDouble("offset.z").orElse(0d);
        if (offsetX != 0 || offsetY != 0 || offsetZ != 0) {
            particleEffect = new OffsetParticle(particleEffect, offsetX, offsetY, offsetZ);
        }

        final Integer repetitions = section.getOptionalInteger("repetitions|loops").orElse(null);
        if (repetitions != null) {
            particleEffect = new RepeatedParticleEffect(particleEffect, repetitions);
        }

        final Integer delay = section.getOptionalInteger("delay").orElse(null);
        if (delay != null) {
            particleEffect = new DelayedParticleEffect(plugin, particleEffect, delay);
        }

        return particleEffect;
    }

    @Override
    public @NotNull ParticleEffect loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
        return new MultiParticleEffect(sequence.getAll(ParticleEffect.class));
    }

}
