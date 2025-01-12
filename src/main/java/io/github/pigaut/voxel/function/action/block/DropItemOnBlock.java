package io.github.pigaut.voxel.function.action.block;

import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class DropItemOnBlock implements BlockAction {

    private final List<ItemStack> items;

    public DropItemOnBlock(List<ItemStack> items) {
        this.items = items;
    }

    @Override
    public void execute(@NotNull Block block) {
        final Location location = block.getLocation();
        for (ItemStack itemStack : items) {
            block.getWorld().dropItemNaturally(location, itemStack);
        }
    }

    public static ConfigLoader<DropItemOnBlock> newConfigLoader() {
        return new ConfigLoader<DropItemOnBlock>() {
            @Override
            public @NotNull String getProblemDescription() {
                return "Could not load action 'DROP_ON_BLOCK'";
            }

            @Override
            public String getKey() {
                return "DROP_ON_BLOCK";
            }

            @Override
            public @NotNull DropItemOnBlock loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
                return new DropItemOnBlock(List.of(scalar.load(ItemStack.class)));
            }

            @Override
            public @NotNull DropItemOnBlock loadFromSection(@NotNull ConfigSection section) throws InvalidConfigurationException {
                return new DropItemOnBlock(List.of(section.load(ItemStack.class)));
            }

            @Override
            public @NotNull DropItemOnBlock loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
                return new DropItemOnBlock(sequence.toList(ItemStack.class));
            }
        };
    }
}
