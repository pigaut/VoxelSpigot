package io.github.pigaut.voxel.core.message;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.manager.*;
import org.jetbrains.annotations.*;

public interface Message extends Identifiable {

    @NotNull MessageType getType();

    void send(@NotNull PlayerState playerState);

}
