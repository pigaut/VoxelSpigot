package io.github.pigaut.voxel.menu.fixed;

import io.github.pigaut.voxel.menu.*;
import io.github.pigaut.voxel.menu.button.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class FixedMenu implements Menu {

    protected final @Nullable EnhancedPlugin plugin;
    private final String title;
    private final int size;

    public FixedMenu(String title, int size) {
        this(null, title, size);
    }

    public FixedMenu(@Nullable EnhancedPlugin plugin, String title, int size) {
        this.plugin = plugin;
        this.title = title;
        this.size = size;
    }

    @Override
    public @Nullable EnhancedPlugin getPlugin() {
        return plugin;
    }

    @Override
    public @NotNull String getTitle() {
        return title;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public @Nullable Button[] createButtons() {
        return new Button[size];
    }

    @Override
    public void onOpen(MenuView view) {}

    @Override
    public void onClose(MenuView view) {}

    @Override
    public @NotNull MenuView createView(PlayerState player, MenuView previousView) {
        return new FixedMenuView(this, player, previousView);
    }

}
