package io.github.pigaut.voxel.plugin.manager;

import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.util.*;
import io.github.pigaut.yaml.node.*;
import io.github.pigaut.yaml.parser.*;
import io.github.pigaut.yaml.util.*;
import org.jetbrains.annotations.*;

import java.io.*;
import java.util.*;
import java.util.function.*;
import java.util.logging.*;

/**
 * Represents an abstract manager class that provides lifecycle methods
 * for enabling, disabling, and reloading functionality.
 * This class is intended to be extended by specific managers within the plugin.
 */
public abstract class Manager {

    protected final EnhancedJavaPlugin plugin;
    protected final Logger logger;

    /**
     * Constructs a new Manager.
     *
     * @param plugin the plugin instance associated with this manager
     */
    protected Manager(EnhancedJavaPlugin plugin) {
        this.plugin = plugin;
        this.logger = plugin.getLogger();
    }

    /**
     * Enables the manager.
     * This method can be overridden to include initialization logic.
     */
    public void enable() {}

    /**
     * Disables the manager.
     * This method can be overridden to include cleanup logic.
     */
    public void disable() {}

    /**
     * Convenience method to reload the manager without collecting errors.
     *
     * Internally calls {@link #reload(Consumer)} with a no-op error collector.
     */
    public void reload() {
        reload(errorsFound -> plugin.logConfigurationErrors(null, errorsFound));
    }

    /**
     * Reloads the manager by first disabling and then enabling it.
     *
     * After that, it performs data saving and loading operations asynchronously.
     * Any configuration errors encountered during loading are collected and
     * passed to the provided errorCollector callback.
     *
     * @param errorCollector a consumer that accepts a list of ConfigurationExceptions
     *                       encountered during the reload process; can be used to
     *                       handle or log errors after reload completes
     */
    public void reload(Consumer<List<ConfigurationException>> errorCollector) {
        plugin.getLogger().info("Reloading " + StringFormatter.toTitleCase(getClass()) + "...");
        disable();
        enable();
        plugin.getScheduler().runTaskAsync(() -> {
            saveData();
            final List<ConfigurationException> errorsFound = new ArrayList<>();
            try {
                loadData();
            } catch (ConfigurationException e) {
                errorsFound.add(e);
            }
            final String filesDirectory = getFilesDirectory();
            if (filesDirectory != null) {
                for (File file : plugin.getFiles(filesDirectory)) {
                    try {
                        loadFile(file);
                    } catch (ConfigurationException e) {
                        errorsFound.add(e);
                    }
                }
            }
            errorCollector.accept(errorsFound);
        });
    }

    /**
     * Loads necessary data for the manager.
     * <p>
     * This method may be executed asynchronously when called by {@link #reload()}.
     * It is safe to schedule plugin tasks within this method.
     * </p>
     */
    public void loadData() { }

    public @Nullable String getFilesDirectory() {
        return null;
    }

    public void loadFile(@NotNull File file) {

    }

    /**
     * Saves data related to the manager.
     * <p>
     * This method may be executed asynchronously when called by {@link #reload()}.
     * However, it is also called during server shutdown when the plugin is disabled.
     * </p>
     * <p>
     * <b>Warning:</b> do not schedule tasks inside this method, as task scheduling is not possible
     * once the plugin is disabled. If you need to sync method calls use {@link #disable()}.
     * </p>
     */
    public void saveData() {}

    /**
     * Checks whether automatic saving is enabled for this manager.
     *
     * @return {@code true} if auto-save is enabled, otherwise {@code false}
     */
    public boolean isAutoSave() { return false; }

}
