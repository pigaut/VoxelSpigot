package io.github.pigaut.voxel.sound.command;

import io.github.pigaut.voxel.command.parameter.*;
import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class SoundNameParameter extends CommandParameter {

    public SoundNameParameter(@NotNull EnhancedPlugin plugin) {
        super(plugin.getLang("sound-name-parameter"), (sender, args) -> plugin.getSounds().getSoundNames());
    }

}
