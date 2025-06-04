package io.github.pigaut.voxel.core.function.action.player;

import io.github.pigaut.voxel.player.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class SetPlayerCursorItem implements PlayerAction {

    private final ItemStack item;

    public SetPlayerCursorItem(ItemStack item) {
        this.item = item;
    }

    @Override
    public void execute(@NotNull PlayerState player) {
        player.asPlayer().setItemOnCursor(item);
    }

}
