package io.github.pigaut.voxel.function;

import io.github.pigaut.voxel.function.action.*;
import io.github.pigaut.voxel.function.condition.*;
import io.github.pigaut.voxel.player.PluginPlayer;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class ConditionalFunction implements Function {

    private final Condition condition;
    private final Action success;
    private final Action failure;

    public ConditionalFunction(Condition condition, Action success, Action failure) {
        this.condition = condition;
        this.success = success;
        this.failure = failure;
    }

    @Override
    public boolean run(@Nullable PluginPlayer player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        if (condition.isMet(player, event, block, target)) {
            success.execute(player, event, block, target);
            if (success.shouldReturn()) {
                return false;
            }
        }
        else {
            failure.execute(player, event, block, target);
            if (failure.shouldReturn()) {
                return false;
            }
        }
        return true;
    }

}
