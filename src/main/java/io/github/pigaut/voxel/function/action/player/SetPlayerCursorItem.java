package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class SetPlayerCursorItem implements PlayerAction {

    private final ItemStack item;

    public SetPlayerCursorItem(ItemStack item) {
        this.item = item;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        player.asPlayer().setItemOnCursor(item);
    }

    public static ConfigLoader<SetPlayerCursorItem> newConfigLoader() {
        return new ConfigLoader<SetPlayerCursorItem>() {
            @Override
            public @NotNull String getProblemDescription() {
                return "Could not load action 'SET_CURSOR_ITEM'";
            }

            @Override
            public String getKey() {
                return "SET_CURSOR_ITEM";
            }

            @Override
            public @NotNull SetPlayerCursorItem loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
                return new SetPlayerCursorItem(scalar.load(ItemStack.class));
            }
        };
    }

}
