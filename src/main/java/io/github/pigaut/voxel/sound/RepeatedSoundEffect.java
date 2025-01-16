package io.github.pigaut.voxel.sound;

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
    public void play(@Nullable Player player, @NotNull Location location) {
        for (int i = 0; i < repetitions; i++) {
            sound.play(player, location);
        }
    }

}
