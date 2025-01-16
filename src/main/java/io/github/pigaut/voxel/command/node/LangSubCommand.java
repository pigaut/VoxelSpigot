package io.github.pigaut.voxel.command.node;

import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class LangSubCommand extends SubCommand {
    public LangSubCommand(@NotNull String id, @NotNull EnhancedPlugin plugin) {
        super(plugin.getLang(id + "-command"), plugin);
        withPermission(plugin.getLang(id + "-permission"));
        withDescription(plugin.getLang(id + "-description", ""));
    }
}
