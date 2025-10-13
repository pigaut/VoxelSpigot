package io.github.pigaut.voxel.core.function.impl;

import io.github.pigaut.voxel.core.function.*;
import io.github.pigaut.voxel.core.function.response.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class MultiFunction implements Function {

    private final String name;
    private final String group;
    private final List<Function> functions;

    public MultiFunction(String name, String group, @NotNull List<@NotNull Function> functions) {
        this.name = name;
        this.group = group;
        this.functions = functions;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public @Nullable String getGroup() {
        return group;
    }

    @Override
    public FunctionResponse dispatch(@Nullable PlayerState player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        for (int i = 0; i < functions.size(); i++) {
            final FunctionResponse response = functions.get(i).dispatch(player, event, block, target);
            if (response == null) {
                continue;
            }
            if (response.getType() == ResponseType.RETURN) {
                return null;
            }
            if (response.getType() == ResponseType.STOP) {
                return response;
            }
            if (response instanceof GotoResponse gotoResponse) {
                final int gotoLine = gotoResponse.getLine();
                if (gotoLine < functions.size()) {
                    i = gotoResponse.getLine();
                }
            }
        }

        return null;
    }

}
