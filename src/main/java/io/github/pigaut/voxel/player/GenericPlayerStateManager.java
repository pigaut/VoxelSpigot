package io.github.pigaut.voxel.player;

import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class GenericPlayerStateManager extends PlayerStateManager<EnhancedJavaPlugin, GenericPlayerState> {

    public GenericPlayerStateManager(@NotNull EnhancedJavaPlugin plugin) {
        super(plugin, GenericPlayerState::new);
    }

}
