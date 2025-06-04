package io.github.pigaut.voxel.core.function.interact.inventory;

import io.github.pigaut.voxel.player.*;
import org.bukkit.event.inventory.*;
import org.jetbrains.annotations.*;

@FunctionalInterface
public interface InventoryClickFunction {

    void onClick(@NotNull PlayerState player, @NotNull InventoryClickEvent event);

}
