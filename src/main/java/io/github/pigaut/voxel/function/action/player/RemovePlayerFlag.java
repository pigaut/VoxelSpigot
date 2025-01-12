package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class RemovePlayerFlag implements PlayerAction {

    private final List<String> flags;

    public RemovePlayerFlag(String flag) {
        this.flags = List.of(flag);
    }

    public RemovePlayerFlag(List<String> flags) {
        this.flags = flags;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        for (String flag : flags) {
            player.removeFlag(flag);
        }
    }

    public static ConfigLoader<RemovePlayerFlag> newConfigLoader() {
        return new ConfigLoader<RemovePlayerFlag>() {
            @Override
            public @NotNull String getProblemDescription() {
                return "Could not load action 'REMOVE_FLAG'";
            }

            @Override
            public String getKey() {
                return "REMOVE_FLAG";
            }

            @Override
            public @NotNull RemovePlayerFlag loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
                return new RemovePlayerFlag(scalar.toString());
            }

            @Override
            public @NotNull RemovePlayerFlag loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
                return new RemovePlayerFlag(sequence.toStringList());
            }
        };
    }
}
