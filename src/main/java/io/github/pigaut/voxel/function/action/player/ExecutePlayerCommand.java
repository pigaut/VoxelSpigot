package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class ExecutePlayerCommand implements PlayerAction {

    private final List<String> commands;

    public ExecutePlayerCommand(String command) {
        this.commands = List.of(command);
    }

    public ExecutePlayerCommand(List<String> commands) {
        this.commands = commands;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        for (String command : commands) {
            player.performCommand(command);
        }
    }

    public static ConfigLoader<ExecutePlayerCommand> newConfigLoader() {
        return new ConfigLoader<ExecutePlayerCommand>() {
            @Override
            public @NotNull String getProblemDescription() {
                return "Could not load action 'COMMAND'";
            }

            @Override
            public String getKey() {
                return "COMMAND";
            }

            @Override
            public @NotNull ExecutePlayerCommand loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
                return new ExecutePlayerCommand(scalar.toString());
            }

            @Override
            public @NotNull ExecutePlayerCommand loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
                return new ExecutePlayerCommand(sequence.toStringList());
            }
        };
    }
}
