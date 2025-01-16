package io.github.pigaut.voxel.item.command;

import io.github.pigaut.voxel.command.parameter.*;
import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class ItemNameParameter extends CommandParameter {

    public ItemNameParameter(@NotNull EnhancedPlugin plugin) {
        super(plugin.getLang("ITEM_NAME_PARAMETER", "item-name"),
                (sender, args) -> plugin.getItems().getItemNames());
    }

}
