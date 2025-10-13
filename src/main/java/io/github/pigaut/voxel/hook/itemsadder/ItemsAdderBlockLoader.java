package io.github.pigaut.voxel.hook.itemsadder;

import dev.lone.itemsadder.api.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.load.*;
import org.jetbrains.annotations.*;

public class ItemsAdderBlockLoader implements ConfigLoader<CustomBlock> {

    @Override
    public @Nullable String getProblemDescription() {
        return "invalid ItemsAdder block";
    }

    @Override
    public @NotNull CustomBlock loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
        String blockName = scalar.toString();
        CustomBlock customBlock = CustomBlock.getInstance(blockName);
        if (customBlock == null) {
            throw new InvalidConfigurationException(scalar, "Could not find ItemsAdder block with name: '" + blockName + "'");
        }
        return customBlock;
    }

}
