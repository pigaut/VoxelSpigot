package io.github.pigaut.voxel.config.deserializer;

import io.github.pigaut.yaml.configurator.convert.deserialize.*;
import io.github.pigaut.yaml.convert.parse.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public class WorldDeserializer implements Deserializer<World> {

    @Override
    public @Nullable String getProblemDescription() {
        return "invalid world";
    }

    @Override
    public @NotNull World deserialize(@NotNull String worldName) throws StringParseException {
        World world = Bukkit.getWorld(worldName);

        if (world == null) {
            throw new StringParseException("Could not find world with name: " + worldName);
        }

        return world;
    }

}
