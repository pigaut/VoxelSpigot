package io.github.pigaut.voxel.sound.command;

import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class SoundSubCommand extends LangSubCommand {

    public SoundSubCommand(@NotNull EnhancedPlugin plugin) {
        super("sound", plugin);
        addSubCommand(new PlaySoundSubCommand(plugin));
        addSubCommand(new PlaySoundToSubCommand(plugin));
    }

}
