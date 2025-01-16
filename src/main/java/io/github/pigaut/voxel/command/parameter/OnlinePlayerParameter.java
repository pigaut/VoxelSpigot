package io.github.pigaut.voxel.command.parameter;

import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.server.*;
import org.jetbrains.annotations.*;

public class OnlinePlayerParameter extends CommandParameter {

    public OnlinePlayerParameter(@NotNull EnhancedPlugin plugin) {
        super(plugin.getLang("ONLINE_PLAYER_PARAMETER", "online-player"),
                false,
                null,
                (sender, args) -> SpigotServer.getOnlinePlayerNames());
    }

}
