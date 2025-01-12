package io.github.pigaut.voxel.function.condition.player;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class PlayerHasPermission implements PlayerCondition {

    private final List<String> permissions;

    public PlayerHasPermission(String permission) {
        this.permissions = List.of(permission);
    }

    public PlayerHasPermission(List<String> permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean isMet(@NotNull PluginPlayer player) {
        for (String permission : permissions) {
            if (!player.hasPermission(permission)) {
                return false;
            }
        }
        return true;
    }

    public static ConfigLoader<PlayerHasPermission> newConfigLoader() {
        return new ConfigLoader<PlayerHasPermission>() {
            @Override
            public @NotNull String getProblemDescription() {
                return "Could not load condition 'HAS_PERMISSION'";
            }

            @Override
            public String getKey() {
                return "HAS_PERMISSION";
            }

            @Override
            public @NotNull PlayerHasPermission loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
                return new PlayerHasPermission(scalar.toString());
            }

            @Override
            public @NotNull PlayerHasPermission loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
                return new PlayerHasPermission(sequence.toStringList());
            }
        };
    }
}
