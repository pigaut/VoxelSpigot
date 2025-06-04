package io.github.pigaut.voxel.menu;

import io.github.pigaut.voxel.player.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public interface MenuView {

    Menu getMenu();

    PlayerState getViewer();

    Inventory getInventory();

    @Nullable MenuView getPreviousView();

    @NotNull MenuView getFirstView();

    void close();

    void update();

    void click(InventoryClickEvent event);

}
