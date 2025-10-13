package io.github.pigaut.voxel.core.function.action.system;

import io.github.pigaut.voxel.core.function.action.*;
import io.github.pigaut.voxel.core.function.response.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

public class ReturnAction implements FunctionAction {

    @Override
    public FunctionResponse dispatch(@Nullable PlayerState player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        return ResponseType.RETURN;
    }

}
