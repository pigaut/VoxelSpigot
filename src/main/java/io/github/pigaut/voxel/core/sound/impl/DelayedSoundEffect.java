package io.github.pigaut.voxel.core.sound.impl;

import io.github.pigaut.voxel.core.sound.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

public class DelayedSoundEffect implements SoundEffect {

    private final EnhancedPlugin plugin;
    private final SoundEffect sound;
    private final int delay;

    public DelayedSoundEffect(EnhancedPlugin plugin, SoundEffect sound, int delay) {
        this.plugin = plugin;
        this.sound = sound;
        this.delay = delay;
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
        plugin.getScheduler().runTaskLater(delay, () -> {
            sound.play(player, location);
        });
    }

}
