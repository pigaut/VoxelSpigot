package io.github.pigaut.voxel.function.interact.block;

import io.github.pigaut.voxel.function.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.bukkit.event.block.*;
import org.bukkit.event.player.*;
import org.jetbrains.annotations.*;

public class SimpleBlockClickFunction implements BlockClickFunction {

    private final Action action;
    private final Function function;

    public SimpleBlockClickFunction(@NotNull Action action, @NotNull Function function) {
        this.action = action;
        this.function = function;
    }

    @Override
    public void onBlockClick(@NotNull PluginPlayer player, @NotNull PlayerInteractEvent event) {
        if (event.getAction() != action) {
            return;
        }
        function.run(player, event, event.getClickedBlock(), null);
    }

}
