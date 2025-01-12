package io.github.pigaut.voxel.function;

import io.github.pigaut.voxel.function.action.*;
import io.github.pigaut.voxel.function.condition.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class SwitchCase {

    private final Set<Action> actions = new HashSet<>();
    private Condition condition;
    private boolean breakCycle;

    public static SwitchCase createDefaultCase(Collection<@NotNull Action> actions) {
        return new SwitchCase(Condition.MET, actions, true);
    }

    public SwitchCase(Condition condition, Collection<@NotNull Action> actions, boolean breakCycle) {
        this.condition = condition;
        this.actions.addAll(actions);
        this.breakCycle = breakCycle;
    }

    public Condition getCondition() {
        return condition;
    }

    public Set<Action> getActions() {
        return new HashSet<>(actions);
    }

    public void addAction(@NotNull Action action) {
        actions.add(action);
    }

    public boolean isBreakCycle() {
        return breakCycle;
    }

}
