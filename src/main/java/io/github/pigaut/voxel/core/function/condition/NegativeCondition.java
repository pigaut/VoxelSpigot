package io.github.pigaut.voxel.core.function.condition;

import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

import java.util.*;

public interface NegativeCondition extends Condition {

    class Simple implements NegativeCondition {
        private final Condition condition;

        public Simple(@NotNull Condition condition) {
            this.condition = condition;
        }

        @Override
        public Boolean evaluate(@Nullable PlayerState player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
            Boolean result = condition.evaluate(player, event, block, target);
            if (result == null) {
                return false;
            }
            return !result;
        }
    }

    class Multi implements NegativeCondition {
        private final List<Condition> conditions;

        public Multi(@NotNull List<@NotNull Condition> conditions) {
            this.conditions = conditions;
        }

        @Override
        public @Nullable Boolean evaluate(@Nullable PlayerState player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
            for (Condition condition : conditions) {
                if (condition.isMet(player, event, block, target)) {
                    return false;
                }
            }
            return true;
        }
    }


}
