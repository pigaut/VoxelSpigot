package io.github.pigaut.voxel.function.condition.player;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class PlayerHasFlag implements PlayerCondition {

    private final List<String> flags;

    public PlayerHasFlag(String flag) {
        this.flags = List.of(flag);
    }

    public PlayerHasFlag(List<String> flags) {
        this.flags = flags;
    }

    @Override
    public boolean isMet(@NotNull PluginPlayer player) {
        for (String flag : flags) {
            if (!player.hasFlag(flag)) {
                return false;
            }
        }
        return true;
    }

    public static ConfigLoader<PlayerHasFlag> newConfigLoader() {
        return new ConfigLoader<PlayerHasFlag>() {
            @Override
            public @NotNull String getProblemDescription() {
                return "Could not load condition 'HAS_FLAG'";
            }

            @Override
            public @Nullable String getKey() {
                return "HAS_FLAG";
            }

            @Override
            public @NotNull PlayerHasFlag loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
                return new PlayerHasFlag(scalar.toString());
            }

            @Override
            public @NotNull PlayerHasFlag loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
                return new PlayerHasFlag(sequence.toStringList());
            }
        };
    }
}
