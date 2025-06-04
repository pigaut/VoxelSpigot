package io.github.pigaut.voxel.bukkit;

import org.bukkit.event.block.*;

public enum BlockInteraction {

    RIGHT_CLICK(Action.RIGHT_CLICK_BLOCK),
    RIGHT_CLICK_BLOCK(Action.RIGHT_CLICK_BLOCK),
    LEFT_CLICK(Action.LEFT_CLICK_BLOCK),
    LEFT_CLICK_BLOCK(Action.LEFT_CLICK_BLOCK),
    PHYSICAL(Action.PHYSICAL);

    private final Action action;

    BlockInteraction(Action action) {
        this.action = action;
    }

    public Action toAction() {
        return action;
    }

}
