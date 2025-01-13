package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class CachePlayerValue implements PlayerAction {

    private final String id;
    private final Object value;

    public CachePlayerValue(String id, Object value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        player.saveCache(id, value);
    }

}
