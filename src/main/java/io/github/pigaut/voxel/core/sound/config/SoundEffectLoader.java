package io.github.pigaut.voxel.core.sound.config;

import io.github.pigaut.voxel.core.sound.*;
import io.github.pigaut.voxel.core.sound.impl.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.load.*;
import io.github.pigaut.yaml.convert.format.*;
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
        final String soundName = scalar.toString(CaseStyle.SNAKE);
        final SoundEffect foundSound = plugin.getSound(soundName);
        if (foundSound == null) {
            throw new InvalidConfigurationException(scalar, "Could not find sound effect with name: '" + soundName + "'");
        }
        return foundSound;
    }

    @Override
    public @NotNull SoundEffect loadFromSection(@NotNull ConfigSection section) throws InvalidConfigurationException {
        final String soundName = section.getKey();
        final String soundGroup = Group.bySoundFile(section.getRoot().getFile());
        final Sound sound = section.getRequired("sound", Sound.class);
        final float volume = section.getFloat("volume").throwOrElse(1.0f);
        final float pitch = section.getFloat("pitch").throwOrElse(1.0f);
        final double offsetX = section.getDouble("offset.x").throwOrElse(0d);
        final double offsetY = section.getDouble("offset.y").throwOrElse(0d);
        final double offsetZ = section.getDouble("offset.z").throwOrElse(0d);
        final boolean playerOnly = section.getBoolean("player-only").throwOrElse(false);

        SoundEffect soundEffect = new SimpleSoundEffect(soundName, soundGroup, sound, volume, pitch,
                offsetX, offsetY, offsetZ, playerOnly);

        final Integer repetitions = section.getInteger("repetitions|loops").throwOrElse(null);
        if (repetitions != null) {
            soundEffect = new RepeatedSoundEffect(soundEffect, repetitions);
        }

        final Integer delay = section.getInteger("delay").throwOrElse(null);
        if (delay != null) {
            soundEffect = new DelayedSoundEffect(plugin, soundEffect, delay);
        }

        return soundEffect;
    }

    @Override
    public @NotNull SoundEffect loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
        final String soundName = sequence.getKey();
        final String soundGroup = Group.bySoundFile(sequence.getRoot().getFile());
        return new MultiSoundEffect(soundName, soundGroup, sequence, sequence.getAll(SoundEffect.class));
    }

}
