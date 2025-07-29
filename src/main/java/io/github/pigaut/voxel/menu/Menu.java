package io.github.pigaut.voxel.menu;

import io.github.pigaut.voxel.menu.button.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public interface Menu {

    @Nullable
    EnhancedPlugin getPlugin();

    @NotNull String getTitle();

    int getSize();

    @NotNull Button[] createButtons();

    boolean keepOpen();

    boolean backOnClose();

    void onOpen(MenuView view);

    void onClose(MenuView view);

    @NotNull MenuView createView(PlayerState player, MenuView previousView);

}
