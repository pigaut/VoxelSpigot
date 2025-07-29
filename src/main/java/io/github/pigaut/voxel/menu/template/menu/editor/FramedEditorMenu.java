package io.github.pigaut.voxel.menu.template.menu.editor;

import io.github.pigaut.voxel.menu.*;
import io.github.pigaut.voxel.menu.button.*;
import io.github.pigaut.voxel.menu.template.button.*;
import io.github.pigaut.voxel.menu.template.menu.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.*;
import org.jetbrains.annotations.*;

public class FramedEditorMenu extends FramedMenu {

    private final ConfigRoot config;

    public FramedEditorMenu(ConfigRoot config, String title, int size) {
        this(null, config, title, size);
    }

    public FramedEditorMenu(@Nullable EnhancedPlugin plugin, ConfigRoot config, String title, int size) {
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

    @Override
    public Button getToolbarButton4() {
        return Buttons.GRAY_PANEL;
    }

    @Override
    public Button getToolbarButton5() {
        return new BackSaveButton(config);
    }

}
