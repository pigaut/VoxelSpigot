package io.github.pigaut.voxel.function.interact.inventory;

import io.github.pigaut.voxel.function.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.event.inventory.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class SimpleInventoryClickFunction implements InventoryClickFunction {

    private final Set<ClickType> clickTypes;
    private final boolean shifting;
    private final Boolean shouldCancel;
    private final Function function;

    public SimpleInventoryClickFunction(@NotNull Set<@NotNull ClickType> clickTypes, boolean shifting,
                                        Boolean shouldCancel, @NotNull Function function) {
        this.clickTypes = clickTypes;
        this.shifting = shifting;
        this.shouldCancel = shouldCancel;
        this.function = function;
    }

    @Override
    public void onInventoryClick(@NotNull PluginPlayer player, @NotNull InventoryClickEvent event) {
        if (!clickTypes.contains(event.getClick())) {
            return;
        }

        if (shifting && !event.isShiftClick()) {
            return;
        }

        if (shouldCancel != null) {
            event.setCancelled(shouldCancel);
        }
        function.run(player);
    }

}
