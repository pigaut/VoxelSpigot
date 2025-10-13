package io.github.pigaut.voxel.core.sound.config;

import io.github.pigaut.voxel.core.sound.*;
import io.github.pigaut.voxel.core.sound.impl.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.load.*;
import io.github.pigaut.yaml.convert.format.*;
import io.github.pigaut.yaml.util.*;
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
        final float volume = section.getFloat("volume").withDefault(1.0f);
        final float pitch = section.getFloat("pitch").withDefault(1.0f);
        final double offsetX = section.getDouble("offset.x").withDefault(0d);
        final double offsetY = section.getDouble("offset.y").withDefault(0d);
        final double offsetZ = section.getDouble("offset.z").withDefault(0d);
        final boolean playerOnly = section.getBoolean("player-only").withDefault(false);

        SoundEffect soundEffect = new SimpleSound(soundName, soundGroup, sound, volume, pitch,
                offsetX, offsetY, offsetZ, playerOnly);

        Integer repetitions = section.getInteger("repeat|repetitions")
                .filter(Predicates.isPositive(), "Repetitions must be greater than 0")
                .withDefault(null);

        Integer interval = section.get("interval|period", Ticks.class)
                .filter(repetitions != null, "Repetitions must be set to use interval delay")
                .map(Ticks::getCount)
                .withDefault(null);

        if (interval != null) {
            soundEffect = new PeriodicSound(plugin, soundEffect, interval, repetitions);
        }
        else if (repetitions != null) {
            soundEffect = new RepeatedSound(soundEffect, repetitions);
        }

        Integer delay = section.get("delay", Ticks.class)
                .map(Ticks::getCount)
                .withDefault(null);

        if (delay != null) {
            soundEffect = new DelayedSound(plugin, soundEffect, delay);
        }

        return soundEffect;
    }

    @Override
    public @NotNull SoundEffect loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
        final String soundName = sequence.getKey();
        final String soundGroup = Group.bySoundFile(sequence.getRoot().getFile());
        return new MultiSound(soundName, soundGroup, sequence, sequence.getAll(SoundEffect.class));
    }

}
