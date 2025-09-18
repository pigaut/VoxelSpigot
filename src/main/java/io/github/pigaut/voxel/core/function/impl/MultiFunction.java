package io.github.pigaut.voxel.core.function.impl;

import io.github.pigaut.voxel.core.function.*;
import io.github.pigaut.voxel.core.function.response.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class MultiFunction implements Function {

    private final String name;
    private final String group;
    private final ConfigSequence sequence;
    private final List<Function> functions;

    public MultiFunction(String name, String group, ConfigSequence sequence, @NotNull List<@NotNull Function> functions) {
        this.name = name;
        this.group = group;
        this.sequence = sequence;
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
    public FunctionResponse run(@Nullable PlayerState player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        for (int i = 0; i < functions.size(); i++) {
            final FunctionResponse response = functions.get(i).run(player, event, block, target);
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
                i = gotoResponse.getLine() - 1;
            }
        }

        return null;
    }

}
