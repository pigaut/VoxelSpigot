package io.github.pigaut.voxel.core.function.action.system;

import io.github.pigaut.voxel.core.function.action.*;
import io.github.pigaut.voxel.core.function.response.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

public class GotoAction extends GotoResponse implements FunctionAction {

    public GotoAction(int gotoLine) {
        super(gotoLine);
    }

    @Override
    public @Nullable FunctionResponse dispatch(@Nullable PlayerState player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        return this;
    }

}
