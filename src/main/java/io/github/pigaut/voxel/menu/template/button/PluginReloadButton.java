package io.github.pigaut.voxel.menu.template.button;

import io.github.pigaut.voxel.*;
import io.github.pigaut.voxel.config.*;
import io.github.pigaut.voxel.menu.*;
import io.github.pigaut.voxel.menu.button.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.boot.*;
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
    public void onLeftClick(MenuView view, PlayerState playerState) {
        view.close();
        Player player = playerState.asPlayer();
        try {
            plugin.reload(errors -> {
                if (errors.isEmpty()) {
                    plugin.sendMessage(player, "reload-completed");
                    plugin.getScheduler().runTask(view::open);
                }
                else {
                    ConfigErrorLogger.logAll(plugin, player, errors);
                }
            });
        } catch (PluginReloadInProgressException e) {
            plugin.sendMessage(player, "already-reloading");
            return;
        }
        plugin.sendMessage(player, "reloading");
    }

}
