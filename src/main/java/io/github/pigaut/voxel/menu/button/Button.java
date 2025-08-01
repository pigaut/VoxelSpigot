package io.github.pigaut.voxel.menu.button;

import io.github.pigaut.voxel.menu.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class Button {

    private ItemStack icon;
    public boolean locked = true;

    public Button(@NotNull ItemStack icon) {
        this.icon = icon;
    }

    public static ButtonBuilder builder() {
        return new ButtonBuilder();
    }

    public ItemStack getIcon() {
        return icon.clone();
    }

    public void setIcon(@NotNull ItemStack icon) {
        this.icon = icon;
    }

    public void onClick(MenuView view, InventoryClickEvent event) {
        final ClickType click = event.getClick();
        switch (click) {
            case LEFT -> onLeftClick(view, view.getViewer(), event);
            case SHIFT_LEFT -> onShiftLeftClick(view, view.getViewer(), event);
            case RIGHT -> onRightClick(view, view.getViewer(), event);
            case SHIFT_RIGHT -> onShiftRightClick(view, view.getViewer(), event);
        }
    }

    public void onLeftClick(MenuView view, PlayerState player, InventoryClickEvent event) {

    }

    public void onRightClick(MenuView view, PlayerState player, InventoryClickEvent event) {

    }

    public void onShiftLeftClick(MenuView view, PlayerState player, InventoryClickEvent event) {

    }

    public void onShiftRightClick(MenuView view, PlayerState player, InventoryClickEvent event) {

    }

}
