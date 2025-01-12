package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class ExecuteOpCommand implements PlayerAction {

    private final List<String> commands;

    public ExecuteOpCommand(List<String> commands) {
        this.commands = commands;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        for (String command : commands) {
            player.performCommandAsOp(command);
        }
    }

    public static ConfigLoader<ExecuteOpCommand> newConfigLoader() {
        return new ConfigLoader<ExecuteOpCommand>() {
            @Override
            public @NotNull String getProblemDescription() {
                return "Could not load action 'OP_COMMAND'";
            }

            @Override
            public String getKey() {
                return "OP_COMMAND";
            }

            @Override
            public @NotNull ExecuteOpCommand loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
                return new ExecuteOpCommand(List.of(scalar.toString()));
            }

            @Override
            public @NotNull ExecuteOpCommand loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
                return new ExecuteOpCommand(sequence.toStringList());
            }
        };
    }
}
