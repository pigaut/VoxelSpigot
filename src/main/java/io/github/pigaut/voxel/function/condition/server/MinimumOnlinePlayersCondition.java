package io.github.pigaut.voxel.function.condition.server;

import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public class MinimumOnlinePlayersCondition implements ServerCondition {

    private final int minimumPlayers;

    public MinimumOnlinePlayersCondition(int minimumPlayers) {
        this.minimumPlayers = minimumPlayers;
    }

    @Override
    public boolean isMet() {
        return Bukkit.getOnlinePlayers().size() >= minimumPlayers;
    }

    public static ConfigLoader<MinimumOnlinePlayersCondition> newConfigLoader() {
        return new ConfigLoader<MinimumOnlinePlayersCondition>() {
            @Override
            public @NotNull String getProblemDescription() {
                return "Could not load condition 'MIN_ONLINE_PLAYERS'";
            }

            @Override
            public String getKey() {
                return "MIN_ONLINE_PLAYERS";
            }

            @Override
            public @NotNull MinimumOnlinePlayersCondition loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
                return new MinimumOnlinePlayersCondition(scalar.toInteger());
            }
        };
    }

}
