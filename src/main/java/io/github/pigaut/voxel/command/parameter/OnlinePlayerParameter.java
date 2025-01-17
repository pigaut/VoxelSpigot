package io.github.pigaut.voxel.command.parameter;

import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.server.*;
import org.jetbrains.annotations.*;

public class OnlinePlayerParameter extends CommandParameter {

    public OnlinePlayerParameter(@NotNull EnhancedPlugin plugin) {
        super(plugin.getLang("online-player-parameter"), (sender, args) -> SpigotServer.getOnlinePlayerNames());
    }

}
