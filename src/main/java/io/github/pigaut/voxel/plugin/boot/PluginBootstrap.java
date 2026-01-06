package io.github.pigaut.voxel.plugin.boot;

import io.github.pigaut.sql.*;
import io.github.pigaut.voxel.*;
import io.github.pigaut.voxel.command.*;
import io.github.pigaut.voxel.config.*;
import io.github.pigaut.voxel.core.language.*;
import io.github.pigaut.voxel.hook.itemsadder.*;
import io.github.pigaut.voxel.listener.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.PluginLogger;
import io.github.pigaut.voxel.plugin.boot.phase.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.voxel.server.*;
import io.github.pigaut.voxel.util.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.*;
import io.github.pigaut.yaml.node.section.*;
import io.github.pigaut.yaml.util.*;
import org.bukkit.plugin.*;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.function.*;

public class PluginBootstrap {

    private final EnhancedJavaPlugin plugin;
    private final PluginLogger logger;

    private final Set<BootPhase> missingStartupRequirements = new HashSet<>();
    private final List<StartupTask> startupTasks = new ArrayList<>();

    private final List<ConfigurationException> startupErrors = new ArrayList<>();

    private final List<Manager> loadedManagers = new ArrayList<>();
    private Configurator configurator;
    private RootSection config;
    private @Nullable UpdateChecker updateChecker = null;
    private @Nullable PluginMetrics metrics = null;
    private @Nullable Database database = null;

    private boolean initialized = false;
    private boolean reloading = false;

    public PluginBootstrap(EnhancedJavaPlugin plugin) {
        this.plugin = plugin;
        this.logger = plugin.getColoredLogger();
    }

    public void init() {
        Preconditions.checkState(!initialized, "Plugin initializer has already been initialized.");
        initialized = true;

        // Generate files and load configuration
        PluginSetup.generateDirectoriesAndFiles(plugin);
        configurator = plugin.createConfigurator();
        config = PluginSetup.createConfiguration(plugin);
        PluginSetup.loadConfiguration(plugin)
                .ifPresent(startupErrors::add);

        // Load config.yml settings
        Settings settings = plugin.getSettings();
        startupErrors.addAll(settings.loadConfigurationData());

        // Load language file messages
        LanguageDictionary dictionary = plugin.getDictionary();
        startupErrors.addAll(dictionary.loadConfigurationData());

        // Initialize command registry and register commands
        CommandRegistry commandRegistry = plugin.getCommands();
        commandRegistry.init();
        plugin.getDefaultCommands().forEach(commandRegistry::registerCommand);

        // Register listeners
        plugin.registerListener(new PluginPhaseListener(plugin));
        plugin.registerListener(new ServerPhaseListener(plugin));
        if (SpigotServer.isPluginLoaded("ItemsAdder")) {
            plugin.registerListener(new ItemsAdderPhaseListener(plugin));
        }
        plugin.registerListener(new PlayerJoinQuitListener(plugin));
        plugin.registerListener(new PlayerInputListener(plugin));
        plugin.registerListener(new PlayerInventoryListener(plugin));
        plugin.registerListener(new StructureWandListener(plugin));
        plugin.getDefaultListeners().forEach(plugin::registerListener);

        // Register the startup tasks
        for (StartupTask startupTask : plugin.getStartupTasks()) {
            startupTasks.add(startupTask);
            startupTask.init();
        }

        // Register and check already met startup requirements
        List<BootPhase> startupRequirements = new ArrayList<>(plugin.getStartupRequirements());
        plugin.getCompatiblePlugins().stream()
                .map(BootPhase::pluginEnabled)
                .forEach(startupRequirements::add);

        for (BootPhase bootPhase : startupRequirements) {
            if (bootPhase instanceof PluginBootPhase) {
                Plugin plugin = SpigotServer.getPlugin(bootPhase.getNamespace());
                if (plugin == null) {
                    continue;
                }

                String pluginPhase = bootPhase.getKey();
                if (pluginPhase == null || (pluginPhase.equals("enabled") && plugin.isEnabled())) {
                    continue;
                }
            }

            missingStartupRequirements.add(bootPhase);
        }

        if (missingStartupRequirements.isEmpty()) {
            startup();
        }
    }

    public void markReady(@NotNull BootPhase bootPhase) {
        for (StartupTask delayedTask : startupTasks) {
            delayedTask.markReady(bootPhase);
        }

        missingStartupRequirements.remove(bootPhase);
        if (missingStartupRequirements.isEmpty()) {
            startup();
        }
    }

