package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.jetbrains.annotations.*;

public class DamagePlayer implements PlayerAction {

    private final int health;

    public DamagePlayer(int health) {
        this.health = -health;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        player.heal(health);
    }

    public static ConfigLoader<DamagePlayer> newConfigLoader() {
        return new ConfigLoader<DamagePlayer>() {
            @Override
            public @NotNull String getProblemDescription() {
                return "Could not load action 'DAMAGE'";
            }

            @Override
            public String getKey() {
                return "DAMAGE";
            }

            @Override
            public @NotNull DamagePlayer loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
                return new DamagePlayer(scalar.toInteger());
            }
        };
    }

}
