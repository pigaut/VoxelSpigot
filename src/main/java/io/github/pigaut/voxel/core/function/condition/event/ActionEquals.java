package io.github.pigaut.voxel.core.function.condition.event;

import io.github.pigaut.voxel.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class ActionEquals implements EventCondition {

    private final List<ClickAction> actions;
    private final @Nullable Boolean sneaking;

    public ActionEquals(List<ClickAction> actions, @Nullable Boolean sneaking) {
        this.actions = actions;
        this.sneaking = sneaking;
    }

    @Override
    public Boolean evaluate(@NotNull Event event) {
        if (!(event instanceof PlayerInteractEvent interactEvent)) {
            return null;
        }

        if (sneaking != null) {
            if (interactEvent.getPlayer().isSneaking() != sneaking) {
                return false;
            }
        }

        for (ClickAction action : actions) {
            if (action.test(interactEvent.getAction())) {
                return true;
            }
        }

        return false;
    }

}