    public void startup() {
        Preconditions.checkState(missingStartupRequirements.isEmpty(), "Cannot startup plugin because not all startup requirements are met.");

        PluginSetup.dumpLogo(plugin);
        PluginSetup.checkServerVersion(plugin);
        PluginSetup.checkCompatiblePlugins(plugin);
        PluginSetup.generateExampleFiles(plugin);
        metrics = PluginSetup.createMetrics(plugin);
        updateChecker = PluginSetup.createUpdateChecker(plugin);
        configurator = plugin.createConfigurator();
        database = PluginSetup.createDatabase(plugin);
        plugin.registerHooks();

        logger.info("Loading configuration and data...");
        List<Manager> pluginManagers = plugin.getAllManagers();
        for (Manager manager : pluginManagers) {
            manager.enable();
        }

        plugin.getScheduler().runTaskAsync(() -> {
            for (Manager manager : pluginManagers) {
                manager.loadData();
                if (manager instanceof ConfigBacked configBackedManager) {
                    startupErrors.addAll(configBackedManager.loadConfigurationData());
                }
                loadedManagers.add(manager);
            }

            if (startupErrors.isEmpty()) {
                logger.info("Startup completed successfully.");
            }
            else {
                logger.info("Startup completed with the following errors:");
                ConfigErrorLogger.logAll(plugin, startupErrors);
                startupErrors.clear();
            }

            int autoSave = plugin.getSettings().getAutoSave().getCount();
            if (autoSave > 0) {
                plugin.getScheduler().runTaskTimerAsync(autoSave, () -> {
                    logger.info("Saving data to database...");
                    for (Manager manager : loadedManagers) {
                        manager.saveData();
                    }
                });
            }
        });
    }

    public void shutdown() {
        missingStartupRequirements.clear();

        logger.info("Saving data to database...");
        for (Manager manager : loadedManagers) {
            manager.disable();
            manager.saveData();
        }

        loadedManagers.clear();

        Database database = plugin.getDatabase();
        if (database != null) {
            database.closeConnection();
        }
    }

    public void reload(@NotNull Consumer<List<ConfigurationException>> errorCollector) throws PluginReloadInProgressException {
        if (reloading) {
            throw new PluginReloadInProgressException();
        }
        reloading = true;

        List<ConfigurationException> errors = new ArrayList<>();

        // Generate files and load configuration
        PluginSetup.generateDirectoriesAndFiles(plugin);
        configurator = plugin.createConfigurator();
        config = PluginSetup.createConfiguration(plugin);
        PluginSetup.loadConfiguration(plugin)
                .ifPresent(errors::add);

        // Load config.yml settings
        Settings settings = plugin.getSettings();
        errors.addAll(settings.loadConfigurationData());

        // Load language file messages
        LanguageDictionary dictionary = plugin.getDictionary();
        errors.addAll(dictionary.loadConfigurationData());

        PluginSetup.dumpLogo(plugin);
        PluginSetup.checkCompatiblePlugins(plugin);
        PluginSetup.generateExampleFiles(plugin);
        metrics = PluginSetup.createMetrics(plugin);
        updateChecker = PluginSetup.createUpdateChecker(plugin);

        for (Manager manager : loadedManagers) {
            manager.disable();
            manager.enable();
        }

        plugin.getScheduler().runTaskLaterAsync(1, () -> {
            logger.info("Saving data to database...");
            loadedManagers.forEach(Manager::saveData);

            logger.info("Loading configuration and data...");
            for (Manager manager : loadedManagers) {
                manager.loadData();
                if (manager instanceof ConfigBacked configBackedManager) {
                    errors.addAll(configBackedManager.loadConfigurationData());
                }
            }
            errorCollector.accept(errors);
            reloading = false;
        });
    }

    public boolean isInitialized() {
        return initialized;
    }

    public boolean isReloading() {
        return reloading;
    }

    public boolean isMissingStartupRequirements() {
        return !missingStartupRequirements.isEmpty();
    }

    public List<BootPhase> getMissingStartupRequirements() {
        return new ArrayList<>(missingStartupRequirements);
    }

    public Configurator getConfigurator() {
        Preconditions.checkState(configurator != null, "Configurator has not been initialized yet.");
        return configurator;
    }

    public RootSection getConfiguration() {
        Preconditions.checkState(config != null, "Configuration has not been initialized yet.");
        return config;
    }

    public @Nullable UpdateChecker getUpdateChecker() {
        return updateChecker;
    }

    public @Nullable PluginMetrics getMetrics() {
        return metrics;
    }

    public @Nullable Database getDatabase() {
        return database;
    }

    public List<Manager> getLoadedManagers() {
        return new ArrayList<>(loadedManagers);
    }

}
