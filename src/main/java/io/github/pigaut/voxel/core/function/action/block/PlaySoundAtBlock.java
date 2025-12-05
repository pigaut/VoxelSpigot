package io.github.pigaut.voxel.core.function.action.block;

import io.github.pigaut.voxel.core.function.action.*;
import io.github.pigaut.voxel.core.sound.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

public class PlaySoundAtBlock implements Action {

    private final SoundEffect sound;

    public PlaySoundAtBlock(SoundEffect sound) {
        this.sound = sound;
    }

    @Override
    public void execute(@Nullable PlayerState player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        if (block != null) {
            sound.play(player != null ? player.asPlayer() : null, block.getLocation());
        }
    }

}
