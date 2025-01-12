package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class DropItemOnPlayer implements PlayerAction {

    private final List<ItemStack> items;

    public DropItemOnPlayer(List<@NotNull ItemStack> items) {
        this.items = items;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        World world = player.getWorld();

        for (ItemStack itemStack : items) {
            world.dropItemNaturally(player.getLocation(), itemStack);
        }
    }

    public static ConfigLoader<DropItemOnPlayer> newConfigLoader() {
        return new ConfigLoader<DropItemOnPlayer>() {
            @Override
            public @NotNull String getProblemDescription() {
                return "Could not load action 'DROP_ON_PLAYER'";
            }

            @Override
            public String getKey() {
                return "DROP_ON_PLAYER";
            }

            @Override
            public @NotNull DropItemOnPlayer loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
                return new DropItemOnPlayer(List.of(scalar.load(ItemStack.class)));
            }

            @Override
            public @NotNull DropItemOnPlayer loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
                return new DropItemOnPlayer(sequence.toList(ItemStack.class));
            }
        };
    }

}
