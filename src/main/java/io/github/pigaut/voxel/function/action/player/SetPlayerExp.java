package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.jetbrains.annotations.*;

public class SetPlayerExp implements PlayerAction {

    private final int amount;

    public SetPlayerExp(int amount) {
        this.amount = amount;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        player.setExp(amount);
    }

    public static ConfigLoader<SetPlayerExp> newConfigLoader() {
        return new ConfigLoader<SetPlayerExp>() {
            @Override
            public @NotNull String getProblemDescription() {
                return "Could not load action 'SET_EXP'";
            }

            @Override
            public String getKey() {
                return "SET_EXP";
            }

            @Override
            public @NotNull SetPlayerExp loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
                return new SetPlayerExp(scalar.toInteger());
            }
        };
    }

}
