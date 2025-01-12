package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class GivePlayerItem implements PlayerAction {

    private final List<ItemStack> items;

    public GivePlayerItem(ItemStack item) {
        items = List.of(item);
    }

    public GivePlayerItem(List<@NotNull ItemStack> items) {
        this.items = items;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        for (ItemStack item : items) {
            player.giveItem(item);
        }
    }

    public static ConfigLoader<GivePlayerItem> newConfigLoader() {
        return new ConfigLoader<GivePlayerItem>() {
            @Override
            public @NotNull String getProblemDescription() {
                return "Could not load action 'GIVE_ITEM'";
            }

            @Override
            public String getKey() {
                return "GIVE_ITEM";
            }

            @Override
            public @NotNull GivePlayerItem loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
                return new GivePlayerItem(scalar.load(ItemStack.class));
            }

            @Override
            public @NotNull GivePlayerItem loadFromSection(@NotNull ConfigSection section) throws InvalidConfigurationException {
                return new GivePlayerItem(section.load(ItemStack.class));
            }

            @Override
            public @NotNull GivePlayerItem loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
                return new GivePlayerItem(sequence.toList(ItemStack.class));
            }
        };
    }

}
