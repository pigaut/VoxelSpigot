package io.github.pigaut.voxel.core.sound.command;

import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class SoundSubCommand extends SubCommand {

    public SoundSubCommand(@NotNull EnhancedPlugin plugin) {
        super("sound", plugin);
        withPermission(plugin.getPermission("sound"));
        withDescription(plugin.getLang("sound-play-command"));
        addSubCommand(new PlaySoundSubCommand(plugin));
        addSubCommand(new PlaySoundToSubCommand(plugin));
    }

}
