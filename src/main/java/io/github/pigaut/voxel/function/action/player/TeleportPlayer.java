package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public class TeleportPlayer implements PlayerAction {

    private final Location destination;

    public TeleportPlayer(Location destination) {
        this.destination = destination;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        player.teleport(destination);
    }

    public static ConfigLoader<TeleportPlayer> newConfigLoader() {
        return new ConfigLoader<TeleportPlayer>() {
            @Override
            public @NotNull String getProblemDescription() {
                return "Could not load action 'TELEPORT'";
            }

            @Override
            public String getKey() {
                return "TELEPORT";
            }

            @Override
            public @NotNull TeleportPlayer loadFromSection(ConfigSection section) throws InvalidConfigurationException {
                return new TeleportPlayer(section.load(Location.class));
            }
        };
    }

}
