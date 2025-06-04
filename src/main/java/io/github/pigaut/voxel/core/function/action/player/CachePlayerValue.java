package io.github.pigaut.voxel.core.function.action.player;

import io.github.pigaut.voxel.player.*;
import org.jetbrains.annotations.*;

public class CachePlayerValue implements PlayerAction {

    private final String id;
    private final Object value;

    public CachePlayerValue(String id, Object value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public void execute(@NotNull PlayerState player) {
        player.saveCache(id, value);
    }

}
