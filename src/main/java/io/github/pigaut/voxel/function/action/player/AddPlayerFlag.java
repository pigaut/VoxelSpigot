package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.meta.flag.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class AddPlayerFlag implements PlayerAction {

    private final List<Flag> flags;

    public AddPlayerFlag(Flag flag) {
        this.flags = List.of(flag);
    }

    public AddPlayerFlag(List<Flag> flags) {
        this.flags = flags;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        for (Flag flag : flags) {
            player.addFlag(flag);
        }
    }

    public static ConfigLoader<AddPlayerFlag> newConfigLoader() {
        return new ConfigLoader<AddPlayerFlag>() {
            @Override
            public @NotNull String getProblemDescription() {
                return "Could not load action 'ADD_FLAG'";
            }

            @Override
            public String getKey() {
                return "ADD_FLAG";
            }

            @Override
            public @NotNull AddPlayerFlag loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
                return new AddPlayerFlag(scalar.load(Flag.class));
            }

            @Override
            public @NotNull AddPlayerFlag loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
                return new AddPlayerFlag(sequence.toList(Flag.class));
            }
        };
    }
}
