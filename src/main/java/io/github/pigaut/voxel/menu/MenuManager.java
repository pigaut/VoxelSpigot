package io.github.pigaut.voxel.menu;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.player.input.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.manager.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;

import java.util.*;

public class MenuManager extends Manager implements Listener {

    private final Map<String, Menu> menusByName = new HashMap<>();

    public MenuManager(EnhancedJavaPlugin plugin) {
        super(plugin);
    }

    public Menu getMenu(String name) {
        return menusByName.get(name);
    }

    public void registerMenu(String name, Menu menu) {
        menusByName.put(name, menu);
    }

    public void unregisterMenu(String name) {
        menusByName.remove(name);
    }

    @Override
    public void disable() {
        menusByName.clear();
    }

    @Override
    public void loadData() {
        plugin.getPluginMenus().forEach(this::registerMenu);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player) {
            final PlayerState playerState = plugin.getPlayerState(player);
            final MenuView openMenu = playerState.getOpenMenu();
            if (openMenu != null) {
                openMenu.click(event);
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        final PlayerState player = plugin.getPlayerState((Player) event.getPlayer());
        if (player.isAwaitingInput(InputType.MENU)) {
            player.setAwaitingInput(null);
        }
        final MenuView view = player.getOpenMenu();
        if (view != null) {
            view.getMenu().onClose(view);
        }
        player.setOpenView(null);
    }

}
