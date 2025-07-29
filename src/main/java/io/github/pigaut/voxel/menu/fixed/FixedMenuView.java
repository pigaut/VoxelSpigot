package io.github.pigaut.voxel.menu.fixed;

import io.github.pigaut.voxel.menu.*;
import io.github.pigaut.voxel.menu.button.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class FixedMenuView implements MenuView {

    protected final Menu menu;
    protected final PlayerState viewer;
    protected Button[] buttons;
    protected final Inventory inventory;
    private final MenuView previousView;
    private boolean forceClose = false;

    public FixedMenuView(FixedMenu menu, PlayerState viewer, MenuView previousView) {
        this.menu = menu;
        this.viewer = viewer;
        this.previousView = previousView;
        this.inventory = InventoryUtil.createInventory(menu.getTitle(), InventoryType.CHEST, menu.getSize());
    }

    @Override
    public Menu getMenu() {
        return menu;
    }

    @Override
    public PlayerState getViewer() {
        return viewer;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public @Nullable MenuView getPreviousView() {
        return previousView;
    }

    @Override
    public @NotNull MenuView getFirstView() {
        final Set<MenuView> visited = new HashSet<>();
        MenuView current = this;
        while (current.getPreviousView() != null) {
            if (!visited.add(current)) {
                break;
            }
            current = current.getPreviousView();
        }
        return current;
    }

    @Override
    public boolean isForcedClose() {
        return forceClose;
    }

    @Override
    public boolean isOpen() {
        return viewer.getOpenMenu() == this;
    }

    @Override
    public void open() {
        forceClose = false;
        final MenuView openMenu = viewer.getOpenMenu();
        if (openMenu != null) {
            openMenu.getMenu().onClose(openMenu);
        }
        viewer.setOpenMenu(null);
        viewer.openInventory(inventory);
        update();
        viewer.setOpenMenu(this);
        menu.onOpen(this);
    }

    @Override
    public void close() {
        forceClose = true;
        viewer.closeInventory();
    }

    @Override
    public void update() {
        buttons = menu.createButtons();
        inventory.clear();
        for (int i = 0; i < menu.getSize(); i++) {
            final Button button = buttons[i];
            if (button != null) {
                inventory.setItem(i, button.getIcon());
            }
        }
        viewer.updateInventory();
    }

    @Override
    public void click(InventoryClickEvent event) {
        final int slot = event.getRawSlot();
        if (slot < 0 || slot > menu.getSize()) {
            event.setCancelled(true);
            return;
        }

        final Button button = buttons[slot];
        if (button != null) {
            event.setCancelled(button.locked);
            button.onClick(this, event);
        }
    }

}
