package io.github.pigaut.voxel.player.input;

import io.github.pigaut.voxel.menu.*;
import io.github.pigaut.voxel.player.*;
import org.jetbrains.annotations.*;

import java.util.function.*;

public abstract class PlayerInput {

    protected final SimplePlayerState playerState;
    protected @NotNull String inputDescription;
    protected @NotNull Consumer<String> inputCollector = input -> {};
    protected @NotNull Runnable onCancel = () -> {};

    public PlayerInput(@NotNull SimplePlayerState player, @NotNull String defaultDescription) {
        this.playerState = player;
        this.inputDescription = defaultDescription;
    }

    public PlayerInput withDescription(@NotNull String inputDescription) {
        this.inputDescription = inputDescription;
        return this;
    }

    public PlayerInput onInput(@NotNull Consumer<String> inputCollector) {
        this.inputCollector = inputCollector;
        return this;
    }

    public PlayerInput onCancel(@NotNull Runnable onCancel) {
        this.onCancel = onCancel;
        return this;
    }

    public abstract void collect();

}
