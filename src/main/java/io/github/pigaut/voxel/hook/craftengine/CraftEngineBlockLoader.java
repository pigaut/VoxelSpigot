package io.github.pigaut.voxel.hook.craftengine;

import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.load.*;
import net.momirealms.craftengine.bukkit.api.*;
import net.momirealms.craftengine.core.block.*;
import net.momirealms.craftengine.core.util.*;
import org.jetbrains.annotations.*;

public class CraftEngineBlockLoader implements ConfigLoader<CustomBlock> {

    @Override
    public @Nullable String getProblemDescription() {
        return "invalid CraftEngine block";
    }

    @Override
    public @NotNull CustomBlock loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
        String rawKey = scalar.toString();
        String[] splitKey = rawKey.split(":");

        if (splitKey.length > 2) {
            throw new InvalidConfigurationException(scalar, "Expected a key but found: '" + rawKey + "'");
        }

        Key key = splitKey.length == 1 ? Key.withDefaultNamespace(splitKey[0]) : Key.of(splitKey);
        CustomBlock customBlock = CraftEngineBlocks.byId(key);
        if (customBlock == null) {
            throw new InvalidConfigurationException(scalar, "Could not find CraftEngine block with name: '" + rawKey + "'");
        }
        return customBlock;
    }

}
