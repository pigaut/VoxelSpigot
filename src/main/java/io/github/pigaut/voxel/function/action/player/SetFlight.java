package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.jetbrains.annotations.*;

public class SetFlight implements PlayerAction {

    private final boolean flight;

    public SetFlight(boolean flight) {
        this.flight = flight;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        player.asPlayer().setAllowFlight(flight);
    }

    public static ConfigLoader<SetFlight> newConfigLoader() {
        return new ConfigLoader<SetFlight>() {
            @Override
            public @NotNull String getProblemDescription() {
                return "Could not load action 'set flight'";
            }

            @Override
            public @Nullable String getKey() {
                return "SET_FLIGHT";
            }

            @Override
            public @NotNull SetFlight loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
                return new SetFlight(scalar.toBoolean());
            }
        };
    }

}
