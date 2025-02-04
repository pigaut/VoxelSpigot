package io.github.pigaut.voxel.function.action.block;

import io.github.pigaut.voxel.function.action.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.sound.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

public class PlaySoundOnBlock implements Action {

    private final SoundEffect sound;

    public PlaySoundOnBlock(SoundEffect sound) {
        this.sound = sound;
    }

    @Override
    public void execute(@Nullable PluginPlayer player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        if (block != null) {
            sound.play(player != null ? player.asPlayer() : null, block.getLocation());
        }
    }

}
