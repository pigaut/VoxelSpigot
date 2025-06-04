package io.github.pigaut.voxel.core.function.action.server;

import io.github.pigaut.voxel.core.function.action.*;
import io.github.pigaut.voxel.core.sound.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

public class PlaySound implements Action {

    private final SoundEffect sound;
    private final Location location;

    public PlaySound(SoundEffect sound, Location location) {
        this.sound = sound;
        this.location = location;
    }

    @Override
    public void execute(@Nullable PlayerState player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        sound.play(player != null ? player.asPlayer() : null, location);
    }

}
