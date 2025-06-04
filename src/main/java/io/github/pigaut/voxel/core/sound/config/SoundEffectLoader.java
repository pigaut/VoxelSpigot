package io.github.pigaut.voxel.core.sound.config;

import io.github.pigaut.voxel.core.sound.*;
import io.github.pigaut.voxel.core.sound.impl.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import io.github.pigaut.yaml.parser.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public class SoundEffectLoader implements ConfigLoader<SoundEffect> {

    private final EnhancedPlugin plugin;

    public SoundEffectLoader(EnhancedPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getProblemDescription() {
        return "invalid sound effect";
    }

    @Override
    public @NotNull SoundEffect loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
        final String soundName = scalar.toString(StringStyle.SNAKE);
        final SoundEffect foundSound = plugin.getSound(soundName);
        if (foundSound == null) {
            throw new InvalidConfigurationException(scalar, "Could not find sound effect with name: '" + soundName + "'");
        }
        return foundSound;
    }

    @Override
    public @NotNull SoundEffect loadFromSection(@NotNull ConfigSection section) throws InvalidConfigurationException {
        final String soundName = section.getKey();
        final String soundGroup = PathGroup.bySoundFile(section.getRoot().getFile());
        final Sound sound = section.get("sound", Sound.class);
        final float volume = section.getOptionalFloat("volume").orElse(1.0f);
        final float pitch = section.getOptionalFloat("pitch").orElse(1.0f);
        final double offsetX = section.getOptionalDouble("offset.x").orElse(0d);
        final double offsetY = section.getOptionalDouble("offset.y").orElse(0d);
        final double offsetZ = section.getOptionalDouble("offset.z").orElse(0d);
        final boolean playerOnly = section.getOptionalBoolean("player-only").orElse(false);

        SoundEffect soundEffect = new SimpleSoundEffect(soundName, soundGroup, section, sound, volume, pitch,
                offsetX, offsetY, offsetZ, playerOnly);

        final Integer repetitions = section.getOptionalInteger("repetitions|loops").orElse(null);
        if (repetitions != null) {
            soundEffect = new RepeatedSoundEffect(soundEffect, repetitions);
        }

        final Integer delay = section.getOptionalInteger("delay").orElse(null);
        if (delay != null) {
            soundEffect = new DelayedSoundEffect(plugin, soundEffect, delay);
        }

        return soundEffect;
    }

    @Override
    public @NotNull SoundEffect loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
        final String soundName = sequence.getKey();
        final String soundGroup = PathGroup.bySoundFile(sequence.getRoot().getFile());
        return new MultiSoundEffect(soundName, soundGroup, sequence, sequence.getAll(SoundEffect.class));
    }

}
