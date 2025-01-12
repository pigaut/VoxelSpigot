package io.github.pigaut.voxel.function.action.block;

import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.jetbrains.annotations.*;

public class StrikeBlockWithLightning implements BlockAction {

    private final boolean doDamage;

    public StrikeBlockWithLightning(boolean doDamage) {
        this.doDamage = doDamage;
    }

    @Override
    public void execute(@NotNull Block block) {
        final World world = block.getWorld();
        final Location location = block.getLocation();

        if (doDamage) {
            world.strikeLightning(location);
        }
        else {
            world.strikeLightningEffect(location);
        }
    }

    public static ConfigLoader<StrikeBlockWithLightning> newConfigLoader() {
        return new ConfigLoader<StrikeBlockWithLightning>() {
            @Override
            public @NotNull String getProblemDescription() {
                return "Could not load action 'STRIKE_BLOCK'";
            }

            @Override
            public String getKey() {
                return "STRIKE_BLOCK";
            }

            @Override
            public @NotNull StrikeBlockWithLightning loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
                return new StrikeBlockWithLightning(scalar.toBoolean());
            }
        };
    }

}
