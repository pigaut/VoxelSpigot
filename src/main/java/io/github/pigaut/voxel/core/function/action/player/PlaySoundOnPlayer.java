package io.github.pigaut.voxel.core.function.action.player;

import io.github.pigaut.voxel.core.sound.*;
import io.github.pigaut.voxel.player.*;
import org.jetbrains.annotations.*;

public class PlaySoundOnPlayer implements PlayerAction {

    private final SoundEffect sound;

    public PlaySoundOnPlayer(@NotNull SoundEffect sound) {
        this.sound = sound;
    }

    @Override
    public void execute(@NotNull PlayerState player) {
        sound.play(player.asPlayer(), player.getLocation());
    }

}
