package io.github.pigaut.voxel.function;

import io.github.pigaut.voxel.function.action.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
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
    public void run(@Nullable PluginPlayer player, @Nullable Block block) {
        for (SwitchCase switchCase : cases) {
            if (switchCase.getCondition().isMet(player, block)) {
                for (Action action : switchCase.getActions()) {
                    action.execute(player, block);
                }
                if (switchCase.isBreakCycle()) return;
            }
        }
    }

}
