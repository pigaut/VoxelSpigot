package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.player.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class SetCursorToCacheItem implements PlayerAction {

    private final String name;

    public SetCursorToCacheItem(String name) {
        this.name = name;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        player.asPlayer().setItemOnCursor(player.getCache(name, ItemStack.class));
    }

}
