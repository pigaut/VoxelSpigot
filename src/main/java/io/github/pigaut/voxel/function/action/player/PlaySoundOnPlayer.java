package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.sound.*;
import org.jetbrains.annotations.*;

public class PlaySoundOnPlayer implements PlayerAction {

    private final SoundEffect sound;

    public PlaySoundOnPlayer(@NotNull SoundEffect sound) {
        this.sound = sound;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        sound.play(player.asPlayer(), player.getLocation());
    }

}
