package io.github.pigaut.voxel.player;

import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class PluginPlayerManager extends PlayerManager<PluginPlayer> {

    public PluginPlayerManager(@NotNull EnhancedPlugin plugin) {
        super(plugin, AbstractPluginPlayer::new);
    }

}
