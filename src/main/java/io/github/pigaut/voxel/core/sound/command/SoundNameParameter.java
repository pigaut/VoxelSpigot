package io.github.pigaut.voxel.core.sound.command;

import io.github.pigaut.voxel.command.parameter.*;
import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class SoundNameParameter extends CommandParameter {

    public SoundNameParameter(@NotNull EnhancedPlugin plugin) {
        super("sound-name", (sender, args) -> plugin.getSounds().getAllNames());
    }

}
