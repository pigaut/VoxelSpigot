package io.github.pigaut.voxel.function;

import io.github.pigaut.voxel.function.action.*;
import io.github.pigaut.voxel.function.condition.*;
import io.github.pigaut.voxel.player.PluginPlayer;
import org.bukkit.block.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class ConditionalFunction implements Function {

    private final Set<Condition> conditions = new HashSet<>();
    private final Set<Action> success = new HashSet<>();
    private final Set<Action> failure = new HashSet<>();

    public ConditionalFunction() {}

    public ConditionalFunction(Collection<Condition> conditions, Collection<Action> success, Collection<Action> failure) {
        this.conditions.addAll(conditions);
        this.success.addAll(success);
        this.failure.addAll(failure);
    }

    @Override
    public void run(@Nullable PluginPlayer player, @Nullable Block block) {
        boolean allConditionsMet = true;
        for (Condition condition : conditions) {
            if (condition.isMet(player, block)) {
                continue;
            }
            allConditionsMet = false;
            break;
        }

        if (allConditionsMet) {
            success.forEach(action -> action.execute(player, block));
        }
        else {
            failure.forEach(action -> action.execute(player, block));
        }
    }

}
