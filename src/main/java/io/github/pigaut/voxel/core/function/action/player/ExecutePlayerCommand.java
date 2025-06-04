package io.github.pigaut.voxel.core.function.action.player;

import io.github.pigaut.voxel.player.*;
import org.jetbrains.annotations.*;

public class ExecutePlayerCommand implements PlayerAction {

    private final String command;

    public ExecutePlayerCommand(String command) {
        this.command = command;
    }

    @Override
    public void execute(@NotNull PlayerState player) {
        player.performCommand(command);
    }

}
