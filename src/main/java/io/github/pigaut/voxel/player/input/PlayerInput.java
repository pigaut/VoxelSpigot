package io.github.pigaut.voxel.player.input;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.configurator.deserialize.*;
import org.jetbrains.annotations.*;

import java.util.function.*;

public abstract class PlayerInput<T> {

    protected final SimplePlayerState playerState;
    protected final @NotNull Deserializer<T> deserializer;
    protected @NotNull String inputDescription;
    protected @NotNull Consumer<T> inputCollector = input -> {};
    protected @NotNull Runnable onCancel = () -> {};

    public PlayerInput(@NotNull SimplePlayerState player, @NotNull Deserializer<T> deserializer, String defaultDescription) {
        this.playerState = player;
        this.inputDescription = defaultDescription;
        this.deserializer = deserializer;
    }

    public PlayerInput<T> withDescription(@NotNull String inputDescription) {
        this.inputDescription = inputDescription;
        return this;
    }

    public PlayerInput<T> withInputCollector(@NotNull Consumer<T> inputCollector) {
        this.inputCollector = inputCollector;
        return this;
    }

    public PlayerInput<T> withCancelTask(@NotNull Runnable onCancel) {
        this.onCancel = onCancel;
        return this;
    }

    public abstract void collect();

}
