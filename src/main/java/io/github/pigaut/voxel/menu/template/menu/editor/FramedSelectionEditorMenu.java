package io.github.pigaut.voxel.menu.template.menu.editor;

import io.github.pigaut.voxel.menu.*;
import io.github.pigaut.voxel.menu.button.*;
import io.github.pigaut.voxel.menu.template.button.*;
import io.github.pigaut.voxel.menu.template.menu.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.*;
import org.jetbrains.annotations.*;

public class FramedSelectionEditorMenu extends FramedSelectionMenu {

    private final ConfigRoot config;

    public FramedSelectionEditorMenu(ConfigRoot config, String title, int size) {
        this(null, config, title, size);
    }

    public FramedSelectionEditorMenu(@Nullable EnhancedPlugin plugin, ConfigRoot config, String title, int size) {
        super(plugin, title, size);
        this.config = config;
    }

    @Override
    public boolean keepOpen() {
        return true;
    }

    @Override
    public void onClose(MenuView view) {
        config.save();
    }

}
