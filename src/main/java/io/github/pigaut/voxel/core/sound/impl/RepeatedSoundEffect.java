package io.github.pigaut.voxel.core.sound.impl;

import io.github.pigaut.voxel.core.sound.*;
import io.github.pigaut.yaml.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

public class RepeatedSoundEffect implements SoundEffect {

    private final SoundEffect sound;
    private final int repetitions;

    public RepeatedSoundEffect(SoundEffect sound, int repetitions) {
        this.sound = sound;
        this.repetitions = repetitions;
    }

    @Override
    public @NotNull String getName() {
        return sound.getName();
    }

    @Override
    public @Nullable String getGroup() {
        return sound.getGroup();
    }

    @Override
    public @NotNull ConfigField getField() {
        return sound.getField();
    }

    @Override
    public void play(@Nullable Player player, @NotNull Location location) {
        for (int i = 0; i < repetitions; i++) {
            sound.play(player, location);
        }
    }

}
