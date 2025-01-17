package io.github.pigaut.voxel.command.parameter;

import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class FilePathParameter extends CommandParameter {

    public FilePathParameter(@NotNull EnhancedPlugin plugin, String path) {
        super(plugin.getLang("file-path-parameter"), (sender, args) -> plugin.getFilePaths(path));
    }

}
