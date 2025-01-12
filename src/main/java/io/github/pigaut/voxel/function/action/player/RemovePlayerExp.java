package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.jetbrains.annotations.*;

public class RemovePlayerExp implements PlayerAction {

    private final int amount;

    public RemovePlayerExp(int amount) {
        this.amount = amount;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        player.addExp(amount);
    }

    public static ConfigLoader<RemovePlayerExp> newConfigLoader() {
        return new ConfigLoader<RemovePlayerExp>() {
            @Override
            public @NotNull String getProblemDescription() {
                return "Could not load action 'remove exp'";
            }

            @Override
            public String getKey() {
                return "REMOVE_EXP";
            }

            @Override
            public @NotNull RemovePlayerExp loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
                return new RemovePlayerExp(scalar.toInteger());
            }
        };
    }

}
