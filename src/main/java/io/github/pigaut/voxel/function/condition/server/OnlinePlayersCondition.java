package io.github.pigaut.voxel.function.condition.server;

import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public class OnlinePlayersCondition implements ServerCondition {

    private final int onlinePlayers;

    public OnlinePlayersCondition(int onlinePlayers) {
        this.onlinePlayers = onlinePlayers;
    }

    @Override
    public boolean isMet() {
        return Bukkit.getOnlinePlayers().size() == onlinePlayers;
    }

    public static ConfigLoader<OnlinePlayersCondition> newConfigLoader() {
        return new ConfigLoader<OnlinePlayersCondition>() {
            @Override
            public @NotNull String getProblemDescription() {
                return "Could not load condition 'ONLINE_PLAYERS'";
            }

            @Override
            public String getKey() {
                return "ONLINE_PLAYERS";
            }

            @Override
            public @NotNull OnlinePlayersCondition loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
                return new OnlinePlayersCondition(scalar.toInteger());
            }
        };
    }

}
