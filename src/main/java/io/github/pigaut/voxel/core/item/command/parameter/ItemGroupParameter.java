package io.github.pigaut.voxel.core.item.command.parameter;

import io.github.pigaut.voxel.command.parameter.*;
import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class ItemGroupParameter extends CommandParameter {

    public ItemGroupParameter(@NotNull EnhancedPlugin plugin) {
        super("item-group", (sender, args) -> plugin.getItems().getAllGroups());
    }

}
