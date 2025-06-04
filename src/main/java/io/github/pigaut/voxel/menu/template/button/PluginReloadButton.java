package io.github.pigaut.voxel.menu.template.button;

import io.github.pigaut.voxel.menu.*;
import io.github.pigaut.voxel.menu.button.*;
import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.util.*;
import io.github.pigaut.yaml.util.*;
import org.bukkit.*;
import org.bukkit.event.inventory.*;

public class PluginReloadButton extends Button {

    private final EnhancedJavaPlugin plugin;

    public PluginReloadButton(EnhancedJavaPlugin plugin) {
        super(IconBuilder.of(Material.OAK_BUTTON)
                .withDisplay("&f&lReload")
                .addLore("")
                .addLore("&eLeft-Click: &fReload the plugin")
                .enchanted(true)
                .buildIcon());
        this.plugin = plugin;
    }

    @Override
    public void onLeftClick(MenuView view, InventoryClickEvent event) {
        view.close();
        try {
            plugin.reload(errorsFound -> {
                final PlayerState player = view.getViewer();
                plugin.logConfigurationErrors(player.asPlayer(), errorsFound);
                if (errorsFound.isEmpty()) {
                    player.setOpenView(view);
                }
            });
        } catch (PluginReloadInProgressException ignored) {}
    }

}
