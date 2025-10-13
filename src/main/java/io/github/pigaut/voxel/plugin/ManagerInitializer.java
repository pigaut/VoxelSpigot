package io.github.pigaut.voxel.plugin;

import io.github.pigaut.sql.*;
import io.github.pigaut.voxel.config.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.voxel.server.*;
import io.github.pigaut.voxel.util.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.util.*;
import org.bukkit.event.*;

import java.util.*;
import java.util.concurrent.*;

public class ManagerInitializer {

    private final EnhancedJavaPlugin plugin;
    private final ColoredLogger logger;

    private final List<Manager> loadedManagers = new ArrayList<>();
    private final List<DelayedLoader> pendingManagers = new CopyOnWriteArrayList<>();

    public ManagerInitializer(EnhancedJavaPlugin plugin) {
        this.plugin = plugin;
        this.logger = plugin.getColoredLogger();
    }

    public List<Manager> getLoadedManagers() {
        return loadedManagers;
    }

    public void initialize() {
        logger.info("Loading configuration and data...");

        List<ConfigurationException> configErrors = new ArrayList<>();
        for (Manager manager : plugin.getAllManagers()) {
            if (!manager.getLoadAfter().isEmpty()) {
                pendingManagers.add(new DelayedLoader(manager));
                continue;
            }

            manager.enable();
            manager.loadData();
            if (manager instanceof ConfigBacked configBacked) {
                configErrors.addAll(configBacked.loadConfigurationData());
            }

            if (manager instanceof Listener listener) {
                plugin.registerListener(listener);
            }

            loadedManagers.add(manager);
        }
        ConfigErrorLogger.logAll(plugin, configErrors);

        int autosave = plugin.getConfiguration().get("auto-save", Ticks.class).map(Ticks::getCount).orElse(0);
        if (autosave > 0) {
            plugin.getScheduler().runTaskTimerAsync(autosave, () -> {
                logger.info("Saving plugin data to database...");
                for (Manager manager : loadedManagers) {
                    if (manager.isAutoSave()) {
                        manager.saveData();
                    }
                }
                logger.info("All plugin data saved successfully.");
            });
        }
    }

    public void disable() {
        logger.info("Saving data to database...");
        for (Manager manager : loadedManagers) {
            manager.disable();
            manager.saveData();
        }

        loadedManagers.clear();
        pendingManagers.clear();

        Database database = plugin.getDatabase();
        if (database != null) {
            database.closeConnection();
        }
    }

    public synchronized void setLoaded(String flag) {
        for (DelayedLoader delayedLoader : pendingManagers) {
            delayedLoader.update(flag);
        }
    }

    private class DelayedLoader {
        private final Manager manager;
        private final List<String> loadAfter;

        private DelayedLoader(Manager manager) {
            this.manager = manager;
            this.loadAfter = new CopyOnWriteArrayList<>(manager.getLoadAfter());
            for (String pluginName : loadAfter) {
                if (!SpigotServer.isPluginEnabled(pluginName)) {
                    update(pluginName);
                }
            }
        }

        public void update(String loaded) {
            loadAfter.remove(loaded);

            if (loadAfter.isEmpty()) {
                manager.enable();
                manager.loadData();
                if (manager instanceof ConfigBacked configBackedManager) {
                    ConfigErrorLogger.logAll(plugin, configBackedManager.loadConfigurationData());
                }
                pendingManagers.remove(this);
                loadedManagers.add(manager);
            }
        }
    }

}
