package io.github.pigaut.voxel.function.interact.inventory;

import io.github.pigaut.voxel.function.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.event.inventory.*;
import org.jetbrains.annotations.*;

public class InventoryActionFunction implements InventoryClickFunction {

    private final InventoryAction action;
    private final Boolean shouldCancel;
    private final Function function;

    public InventoryActionFunction(InventoryAction action, @Nullable Boolean shouldCancel, Function function) {
        this.action = action;
        this.shouldCancel = shouldCancel;
        this.function = function;
    }

    @Override
    public void onInventoryClick(@NotNull PluginPlayer player, @NotNull InventoryClickEvent event) {
        if (event.getAction() != action) {
            return;
        }

        if (shouldCancel != null) {
            event.setCancelled(shouldCancel);
        }
        function.run(player);
    }

}
