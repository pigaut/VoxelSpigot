package io.github.pigaut.voxel.command.parameter;

import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.server.*;
import org.jetbrains.annotations.*;

public class WorldNameParameter extends CommandParameter {

    public WorldNameParameter(@NotNull EnhancedPlugin plugin) {
        super(plugin.getLang("WORLD_NAME_PARAMETER", "world-name"),
                (sender, args) -> SpigotServer.getWorldNames());
    }

}
