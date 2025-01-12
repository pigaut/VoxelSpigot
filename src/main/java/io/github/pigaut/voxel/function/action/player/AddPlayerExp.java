package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.jetbrains.annotations.*;

public class AddPlayerExp implements PlayerAction {

    private final int amount;

    public AddPlayerExp(int amount) {
        this.amount = amount;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        player.addExp(amount);
    }

    public static ConfigLoader<AddPlayerExp> newConfigLoader() {
        return new ConfigLoader<AddPlayerExp>() {
            @Override
            public @NotNull String getProblemDescription() {
                return "Could not load action 'add exp'";
            }

            @Override
            public String getKey() {
                return "ADD_EXP";
            }

            @Override
            public @NotNull AddPlayerExp loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
                return new AddPlayerExp(scalar.toInteger());
            }
        };
    }

}
