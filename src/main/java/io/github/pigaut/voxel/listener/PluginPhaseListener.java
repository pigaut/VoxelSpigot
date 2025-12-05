package io.github.pigaut.voxel.listener;

import io.github.pigaut.voxel.plugin.boot.*;
import io.github.pigaut.voxel.plugin.boot.phase.*;
import org.bukkit.event.*;
import org.bukkit.event.server.*;

public class PluginPhaseListener implements Listener {

    private final EnhancedJavaPlugin plugin;

    public PluginPhaseListener(EnhancedJavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEnable(PluginEnableEvent event) {
        PluginBootstrap bootstrap = plugin.getBootstrap();
        if (bootstrap.isMissingStartupRequirements()) {
            String pluginName = event.getPlugin().getName();
            plugin.getBootstrap().markReady(BootPhase.pluginEnabled(pluginName));
        }
    }

    @EventHandler
    public void onDisable(PluginDisableEvent event) {
        PluginBootstrap bootstrap = plugin.getBootstrap();
        if (bootstrap.isMissingStartupRequirements()) {
            String pluginName = event.getPlugin().getName();
            plugin.getBootstrap().markReady(BootPhase.pluginAll(pluginName));
        }
    }

}
