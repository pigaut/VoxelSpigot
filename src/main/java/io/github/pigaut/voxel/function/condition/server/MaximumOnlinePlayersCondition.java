package io.github.pigaut.voxel.function.condition.server;

import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public class MaximumOnlinePlayersCondition implements ServerCondition {

    private final int maximumPlayers;

    public MaximumOnlinePlayersCondition(int maximumPlayers) {
        this.maximumPlayers = maximumPlayers;
    }

    @Override
    public boolean isMet() {
        return Bukkit.getOnlinePlayers().size() <= maximumPlayers;
    }

    public static ConfigLoader<MaximumOnlinePlayersCondition> newConfigLoader() {
        return new ConfigLoader<MaximumOnlinePlayersCondition>() {
            @Override
            public @NotNull String getProblemDescription() {
                return "Could not load condition 'MAX_ONLINE_PLAYERS'";
            }

            @Override
            public String getKey() {
                return "MAX_ONLINE_PLAYERS";
            }

            @Override
            public @NotNull MaximumOnlinePlayersCondition loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
                return new MaximumOnlinePlayersCondition(scalar.toInteger());
            }
        };
    }

}
