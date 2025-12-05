package io.github.pigaut.voxel.listener;

import io.github.pigaut.voxel.plugin.boot.*;
import io.github.pigaut.voxel.plugin.boot.phase.*;
import io.github.pigaut.voxel.server.*;
import io.github.pigaut.voxel.util.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.util.*;
import org.bukkit.event.*;
import org.bukkit.event.server.*;
import org.bukkit.event.world.*;

import java.util.*;

public class ServerPhaseListener implements Listener {

    private final EnhancedJavaPlugin plugin;
    private final ColoredLogger logger;

    private final List<String> worldsToBeLoaded = SpigotServer.getWorldFolderNames();

    public ServerPhaseListener(EnhancedJavaPlugin plugin) {
        this.plugin = plugin;
        this.logger = plugin.getColoredLogger();
    }

    @EventHandler
    public void onServerLoad(ServerLoadEvent event) {
        if (event.getType() != ServerLoadEvent.LoadType.STARTUP) {
            return;
        }

        PluginBootstrap initializer = plugin.getBootstrap();
        if (!initializer.isMissingStartupRequirements()) {
            return;
        }

        initializer.markReady(BootPhase.SERVER_LOADED);

        if (!worldsToBeLoaded.isEmpty()) {
            Ticks worldLoadTimeout = plugin.getSettings().getWorldLoadTimeout();
            logger.info("Delaying plugin startup: some worlds are still being loaded by other plugins. (Max wait: " + worldLoadTimeout.toSeconds() + "s)");
            plugin.getScheduler().runTaskLater(worldLoadTimeout.getCount(), () -> {
                if (worldsToBeLoaded.isEmpty()) {
                    return;
                }
                logger.warning("The following worlds took too long to load: " + String.join(", ", worldsToBeLoaded) + ". Proceeding without them.");
                initializer.markReady(BootPhase.WORLDS_LOADED);
            });
        }
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        PluginBootstrap initializer = plugin.getBootstrap();
        if (!initializer.isMissingStartupRequirements()) {
            return;
        }

        worldsToBeLoaded.remove(event.getWorld().getName());
        if (worldsToBeLoaded.isEmpty()) {
            initializer.markReady(BootPhase.WORLDS_LOADED);
        }
    }

}
