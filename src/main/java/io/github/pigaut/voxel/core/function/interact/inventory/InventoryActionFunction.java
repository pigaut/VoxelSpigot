package io.github.pigaut.voxel.core.function.interact.inventory;

import io.github.pigaut.voxel.core.function.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.event.inventory.*;
import org.jetbrains.annotations.*;

public class InventoryActionFunction implements InventoryClickFunction {

    private final InventoryAction action;
    private final Function function;

    public InventoryActionFunction(InventoryAction action, Function function) {
        this.action = action;
        this.function = function;
    }

    @Override
    public void onClick(@NotNull PlayerState player, @NotNull InventoryClickEvent event) {
        if (event.getAction() == action) {
            function.run(player);
        }
    }

}
