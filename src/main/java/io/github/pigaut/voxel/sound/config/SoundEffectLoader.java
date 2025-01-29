package io.github.pigaut.voxel.sound.config;

import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.sound.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
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
        final String soundName = scalar.toString();
        final SoundEffect foundSound = plugin.getSound(soundName);
        if (foundSound == null) {
            throw new InvalidConfigurationException(scalar, "Could not find any sound effect with name: '" + soundName + "'");
        }
        return foundSound;
    }

    @Override
    public @NotNull SoundEffect loadFromSection(@NotNull ConfigSection section) throws InvalidConfigurationException {
        final Sound sound = section.get("sound", Sound.class);
        final float volume = section.getOptionalFloat("volume").orElse(1.0f);
        final float pitch = section.getOptionalFloat("pitch").orElse(1.0f);
        final double offsetX = section.getOptionalDouble("offset.x").orElse(0d);
        final double offsetY = section.getOptionalDouble("offset.y").orElse(0d);
        final double offsetZ = section.getOptionalDouble("offset.z").orElse(0d);
        final boolean playerOnly = section.getOptionalBoolean("player-only").orElse(false);
        return addSoundOptions(section, new SimpleSoundEffect(sound, volume, pitch, offsetX, offsetY, offsetZ, playerOnly));
    }

    @Override
    public @NotNull SoundEffect loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
        return new MultiSoundEffect(sequence.getAll(SoundEffect.class));
    }

    public SoundEffect addSoundOptions(ConfigSection section, SoundEffect sound) {
        final Integer repetitions = section.getOptionalInteger("repetitions|loops").orElse(null);
        if (repetitions != null) {
            sound = new RepeatedSoundEffect(sound, repetitions);
        }

        final Integer delay = section.getOptionalInteger("delay").orElse(null);
        if (delay != null) {
            sound = new DelayedSoundEffect(plugin, sound, delay);
        }

        return sound;
    }

}
