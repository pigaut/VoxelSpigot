package io.github.pigaut.voxel.function.interact.inventory;

import io.github.pigaut.voxel.player.*;
import org.bukkit.event.inventory.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class MultiInventoryClickFunction implements InventoryClickFunction {

    private final List<InventoryClickFunction> functions;

    public MultiInventoryClickFunction(@NotNull List<@NotNull InventoryClickFunction> functions) {
        this.functions = functions;
    }

    @Override
    public void onInventoryClick(@NotNull PluginPlayer player, @NotNull InventoryClickEvent event) {
        for (InventoryClickFunction function : functions) {
            function.onInventoryClick(player, event);
        }
    }

}
