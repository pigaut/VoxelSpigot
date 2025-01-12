package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public class StrikePlayerWithLightning implements PlayerAction {

    private final boolean doDamage;

    public StrikePlayerWithLightning(boolean doDamage) {
        this.doDamage = doDamage;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        final World world = player.getWorld();
        final Location location = player.getLocation();

        if (doDamage) {
            world.strikeLightning(location);
        }
        else {
            world.strikeLightningEffect(location);
        }
    }

    public static ConfigLoader<StrikePlayerWithLightning> newConfigLoader() {
        return new ConfigLoader<StrikePlayerWithLightning>() {
            @Override
            public @NotNull String getProblemDescription() {
                return "Could not load action 'STRIKE_PLAYER'";
            }

            @Override
            public String getKey() {
                return "STRIKE_PLAYER";
            }

            @Override
            public @NotNull StrikePlayerWithLightning loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
                return new StrikePlayerWithLightning(scalar.toBoolean());
            }
        };
    }

}
