package io.github.pigaut.voxel.core.particle.config;

import io.github.pigaut.voxel.core.particle.*;
import io.github.pigaut.voxel.core.particle.impl.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import io.github.pigaut.yaml.parser.*;
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
        final String particleName = scalar.toString(StringStyle.SNAKE);
        final ParticleEffect foundParticle = plugin.getParticle(particleName);
        if (foundParticle == null) {
            throw new InvalidConfigurationException(scalar, "Could not find any particle effect with name: '" + particleName + "'");
        }
        return foundParticle;
    }

    @Override
    public @NotNull ParticleEffect loadFromSection(@NotNull ConfigSection section) throws InvalidConfigurationException {
        final String particleName = section.getKey();
        final String particleGroup = PathGroup.byParticleFile(section.getRoot().getFile());
        final Particle particle = section.get("particle", Particle.class);
        final int count = section.getOptionalInteger("count|amount").orElse(1);
        final double rangeX = section.getOptionalDouble("range.x").orElse(0d);
        final double rangeY = section.getOptionalDouble("range.y").orElse(0d);
        final double rangeZ = section.getOptionalDouble("range.z").orElse(0d);
        final boolean force = section.getOptionalBoolean("force").orElse(false);
        final boolean playerOnly = section.getOptionalBoolean("player-only").orElse(false);

        ParticleEffect particleEffect = new SimpleParticle(particleName, particleGroup, section, particle, count,
                rangeX, rangeY, rangeZ, force, playerOnly);

        final double offsetX = section.getOptionalDouble("offset.x").orElse(0d);
        final double offsetY = section.getOptionalDouble("offset.y").orElse(0d);
        final double offsetZ = section.getOptionalDouble("offset.z").orElse(0d);
        if (offsetX != 0 || offsetY != 0 || offsetZ != 0) {
            particleEffect = new OffsetParticle(particleEffect, offsetX, offsetY, offsetZ);
        }

        final Integer repetitions = section.getOptionalInteger("repetitions|loops").orElse(null);
        final Integer interval = section.getOptionalInteger("interval|period").orElse(null);

        if (repetitions != null && repetitions < 1) {
            throw new InvalidConfigurationException(section, "repetitions", "The particle repetitions must be greater than 0");
        }

        if (interval != null) {
            if (repetitions == null) {
                throw new InvalidConfigurationException(section, "interval", "The 'repetitions' option must be set to use interval delay");
            }
            if (interval < 1) {
                throw new InvalidConfigurationException(section, "interval", "The particle interval must be greater than 0");
            }
            particleEffect = new PeriodicalParticleEffect(plugin, particleEffect, interval, repetitions);
        }

        else if (repetitions != null) {
            particleEffect = new RepeatedParticleEffect(particleEffect, repetitions);
        }

        final Integer delay = section.getOptionalInteger("delay").orElse(null);
        if (delay != null) {
            if (delay < 1) {
                throw new InvalidConfigurationException(section, "delay", "The particle delay must be greater than 0");
            }
            particleEffect = new DelayedParticleEffect(plugin, particleEffect, delay);
        }

        return particleEffect;
    }

    @Override
    public @NotNull ParticleEffect loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
        final String particleName = sequence.getKey();
        final String particleGroup = PathGroup.byParticleFile(sequence.getRoot().getFile());
        return new MultiParticleEffect(particleName, particleGroup, sequence, sequence.getAll(ParticleEffect.class));
    }

}
