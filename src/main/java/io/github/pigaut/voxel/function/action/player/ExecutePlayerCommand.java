package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class ExecutePlayerCommand implements PlayerAction {

    private final String command;

    public ExecutePlayerCommand(String command) {
        this.command = command;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        player.performCommand(command);
    }

}
