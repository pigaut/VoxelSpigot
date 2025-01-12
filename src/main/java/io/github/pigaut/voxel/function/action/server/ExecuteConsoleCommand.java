package io.github.pigaut.voxel.function.action.server;

import io.github.pigaut.voxel.function.action.player.PlayerAction;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class ExecuteConsoleCommand implements ServerAction {

    private final List<String> commands;

    public ExecuteConsoleCommand(List<String> commands) {
        this.commands = commands;
    }

    @Override
    public void execute() {
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        for (String command : commands) {
            Bukkit.dispatchCommand(console, command);
        }
    }

    public static ConfigLoader<ExecuteConsoleCommand> newConfigLoader() {
        return new ConfigLoader<ExecuteConsoleCommand>() {
            @Override
            public @NotNull String getProblemDescription() {
                return "Could not load action 'CONSOLE_COMMAND'";
            }

            @Override
            public String getKey() {
                return "CONSOLE_COMMAND";
            }

            @Override
            public @NotNull ExecuteConsoleCommand loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
                return new ExecuteConsoleCommand(List.of(scalar.toString()));
            }

            @Override
            public @NotNull ExecuteConsoleCommand loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
                return new ExecuteConsoleCommand(sequence.toStringList());
            }
        };
    }
}
