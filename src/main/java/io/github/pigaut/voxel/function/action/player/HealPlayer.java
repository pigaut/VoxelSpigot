package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.jetbrains.annotations.*;

public class HealPlayer implements PlayerAction {

    private final int health;

    public HealPlayer(int health) {
        this.health = health;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        player.heal(health);
    }

    public static ConfigLoader<HealPlayer> newConfigLoader() {
        return new ConfigLoader<HealPlayer>() {
            @Override
            public @NotNull String getProblemDescription() {
                return "Could not load action 'HEAL'";
            }

            @Override
            public String getKey() {
                return "HEAL";
            }

            @Override
            public @NotNull HealPlayer loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
                return new HealPlayer(scalar.toInteger());
            }
        };
    }

}
