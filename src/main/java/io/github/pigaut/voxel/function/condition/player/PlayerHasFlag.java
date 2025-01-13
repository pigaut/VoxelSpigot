package io.github.pigaut.voxel.function.condition.player;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class PlayerHasFlag implements PlayerCondition {

    private final List<String> flags;

    public PlayerHasFlag(String flag) {
        this.flags = List.of(flag);
    }

    public PlayerHasFlag(List<String> flags) {
        this.flags = flags;
    }

    @Override
    public boolean isMet(@NotNull PluginPlayer player) {
        for (String flag : flags) {
            if (!player.hasFlag(flag)) {
                return false;
            }
        }
        return true;
    }

}
