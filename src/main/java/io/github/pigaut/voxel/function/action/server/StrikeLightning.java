package io.github.pigaut.voxel.function.action.server;

import io.github.pigaut.voxel.server.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class StrikeLightning implements ServerAction {

    private final List<Location> locations;

    public StrikeLightning(@NotNull Location location) {
        this.locations = List.of(location);
    }

    public StrikeLightning(@NotNull List<Location> locations) {
        this.locations = locations;
    }

    @Override
    public void execute() {
        for (Location location : locations) {
            World world = location.getWorld();
            if (world == null) {
                world = SpigotServer.getDefaultWorld();
            }
            world.strikeLightning(location);
        }
    }

    public static ConfigLoader<StrikeLightning> newConfigLoader() {
        return new ConfigLoader<StrikeLightning>() {
            @Override
            public @NotNull String getProblemDescription() {
                return "Could not load action 'LIGHTNING'";
            }

            @Override
            public String getKey() {
                return "LIGHTNING";
            }

            @Override
            public @NotNull StrikeLightning loadFromSection(ConfigSection section) throws InvalidConfigurationException {
                return new StrikeLightning(section.load(Location.class));
            }

            @Override
            public @NotNull StrikeLightning loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
                return new StrikeLightning(sequence.toList(Location.class));
            }
        };
    }
}
