package io.github.pigaut.voxel.menu.template.button;

import io.github.pigaut.voxel.menu.*;
import io.github.pigaut.voxel.menu.button.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.inventory.*;

public class PluginReloadButton extends Button {

    private final EnhancedJavaPlugin plugin;

    public PluginReloadButton(EnhancedJavaPlugin plugin) {
        super(IconBuilder.of(Material.OAK_BUTTON)
                .withDisplay("&aReload")
                .enchanted(true)
                .buildIcon());
        this.plugin = plugin;
    }

    @Override
    public void onLeftClick(MenuView view, PlayerState playerState, InventoryClickEvent event) {
        view.close();
        final Player player = playerState.asPlayer();
        try {
            plugin.reload(errorsFound -> {
                plugin.logConfigurationErrors(player, errorsFound);
                plugin.sendMessage(player, "reload-complete");
                if (errorsFound.isEmpty()) {
                    plugin.getScheduler().runTask(view::open);
                }
            });
        } catch (PluginReloadInProgressException e) {
            plugin.sendMessage(player, "already-reloading");
            return;
        }
        plugin.sendMessage(player, "reloading");
    }

}
