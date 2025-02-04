package io.github.pigaut.voxel.function;

import io.github.pigaut.voxel.function.action.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class SwitchFunction implements Function {

    private final List<SwitchCase> cases = new ArrayList<>();

    public SwitchFunction() {}

    public SwitchFunction(Collection<? extends @NotNull SwitchCase> cases) {
        this.cases.addAll(cases);
    }

    public void addCase(@NotNull SwitchCase switchCase) {
        cases.add(switchCase);
    }

    public void clear() {
        cases.clear();
    }

    @Override
    public void run(@Nullable PluginPlayer player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        for (SwitchCase switchCase : cases) {
            if (switchCase.getCondition().isMet(player, event, block, target)) {
                for (Action action : switchCase.getActions()) {
                    action.execute(player, event, block, target);
                }
                if (switchCase.isBreakCycle()) return;
            }
        }
    }

}
