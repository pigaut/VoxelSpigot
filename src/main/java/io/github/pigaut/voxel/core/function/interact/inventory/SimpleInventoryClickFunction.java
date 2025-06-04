package io.github.pigaut.voxel.core.function.interact.inventory;

import io.github.pigaut.voxel.core.function.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.event.inventory.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class SimpleInventoryClickFunction implements InventoryClickFunction {

    private final Set<ClickType> clickTypes;
    private final Function function;

    public SimpleInventoryClickFunction(@NotNull Set<@NotNull ClickType> clickTypes, @NotNull Function function) {
        this.clickTypes = clickTypes;
        this.function = function;
    }

    @Override
    public void onClick(@NotNull PlayerState player, @NotNull InventoryClickEvent event) {
        if (clickTypes.contains(event.getClick())) {
            function.run(player);
        }
    }

}
