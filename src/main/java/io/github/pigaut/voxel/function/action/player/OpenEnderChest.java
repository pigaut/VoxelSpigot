package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.jetbrains.annotations.*;

public class OpenEnderChest implements PlayerAction {

    @Override
    public void execute(@NotNull PluginPlayer player) {
        player.openEnderChest();
    }

    public static ConfigLoader<OpenEnderChest> newConfigLoader() {
        return new ConfigLoader<OpenEnderChest>() {
            @Override
            public @NotNull String getProblemDescription() {
                return "Could not load action 'OPEN_ENDER_CHEST'";
            }

            @Override
            public String getKey() {
                return "OPEN_ENDER_CHEST";
            }

            @Override
            public @NotNull OpenEnderChest loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
                return new OpenEnderChest();
            }
        };
    }

}
