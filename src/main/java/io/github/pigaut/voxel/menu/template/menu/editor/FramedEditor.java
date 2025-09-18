package io.github.pigaut.voxel.menu.template.menu.editor;

import io.github.pigaut.voxel.menu.*;
import io.github.pigaut.voxel.menu.button.*;
import io.github.pigaut.voxel.menu.template.button.*;
import io.github.pigaut.voxel.menu.template.menu.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.*;
import org.jetbrains.annotations.*;

public class FramedEditor extends FramedMenu {

    private final ConfigRoot config;
    protected final ConfigSection section;

    public FramedEditor(ConfigSection section, String title, int size) {
        this(null, section, title, size);
    }

    public FramedEditor(@Nullable EnhancedPlugin plugin, ConfigSection section, String title, int size) {
        super(plugin, title, size);
        this.config = section.getRoot();
        this.section = section;
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
    public Button getToolbarButton5() {
        return new BackSaveButton(config);
    }

}
