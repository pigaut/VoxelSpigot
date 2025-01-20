package io.github.pigaut.voxel.function.interact.inventory;

import io.github.pigaut.voxel.player.*;
import org.bukkit.event.inventory.*;
import org.jetbrains.annotations.*;

@FunctionalInterface
public interface InventoryClickFunction {

    void onInventoryClick(@NotNull PluginPlayer player, @NotNull InventoryClickEvent event);

}
