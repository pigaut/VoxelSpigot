package io.github.pigaut.voxel.function.condition.player;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class PlayerHasFlag implements PlayerCondition {

    private final String flag;

    public PlayerHasFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public boolean isMet(@NotNull PluginPlayer player) {
        return player.hasFlag(flag);
    }

}
