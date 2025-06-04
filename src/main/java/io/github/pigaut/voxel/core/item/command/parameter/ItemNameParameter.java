package io.github.pigaut.voxel.core.item.command.parameter;

import io.github.pigaut.voxel.command.parameter.*;
import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class ItemNameParameter extends CommandParameter {

    public ItemNameParameter(@NotNull EnhancedPlugin plugin) {
        super("item-name", (sender, args) -> plugin.getItems().getAllNames());
    }

}
