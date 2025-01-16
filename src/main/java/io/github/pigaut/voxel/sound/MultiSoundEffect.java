package io.github.pigaut.voxel.sound;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class MultiSoundEffect implements SoundEffect {

    private final List<SoundEffect> sounds;

    public MultiSoundEffect(@NotNull List<@NotNull SoundEffect> sounds) {
        this.sounds = sounds;
    }

    @Override
    public void play(@Nullable Player player, @NotNull Location location) {
        for (SoundEffect sound : sounds) {
            sound.play(player, location);
        }
    }

}
