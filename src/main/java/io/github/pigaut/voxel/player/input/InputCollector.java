package io.github.pigaut.voxel.player.input;

import io.github.pigaut.voxel.player.*;
import org.jetbrains.annotations.*;

public interface InputCollector {

    @NotNull InputSource getInputSource();

    @NotNull PlayerState getPlayerState();

    void start();

    void cancel();

    boolean isCollecting();

    void accept(@NotNull String input);

}
