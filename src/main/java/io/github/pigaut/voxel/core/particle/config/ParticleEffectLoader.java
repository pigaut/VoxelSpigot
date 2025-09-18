package io.github.pigaut.voxel.core.particle.config;

import com.cryptomorin.xseries.particles.*;
import io.github.pigaut.voxel.core.particle.*;
import io.github.pigaut.voxel.core.particle.impl.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.voxel.server.*;
import io.github.pigaut.voxel.util.*;
import io.github.pigaut.voxel.version.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.amount.*;
import io.github.pigaut.yaml.configurator.load.*;
import io.github.pigaut.yaml.convert.format.*;
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
        final String particleName = scalar.toString(CaseStyle.SNAKE);
        final ParticleEffect foundParticle = plugin.getParticle(particleName);
        if (foundParticle == null) {
            throw new InvalidConfigurationException(scalar, "Could not find any particle effect with name: '" + particleName + "'");
        }
        return foundParticle;
    }

    @Override
    public @NotNull ParticleEffect loadFromSection(@NotNull ConfigSection section) throws InvalidConfigurationException {
        final String particleName = section.getKey();
        final String particleGroup = Group.byParticleFile(section.getRoot().getFile());

        final String type = section.getString("type", CaseStyle.CONSTANT).throwOrElse("BASIC");
        final Particle particle = section.get("particle", Particle.class).throwOrElse(XParticle.EXPLOSION.get());
        final Amount amount = section.get("count|amount", Amount.class).throwOrElse(Amount.fixed(1));
        final BlockRange range = section.get("range", BlockRange.class).throwOrElse(BlockRange.ZERO);
        final boolean playerOnly = section.getBoolean("player-only").throwOrElse(false);

        ParticleEffect particleEffect;
        switch (type) {
            case "BASIC" -> {
                particleEffect = new GenericParticle(particleName, particleGroup,
                        particle, amount, range, playerOnly);
            }

            case "DUST" -> {
                final Amount red = section.get("color.red|r", Amount.class).throwOrElse(Amount.ZERO);
                final Amount green = section.get("color.green|g", Amount.class).throwOrElse(Amount.ZERO);
                final Amount blue = section.get("color.blue|b", Amount.class).throwOrElse(Amount.ZERO);
                final Amount size = section.get("size", Amount.class).throwOrElse(Amount.fixed(1));
                final boolean uniform = section.getBoolean("uniform|iso").throwOrElse(true);
                particleEffect = new DustParticle(particleName, particleGroup, amount, range, playerOnly,
                        red, green, blue, size, uniform);
            }

            case "DIRECTIONAL" -> {
                if (!ParticleType.DIRECTIONAL.contains(particle)) {
                    throw new InvalidConfigurationException(section, "particle", "Particle is not directional and cannot be rotated");
                }

                final BlockRange direction = section.get("direction", BlockRange.class).throwOrElse(BlockRange.ZERO);
                final Amount speed = section.get("speed", Amount.class).orElse(Amount.fixed(1));
                particleEffect = new DirectionalParticle(particleName, particleGroup, particle, amount,
                        direction, playerOnly, speed);
            }

            case "SPELL" -> {
                if (!ParticleType.SPELLS.contains(particle)) {
                    throw new InvalidConfigurationException(section, "particle", "Particle is not a spell and cannot be colored");
                }

                if (SpigotServer.getVersion().equalsOrIsNewerThan(SpigotVersion.V1_20_6)) {
                    final Amount red = section.get("color.red", Amount.class).orElse(Amount.ZERO);
                    final Amount green = section.get("color.green", Amount.class).orElse(Amount.ZERO);
                    final Amount blue = section.get("color.blue", Amount.class).orElse(Amount.ZERO);
                    final Amount size = section.get("size", Amount.class).orElse(Amount.fixed(1));
                    final boolean uniform = section.getBoolean("uniform|iso").orElse(true);
                    particleEffect = new SpellParticle(particleName, particleGroup, particle, amount,
                            range, playerOnly, red, green, blue, size, uniform);
                }
                else {
                    final Amount red = section.get("color.red", Amount.class).orElse(Amount.ZERO);
                    final Amount green = section.get("color.green", Amount.class).orElse(Amount.ZERO);
                    final Amount blue = section.get("color.blue", Amount.class).orElse(Amount.ZERO);
                    particleEffect = new SpellParticle.Legacy(particleName, particleGroup, particle, amount, playerOnly,
                            red.transform(value -> value / 255D), green.transform(value -> value / 255D), blue.transform(value -> value / 255D));
                }
            }

            case "NOTE" -> {
                final Amount note = section.get("note", Amount.class).orElse(Amount.fixed(6))
                        .transform(value -> value / 24D);
                particleEffect = new NoteParticle(particleName, particleGroup, amount, playerOnly, note);
            }

            case "MATERIAL" -> {
                final Material material = section.get("block|item", Material.class).orElse(Material.STONE);
                if (particle.equals(XParticle.ITEM.get())) {
                    particleEffect = new ItemParticle(particleName, particleGroup,
                            amount, range, playerOnly, material);
                }
                else {
                    particleEffect = new BlockParticle(particleName, particleGroup,
                            particle, amount, range, playerOnly, material);
                }
            }

            case "DUST_TRANSITION" -> {
                if (SpigotServer.getVersion().isOlderThan(SpigotVersion.V1_20_6)) {
                    throw new InvalidConfigurationException(section, "type", "Dust Transition can be used only in 1.20.6+");
                }
                final Color startColor = section.get("start-color", Color.class).orElse(Color.RED);
                final Color endColor = section.get("end-color", Color.class).orElse(Color.WHITE);
                final Amount size = section.get("size", Amount.class).orElse(Amount.fixed(1));
                particleEffect = new DustTransitionParticle(particleName, particleGroup,
                        amount, range, playerOnly, startColor, endColor, size);
            }

            default -> throw new InvalidConfigurationException(section, "type", "Found unknown particle type: '" + type + "'");
        }

        final Amount offsetX = section.get("offset.x", Amount.class).orElse(Amount.ZERO);
        final Amount offsetY = section.get("offset.y", Amount.class).orElse(Amount.ZERO);
        final Amount offsetZ = section.get("offset.z", Amount.class).orElse(Amount.ZERO);
        if (offsetX != Amount.ZERO || offsetY != Amount.ZERO || offsetZ != Amount.ZERO) {
            particleEffect = new OffsetParticle(particleEffect, offsetX, offsetY, offsetZ);
        }

        final Integer repetitions = section.getInteger("repetitions|loops").orElse(null);
        final Integer interval = section.getInteger("interval|period").orElse(null);

        if (repetitions != null && repetitions < 1) {
            throw new InvalidConfigurationException(section, "repetitions", "The particle repetitions must be greater than 0");
        }

        if (interval != null) {
            if (repetitions == null) {
                throw new InvalidConfigurationException(section, "interval", "The 'repetitions' option must be set to use interval delay");
            }
            if (interval > 0) {
                particleEffect = new PeriodicalParticleEffect(plugin, particleEffect, interval, repetitions);
            }
        }
        else if (repetitions != null) {
            particleEffect = new RepeatedParticleEffect(particleEffect, repetitions);
        }

        final Integer delay = section.getInteger("delay").orElse(null);
        if (delay != null && delay > 0) {
            particleEffect = new DelayedParticleEffect(plugin, particleEffect, delay);
        }

        return particleEffect;
    }

    @Override
    public @NotNull ParticleEffect loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
        final String particleName = sequence.getKey();
        final String particleGroup = Group.byParticleFile(sequence.getRoot().getFile());
        return new MultiParticleEffect(particleName, particleGroup, sequence, sequence.getAll(ParticleEffect.class));
    }

}
