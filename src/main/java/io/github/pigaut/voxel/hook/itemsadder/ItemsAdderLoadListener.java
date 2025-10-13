package io.github.pigaut.voxel.hook.itemsadder;

import dev.lone.itemsadder.api.Events.*;
import io.github.pigaut.voxel.plugin.*;
import org.bukkit.*;
import org.bukkit.event.*;

public class ItemsAdderLoadListener implements Listener {

    private final EnhancedPlugin plugin;

    public ItemsAdderLoadListener(EnhancedPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLoad(ItemsAdderLoadDataEvent event) {
        if (event.getCause() == ItemsAdderLoadDataEvent.Cause.FIRST_LOAD) {
            plugin.getInitializer().setLoaded("ItemsAdder");
        }
    }

}
