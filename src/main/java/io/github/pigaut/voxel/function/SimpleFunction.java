package io.github.pigaut.voxel.function;

import io.github.pigaut.voxel.function.action.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class SimpleFunction implements Function {

    private final Set<Action> actions = new HashSet<>();

    public SimpleFunction() {}

    public SimpleFunction(Collection<@NotNull Action> actions) {
        this.actions.addAll(actions);
    }

    @Override
    public void run(@Nullable PluginPlayer player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        actions.forEach(action -> action.execute(player, event, block, target));
    }

}
